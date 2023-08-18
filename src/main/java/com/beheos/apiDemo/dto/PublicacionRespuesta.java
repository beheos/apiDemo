package com.beheos.apiDemo.dto;

import java.util.List;

public class PublicacionRespuesta {
	
	private List<PublicacionDTO> contenido;
	private int numeroPaginas;
	private int medidaPagina;
	private long totalElementos;
	private int totalPaginas;
	private boolean ultimas;
	
	public PublicacionRespuesta() {
		super();
	}
	
	public List<PublicacionDTO> getContenido() {
		return contenido;
	}
	public void setContenido(List<PublicacionDTO> contenido) {
		this.contenido = contenido;
	}
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public int getMedidaPagina() {
		return medidaPagina;
	}
	public void setMedidaPagina(int medidaPagina) {
		this.medidaPagina = medidaPagina;
	}
	public long getTotalElementos() {
		return totalElementos;
	}
	public void setTotalElementos(long totalElementos) {
		this.totalElementos = totalElementos;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	public boolean isUltimas() {
		return ultimas;
	}
	public void setUltimas(boolean ultimas) {
		this.ultimas = ultimas;
	}
	
}
