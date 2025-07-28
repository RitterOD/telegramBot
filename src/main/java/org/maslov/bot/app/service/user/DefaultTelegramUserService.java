package org.maslov.bot.app.service.user;

import lombok.Builder;
import org.maslov.bot.app.dao.repository.TelegramUserRepository;
import org.maslov.bot.app.model.LangCode;
import org.maslov.bot.app.model.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultTelegramUserService implements TelegramUserService {


    private final TelegramUserRepository telegramUserRepository;

    public DefaultTelegramUserService(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public Optional<TelegramUser> findUserByTelegramId(Long telegramId) {
        return telegramUserRepository.findByTelegramId(telegramId);
    }

    @Override
    public TelegramUser save(TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    @Override
    public TelegramUser save(Long id, String firstName, String userName, String langCode) {
        var rv = TelegramUser.builder().telegramId(id)
                .firstName(firstName)
                .telegramUserName(userName).langCode(LangCode.getLangCodeFromTelegramLangCode(langCode)).build();
        return telegramUserRepository.save(rv);
    }
}
