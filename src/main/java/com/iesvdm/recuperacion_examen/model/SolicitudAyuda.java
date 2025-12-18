package com.iesvdm.recuperacion_examen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAyuda {

    Long id;
    Long programa_id;
    String entidadSolicitante;
    String cifNif;
    String pais;
    String region;
    String personaContacto;
    String emailContacto;
    String tituloProyecto;
    BigDecimal importeTotalProyecto;
    BigDecimal importeAyudaSolicitada;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime fechaInicioPrevista;
    Integer duracionMeses;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime fechaCreacion;
    String estado;

}
