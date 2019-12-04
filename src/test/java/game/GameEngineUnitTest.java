package game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
public class GameEngineUnitTest {

    private GameEngine gameEngine;

    @BeforeEach
    void init(){
        gameEngine= new GameEngine("me","johnny", 77);
    }

    @Test
    void opponentStartsAndWinTheGameTest() {
        assertTrue(gameEngine.getMyState() == GameState.WAITING_FOR_START);
        assertTrue(gameEngine.playMove(79) == 79);
        assertTrue(gameEngine.getMyState() == GameState.HANSHAKE_FINISHED);
        assertTrue(gameEngine.playMove(26) == 9);
        assertTrue(gameEngine.getMyState() == GameState.GAME_STARTED);
        assertTrue(gameEngine.getMovesLog().size() == 5);
        assertTrue(gameEngine.playMove(3) == 1,"I played 3");
        assertTrue(gameEngine.getMyState() == GameState.WINNER);
    }

    @Test
    void startAndWinTheGameTest(){
        assertTrue(gameEngine.getMyState() == GameState.WAITING_FOR_START,"the state was not waiting for start");
        assertTrue(gameEngine.playMove(9) == 77,"I dont seem to start even if my number was bigger");
        assertTrue(gameEngine.getMyState() == GameState.HANSHAKE_FINISHED);
        assertTrue(gameEngine.playMove(36) == 12);
        assertTrue(gameEngine.getMyState() == GameState.GAME_STARTED);
        assertTrue(gameEngine.getMovesLog().size() == 5);
        assertTrue(gameEngine.playMove(4) == 1,"I played 1");
        assertTrue(gameEngine.getMyState() == GameState.WINNER);
    }
}
