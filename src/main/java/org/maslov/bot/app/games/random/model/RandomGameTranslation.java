package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.model.LangCode;

import java.util.UUID;

public class RandomGameTranslation {
    private UUID id;
    private LangCode from;
    private LangCode to;
    private String word;
    private String wordTranscription;
    private String translation;

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

    public LangCode getTo() {
        return to;
    }

    public void setTo(LangCode to) {
        this.to = to;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordTranscription() {
        return wordTranscription;
    }

    public void setWordTranscription(String wordTranscription) {
        this.wordTranscription = wordTranscription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
