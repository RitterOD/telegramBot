package org.maslov.bot.app.metrics;

import java.util.concurrent.TimeUnit;

public interface BotMetricsService {

    void incrementMessageCountMetric();

    void regInputMessage(Long ticks, TimeUnit timeUnit);
}
