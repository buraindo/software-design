package ru.itmo.stats.event;

import ru.itmo.stats.clock.Clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockEventsStatistics implements EventsStatistics {
    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 3600;

    private final Clock clock;

    private final Map<String, List<Long>> events = new HashMap<>();

    public ClockEventsStatistics(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(final String name) {
        final var now = clock.now();
        if (!events.containsKey(name)) {
            events.put(name, new ArrayList<>());
        }
        events.get(name).add(now.getEpochSecond());
    }

    @Override
    public double getEventStatisticsByName(final String name) {
        if (name == null || !events.containsKey(name)) {
            return 0.0;
        }
        final var eventsByName = events.get(name);
        final var now = clock.now().getEpochSecond();
        final var hourAgo = Math.max(0L, now - SECONDS_IN_HOUR);
        final var eventsByNameLastHourCount = eventsByName.stream().filter(t -> t <= now && t >= hourAgo).count();
        return eventsByNameLastHourCount / (double) MINUTES_IN_HOUR;
    }

    @Override
    public Map<String, Double> getAllEventStatistics() {
        final var result = new HashMap<String, Double>();
        for (final var k : events.keySet()) {
            result.put(k, getEventStatisticsByName(k));
        }
        return result;
    }

    @Override
    public void printStatistics() {
        for (final var e : events.entrySet()) {
            final var validEventsCount = e.getValue().stream().filter(t -> t >= 0).count();
            System.out.printf("Event: %s, rpm: %f%n", e.getKey(), validEventsCount / (double) MINUTES_IN_HOUR);
        }
    }
}
