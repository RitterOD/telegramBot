package org.maslov.bot.app.games;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface BotEngine {

    List<SendMessage> consume(final Update update);
}
