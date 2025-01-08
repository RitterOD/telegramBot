package org.maslov.bot.app.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = TelegramUser.TABLE_NAME)
public class TelegramUser {

    public static final String TABLE_NAME = "telegram_user";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;


    @Column(name = "telegram_id", nullable = false)
    private Long telegramId;

    @Column(name = "telegram_user_name", columnDefinition = "TEXT")
    private String telegramUserName;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setTelegramUserName(String telegramUserName) {
        this.telegramUserName = telegramUserName;
    }
}
