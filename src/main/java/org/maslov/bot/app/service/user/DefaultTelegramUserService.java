package org.maslov.bot.app.service.user;

import org.maslov.bot.app.dao.repository.TelegramUserRepository;
import org.maslov.bot.app.model.LangCode;
import org.maslov.bot.app.model.user.TelegramUser;
import org.maslov.bot.app.model.user.UserState;
import org.maslov.bot.app.model.user.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public TelegramUser save(Long id, String firstName, String userName, String langCode) {
        var rv = TelegramUser.builder().telegramId(id)
                .firstName(firstName)
                .telegramUserName(userName)
                .langCode(LangCode.getLangCodeFromTelegramLangCode(langCode))
                .build();
        var state  =  UserState.builder().status(UserStatus.IDLE).activity(null).build();
        rv.setState(state);
        return telegramUserRepository.save(rv);
    }
}
