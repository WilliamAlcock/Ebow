import java.awt.*;
import java.awt.event.*;

import javax.swing.*;	
import javax.media.opengl.GL3;
import javax.media.opengl.GLAnimatorControl;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import OpenGLTools.WindowDimensions;

import com.jogamp.opengl.util.FPSAnimator;

public class Ebow extends GLCanvas implements GLEventListener{
	/*
	 * 
	 */
	private static final long serialVersionUID = 677743482386352704L;
	// Define constants for the top-level container
	private static boolean FULLSCREEN = true;
	private static String TITLE = "Ebow Starfighter";  			// window's title
	private static final int CANVAS_WIDTH = 640;  				// width of the drawable
	private static final int CANVAS_HEIGHT = 480;				// height of the drawable		
	private static final int FPS = 60; 							// animator's target frames per second
	
	private WindowDimensions windowDimensions = new WindowDimensions(CANVAS_WIDTH,CANVAS_HEIGHT,45,1,100);
	
	private GameKeyListener keyListener;						// Keyboard listener
	private GameEngine gameEngine;								// Game Engine
	private Display display;									// Display renderer
	private GLU glu;
	
	public static void main (String agrs[]){
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {

			@Override
		    public void run() {
				// Create the OpenGL rendering canvas
				GLCanvas canvas = new Ebow();
			    
				// Create a animator that drives canvas' display() at the specified FPS.
	            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);	            
	            // Create the top-level container
				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
				frame.getContentPane().add(canvas);
				
				if (FULLSCREEN) {
					frame.setUndecorated(true);		// no title bar etc
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);	// Full screen mode
				} else {
					canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							// Use a dedicate thread to run the stop() to ensure that the
							// animator stops before program exits.
							new Thread() {
								@Override 
					            public void run() {
					                if (animator.isStarted()) animator.stop();
					                System.exit(0);
					            }
					        }.start();
						}
					});
					frame.setTitle(TITLE);
					frame.pack();	
				}
				
				frame.setVisible(true);
				animator.start(); 								// start the animation loop
			}
		});
	}
	
	public void quit() {
		new Thread() {
            @Override 
            public void run() {
            	GLAnimatorControl animator = getAnimator();
            	if (animator.isStarted()) animator.stop();
            	System.exit(0);
	        }
         }.start();
	}
	
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();      				// get the OpenGL 2 graphics context
		System.out.println(gl.glGetString(GL3.GL_SHADING_LANGUAGE_VERSION));
		glu = new GLU();                         				// get GL Utilities
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 				// set background (clear) color
	    gl.glClearDepth(1.0f);      							// set clear depth value to farthest
	    
	    gl.glEnable(GL3.GL_DEPTH_TEST); 								// enables depth testing
	    gl.glDepthFunc(GL3.GL_LEQUAL);  								// the type of depth test to do
        display = new Display(gl,windowDimensions);									// Create the Display renderer
        gameEngine = new GameEngine(display.getObjectDimensions(),windowDimensions);	// Create the Game Engine
        // Pass all the containers from the gameEngine to the display
        display.setBackgroundObjects(gameEngine.getBackgroundToDisplay());						
        display.setEnemyObjects(gameEngine.getEnemysToDisplay());
        display.setPlayerObjects(gameEngine.getPlayersToDisplay());
        display.setParticleObjects(gameEngine.getParticlesToDisplay());
        display.setHUDObjects(gameEngine.getHUDObjects());
        keyListener.setGameEngine(gameEngine);													// Set the keyBoard listener to point at the game engine
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	    GL3 gl = drawable.getGL().getGL3();  					// get the OpenGL 4 graphics context
	    if (height == 0) height = 1;   							// prevent divide by zero
	    
	    System.out.println("Window dimensions set : "+width+","+height);
	    windowDimensions.setWidth(width);
	    windowDimensions.setHeight(height);
	    windowDimensions.notifyObservers(windowDimensions);
	    
	    // Set the view port (display area) to cover the entire window
	    gl.glViewport(0, 0, width, height);
	 
	    // Setup perspective projection, with aspect ratio matches viewport
	    glu.gluPerspective(windowDimensions.getFovyDeg(), windowDimensions.getAspect(), 0.1, 110.0); 	// fovy, aspect, zNear, zFar			
	}
		
	/**
	 * Called back by the animator to perform rendering.
	 */
	public void display(GLAutoDrawable drawable) {
	    GL3 gl = drawable.getGL().getGL3();  							// get the OpenGL 2 graphics context
	    gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT); 	// clear color and depth buffers
	    // Check Keyboard Buffer
	    keyListener.checkKeyBuffer();
	    // Update Game Engine
	    gameEngine.tick();
	    // Update the display
	    display.tick();
	    // Flush buffer
	    gl.glFlush();
	}
	
	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as buffers.
	 */
	public void dispose(GLAutoDrawable drawable) { }
		
	public Ebow() {
        keyListener = new GameKeyListener(this);											// Create the Keyboard Listener
		this.addGLEventListener(this);		// for Handling GLEvents
	    this.addKeyListener(keyListener);	// for Handling KeyEvents 
	    this.setFocusable(true);
	    this.requestFocus();
	}
}
	