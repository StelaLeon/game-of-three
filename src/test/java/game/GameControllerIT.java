package game;
/**
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;

import kafka.KafkaGameController;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    ConfigurationProvider configurationProvider;


    @Autowired
    NewTopic newTopic = Mockito.mock(NewTopic.class);



    @Autowired
    KafkaGameController kafkaGameController = Mockito.mock(KafkaGameController.class);

    @Primary
    public ConfigurationProvider configurationProvider() {
        Mockito.when(configurationProvider.getOpponentName()).thenReturn("player2");
        Mockito.when(configurationProvider.getPlayerName()).thenReturn("player1");
        Mockito.when(configurationProvider.getOpponentTopic()).thenReturn("player2-topic");
        Mockito.when(configurationProvider.getPlayerTopic()).thenReturn("player1-topic");

        return Mockito.mock(ConfigurationProvider.class);

    }

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/status");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("WTV it returns"));
    }
}
 **/