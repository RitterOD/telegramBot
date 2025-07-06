package org.maslov.bot.app.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class BotInitService {

    private static final org.slf4j.Logger log
            = org.slf4j.LoggerFactory.getLogger(BotInitService.class);

    private TelegramBotsLongPollingApplication botsApplication;


    private final LongPollingBot longPollingBot;

    public BotInitService(LongPollingBot longPollingBot) {
        this.longPollingBot = longPollingBot;
        botsApplication = new TelegramBotsLongPollingApplication();
        try {
            botsApplication.registerBot(longPollingBot.getBotToken(), longPollingBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {

    }
}
