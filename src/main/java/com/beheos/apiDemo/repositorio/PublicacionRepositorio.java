package com.beheos.apiDemo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beheos.apiDemo.entidades.Publicacion;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

}
