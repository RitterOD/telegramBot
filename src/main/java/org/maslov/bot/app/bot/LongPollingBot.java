package org.maslov.bot.app.bot;

import org.maslov.bot.app.games.BotEngine;
import org.maslov.bot.app.metrics.BotMetricsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LongPollingBot implements LongPollingSingleThreadUpdateConsumer {

    private final String botToken;
    private TelegramClient telegramClient;

    private final BotEngine botEngine;
    private final BotMetricsService botMetricsService;
    private Long previousTs;

    public String getBotToken() {
        return botToken;
    }

    public LongPollingBot(@Value("${telegram.bot.token:tokenStub}") String botToken, BotEngine botEngine, BotMetricsService botMetricsService) {
        this.botToken = botToken;
        telegramClient = new OkHttpTelegramClient(botToken);
        this.botEngine = botEngine;
        this.botMetricsService = botMetricsService;
    }

    @Override
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        collectMetrics();
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
//            String message_text = update.getMessage().getText();
//            long chat_id = update.getMessage().getChatId();
            var msgs = botEngine.consume(update);
            msgs.forEach(
                    msg -> {
                        try {
                            telegramClient.execute(msg);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        }
    }

    private void collectMetrics() {
        if (previousTs == null) {
            previousTs = System.currentTimeMillis();
        } else {
            var currentTs = System.currentTimeMillis();
            botMetricsService.regInputMessage(currentTs - previousTs, TimeUnit.MILLISECONDS);
            previousTs = currentTs;
        }
        botMetricsService.incrementMessageCountMetric();
    }
}
