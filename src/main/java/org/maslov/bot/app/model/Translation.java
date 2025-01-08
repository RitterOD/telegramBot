package org.maslov.bot.app.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = Translation.NAME)
public class Translation {

    public static final String NAME = "word";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "fromLang")
    @Enumerated(value = EnumType.STRING)
    private LangCode from;

    @Column(name = "toLang")
    @Enumerated(value = EnumType.STRING)
    private LangCode tu;


    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "word_transcription", nullable = false)
    private String wordTranscription;

    @Column(name = "translation", nullable = false)
    private String translation;


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
        return tu;
    }

    public void setTu(LangCode tu) {
        this.tu = tu;
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
