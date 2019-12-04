package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;

public class GameEngine {

    private GameState myState;
    private LinkedList<Move> movesLog = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);
    private String myPlayer;
    private String opponent;

    public GameEngine(String myPlayer, String opponent, int initMove){
        logger.info("Game started");
        this.myPlayer = myPlayer;
        this.opponent = opponent;
        movesLog.add(new Move(myPlayer,initMove));
        myState = GameState.WAITING_FOR_START;
    }
    public int playMove(int opponentMove){
        if(myState == GameState.HANSHAKE_FINISHED && verifyMove(opponentMove))
            myState = GameState.GAME_STARTED;

        if(isWinner(opponentMove)){
            myState = GameState.GAME_OVER;
            return 1;
        }
        int nextMove ;
        switch (myState) {
            case WAITING_FOR_START:
                nextMove = continueHandShake(opponentMove);
                myState = GameState.HANSHAKE_FINISHED;
                break;
            case GAME_STARTED:
                nextMove = play(opponentMove);
                if (isWinner(nextMove)) {
                    logger.info("I won the game!!! Hurray!!");
                    myState = GameState.WINNER;
                }
                break;
            default:
                throw new IllegalMoveException("The game has finished, I am just stop talking to you unless you start a new one");
        }
        updateLogs(opponent,opponentMove);
        updateLogs(myPlayer,nextMove);
        return nextMove;
    }

    private boolean isWinner(int move){
        return (myState == GameState.GAME_STARTED && move == 1);
    }

    private void updateLogs(String player, int move){
        logger.info("Player: "+player+" moved: "+move);
        this.movesLog.add(new Move(player,move));
    }
    private int continueHandShake(int n){
        int prevMove = movesLog.getLast().move;
        if(prevMove > n){
            logger.info("I am starting the game while I am the one that starts");
            return prevMove;
        }else{
            logger.info("The opponent should start the game");
            return n;
        }
    }

    private boolean verifyMove(int opponentMove){
        if(movesLog.isEmpty() || movesLog.getLast().playerName == myPlayer)
            return true;
        else
            throw new IllegalMoveException("It is not your turn, mr. "+opponent);
    }

    private int play(int oponentMove){

        int rest = Math.round(oponentMove / 3);
        int move;
        switch (rest){
            case 0: move = oponentMove/3;
            case 1: move = (oponentMove -1)/3;
            default: move = (oponentMove + 1)/3;
        }
        return move;
    }

    public List<Move> getMovesLog(){
        return this.movesLog;
    }

    public GameState getMyState() {
        return myState;
    }

    public class Move{
        String playerName;
        int move;
        Move(String playerName, int move){
            this.playerName = playerName;
            this.move = move;
        }

        public String toString(){
            return new String(playerName+" has moved: "+ move);
        }
    }
    private class IllegalMoveException extends RuntimeException{
        IllegalMoveException(String reason){
            super(reason);
        }
    };

}
