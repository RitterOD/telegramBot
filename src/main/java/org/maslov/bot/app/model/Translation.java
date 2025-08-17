package org.maslov.bot.app.model;

import jakarta.persistence.*;
import org.maslov.bot.app.model.tag.TranslationToTagLink;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = Translation.NAME)
public class Translation {

    public static final String NAME = "word";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "from_lang")
    @Enumerated(value = EnumType.STRING)
    private LangCode from;

    @Column(name = "to_lang")
    @Enumerated(value = EnumType.STRING)
    private LangCode to;


    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "word_transcription")
    private String wordTranscription;

    @Column(name = "translation", nullable = false)
    private String translation;

    @OneToMany(mappedBy = "translation")
    private Set<TranslationToTagLink> translationToTagLinks = new LinkedHashSet<>();

    @Column(name = "telegram_user_id")
    private Long telegramUserId;

    @Column(name = "technical_user_id")
    private UUID technicalUserId;

    @Column(name = "to_translate_clue")
    private String toTranslateClue;

    @Column(name = "from_translate_clue")
    private String fromTranslateClue;

    public String getToTranslateClue() {
        return toTranslateClue;
    }

    public void setToTranslateClue(String toTranslateClue) {
        this.toTranslateClue = toTranslateClue;
    }

    public String getFromTranslateClue() {
        return fromTranslateClue;
    }

    public void setFromTranslateClue(String fromTranslateClue) {
        this.fromTranslateClue = fromTranslateClue;
    }

    public LangCode getTo() {
        return to;
    }

    public void setTo(LangCode to) {
        this.to = to;
    }

    public Long getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(Long telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    public UUID getTechnicalUserId() {
        return technicalUserId;
    }

    public void setTechnicalUserId(UUID technicalUserId) {
        this.technicalUserId = technicalUserId;
    }


    public String getWordTranscription() {
        return wordTranscription;
    }

    public void setWordTranscription(String wordTranscription) {
        this.wordTranscription = wordTranscription;
    }

    public String getTranslationTranscription() {
        return translationTranscription;
    }

    public void setTranslationTranscription(String translationTranscription) {
        this.translationTranscription = translationTranscription;
    }

    @Column(name = "translation_transcriprion", nullable = false)
    private String translationTranscription;

    public Set<TranslationToTagLink> getTranslationToTagLinks() {
        return translationToTagLinks;
    }

    public void setTranslationToTagLinks(Set<TranslationToTagLink> translationToTagLinks) {
        this.translationToTagLinks = translationToTagLinks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LangCode getFrom() {
        return from;
    }

    public void setFrom(LangCode from) {
        this.from = from;
    }

    public LangCode getTu() {
        return to;
    }

    public void setTu(LangCode to) {
        this.to = to;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
