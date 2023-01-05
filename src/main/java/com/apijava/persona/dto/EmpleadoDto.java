package com.apijava.persona.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto {

    private Integer id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @DecimalMin(value = "18", inclusive = false, message = "Verifique que la edad ingresada sea mayor o igual que 18")
    private int edad;
    @DecimalMin(value = "100", inclusive = false, message = "Verifique que el sueldo sea mayor o igual que 100")
    private BigDecimal sueldo;
    @Email(message = "Compruebe si es el formato de email correcto")
    private String correo;
    @NotBlank
    private String pais;

}
