package org.maslov.bot.app.games;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface GamePlayer {

    List<SendMessage> consume(Update update);
}
