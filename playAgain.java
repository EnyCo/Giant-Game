
/**
 * Write a description of class playAgain here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;
public class playAgain extends JFrame
{
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    
    public static String input3 = "";
    
    KeyboardInput keyboard = new KeyboardInput(); // Keyboard polling 
    Canvas canvas;
    
    boolean gameover = false;
    public playAgain() 
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
        BufferedImage bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
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
            g2d.drawString( "Input 2:", 450, 500 );   
            g2d.drawString( "Press N to quit", 450, 524 );
            g2d.drawString( "Press Y to play again", 450, 548 ); 
            g2d.drawString( "Press R to revive yourself", 450, 560 );            
            if (keyboard.keyDown( KeyEvent.VK_Y ))
            {
                input3 = "Y";
            }
            else if (keyboard.keyDown( KeyEvent.VK_N ))
            {
                input3 = "N";
            }
            else if (keyboard.keyDown( KeyEvent.VK_R ))
            {
                input3 = "R";
            }
            
            if (input3 != "")
            {
                g2d.drawString( input3 + " it is...", 485, 536 );
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
    
    public static String main() throws InterruptedException
    { 
        input3 = "";
        playAgain app = new playAgain();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        return input3;
    } 
}