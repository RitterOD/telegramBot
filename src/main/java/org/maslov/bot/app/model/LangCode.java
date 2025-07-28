package org.maslov.bot.app.model;

public enum LangCode {

    RU(1L),
    ENG(2L),
    UNKNOWN(3L);

    private Long id;

    LangCode(Long id) {
        this.id = id;
    }


    public static LangCode getLangCodeFromTelegramLangCode(String tlg) {
        return switch (tlg) {
            case "ru" -> RU;
            case "en" -> ENG;
            default -> UNKNOWN;
        };
    }


}
