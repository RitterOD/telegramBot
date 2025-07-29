package org.maslov.bot.app.model.tlg.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.maslov.bot.app.model.LangCode;

import java.util.UUID;

@Entity
@Table(name = TelegramUser.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegramUser {

    public static final String TABLE_NAME = "telegram_user";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;


    @Column(name = "telegram_id", nullable = false, unique = true)
    private Long telegramId;

    @Column(name = "telegram_user_name", columnDefinition = "TEXT")
    private String telegramUserName;

    @Column(name = "lang_code")
    @Enumerated(value = EnumType.STRING)
    private LangCode langCode;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserState state;

    @Column(name = "first_name")
    private String firstName;


    public UserState getState() {
        return state;
    }


    public LangCode getLangCode() {
        return langCode;
    }

    public void setLangCode(LangCode langCode) {
        this.langCode = langCode;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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


    public TelegramUser setState(UserState userState) {
        this.state = userState;
        userState.setUser(this);
        return this;
    }
}
