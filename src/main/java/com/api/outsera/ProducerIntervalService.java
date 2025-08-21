package com.api.outsera;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProducerIntervalService {

    private final MovieRepository movieRepository;
    private static final Pattern AND_SPLIT = Pattern.compile("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

    public ProducerIntervalService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Map<String, List<IntervalDTO>> computeIntervals() {
        List<Movie> winners = movieRepository.findByWinnerTrue();

        Map<String, List<Integer>> winsByProducer = new HashMap<>();
        for (Movie m : winners) {
            for (String p : splitProducers(m.getProducers())) {
                winsByProducer.computeIfAbsent(p, k -> new ArrayList<>()).add(m.getYear());
            }
        }

        List<IntervalDTO> allIntervals = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> e : winsByProducer.entrySet()) {
            List<Integer> years = e.getValue().stream().sorted().toList();
            if (years.size() < 2) continue;
            for (int i = 0; i < years.size() - 1; i++) {
                allIntervals.add(new IntervalDTO(e.getKey(), years.get(i+1) - years.get(i), years.get(i), years.get(i+1)));
            }
        }

        if (allIntervals.isEmpty()) {
            return Map.of("min", List.of(), "max", List.of());
        }

        int min = allIntervals.stream().mapToInt(IntervalDTO::getInterval).min().orElse(0);
        int max = allIntervals.stream().mapToInt(IntervalDTO::getInterval).max().orElse(0);

        List<IntervalDTO> minList = allIntervals.stream().filter(i -> i.getInterval() == min).sorted().collect(Collectors.toList());
        List<IntervalDTO> maxList = allIntervals.stream().filter(i -> i.getInterval() == max).sorted().collect(Collectors.toList());

        Map<String, List<IntervalDTO>> resp = new LinkedHashMap<>();
        resp.put("min", minList);
        resp.put("max", maxList);
        return resp;
    }

    private List<String> splitProducers(String producers) {
        if (producers == null || producers.isEmpty()) return List.of();
        String replaced = AND_SPLIT.matcher(producers).replaceAll(",");
        String[] parts = replaced.split(",");
        List<String> out = new ArrayList<>();
        for (String s : parts) {
            String p = s.trim();
            if (!p.isEmpty()) out.add(p);
        }
        return out;
    }
}
