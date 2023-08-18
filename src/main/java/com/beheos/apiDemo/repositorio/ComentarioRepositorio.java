package com.beheos.apiDemo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beheos.apiDemo.entidades.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

	public List<Comentario>findByPublicacionId(Long publicacionId);

}
