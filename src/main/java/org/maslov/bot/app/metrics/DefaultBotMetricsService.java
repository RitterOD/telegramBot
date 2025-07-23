package org.maslov.bot.app.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DefaultBotMetricsService implements BotMetricsService{

    private final MeterRegistry meterRegistry;
    private final Counter botInputMessageCounter;
    private final Timer botInputMessageFrequencyTimer;

    public DefaultBotMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.botInputMessageCounter = Counter.builder("bot_input_message_counter") // Metric name
                .description("Number of input message") // Description
                .tags("category", "application") // Optional tags
                .register(meterRegistry);
        this.botInputMessageFrequencyTimer = meterRegistry.timer("bot_input_message_frequency_timer");
    }


    @Override
    public void incrementMessageCountMetric() {
        botInputMessageCounter.increment();
    }

    public void regInputMessage(Long ticks, TimeUnit timeUnit) {
        botInputMessageFrequencyTimer.record(ticks, timeUnit);
    }
}
