/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The snake
 * @author ewilliams
 */
public class Snake {
    private List<Segment> segList = new ArrayList<Segment>();
    private int length = 3; // start off with a length of 3
    
    // Direction constants; make things run faster BC ints
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    
    public Snake() {
        segList.add(new Head(0, 0, RIGHT));
        segList.add(new Segment(0, 1));
        segList.add(new Segment(0, 2));
        
        // Instantiate the "prior" field for non-head segments
        int i = length - 1; // Start at the last one
        Segment s;
        Segment prior;
        // not including 0; we're not instantiating prior for the head
        while (i > 0) {
            s = getSegment(i);
            prior = getSegment(i-1);
            s.setPrior(prior);
            
            i--; // DECREMENT THE INDEX
        }
    }
    
    // Return the segment in the chain at the given index
    private Segment getSegment(int id) {
        return segList.get(id);
    }
    
    /**
     * Loop through all the segments, executing their move method.
     * Starts at the back, moves forward
     */
    public void moveAll() {
        // subtracts one off the length to convert it index of the last segment in the chain
        int finalSegmentId = length - 1;
        
        // loop backwards
        for (int i = finalSegmentId; i >= 0; i--) {
            getSegment(i).move(); // move it
        }
    }
    
    // Passes along the given direction to the head of the snake
    public void setDirection(int dir) {
        Head head = (Head) getSegment(0); // Get the head
        head.setDirection(dir); // Let it know what's up
    }
    
    @Override
    public String toString() {
        String output = ""; // this is what we'll return later
        
        String workingLine; // this is the output for the specific line we work on
        int[] pos;
        for (Segment s : segList) {
            pos = s.getPos(); // get the position for future reference
            workingLine = s.getClass().toString(); // Get the class
            workingLine += ", ID: " + s.getId() + ", x-Position: " + pos[0] + 
                    ", y-Position: " + pos[1] + "\n";
            
            output += workingLine;
        }
        
        return output;
    }
    
    // Adds a segment to the end of the snake at the coordinates x,y
    public void addSegment(int x, int y) {
        Segment newSeg = new Segment(x,y); // initialize the new segment
        segList.add(newSeg); // add it on
        
        Segment prior = getSegment(length-1); // get the segment that used to be the end
        newSeg.setPrior(prior); // Set the reference
        
        length++;
    }
    
    public void addSegment(Segment s) {
        segList.add(s);
        
        Segment prior = getSegment(length-1); // get the segment that used to be the end
        s.setPrior(prior); // Set the reference
        
        length++;
    }
    
    // Returns an array of all segments in the snake
    public Segment[] getMembers() {
        return segList.toArray(new Segment[this.length]);
    }
    
    // Returns an array of all segments EXCEPT the head.
    public Segment[] getSegments() {
        return Arrays.copyOfRange(getMembers(), 1, length);
    }
    
    // Returns the head of the snake
    public Head getHead() {
        return (Head) getSegment(0);
    }
    
    /** gets the length of the snake
     * @return length of the snake
     */
    public int getLength() {
        return length;
    }
}
