package com.beheos.apiDemo.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.beheos.apiDemo.dto.ComentarioDTO;
import com.beheos.apiDemo.entidades.Comentario;
import com.beheos.apiDemo.entidades.Publicacion;
import com.beheos.apiDemo.excepsiones.BlogAppException;
import com.beheos.apiDemo.excepsiones.ResourceNotFoundExeption;
import com.beheos.apiDemo.repositorio.ComentarioRepositorio;
import com.beheos.apiDemo.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
		
	@Override
	public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
		Comentario comentario = mapearEntidad(comentarioDTO);
		Publicacion publicacion = publicacionRepositorio
				.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", publicacionId));
		comentario.setPublicacion(publicacion);
		Comentario nuevoComentario = comentarioRepositorio.save(comentario); 
		return mapearDTO(nuevoComentario);
	}
	
	private ComentarioDTO mapearDTO(Comentario comentario) {
		ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
		return comentarioDTO;
	}
	
	private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
		Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
		return comentario;
	}

	@Override
	public List<ComentarioDTO> obtenerCometarioPorPublicacion(Long publicacionId) {
		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId) {
		Publicacion publicacion = publicacionRepositorio
				.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", publicacionId));
		
		Comentario comentario = comentarioRepositorio
				.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundExeption("Comentario", "id", comentarioId));
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
			
		return mapearDTO(comentario);
	}

	@Override
	public ComentarioDTO actualizarComentario(long publicacionId,long comentarioId, ComentarioDTO solicitudComentario) {
		Publicacion publicacion = publicacionRepositorio
				.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", publicacionId));
		
		Comentario comentario = comentarioRepositorio
				.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundExeption("Comentario", "id", comentarioId));
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
		comentario.setNombre(solicitudComentario.getNombre());
		comentario.setEmail(solicitudComentario.getEmail());
		comentario.setCuerpo(solicitudComentario.getCuerpo());
		
		Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
		return mapearDTO(comentarioActualizado);
	}

	@Override
	public void eliminarComentario(long publicacionId, long comentarioId) {
		Publicacion publicacion = publicacionRepositorio
				.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", publicacionId));
		
		Comentario comentario = comentarioRepositorio
				.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundExeption("Comentario", "id", comentarioId));
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
		
		comentarioRepositorio.delete(comentario);
	}

}
