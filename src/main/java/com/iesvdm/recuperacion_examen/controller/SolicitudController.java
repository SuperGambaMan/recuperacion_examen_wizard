package com.iesvdm.recuperacion_examen.controller;

import com.iesvdm.recuperacion_examen.dto.Paso1DTO;
import com.iesvdm.recuperacion_examen.dto.Paso2DTO;
import com.iesvdm.recuperacion_examen.model.ProgramaEuropeo;
import com.iesvdm.recuperacion_examen.model.SolicitudAyuda;
import com.iesvdm.recuperacion_examen.repository.SolicitudRepository;
import com.iesvdm.recuperacion_examen.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes("solicitud")
@RequestMapping("/fondos")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final SolicitudRepository solicitudRepository;

    public SolicitudController(SolicitudService solicitudService, SolicitudRepository solicitudRepository) {
        this.solicitudService = solicitudService;
        this.solicitudRepository = solicitudRepository;
    }

    @ModelAttribute("solicitud")
    public SolicitudAyuda solicitudAyuda(){
        return new SolicitudAyuda();
    }

    @GetMapping("/paso1")
    public String paso1Get (Model model,@ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda){

        List<ProgramaEuropeo> programas = solicitudService.listarProgramas();

        //Creamos un DTO para las validaciones
        Paso1DTO dto = new Paso1DTO();
        dto.setProgramaId(solicitudAyuda.getPrograma_id());
        dto.setEntidadSolicitante(solicitudAyuda.getEntidadSolicitante());
        dto.setCifNif(solicitudAyuda.getCifNif());
        dto.setPais(solicitudAyuda.getPais());
        dto.setRegion(solicitudAyuda.getRegion());
        dto.setPersonaContacto(solicitudAyuda.getPersonaContacto());
        dto.setEmailContacto(solicitudAyuda.getEmailContacto());

        //programas para listar los programas de la base de datos
        model.addAttribute("programas",programas);
        //Le pasamos el dto para la persistencia y validacion
        model.addAttribute("paso1DTO", dto);
        return "paso1";
    }

    @PostMapping("/paso1")
    public String paso1Post (Model model, @ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda,
                             @Valid @ModelAttribute("paso1DTO") Paso1DTO paso1DTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ProgramaEuropeo> programas = solicitudService.listarProgramas();
            //Estos "programas" es para seguir mantiendo la información del campo de programas europes, tras un fallo de formulario
            model.addAttribute("programas", programas);
            return "paso1";
        }
        //introducimos los datos del formulario para mantenerlos en sesión
        solicitudAyuda.setPrograma_id(paso1DTO.getProgramaId());
        solicitudAyuda.setEntidadSolicitante(paso1DTO.getEntidadSolicitante());
        solicitudAyuda.setCifNif(paso1DTO.getCifNif());
        solicitudAyuda.setPais(paso1DTO.getPais());
        solicitudAyuda.setRegion(paso1DTO.getRegion());
        solicitudAyuda.setPersonaContacto(paso1DTO.getPersonaContacto());
        solicitudAyuda.setEmailContacto(paso1DTO.getEmailContacto());
        solicitudAyuda.setFechaCreacion(LocalDateTime.now());
        solicitudAyuda.setEstado("BORRADOR");
        //guardamos en sesión para mostrar en paso2
        return "redirect:/fondos/paso2";
    }

    @GetMapping("/paso2")
    public String paso2Get (Model model, @ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda, @ModelAttribute("Paso1DTO") Paso1DTO paso1DTO){

        //Solicitud para coger la sesion del paso1
        model.addAttribute("solicitud", solicitudAyuda);

        //Creamos un DTO para las validaciones
        Paso2DTO dto = new Paso2DTO();
        dto.setTituloProyecto(solicitudAyuda.getTituloProyecto());
        dto.setImporteTotalProyecto(solicitudAyuda.getImporteTotalProyecto());
        dto.setImporteAyudaSolicitada(solicitudAyuda.getImporteAyudaSolicitada());
        dto.setFechaInicioPrevista(solicitudAyuda.getFechaInicioPrevista());
        dto.setDuracionMeses(solicitudAyuda.getDuracionMeses());
        //Le pasamos el dto para la persistencia y validacion
        model.addAttribute("paso2DTO",dto);

        return "paso2";
    }

    @PostMapping("/paso2")
    public String paso2Post (Model model, @ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda,
                             @Valid @ModelAttribute("paso2DTO") Paso2DTO paso2DTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //SolicitudAyuda solicitud = solicitudService.buscarSolicitudId(solicitudAyuda.getId());
            //Solicitud para coger la cuenta de la base de datos con un ID y seguir manteniendola
            model.addAttribute("solicitud", solicitudAyuda);
            return "paso2";
        }
        solicitudAyuda.setTituloProyecto(paso2DTO.getTituloProyecto());
        solicitudAyuda.setImporteTotalProyecto(paso2DTO.getImporteTotalProyecto());
        solicitudAyuda.setImporteAyudaSolicitada(paso2DTO.getImporteAyudaSolicitada());
        solicitudAyuda.setFechaInicioPrevista(paso2DTO.getFechaInicioPrevista());
        solicitudAyuda.setDuracionMeses(paso2DTO.getDuracionMeses());
        solicitudAyuda.setFechaCreacion(LocalDateTime.now());
        solicitudAyuda.setEstado("BORRADOR");
        return "redirect:/fondos/final";
    }

    @GetMapping("/final")
    public String finalGet ( Model model, @ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda){

        ProgramaEuropeo programaEuropeo = solicitudService.buscarProgramaId(solicitudAyuda.getPrograma_id());
        model.addAttribute("programaEuropeo", programaEuropeo);
        model.addAttribute("solicitud",solicitudAyuda);

        return "final";
    }

    //Este PostMapping me sirve para limpiar la sesion, enlazandolo al boton de volver al paso 1 del html final
    @PostMapping("/final")
    public String volverPaso1(@ModelAttribute("solicitud") SolicitudAyuda solicitudAyuda, SessionStatus sessionStatus){
        //Creamos finalmente con todos los datos la nueva solicitud en la Base de Datos
        solicitudService.crearSolicitud(solicitudAyuda);
        //Limpiar la sesión para vaciar todos los datos
        sessionStatus.setComplete();
        //Redirigir al paso1 con to-do limpio
        return "redirect:/fondos/paso1";
    }
}
