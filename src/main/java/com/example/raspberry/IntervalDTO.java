package com.example.raspberry;

public class IntervalDTO implements Comparable<IntervalDTO> {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

    public IntervalDTO(String producer, int interval, int previousWin, int followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducer() { return producer; }
    public int getInterval() { return interval; }
    public int getPreviousWin() { return previousWin; }
    public int getFollowingWin() { return followingWin; }

    @Override
    public int compareTo(IntervalDTO o) {
        int c = Integer.compare(this.interval, o.interval);
        if (c != 0) return c;
        c = this.producer.compareTo(o.producer);
        if (c != 0) return c;
        c = Integer.compare(this.previousWin, o.previousWin);
        if (c != 0) return c;
        return Integer.compare(this.followingWin, o.followingWin);
    }
}
