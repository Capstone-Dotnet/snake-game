package com.dotnet.character.snake;

import com.dotnet.Position;
import com.dotnet.UnitResourceManager;
import com.dotnet.character.Unit;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

import static com.dotnet.character.snake.Snake.Direction.*;

public class Snake extends Unit {
    private final int DOT_SIZE = 50;
    private ArrayList<Unit> snakeResources;
    private Direction direction = LEFT;
    private int bodyWidth;
    private int bodyHeight;
    private int speed;


    public Snake() {
        super("snake1h", new ImageIcon("res/head1_new.png").getImage(), 60, 80);
        initSnake();
    }

    private void initSnake() {
        speed = 8;/*
        positions = new ArrayList<>();
        positions.add(new Position());*/
        snakeResources = new ArrayList<>();

        snakeResources.add(this);
        bodyWidth = 50;
        bodyHeight = 50;
    }

    public void incrementBody(UnitResourceManager unitResourceManager) {
        Unit tail = new Unit("snake1b", new ImageIcon("res/body1_new.png").getImage(), bodyWidth, bodyHeight);
        tail.setPosition(snakeResources.get(snakeResources.size() - 1).getPoint());
        snakeResources.add(tail);
        unitResourceManager.addUnit(tail);
    }

    public List<Unit> getDrawResource() {
        return snakeResources;
    }

    void down() {
        direction = DOWN;
    }

    void up() {
        direction = UP;
    }

    void right() {
        direction = RIGHT;
    }

    void left() {
        direction = LEFT;
    }

    public void move() {
        Position prePos = getPoint().clone();
        if (direction == LEFT) {
            getPoint().left(speed);
        } else if (direction == RIGHT) {
            getPoint().right(speed);
        } else if (direction == UP) {
            getPoint().up(speed);
        } else if (direction == DOWN) {
            getPoint().down(speed);
        }

        for (int i = 1; i < snakeResources.size(); i++) {
            Position pos = snakeResources.get(i).getPoint();
            int m = DOT_SIZE - speed;
            int n = speed;
            Position nextPos = new Position((m * pos.getX() + n * prePos.getX()) / DOT_SIZE, (m * pos.getY() + n * prePos.getY()) / DOT_SIZE);
            prePos.setPosition(pos);
            pos.setPosition(nextPos);
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}

