package com.beheos.apiDemo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beheos.apiDemo.entidades.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

	public Optional<Rol> findByNombre(String nombre);
	
}
