package com.beheos.apiDemo.controlador;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beheos.apiDemo.dto.PublicacionDTO;
import com.beheos.apiDemo.dto.PublicacionRespuesta;
import com.beheos.apiDemo.servicio.PublicacionServicio;
import com.beheos.apiDemo.utilerias.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControler {
	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping public ResponseEntity<PublicacionDTO>guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO),HttpStatus.CREATED);
	}
	
	@GetMapping
	public PublicacionRespuesta listarPublicaciones(
			@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINAS_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {
		return publicacionServicio.obtenerTodaslasPublicaciones(numeroPagina,medidaPagina,ordenarPor,sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO>obtenerPublicacionPorId(@PathVariable(name = "id")long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO>actualizarPublicacion(@RequestBody PublicacionDTO publicacionDTO,@PathVariable(name = "id")long id){
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id")long id){
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada exitosamente", HttpStatus.OK);
	}
}
