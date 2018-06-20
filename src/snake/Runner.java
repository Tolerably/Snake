/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import com.apple.eawt.Application;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * Main class for the game.
 * Handles major GUI business, and the backend
 * @author ewilliams
 */
public class Runner extends JFrame implements KeyListener {
    /* the timer */
    private final int START_DELAY = 500; // the starting delay, in milliseconds, on the timer
    private final int TIMER_DECREMENT = 50; // the amount to decrement the timer by each time the apple is captured
    // Netbeans recomended a fancy shmancy "lambda expression"; i'll need to look into those later
    private ActionListener ticker = (ActionEvent evt) -> {  
        tick();
    };
    private Timer t = new Timer(START_DELAY, ticker);

    // Back end declarations
    private Snake snake;
    private Apple apple;
    private Grid grid;
    
    // Set the snake's initial length
    private final int SNAKE_LENGTH = 3; // Head, Segment, Segment
    
    // Set the width and height of the grid
    private final int GRID_WIDTH = 15;
    private final int GRID_HEIGHT = 15;
    
    private final int APPLE_C = GRID_WIDTH / 2;
    private final int APPLE_R = GRID_HEIGHT / 2;
    
     // Set the dimensions of the main window
    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 300;
    
    // Set the title name for the window
    private final String TITLE = "Snake";
    
    // Set the location of the snake icon
    // Icon by Creative Tail, 2015. Used under Creative Commons Attribution 4.0 International License. 
    // https://creativecommons.org/licenses/by/4.0/legalcode
    
    private final String FILE_NAME = "icon.png";
    private final URL FILE_PATH = getClass().getResource(FILE_NAME);
    
    Image icon = Toolkit.getDefaultToolkit().getImage(FILE_PATH);
    
    
    // Declare the label that will display the score
    private JLabel scoreLabel;
    
    // TODO: write the TextFields into constants taht are just copied over
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Runner() throws IOException {
        // Instantiate the back end
        snake = new Snake(); // make the snake
        apple = new Apple(APPLE_C, APPLE_R, GRID_WIDTH, GRID_HEIGHT); // Make the apple
        grid = new Grid(GRID_WIDTH, GRID_HEIGHT, snake, apple); // make the grid
        
        // GUI and I/O
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout()); // Create the layout
        cp.add(grid); // add the visuals of the play grid to the container
        
        // initialize the score label
        scoreLabel = new JLabel("Score: 00");
        cp.add(scoreLabel);
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(TITLE);
        
        // Set the icon 
        Application.getApplication().setDockIconImage(icon);
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes sure we can close the window later
        addKeyListener(this); // Lets us read input
        
        t.start(); // Start timing
    }
    
    // Sets the direction of the snake if the key typed was W, A, S, D.
    @Override
    public void keyTyped(KeyEvent evt) {
        char c = evt.getKeyChar(); // get the key
        String input = String.valueOf(c); // Convert the key data to a string
        //System.out.println(input);
        
        // Decide what to do with it
        switch(input) {
            case "w": snake.setDirection(Snake.UP);     // W --> up             
                break;
            case "a": snake.setDirection(Snake.LEFT);   // A --> left
                break;
            case "s": snake.setDirection(Snake.DOWN);   // S --> down
                break;
            case "d": snake.setDirection(Snake.RIGHT);  // D --> right
                break;
            default: break; // If it's not one of those, we don't care
        }
        
        // Debug stuff
        System.out.println("'" + input + "' key was pressed");
    }
   
    // For Debugging
    @Override public void keyPressed(KeyEvent evt) { }
    @Override public void keyReleased(KeyEvent evt) { System.out.println("'" + evt.getKeyChar() + "' key was released"); }
    
    // Put the game one cycle forward
    private void tick() {
        // Check for collisions in the current move
        // Head cannot be facing: outer boundary, or segment
        if (grid.isCollision()) {
            gameOver();
            return; // get out of this function! AHHH!
        }

        // Move the snake
        snake.moveAll();
        
        // Handle the apple
        if (grid.isCapture()) {
            // Update the apple
            apple.move();
            apple.increment();
            
            // Add the extra segment
            // Get the tail
            Segment[] segs = snake.getSegments();
            Segment tail = segs[segs.length-1]; 
            
            // Start wherever the tail is
            int x = tail.getPos()[0]; 
            int y = tail.getPos()[1];
            Segment s = new Segment(x,y);
            
            // Add to the snake and grid
            snake.addSegment(s);
            grid.addMember(s);
            
            // Update the score display
            int score = apple.getScore();
            if (score < 10) {
                scoreLabel.setText("Score: 0" + score); // add a 0 at the front to keep it 2 digits
            }
            else {
                scoreLabel.setText("Score: " + score);
            }
            
            // Make the timer faster
            int delay = t.getDelay(); // get the delay
            delay -= TIMER_DECREMENT; // decrement
            t.setDelay(delay); // set the new delay
        }
        
        // update the grid
        grid.update();
    }

    // Called when the game is lost
    private void gameOver() {
        t.stop();
        JOptionPane.showMessageDialog(this, "You lose! You had a score of " + apple.getScore() + "!");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        /* Snake snake = new Snake();
        
        System.out.println(snake);
        snake.moveAll();
        System.out.println(snake);
        snake.moveAll();
        System.out.println(snake);*/
        
        Runner r = new Runner();
    }
}
