/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 *
 * @author ewilliams
 */
public class Head extends Segment {

    private int direction; // can be "UP", "DOWN", "LEFT" or "RIGHT"
    
    public Head(int x, int y, int dir) {
        super(x, y);
        this.direction = dir;
    }
    
    @Override
    // Hardcoded for now; will make it go based off direction later
    // Move the head; always done last
    public void move() {
        int[] pos = getPos(); // get this one's position
        
        // Determine direction, move position appropriately
        switch (direction) {
            case Snake.UP: pos[1]--; // move y up one
                break;
            case Snake.DOWN: pos[1]++; // move y down one
                break;
            case Snake.LEFT: pos[0]--; // move x left one
                break;
            case Snake.RIGHT: pos[0]++; // move x right one
        }
        
        // commit the new position
        setPos(pos);
    }
    
    @Override
    // SHOULD NOT BE USED
    public Segment getPrior() {
        throw new java.lang.UnsupportedOperationException("getPrior() should not be used on heads!");
    }
    
    @Override
    // SHOULD NOT BE USED
    public void setPrior(Segment s) {
        throw new java.lang.UnsupportedOperationException("setPrior() should not be used on heads!");
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set. Must be "UP", "DOWN", "LEFT" or "RIGHT"
     */
    public void setDirection(int direction) {
        if (direction == Snake.UP || direction == Snake.DOWN || 
                direction == Snake.LEFT || direction == Snake.RIGHT) {
            this.direction = direction;
        }
        else {
            throw new java.lang.IllegalArgumentException("setDirection must take either"
                    + "Snake.UP, Snake.DOWN, Snake.LEFT, or Snake.RIGHT");
        }
    }
    
    @Override
    public int getType() { return Entity.HEAD; }
}
