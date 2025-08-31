package me.orel.gamemanager;

public class GameManager {

    private static GameManager instance;
    private GameState gameState;

    private GameManager() {
        this.gameState = GameState.WAITING;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
