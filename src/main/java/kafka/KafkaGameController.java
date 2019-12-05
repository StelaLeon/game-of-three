package kafka;


import game.ConfigurationProvider;
import game.GameEngine;
import game.GameEngineFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

@Configuration
@EnableConfigurationProperties(ConfigurationProvider.class)
@Component
public class KafkaGameController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaGameController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConfigurationProvider configurationProvider;

    private GameEngine gameEngine ;

    public KafkaGameController(){
    }

    public GameEngine startTheGame(int initMove) {
         sendMessage(
                Integer.valueOf(initMove).toString(),
                configurationProvider.getOpponentTopic()
        );
        this.gameEngine = GameEngineFactory.getInstance().startAGame(initMove,configurationProvider.getPlayerName(), configurationProvider.getOpponentName());
        return this.gameEngine;
    }


    @KafkaListener(topics = "#{configurationProvider.getPlayerTopic()}", groupId = "game")
    public void listenForMoves(String message) {
        int m = Integer.valueOf(message);

        if(this.gameEngine == null)
            this.gameEngine = GameEngineFactory.getInstance().startAGame(m, configurationProvider.getPlayerName(), configurationProvider.getOpponentName());

        logger.info("[Play] Received Message in group: " + message);
        Optional<Integer> nextMove = gameEngine.playMove(m);
        if(nextMove.isPresent()){
            sendMessage(nextMove.get().toString(),configurationProvider.getOpponentTopic());
        }
        else
            logger.error("The game was paused");
    }

    @Bean
    public NewTopic opponentTopic() {
        return new NewTopic(
                configurationProvider.getOpponentTopic(),
                1,
                (short) 1
        );
    }

    @Bean
    public NewTopic myTopic() {
        return new NewTopic(
                configurationProvider.getPlayerTopic(),
                1,
                (short) 1);
    }

    private int sendMessage(String message, String toTopic) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(toTopic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
        return Integer.valueOf(message);
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
