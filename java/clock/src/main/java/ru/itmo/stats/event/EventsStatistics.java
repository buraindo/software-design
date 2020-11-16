package ru.itmo.stats.event;

import java.util.Map;

public interface EventsStatistics {
    /**
     * Increases amount of event occurrences
     */
    void incEvent(String name);

    /**
     * Returns rpm for the event with given name within an hour
     * If there is no event with such a name, returns 0.0
     *
     * @return rpm for the event with given name within an hour
     */
    double getEventStatisticsByName(String name);

    /**
     * Returns rpm for every event within an hour
     * If there is such an event that didn't happen within
     * last hour, but happened at some time before,
     * there will be 0.0 for this event returned
     *
     * @return rpm for every event within an hour
     */
    Map<String, Double> getAllEventStatistics();

    void printStatistics();
}
