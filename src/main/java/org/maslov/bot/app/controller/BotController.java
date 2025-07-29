package org.maslov.bot.app.controller;

import org.maslov.bot.app.dao.repository.TelegramUserRepository;
import org.maslov.bot.app.games.random.RandomGameService;
import org.maslov.bot.app.model.tlg.user.TelegramUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint for telegram
 * */
@RestController
public class BotController {

    public BotController(TelegramUserRepository telegramUserRepository, RandomGameService randomGameService) {
        this.telegramUserRepository = telegramUserRepository;
        this.randomGameService = randomGameService;
    }

    private final TelegramUserRepository telegramUserRepository;

    private final RandomGameService randomGameService;

    /**
     * Test endpoint for development and debug
     * */
    @GetMapping("/bot")
    public String probe() {
        return "test response";
    }

    @RequestMapping("/users")
    public ResponseEntity<List<TelegramUser>> getUsers() {
        return ResponseEntity.ok(telegramUserRepository.findAll());
    }

    @GetMapping("/random-game")
    public ResponseEntity<?> getRandomGame() {
        randomGameService.creatRandomGame();
        return ResponseEntity.ok().build();
    }

}
