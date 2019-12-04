package game;


public enum GameState {
    WAITING_FOR_START,
    HANSHAKE_FINISHED,
    // we need this in case the i send a number,
    // he sends a number, he wins and needs to start the game
    // then the validation is crashing because it's not his turn
    GAME_STARTED,
    GAME_OVER,
    WINNER
}
