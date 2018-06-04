package com.dotnet;

import com.dotnet.character.snake.Snake;
import com.dotnet.character.snake.UserSnake;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameControlLayer {
    private UserSnake userSnake;
    private Timer timer;
    private GameGraphicLayer gameGraphicLayer;
    private UnitResourceManager unitResourceManager;
    private UnitMaker unitMaker;
    private GameDataLayer gameDataLayer;
    private SnakeAi snakeAi;
    private final ScoreBoard scoreBoard;
    private int stage;
    private final SoundController soundController;

    GameControlLayer() {
        soundController = new SoundController();
        unitResourceManager = new UnitResourceManager();
        unitMaker = new UnitMaker(unitResourceManager);
        scoreBoard = new ScoreBoard();
        gameGraphicLayer = new GameGraphicLayer(unitResourceManager, scoreBoard);

        gameDataLayer = new GameDataLayer(unitResourceManager, scoreBoard);

        ScreenConfig screenConfig = new ScreenConfig();
        gameGraphicLayer.setPreferredSize(new Dimension(screenConfig.getWidth(), screenConfig.getHeight()));
        stage = 0;
    }

    private void gameProcess() {
        if (gameDataLayer.checkFenceCollision(userSnake)) {
            stopGame();
            gameGraphicLayer.gameOver();
            soundController.playGameOver();
            soundController.stop_background();
        }
        if (scoreBoard.getScore() > 400 && stage == 1) {
            nextStage();
            generateFood();
        } else if (scoreBoard.getScore() > 200 && stage == 0) {
            nextStage();
            generateFood();

        }
        if (gameDataLayer.checkFoodCollision(userSnake)) {
            generateFood();
        }
        userSnake.move();
        //snakeAi.move();
    }

    private void generateFood() {
        Random random = new Random();
        int boundary[] = gameDataLayer.getCurrentBgBoundary();
        unitMaker.makeFood(new Position(random.nextInt(boundary[2] - boundary[3]) + boundary[3], random.nextInt(boundary[0] - boundary[1]) + boundary[1]));
    }

    private void nextStage() {
        stage++;
        userSnake.setPosition(new Position(550, 600));
        gameGraphicLayer.changeBackground(stage);
        gameDataLayer.changeFenceBoundary(stage);
        soundController.playBackground(stage);
        soundController.playNextStage();

    }

    private void initStartPosition() {
        unitMaker.makePpi(new Position(350, 550));
        userSnake = unitMaker.makeUserSnake(new Position(550, 600));
        unitMaker.makeRabbit(new Position(250, 350));
        Snake snake = unitMaker.makeSnake(new Position(1000, 650));
        snakeAi = new SnakeAi(snake);
        userSnake.incrementBody(unitResourceManager);
    }

    public void runGame() {
        initStartPosition();

        GameKeyAdapter gameKeyAdapter = new GameKeyAdapter();
        gameGraphicLayer.addUserKeyListener(gameKeyAdapter);
        gameKeyAdapter.setKeyListener(userSnake);

        gameGraphicLayer.run();

        timer = new Timer(gameDataLayer.getDELAY(), e -> {
            if (gameDataLayer.isInGame()) {
                gameProcess();
            }
            if (!gameDataLayer.isInGame()) {
                stopGame();
            }
        });
        timer.start();
        soundController.playBackground(stage);
    }

    private void stopGame() {
        timer.stop();
    }

    public GameGraphicLayer getGameGraphicLayer() {
        return gameGraphicLayer;
    }
}
