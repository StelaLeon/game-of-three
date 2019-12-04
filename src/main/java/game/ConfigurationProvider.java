package game;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigurationProvider {

    @Value("opponent-topic")
    String opponentTopic;

    @Value("player-name")
    String playerName;

    @Value("player-topic")
    String playerTopic;

    @Value("opponent-name")
    String opponentName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getOpponentTopic() {
        return opponentTopic;
    }

    public void setOpponentTopic(String opponentTopic) {
        this.opponentTopic = opponentTopic;
    }

    public String getPlayerTopic(){
        return playerTopic;
    }

    public void setPlayerTopic(String playerTopic){
        this.playerTopic = playerTopic;
    }

}
