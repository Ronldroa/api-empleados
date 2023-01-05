package com.apijava.persona.repository;

import com.apijava.persona.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByCorreo(String correo);

    long countByPais(String pais);
}
