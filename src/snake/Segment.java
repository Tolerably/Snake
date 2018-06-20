/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 * A body segment of the snake
 * @author ewilliams
 */
public class Segment extends Entity {
    private static int nextId = 0; // id the next created segment should take on
    private final int ID; // Start numbering at 0; position corresponds to that in the chain
    private Segment prior; // The body segment coming before this one
    
    // Should only ever be instantiated by 
    public Segment(int x, int y) {
        super(x, y);
        ID = nextId;
        nextId++; // increment the id so that it is correct for the next guy.
    }
    
    // Return this segment's id
    public int getId() {
        return ID;
    }
    
    // Return the reference for the segment that comes *before* one in the chain
    // Never used by the Head subclass, leaving prior null for it
    public Segment getPrior() {
        return prior; // return the prior one
    }
    
    // Set the reference for the segment coming before in the chain.
    // Never used by the Head subclass, leaving prior null for it.
    public void setPrior(Segment s) {
        this.prior = s;
    }
    
    // Move the segment to wherever the one that comes before it in the chain was
    public void move() {
        Segment s = getPrior(); // Get the reference for the next guy in the list
        int[] priorPos = s.getPos(); // Get the next guy's position
        setPos(priorPos); // Set this segment's position to that of the next
    }
    
    @Override
    public int getType() { return Entity.SEGMENT; }
}
