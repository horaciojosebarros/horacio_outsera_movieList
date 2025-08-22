package com.api.outsera;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer release_year;
    private String title;
    private String studios;
    @Column(length = 1000)
    private String produtores;
    private Boolean winner;

    public Movie() {}

    public Movie(Integer year, String title, String studios, String produtores, Boolean winner) {
        this.release_year = year;
        this.title = title;
        this.studios = studios;
        this.produtores = produtores;
        this.winner = winner;
    }

    public Long getId() { return id; }
    public Integer getYear() { return release_year; }
    public String getTitle() { return title; }
    public String getStudios() { return studios; }
    public String getprodutores() { return produtores; }
    public Boolean getWinner() { return winner; }

    public void setId(Long id) { this.id = id; }
    public void setYear(Integer year) { this.release_year = year; }
    public void setTitle(String title) { this.title = title; }
    public void setStudios(String studios) { this.studios = studios; }
    public void setprodutores(String produtores) { this.produtores = produtores; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}
