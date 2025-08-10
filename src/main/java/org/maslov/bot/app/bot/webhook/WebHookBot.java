package org.maslov.bot.app.bot.webhook;

import lombok.extern.slf4j.Slf4j;
import org.maslov.bot.app.games.BotEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

@Service
@Profile("prod")
@Slf4j
public class WebHookBot implements TelegramWebhookBot {

    private final TelegramClient telegramClient;

    private final BotEngine botEngine;
    private final String host;

    public WebHookBot(@Value("${telegram.bot.token:tokenStub}") String botToken, BotEngine botEngine, @Value("${app.host}") String host) {
        telegramClient = new OkHttpTelegramClient(botToken);
        this.botEngine = botEngine;
        this.host = host;
        log.info("Start on host: {}", host);
    }

    @Override
    public void runDeleteWebhook() {
        try {
            telegramClient.execute(new DeleteWebhook());
        } catch (TelegramApiException e) {
            log.info("Error deleting webhook");
        }
    }

    @Override
    public void runSetWebhook() {
        try {
            var url = host + getBotPath();
            log.info("Set web hook on url: {}", url);
            telegramClient.execute(SetWebhook
                    .builder()
                    .url(url)
                    .build());
        } catch (TelegramApiException e) {
            log.info("Error setting webhook");
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
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
        return null;
    }

    @Override
    public String getBotPath() {
        return "/bot";
    }
}
