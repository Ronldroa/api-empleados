package com.apijava.persona.service;

import com.apijava.persona.dto.EmpleadoDto;
import com.apijava.persona.model.Empleado;
import com.apijava.persona.repository.EmpleadoRepositorio;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoService.class);
    EmpleadoRepositorio repositorio;
    @Autowired
    public EmpleadoService(EmpleadoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    //-------------------POST----------------------------
    private Empleado convertirDtoAEntity(EmpleadoDto dto) {
        Empleado entity = new Empleado();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEdad(dto.getEdad());
        entity.setSueldo(dto.getSueldo());
        entity.setCorreo(dto.getCorreo());
        entity.setPais(dto.getPais());
        return entity;
    }

    private Optional<Empleado> estaRegistradoElCorreo(String correo) {
        return repositorio.findByCorreo(correo);
    }

    public void registrar(EmpleadoDto request) {
        Empleado entity = convertirDtoAEntity(request);
        this.verificarSiElCorreoEstaRegistrado(request.getCorreo(), entity);
    }

    private void verificarSiElCorreoEstaRegistrado(String correo, Empleado entity) {
        Optional<Empleado> empleadoOptional = estaRegistradoElCorreo(correo);
        if (empleadoOptional.isEmpty()) {
            repositorio.save(entity);
        } else {
            LOG.error("El correo: {} ya se encuentra registrado", correo);
            throw new EntityExistsException("El correo " + correo + " ya esta registrado:");
        }
    }

    //-------------------GET---------------------------
    private EmpleadoDto convertirEntityADto(Empleado entity) {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEdad(entity.getEdad());
        dto.setSueldo(entity.getSueldo());
        dto.setCorreo(entity.getCorreo());
        dto.setPais(entity.getPais());
        return dto;
    }

    //Metodo para listar por Pagina
    public Page<EmpleadoDto> listarTodos(Pageable pageable) {
        Page<Empleado> entities = repositorio.findAll(pageable);
        List<EmpleadoDto> dtos = entities.getContent().stream().map(this::convertirEntityADto).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    //Metodo para listar todos
    public List<EmpleadoDto> listarTodos() {
        List<EmpleadoDto> dtos = new ArrayList<>();
        repositorio.findAll().forEach(empleado -> dtos.add(this.convertirEntityADto(empleado)));

        return dtos;
    }

    //Metodo para listar por id
    private Empleado buscoOIdentificoElEmpleadoEnBaseDeDatos(Integer idEmpleadoABuscar) {
        Optional<Empleado> empleadoOptional = repositorio.findById(idEmpleadoABuscar);
        if (empleadoOptional.isEmpty()) {
            LOG.error("El empleado con id {} no existe", idEmpleadoABuscar);
            throw new EntityNotFoundException("El empleado con id " + idEmpleadoABuscar + " no existe");
        }

        return empleadoOptional.get();
    }

    public EmpleadoDto buscaUnEmpleado(Integer idEmpleadoABuscar) {
        Empleado empleado = buscoOIdentificoElEmpleadoEnBaseDeDatos(idEmpleadoABuscar);
        return this.convertirEntityADto(empleado);
    }

    //-------------------PUT---------------------------
    public void modificarUnCliente(Integer idEmpleadoAModificar, EmpleadoDto request) {
        Empleado entity = this.buscoOIdentificoElEmpleadoEnBaseDeDatos(idEmpleadoAModificar);
        //suponemos que no se puede modificar el correo, solo nombre y apellido
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setPais(request.getPais());
        this.verificarSiElCorreoEstaRegistrado(request.getCorreo(), entity);

    }

    //-------------------DELETE---------------------------
      public void eliminar(Integer idEmpleadoAEliminar) {
        Empleado entity = this.buscoOIdentificoElEmpleadoEnBaseDeDatos(idEmpleadoAEliminar);
        repositorio.delete(entity);
    }

    //-------------------solamente se acepta 3 registro por pais---------------------------
    public long cuentaPorPais(String pais) {
        return repositorio.countByPais(pais);

    }
}
