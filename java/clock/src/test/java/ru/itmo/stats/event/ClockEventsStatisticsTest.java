package ru.itmo.stats.event;

import org.junit.jupiter.api.Test;
import ru.itmo.stats.clock.SettableClock;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClockEventsStatisticsTest {
    private static final double EPSILON = 1e-10;

    private boolean equals(final double lhs, final double rhs) {
        return Math.abs(lhs - rhs) <= EPSILON;
    }

    @Test
    public void testClockEventsStatisticsFixedTimeNoEvents() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        assertEquals(0, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsFixedTimeOneEventStatsForNonExisting() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        final var anotherEventName = "another event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsFixedTimeOneEventOneIncrement() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsFixedTimeOneEventManyIncrements() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        final var count = 500;
        for (var i = 1; i < count; i++) {
            statsManager.incEvent(eventName);
            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), i / 60.0));
            assertEquals(1, statsManager.getAllEventStatistics().size());
        }
    }

    @Test
    public void testClockEventsStatisticsChangingTimeNoEvents() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        assertEquals(0, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(123));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        assertEquals(0, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3800));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        assertEquals(0, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsChangingTimeOneEventStatsForNonExisting() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        final var anotherEventName = "another event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(123));
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3800));
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsChangingTimeOneEventOneIncrement() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(123));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsChangingTimeOneEventOneIncrementHourPassed() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(123));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3600));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3601));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsChangingTimeOneEventFewIncrementsWithinAnHour() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var eventName = "event";
        statsManager.incEvent(eventName);
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(123));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3600));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        statsManager.incEvent(eventName);
        clock.setNow(Instant.ofEpochSecond(3600));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 2.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(3601));
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / 60.0));
        assertEquals(1, statsManager.getAllEventStatistics().size());
    }

    @Test
    public void testClockEventsStatisticsChangingTimeManyEventsManyIncrements() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var events = new String[]{"event0", "event1", "event2", "event3", "event4", "event5", "event6", "event7"};
        final var periods = new int[]{10, 11, 24, 31, 47, 53, 66, 79};
        final var count = 500;
        for (var i = 1; i <= count; i++) {
            for (var j = 0; j < events.length; j++) {
                final var eventName = events[j];
                final var period = periods[j];
                clock.setNow(Instant.ofEpochSecond(i * period));
                statsManager.incEvent(eventName);
            }
        }
        assertEquals(events.length, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(0L));
        for (final var eventName : events) {
            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        }
        final var max = count * periods[periods.length - 1];
        for (var i = 0; i < max; i++) {
            clock.setNow(Instant.ofEpochSecond(i));
            for (var j = 0; j < events.length; j++) {
                final var eventName = events[j];
                final var period = periods[j];
                if (i < count * period) {
                    var first = period * ((i - 3600) / period);
                    if ((i - 3600) % period != 0) {
                        first += period;
                    }
                    final var start = Math.max(period, first);
                    final var cnt = Math.max(0, i - start + period) / period;
                    assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / 60.0));
                }
            }
        }
    }

    @Test
    public void testClockEventsStatisticsChangingTimeManyEventsManyIncrementsTimeGoesBackwards() {
        final var clock = new SettableClock(Instant.ofEpochSecond(0L));
        final var statsManager = new ClockEventsStatistics(clock);
        final var events = new String[]{"event0", "event1", "event2", "event3", "event4", "event5", "event6", "event7"};
        final var periods = new int[]{10, 11, 24, 31, 47, 53, 66, 79};
        final var count = 500;
        for (var i = count; i >= 1; i--) {
            for (var j = 0; j < events.length; j++) {
                final var eventName = events[j];
                final var period = periods[j];
                clock.setNow(Instant.ofEpochSecond(i * period));
                statsManager.incEvent(eventName);
            }
        }
        assertEquals(events.length, statsManager.getAllEventStatistics().size());
        clock.setNow(Instant.ofEpochSecond(0L));
        for (final var eventName : events) {
            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0));
        }
        final var max = count * periods[periods.length - 1];
        for (var i = 0; i < max; i++) {
            clock.setNow(Instant.ofEpochSecond(i));
            for (var j = 0; j < events.length; j++) {
                final var eventName = events[j];
                final var period = periods[j];
                if (i < count * period) {
                    var first = period * ((i - 3600) / period);
                    if ((i - 3600) % period != 0) {
                        first += period;
                    }
                    final var start = Math.max(period, first);
                    final var cnt = Math.max(0, i - start + period) / period;
                    assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / 60.0));
                }
            }
        }
    }
}
