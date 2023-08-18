package com.beheos.apiDemo.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.beheos.apiDemo.dto.PublicacionDTO;
import com.beheos.apiDemo.dto.PublicacionRespuesta;
import com.beheos.apiDemo.entidades.Publicacion;
import com.beheos.apiDemo.excepsiones.ResourceNotFoundExeption;
import com.beheos.apiDemo.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private  ModelMapper modelMapper;
	
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = mapaearEntidad(publicacionDTO);
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
		return publicacionRespuesta;
	}


	@Override
	public PublicacionRespuesta obtenerTodaslasPublicaciones(int numeroPagina, int medidaPagina, String ordenarPor, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroPagina, medidaPagina, sort);
		Page<Publicacion>publicaciones = publicacionRepositorio.findAll(pageable);
		List<Publicacion> listaPublicaciones = publicaciones.getContent();
		List<PublicacionDTO> contenido = listaPublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPaginas(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltimas(publicaciones.isLast());
		return publicacionRespuesta;
	}

	//convierte entidad a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
		return publicacionDTO;
	}
	
	//convierte DTO a entidad
	private Publicacion mapaearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}


	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		Publicacion publicacion = publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", id));
		return mapearDTO(publicacion);
	}


	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		Publicacion publicacion = publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", id));
		
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDTO(publicacionActualizada);
	}


	@Override
	public void eliminarPublicacion(long id) {
		Publicacion publicacion = publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Publicacion", "id", id));
		
		publicacionRepositorio.delete(publicacion);
	}
	
}
