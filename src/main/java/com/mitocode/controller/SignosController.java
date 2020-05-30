package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Signos;
import com.mitocode.service.ISignosService;


@RestController
@RequestMapping("/signos")
public class SignosController {
	
	@Autowired
	private ISignosService service;
	
	@GetMapping
	public ResponseEntity<List<Signos>> listar() throws Exception{
		List<Signos> lista = service.listar();
		return new ResponseEntity<List<Signos>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Signos> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Signos s = service.listarPorId(id);
		if(s == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Signos>(s, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Signos> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception{
		
		Signos sig = service.listarPorId(id);
		EntityModel<Signos> recurso = new EntityModel<Signos>(sig);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(linkTo.withRel("signos-recurso"));
		return recurso;
	}
	
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Signos signos) throws Exception{
		Signos s = service.registrar(signos);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getIdSignos()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Signos> modificar(@Valid @RequestBody Signos signos) throws Exception{
		Signos s = service.modificar(signos);
		return new ResponseEntity<Signos>(s, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) throws Exception {
		Signos s = service.listarPorId(id);
		if(s == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Signos>> listarPageable(Pageable pageable) throws Exception{
		Page<Signos> signos = service.listarPageable(pageable);
		return new ResponseEntity<Page<Signos>>(signos, HttpStatus.OK);
	}

}
