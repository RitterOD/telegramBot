package org.maslov.bot.app.bot.webhook;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.TelegramBotsWebhookApplication;
import org.telegram.telegrambots.webhook.WebhookOptions;

@Service
@Profile("prod")
public class WebHookBotInitService {

    private TelegramBotsWebhookApplication webhookApplication;

    public WebHookBotInitService(WebHookBot webHookBot) {
        try {
            webhookApplication = new TelegramBotsWebhookApplication(WebhookOptions.builder().enableRequestLogging(true).build());
            webhookApplication.registerBot(webHookBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }
}
