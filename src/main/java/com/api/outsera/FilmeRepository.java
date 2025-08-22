package com.api.outsera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filmes, Long> {
    List<Filmes> findByGanhadorTrue();
}
