package com.iesvdm.recuperacion_examen.service;

import com.iesvdm.recuperacion_examen.model.ProgramaEuropeo;
import com.iesvdm.recuperacion_examen.model.SolicitudAyuda;
import com.iesvdm.recuperacion_examen.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;


    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    //programa_europeo
    public List<ProgramaEuropeo> listarProgramas(){
        return solicitudRepository.findAllProgramas();
    }

    public  ProgramaEuropeo buscarProgramaId(Long id){
        return solicitudRepository.findProgramaById(id);
    }

    //solicitud_ayuda
    public List<SolicitudAyuda> listarSolicitudes(){
        return solicitudRepository.findAllSolicitudes();
    }

    public  SolicitudAyuda buscarSolicitudId(Long id){
        return solicitudRepository.findSolicitudById(id);
    }

   //Crear la solicitud
    public void crearSolicitud (SolicitudAyuda solicitudAyuda){
        solicitudRepository.crearSol(solicitudAyuda);
    }

}
