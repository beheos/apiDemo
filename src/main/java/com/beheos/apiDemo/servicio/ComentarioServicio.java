package com.beheos.apiDemo.servicio;

import java.util.List;

import com.beheos.apiDemo.dto.ComentarioDTO;

public interface ComentarioServicio {

	public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);
	
	public List<ComentarioDTO> obtenerCometarioPorPublicacion(Long publicacionId);
	
	public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId);
	
	public ComentarioDTO actualizarComentario(long publicacionId,long comentarioId, ComentarioDTO solicitudComentario);
	
	public void eliminarComentario(long publicacionId,long comentarioId);
}
