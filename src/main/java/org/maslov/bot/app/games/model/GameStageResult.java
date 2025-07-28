package org.maslov.bot.app.games.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.maslov.bot.app.games.random.model.RandomGameStageResult;

import java.io.Serializable;



@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "stageResultType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RandomGameStageResult.class, name = "RandomGameStageResult"), // Map "DOG" to Dog.class
})

public interface GameStageResult extends Serializable {
    Number getPoints();

}
