/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Playing grid for the snake and apples. Handles location
 * @author ewilliams
 */
public class Grid extends JPanel {
    private Entity[][] grid; // Backend
    private List<Entity> membersList = new ArrayList<Entity>();
    
    private Head head; // the head of the snake
    private Apple apple; // the apple
    
    private final int WIDTH; // How many spaces wide should it be?
    private final int HEIGHT; // How many spaces tall should it be?
    
    private JLabel[] playGrid; // frontend; organizes labels for later editing
    private final int SPACE_WIDTH = 15; // Preferred width of the labels
    private final int SPACE_HEIGHT = 15; // Preferred height of the labels
    
    // Create a x by y grid, which keeps track of any Entities given to it
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Grid(int w, int h, Snake s, Apple a) {
        WIDTH = w;
        HEIGHT = h;
        
        // Initialize the back end part of the grid
        // Get the head out of the list of members
        head = (Head) s.getHead();
        apple = a;
        
        // Add the members to the member list
        membersList.addAll(Arrays.asList(s.getSegments()));
        membersList.add(apple);
        membersList.add(head);
        
        // update the grid positions for the first time
        updateBack();
        
        // Set up the area to display with
        this.setLayout(new GridLayout(HEIGHT, WIDTH));
        this.initFrontend();
        this.setBorder(new LineBorder(Color.BLACK)); // set the border around the game
    }
    
    // Updates the grid's frontend and back end
    public void update() {
        updateBack();
        updateFront();
    }
    
    // Updates the grid with the new location of the entities
    // Updates the backend of the grid
    private void updateBack() {
        grid = new Entity[HEIGHT][WIDTH];
        int[] pos; // new position of the member
        int x; // x coordinate
        int y; // y coordinate
        for (Entity member : membersList) {
            pos = member.getPos();
            x = pos[0];
            y = pos[1];
            
            grid[y][x] = member;
        }
    }
    
    /**
     * gets the character that should be displayed for the specific entity
     * @param e the entity whose display char is to be gotten
     * @return the string " ", "H", "@", or "S", depending if e is null, a Head,
     * an Apple, or a Segment.
     */
    private String getChar(Entity e) {
        String contents = "";
        
        if (e == null) { // Nothing's there. null values are why this is a function of grid, not a method of entity
            contents = " "; // Empty text field
        }
        else if (e.getType() == Entity.HEAD) { // It's a head
            contents = "H"; // H
        }
        else if (e.getType() == Entity.APPLE){ // It's an apple
            contents = "@";
        }
        else if (e.getType() == Entity.SEGMENT) { // It's a segment
            contents = "S"; // S
        }
        
        return contents;
    }
    
    // TODO: Use a buffered image for playing area
    // update the text displayed in the playing area
    private void updateFront() {
        Entity e; // holds the info on the entity we're pulling over from the grid
        
        int id = 0; // the number along the grid we're at. helps us go back to labels later
        String contents = "";
        
        for (int r = 0; r < HEIGHT; r++) {     // Point up or down --> y
            for (int c = 0; c < WIDTH; c++) {  // Point left or right --> x
                e = getMember(c, r);
                
                playGrid[id].setText(getChar(e)); // update the text
                id++; // go on to the next spot in the array
            }
        }
    }
    
    // Subroutine to initialize the stuff for the front end
    // Populate the play area with the grid space
    private void initFrontend() {
        Entity e; // holds the info on the entity we're pulling over from the grid
        
        JLabel jl; // Holds the info on the label we're putting in
        String contents; // holds the contents of the soon to be made field
        
        int id = 0; // the number along the grid we're at. helps us go back to labels later
        
        // Initialize the label array. Needs to hold however many gridspaces there are
        playGrid = new JLabel[HEIGHT * WIDTH];
        
        for (int r = 0; r < HEIGHT; r++) {     // Point up or down --> y
            for (int c = 0; c < WIDTH; c++) {  // Point left or right --> x
                e = getMember(c, r);
                
                jl = new JLabel(getChar(e)); // Initialize the text field w/ the text
                jl.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Set the alignment
                jl.setAlignmentY(JLabel.CENTER_ALIGNMENT); // set the alignment
                jl.setPreferredSize(new Dimension(SPACE_WIDTH, SPACE_HEIGHT)); // Set the preferred size
                
                playGrid[id] = jl; // put the label into our array
                id++; // go on to the next spot in the array
                
                this.add(jl); // Get ready to display the label
            }
        }
    }
    
    // Add an entity to the grid's member list
    public void addMember(Entity e) {
        membersList.add(e);
    }
    
    /** 
     * Get the member at the specified coordinates.
     * @param c the column to get from
     * @param r the row to get from
     * @return the member at the specified column and row
     * Will be null if no member exists there
     */
    public Entity getMember(int c, int r) {
        return grid[r][c]; // column, row
    }
    
    /**
     * Checks if the apple and head are at the same point
     * @return true/false depending if the apple has been captured
     */
    public boolean isCapture() {
        int[] headPos = head.getPos();
        int headX = headPos[0];
        int headY = headPos[1];
        
        int[] applePos = apple.getPos();
        int appleX = applePos[0];
        int appleY = applePos[1];
        
        return (headX == appleX && headY == appleY);
    }

    /** 
     * Checks if there is a potential collision
     * Used after the snake has set direction, but before the snake has moved
     * @return false: no collision
     *         true: there will be a collision, given the head's current direction
     */
    
    // T0DO: implement collision testing for the segments
    public boolean isCollision() {
        // Whether or not there will be a collision
        boolean result = false; // we start by assuming that there is no collision
        int[] headPos = head.getPos(); // where the head currently is
        int dir = head.getDirection(); // head's current direction
        
        // Check for boundaries
        // if on an edge and facing it, there will be a collision
        switch (dir) {
            case Snake.UP:    if (headPos[1] == 0)        { result = true; } break; // If on the top border, facing up, we stop
            case Snake.DOWN:  if (headPos[1] == HEIGHT-1) { result = true; } break; // If on the bottom border, facing down, stop
            case Snake.LEFT:  if (headPos[0] == 0)        { result = true; } break; // If on the left border, facing left, stop
            case Snake.RIGHT: if (headPos[0] == WIDTH-1)  { result = true; } break; // If on the right border, facing right, stop
        }
        
        // Check for segments
        switch (dir) {
            case Snake.UP:    if (getMember(headPos[0], headPos[1]-1) instanceof Segment) { result = true; } break; // If on the top border, facing up, we stop
            case Snake.DOWN:  if (getMember(headPos[0], headPos[1]+1) instanceof Segment) { result = true; } break; // If on the bottom border, facing down, stop
            case Snake.LEFT:  if (getMember(headPos[0]-1, headPos[1]) instanceof Segment)  { result = true; } break; // If on the left border, facing left, stop
            case Snake.RIGHT: if (getMember(headPos[0]+1, headPos[1]) instanceof Segment)  { result = true; } break; // If on the right border, facing right, stop
        }
        return result; // Return the result
    }
}
