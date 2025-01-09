package org.maslov.bot.app.controller;

import org.maslov.bot.app.dao.repository.TelegramUserRepository;
import org.maslov.bot.app.model.TelegramUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint for telegram
 * */
@RestController
public class BotController {

    public BotController(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    private TelegramUserRepository telegramUserRepository;

    /**
     * Test endpoint for development and debug
     * */
    @RequestMapping("/bot")
    public String probe() {
        return "test response";
    }

    @RequestMapping("/users")
    public ResponseEntity<List<TelegramUser>> getUsers() {
        return ResponseEntity.ok(telegramUserRepository.findAll());
    }

}
