/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;
import java.lang.IllegalArgumentException;

/**
 * 1x1 thing on the grid
 * @author ewilliams
 */
public abstract class Entity {

    // Ease of use constants
    public static final int SEGMENT = 0;
    public static final int HEAD = 1;
    public static final int APPLE = 2;
    
    private int xPos; // position x-wise in the grid
    private int yPos; // position y-wise in the grid
    
    public Entity(int x, int y) {
        xPos = x;
        yPos = y;
    }
    
    // Returns the position on the grid as an int array of length 2. order is x,y
    public int[] getPos() {
        int[] pos = {xPos, yPos};
        
        return pos; // return the array
    }
    
    public void setPos(int[] pos) {
        // Throw out bad arguments
        if (pos.length != 2) {
            throw new IllegalArgumentException("setPos() only takes arrays of length 2!");
        }
        
        // Set the positions accordingly
        xPos = pos[0];
        yPos = pos[1];
    }
    
    // Returns the type of entity the entity is
    public abstract int getType();
}
