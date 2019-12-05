package game;

import java.util.LinkedList;
import java.util.List;

final public class GameEngineFactory {
    private static GameEngineFactory ourInstance = new GameEngineFactory();

    public static GameEngineFactory getInstance() {
        return ourInstance;
    }

    private GameEngineFactory() {
    }

    public GameEngine startAGame(int initMove, String player1, String opponent){
        GameEngine gameEngine = new GameEngine(
                player1,
                opponent,
                initMove
        );
        return gameEngine;
    }
}
