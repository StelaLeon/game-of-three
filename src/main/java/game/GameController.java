package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class GameController {

    private static final Logger logger =
            LoggerFactory.getLogger(GameEngine.class);

    @Autowired
    private GameEngine gameEngine;

    public GameController(GameEngine gameEngine) {

        this.gameEngine = gameEngine;
    }

    @GetMapping("/status")
    @ResponseBody
    public String status(){
        return String.join("\n \n",gameEngine
                .getMovesLog()
                .stream()
                .map(GameEngine.Move::toString).collect(Collectors.toList()));
    }



}