package ru.itmo.stats.clock;

import java.time.Instant;

public interface Clock {
    Instant now();
}
