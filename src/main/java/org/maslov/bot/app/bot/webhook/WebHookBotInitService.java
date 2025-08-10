package org.maslov.bot.app.bot.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.TelegramBotsWebhookApplication;
import org.telegram.telegrambots.webhook.WebhookOptions;

@Service
@Slf4j
@Profile("prod")
public class WebHookBotInitService {

    private TelegramBotsWebhookApplication webhookApplication;

    public WebHookBotInitService(WebHookBot webHookBot) {
        try {
            webhookApplication = new TelegramBotsWebhookApplication(WebhookOptions.builder().enableRequestLogging(true).build());
            log.info("After app creation");
            webhookApplication.registerBot(webHookBot);
            log.info("After bot registration");
        } catch (TelegramApiException e) {
            log.error("Exception in bot registration", e);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }


    }
}
