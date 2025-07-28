package org.maslov.bot.app.games.random.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.maslov.bot.app.games.model.Activity;
import org.maslov.bot.app.games.model.GameStageResult;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class RandomWordGame implements Activity, Serializable {

    @JsonProperty
    private RandomGameState state;

    @JsonProperty
    private int stageNum;

    public void setRandomWordGameStages(List<RandomWordGameStage> randomWordGameStages) {
        this.randomWordGameStages = randomWordGameStages;
    }

    @JsonProperty
    private List<RandomWordGameStage> randomWordGameStages;

    @JsonIgnore
    private final static String INITIAL_MESSAGE = "Игра в перевод слов";

    public RandomGameState getState() {
        return state;
    }

    public void setState(RandomGameState state) {
        this.state = state;
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    public List<RandomWordGameStage> getRandomWordGameStages() {
        return randomWordGameStages;
    }

    @JsonIgnore
    public String getInitialMessage() {
        return INITIAL_MESSAGE;
    }

    public RandomWordGame(List<RandomWordGameStage> randomWordGameStages) {
        this.randomWordGameStages = randomWordGameStages;
        this.state = RandomGameState.INIT;
        this.stageNum = 0;
    }

    @JsonIgnore
    public String getCurrentStageMessage() {
        return randomWordGameStages.get(stageNum).getInitialStageMessage();
    }

    @JsonIgnore
    public String handleCurrentStateMessage(String message) {
        var currentState = randomWordGameStages.get(stageNum);
        var result = currentState.handleAnswer(message);
        return currentState.getAnswerMessage(result);
    }

    @JsonIgnore
    public boolean incrementStage() {
        ++stageNum;
        if (stageNum >= randomWordGameStages.size()) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public String getFinishMessage() {
        var right = randomWordGameStages.stream().map(RandomWordGameStage::getGameStageResult)
                .filter(Objects::nonNull).map(GameStageResult::getPoints).map(Number::intValue).reduce(0, Integer::sum);
        StringBuilder sb = new StringBuilder("Игра окончена. Всего слов: ").append(randomWordGameStages.size());
        sb.append("\n Правильно: ").append(right);
        sb.append("\n Неравильно: ").append(randomWordGameStages.size() - right);
        return sb.toString();
    }
}
