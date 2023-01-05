package com.apijava.persona.controller;

import com.apijava.persona.dto.EmpleadoDto;
import com.apijava.persona.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("empleados")
@AllArgsConstructor
public class EmpleadoController {

    @Autowired
    EmpleadoService service;

    @PostMapping()
    public ResponseEntity<Void> crear(@Valid @RequestBody EmpleadoDto request) {
        service.registrar(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<Page<EmpleadoDto>> listar(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.listarTodos(pageable), headers, HttpStatus.OK);

    }

    @GetMapping("/listado-simple")
    public ResponseEntity<List<EmpleadoDto>> listarSimple() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.listarTodos(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id-cliente}")
    public ResponseEntity<EmpleadoDto> buscarUnCliente(@PathVariable("id-cliente") Integer idEmpleadoABuscar) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.buscaUnEmpleado(idEmpleadoABuscar), headers, HttpStatus.OK);
    }

    @PutMapping("/{id-cliente}")
    public ResponseEntity<EmpleadoDto> modificarUnCliente(@PathVariable("id-cliente") Integer idEmpleadoModificar,
                                                          @RequestBody EmpleadoDto request) {
        this.service.modificarUnCliente(idEmpleadoModificar, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id-cliente}")
    ResponseEntity<Void> eliminarUnCliente(@PathVariable("id-cliente") Integer idEmpleadoEliminar) {
        service.eliminar(idEmpleadoEliminar);
        return ResponseEntity.noContent().build();
    }

}
