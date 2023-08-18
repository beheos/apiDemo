package com.beheos.apiDemo.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.beheos.apiDemo.dto.ComentarioDTO;
import com.beheos.apiDemo.servicio.ComentarioServicio;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioControlador {

	@Autowired
	ComentarioServicio comentarioServicio;
	
	@GetMapping("/publicaciones/{publicacionId}/comentarios")
	public List<ComentarioDTO>listarComentariosPorPublicacion(@PathVariable(value = "publicacionId")Long publicacionId){
		return comentarioServicio.obtenerCometarioPorPublicacion(publicacionId);
	}
	
	@GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value = "publicacionId")Long publicacionId,
			@PathVariable(value = "id")Long id){
		ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, id);
		return new ResponseEntity<>(comentarioDTO,HttpStatus.OK);
	}
	
	
	@PostMapping("/publicaciones/{publicacionId}/comentarios")
	public  ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId")long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
		return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public  ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId")long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
		ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, publicacionId, comentarioDTO);
		return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<String>eliminarComentario(@PathVariable(value = "publicacionId")Long publicacionId,
			@PathVariable(value = "id")Long id){
		comentarioServicio.eliminarComentario(publicacionId, id);
		return new ResponseEntity<>("Comentario Eliminado", HttpStatus.OK);
	}
	
}
