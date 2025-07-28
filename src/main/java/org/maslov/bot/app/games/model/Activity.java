package org.maslov.bot.app.games.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.maslov.bot.app.games.random.model.RandomGameStageResult;
import org.maslov.bot.app.games.random.model.RandomWordGame;

import java.io.Serializable;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "activityType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RandomWordGame.class, name = "RandomWordGame"), // Map "DOG" to Dog.class
})

public interface Activity extends Serializable {
}
