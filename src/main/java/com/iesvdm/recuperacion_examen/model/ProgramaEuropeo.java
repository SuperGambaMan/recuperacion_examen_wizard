package com.iesvdm.recuperacion_examen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramaEuropeo {

    Long id;

    String codigo;

    String nombre;

    String descripcion;

}
