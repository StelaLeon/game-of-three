package game;

import kafka.KafkaGameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApp {

    @Autowired
    KafkaGameController gameController;

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    private static Integer getRandomIntegerBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    @Bean
    public KafkaGameController kafkaGameController(){
        return new KafkaGameController();
    }

    @Bean
    public GameEngine gameEngine(){
        return gameController
                .startTheGame(getRandomIntegerBetweenRange(1, 1000));
    }


}


