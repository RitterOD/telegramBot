package org.maslov.bot.app.bot.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.starter.TelegramBotsSpringWebhookApplication;

@Service
@Slf4j
@Profile("prod")
public class WebHookBotInitService {

    private final TelegramBotsSpringWebhookApplication webhookApplication;

    private final WebHookBot webHookBot;

    public WebHookBotInitService(TelegramBotsSpringWebhookApplication telegramBotsSpringWebhookApplication, WebHookBot webHookBot) {
        webhookApplication = telegramBotsSpringWebhookApplication;
        try {
            webhookApplication.registerBot("/bot", (Update update) -> {
                return webHookBot.consumeUpdate(update);
            }, () -> {
                webHookBot.runSetWebhook();
                log.info("set");
            }, () -> {
                webHookBot.runDeleteWebhook();
                log.info("delete");
            });
        } catch (TelegramApiException e) {
            log.error("Exception in bot registration ", e);
            throw new RuntimeException(e);
        }
        this.webHookBot = webHookBot;

    }
}
