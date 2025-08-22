package com.api.outsera;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProdutorIntervaloservice {

    private final FilmeRepository movieRepository;
    private static final Pattern AND_SPLIT = Pattern.compile("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

    public ProdutorIntervaloservice(FilmeRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Map<String, List<IntervalDTO>> computeintervalos() {
        List<Filmes> ganhadores = movieRepository.findByGanhadorTrue();

        Map<String, List<Integer>> winsByProducer = new HashMap<>();
        for (Filmes m : ganhadores) {
            for (String p : splitprodutores(m.getProdutores())) {
                winsByProducer.computeIfAbsent(p, k -> new ArrayList<>()).add(m.getAno());
            }
        }

        List<IntervalDTO> allintervalos = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> e : winsByProducer.entrySet()) {
            List<Integer> years = e.getValue().stream().sorted().toList();
            if (years.size() < 2) continue;
            for (int i = 0; i < years.size() - 1; i++) {
                allintervalos.add(new IntervalDTO(e.getKey(), years.get(i+1) - years.get(i), years.get(i), years.get(i+1)));
            }
        }

        if (allintervalos.isEmpty()) {
            return Map.of("min", List.of(), "max", List.of());
        }

        int min = allintervalos.stream().mapToInt(IntervalDTO::getInterval).min().orElse(0);
        int max = allintervalos.stream().mapToInt(IntervalDTO::getInterval).max().orElse(0);

        List<IntervalDTO> minList = allintervalos.stream().filter(i -> i.getInterval() == min).sorted().collect(Collectors.toList());
        List<IntervalDTO> maxList = allintervalos.stream().filter(i -> i.getInterval() == max).sorted().collect(Collectors.toList());

        Map<String, List<IntervalDTO>> resp = new LinkedHashMap<>();
        resp.put("min", minList);
        resp.put("max", maxList);
        return resp;
    }

    private List<String> splitprodutores(String produtores) {
        if (produtores == null || produtores.isEmpty()) return List.of();
        String replaced = AND_SPLIT.matcher(produtores).replaceAll(",");
        String[] parts = replaced.split(",");
        List<String> out = new ArrayList<>();
        for (String s : parts) {
            String p = s.trim();
            if (!p.isEmpty()) out.add(p);
        }
        return out;
    }
}
