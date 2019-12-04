package game;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;
import static org.springframework.kafka.test.utils.KafkaTestUtils.getSingleRecord;

import java.util.Map;

/**
import kafka.KafkaGameController;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        // slice our unit test app context down to just these specific pieces
        classes = {
                KafkaGameController.class
        }
)
@EmbeddedKafka(
        partitions = 1,
        topics = {
                "player1-topic","player2-topic"
        }
)
public class KafkaGameControllerIT {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    KafkaGameController gameController;

    @Autowired
    ConfigurationProvider configurationProvider;


    @Primary
    public ConfigurationProvider configurationProvider() {
        Mockito.when(configurationProvider.getOpponentName()).thenReturn("player2");
        Mockito.when(configurationProvider.getPlayerName()).thenReturn("player1");
        Mockito.when(configurationProvider.getOpponentTopic()).thenReturn("player2-topic");
        Mockito.when(configurationProvider.getPlayerTopic()).thenReturn("player1-topic");

        return Mockito.mock(ConfigurationProvider.class);

    }

    @Test
    public void testMetricsEncodedAsSent() {
        gameController.startTheGame(3);

        final Consumer<String, String> consumer = buildConsumer(
                StringDeserializer.class,
                StringDeserializer.class
        );

        embeddedKafka.consumeFromEmbeddedTopics(consumer, "player1");
        final ConsumerRecord<String, String> record = getSingleRecord(consumer, configurationProvider.getPlayerTopic(), 500);

        assertThat(record, hasKey("tenant-1"));
        assertThat(record, hasValue("3"));
    }

    private <K,V> Consumer<K, V> buildConsumer(Class<? extends Deserializer> keyDeserializer,
                                               Class<? extends Deserializer> valueDeserializer) {

        final Map<String, Object> consumerProps = KafkaTestUtils
                .consumerProps("game-group-id", "true", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getName());

        final DefaultKafkaConsumerFactory<K, V> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps);
        return consumerFactory.createConsumer();
    }
}
**/