
/**
 * Write a description of class inputForGame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;
import java.applet.Applet;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
public class inputForGame extends JFrame
{
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    
    public static String input = "";
    public static BufferedImage bi = null; 
    
    KeyboardInput keyboard = new KeyboardInput(); // Keyboard polling 
    Canvas canvas;
    
    boolean gameover = false;
    public inputForGame() 
    {
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        
        // for keyboard using
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
    }
    
    public void run() throws InterruptedException
    {
      
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
        Graphics graphics = null;
        Graphics2D g2d = null;
        Color background = Color.BLACK;
        
        
        
        while(gameover == false ) {
          try {
          
            keyboard.poll();
            
                   
            g2d = bi.createGraphics();
            g2d.setColor( background );
            g2d.fillRect( 0, 0, WIDTH, HEIGHT );
            
            g2d.setColor( Color.GREEN );
            g2d.drawString( "Input 1:", 450, 500 ); 
            g2d.drawString( "Press M to use Mouse", 450, 512 ); 
            g2d.drawString( "Press W to use WASD", 450, 524 );
            
            if (keyboard.keyDown( KeyEvent.VK_M ))
            {
                input = "M";
            }
            else if (keyboard.keyDown( KeyEvent.VK_W ))
            {
                input = "W";
            }
            
            if (input != "")
            {
                g2d.drawString( input + " it is...", 485, 536 );
                break;
            }
            
            graphics = buffer.getDrawGraphics();
            graphics.drawImage( bi, 0, 0, null );
            if( !buffer.contentsLost() )
              buffer.show();
            
              
            Thread.sleep(10);
            
          } finally 
          {
            // Release resources
            if( graphics != null ) 
              graphics.dispose();
            if( g2d != null ) 
              g2d.dispose();
              
          }
          
          
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException, IOException
    { 
        
        
        inputForGame app = new inputForGame();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        inputForGame2.main(input, 0, bi); 
        System.exit( 0 );
        
    } 
}
