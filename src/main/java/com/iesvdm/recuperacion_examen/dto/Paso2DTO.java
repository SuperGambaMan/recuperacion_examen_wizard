package com.iesvdm.recuperacion_examen.dto;

import jakarta.validation.constraints.*;
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
public class Paso2DTO {

    @NotEmpty(message = "El titulo no puede estar vacio")
    private String tituloProyecto;

    private BigDecimal importeTotalProyecto;

    private BigDecimal importeAyudaSolicitada;

    @NotNull(message = "Tienes que rellenar la Fecha")
    @FutureOrPresent(message = "La fecha de inicio debe ser posterior a la fecha actual")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaInicioPrevista;

    @NotNull(message = "Tienes que rellenar la duración")
    @Min(value=1, message = "La duración estimada debe ser mayor que 0")
    private Integer duracionMeses;

}
