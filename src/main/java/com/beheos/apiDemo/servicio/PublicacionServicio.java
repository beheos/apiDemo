package com.beheos.apiDemo.servicio;

import com.beheos.apiDemo.dto.PublicacionDTO;
import com.beheos.apiDemo.dto.PublicacionRespuesta;

public interface PublicacionServicio {
	
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
	
	public PublicacionRespuesta obtenerTodaslasPublicaciones(int numeroPagina, int medidaPagina, String ordenarPor, String sortDir);
	
	public PublicacionDTO obtenerPublicacionPorId(long id);
	
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
	
	public void eliminarPublicacion(long id);
}
