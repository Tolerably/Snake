/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.lang.Math;

/**
 * The apple
 * @author ewilliams
 */
public class Apple extends Entity{
    private int score = 0; // How many times the apple has been captured
    private final int COLUMNS; // How many columns in the grid
    private final int ROWS; // How many rows in the grid
    
    /**
     * Constructs an apple
     * @param x where to place the apple to start, x-wise
     * @param y where to place the apple to start, y-wise
     * @param colBound width of the grid
     * @param rowBound height of the grid
     */
    public Apple(int x, int y, int colBound, int rowBound) {
        super(x,y);
        
        COLUMNS = colBound;
        ROWS = rowBound;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * increment the score by one
     */
    public void increment() {
        score++;
    }
    
    /**
     * Move the apple to a random point on the grid.
     */
    public void move() {
        int randCol = (int) (Math.random() * COLUMNS); // new column to go to   x-pos
        int randRow = (int) (Math.random() * ROWS); // new row to go to         y-pos
        
        int[] pos = {randCol, randRow}; // Convert to a position array
        setPos(pos); // commit position
    }
    
    @Override
    public int getType() { return Entity.APPLE; }
}
