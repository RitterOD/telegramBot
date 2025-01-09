package org.maslov.bot.app.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class BotInitService {

    private static final org.slf4j.Logger log
            = org.slf4j.LoggerFactory.getLogger(BotInitService.class);

    private TelegramBotsLongPollingApplication botsApplication;

    private final String botToken;

    public BotInitService(@Value("${telegram.bot.token:tokenStub}") String botToken) {
        this.botToken = botToken;
        botsApplication = new TelegramBotsLongPollingApplication();
        try {
            botsApplication.registerBot(botToken, new LongPollingBot(botToken));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {

    }
}
