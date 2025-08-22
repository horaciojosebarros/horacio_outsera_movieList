package com.api.outsera;

import jakarta.persistence.*;

@Entity
@Table(name = "filmes")
public class Filmes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ano;
    private String titulo;
    private String estudio
    ;
    @Column(length = 1000)
    private String produtores;
    private Boolean ganhador;

    public Filmes() {}

    public Filmes(Integer year, String title, String studios, String produtores, Boolean ganhador) {
        this.ano = year;
        this.titulo = title;
        this.estudio = studios;
        this.produtores = produtores;
        this.ganhador = ganhador;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getProdutores() {
		return produtores;
	}

	public void setProdutores(String produtores) {
		this.produtores = produtores;
	}

	public Boolean getGanhador() {
		return ganhador;
	}

	public void setGanhador(Boolean ganhador) {
		this.ganhador = ganhador;
	}

    
}
