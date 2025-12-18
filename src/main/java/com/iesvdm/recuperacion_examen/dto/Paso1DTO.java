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
public class Paso1DTO {

    @NotNull(message="Debes seleccionar un Programa Europeo")
    private Long programaId;

    private String entidadSolicitante;

    /*^([ABCDEFGHJKLMNPQSV])(\d{7})([0-9A-J])$|^(\d{8})([0-9A-J])$|^([XYZ])(\d{7})([0-9A-J])$

    Explicación:
            ^...$: Asegura que la cadena completa coincida.
            ([ABCDEFGHJKLMNPQSV]): Para NIF/NIE, la primera letra (Persona Física, Extranjero).
            (\d{7}): Siete dígitos.
            ([0-9A-J]): El dígito de control (número o letra).
            |(\d{8})([0-9A-J]): Para antiguos CIF (8 dígitos + control).
            |([XYZ])(\d{7})([0-9A-J]): Para NIE (X, Y, Z + 7 dígitos + control).*/

    @Pattern(regexp = "^([ABCDEFGHJKLMNPQSV])(\\d{7})([0-9A-J])$|^(\\d{8})([0-9A-J])$|^([XYZ])(\\d{7})([0-9A-J])$",
            message = "El CIF/NIF no tiene un formato válido")
    @NotBlank(message = "No puedes dejar vacío el CIF")
    private String cifNif;

    @Size(min=3, message = "Tiene que tener como mínimo 3 caracteres")
    @NotBlank(message = "No puedes dejar vacío el Pais")
    private String pais;

    @Size(min=3, message = "Tiene que tener como mínimo 3 caracteres")
    @NotBlank(message = "No puedes dejar vacío la Region")
    private String region;

    private String personaContacto;

    @Email(message = "No es un formato de email correcto")
    @NotBlank(message = "No puedes dejar vacío el email de contacto")
    private String emailContacto;

}
