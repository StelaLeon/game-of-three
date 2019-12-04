package game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Optional;


@RunWith(JUnitPlatform.class)
public class GameEngineUnitTest {

    private GameEngine gameEngine;

    @BeforeEach
    void init(){
        gameEngine= new GameEngine("me","johnny", 77);
    }

    @Test
    void opponentStartsAndWinTheGameTest() {
        assertTrue(gameEngine.getMyState() == GameState.WAITING_FOR_START,"my state is: "+gameEngine.getMyState().toString()+" although expected is WAITING_FOR_START");
        assertFalse(gameEngine.playMove(79).isPresent());
        assertTrue(gameEngine.getMyState() == GameState.HANSHAKE_FINISHED);
        assertTrue(gameEngine.playMove(26).get() == 9);
        assertTrue(gameEngine.getMyState() == GameState.GAME_STARTED);
        assertTrue(gameEngine.getMovesLog().size() == 3,"the size is:  "+gameEngine.getMovesLog().size()+"expected: 3");
        assertTrue(gameEngine.playMove(3).get() == 1,"I played 3");
        assertTrue(gameEngine.getMyState() == GameState.WINNER,"i am not the winner as i expected");
    }


    @Test
    void startAndWinTheGameTest(){
        assertTrue(gameEngine.getMyState() == GameState.WAITING_FOR_START,"the state was not waiting for start");
        assertTrue(gameEngine.playMove(9).isPresent(),"I dont seem to start even if my number was bigger");
        assertTrue(gameEngine.getMyState() == GameState.GAME_STARTED,"I was expected to start first.");
        assertTrue(gameEngine.playMove(36).get() == 12,"The opponent is supposed to play 36, and me 12");
        assertTrue(gameEngine.getMyState() == GameState.GAME_STARTED,"the game is still started");
        assertTrue(gameEngine.getMovesLog().size() == 5," the size should have been 3, but was:"+gameEngine.getMovesLog().size());
        assertTrue(gameEngine.playMove(4).get() == 1,"I played 1");
        assertTrue(gameEngine.getMyState() == GameState.WINNER,"I am not the winner though");
    }
}
