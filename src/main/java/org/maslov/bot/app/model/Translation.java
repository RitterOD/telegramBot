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


    @Column(name = "word", nullable = false, unique = true)
    private String word;

    @Column(name = "word_transcription", nullable = true)
    private String wordTranscription;

    @Column(name = "translation", nullable = false)
    private String translation;

    @OneToMany(mappedBy = "translation")
    private Set<TranslationToTagLink> translationToTagLinks = new LinkedHashSet<>();


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
