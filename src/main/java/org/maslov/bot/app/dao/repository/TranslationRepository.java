package org.maslov.bot.app.dao.repository;

import org.maslov.bot.app.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<Translation, UUID> {

    @Query(value = "select * from word  order by random() limit :amount", nativeQuery = true)
    List<Translation> findRandomN(@Param("amount") Long amount);


    @Query(value = "select * from word w where w.telegram_user_id = :userId  order by random() limit :amount", nativeQuery = true)
    List<Translation> findRandomNWithTelegramUserId(@Param("amount") Long amount, @Param("userId") Long telegramUserId);

    Long countAllByTelegramUserId(Long telegramUserId);

    @Query(value = "select w.id from word w where w.telegram_user_id = :userId", nativeQuery = true)
    List<UUID> findAllTelegramUserTranslationIds(@Param("userId") Long telegramUserId);


    @Query(value = "select w.id from word w", nativeQuery = true)
    List<UUID> findAllIds();

}
