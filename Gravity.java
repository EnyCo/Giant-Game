
/**
 * Write a description of class Gravity here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.IOException;
import java.applet.Applet;
public class Gravity extends JFrame
{
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000; 
    public static BufferedImage bi2 = null;
    static int money = 0;
    class cha
    {
       int x, y;
       int w, h;
       int dx, dy;
    }
    public static String input2 = "";
    
    class obj
    {
       int x;
       int y;
       obj (int X, int Y)
       {
           x = X;
           y = Y;
       }
       
       public int getX()
       {
           return x;
       }
       public int getY()
       {
           return y;
       }
       
       public void setX(int X)
       {
           x = X;
       }
       public void setY(int Y)
       {
           y = Y;
       }
    }
    
    KeyboardInput keyboard = new KeyboardInput(); // Keyboard polling 
    Canvas canvas;
    Vector< obj > circles = new Vector< obj >(); // Circles
    Vector< obj > circles2 = new Vector< obj >(); // Circles
    
    cha me = new cha();// my circle
    
    Random rand = new Random(); // Used for random circle locations
    
    boolean gameover = false;
    boolean victory = false;
    int eSpeed = 1;
    
    int points = 0;  
    public Gravity() 
    {
      
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        
        // Hookup keyboard polling
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
        
        me.x = me.y = WIDTH/2;
        me.dx = me.dy = 5;
        me.w = me.h = 25;
    }
    
    public void run() throws InterruptedException, IOException
    {
      
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage bi = bi2;
        
        Graphics graphics = null;
        Graphics2D g2d = null;
        Color background = Color.BLACK;//Color.BLACK
        
        
        while(gameover == false ) {
          try {
          
            // Poll the keyboard
            keyboard.poll();
            // Should we exit?
            if( keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) )
              inputForGame2.main(input2, money, bi);
            
            // Clear the back buffer          
            g2d = bi.createGraphics();
            g2d.setColor( background );
            g2d.fillRect( 0, 0, WIDTH, HEIGHT );
            
            // Draw help
            g2d.setColor(  Color.GREEN );
            g2d.drawString( "Run from the circles", 20, 20 );
            g2d.drawString( "Use the arrow keys or WASD to move the circle", 20, 32 );
            g2d.drawString( "Red is Dead!", 20, 44 );
            g2d.drawString( "Points: " + points, 20, 56 );
            g2d.drawString( "$" + money, 20, 68 );
             
            // move add circles
            processInput();
            
            // Draw random circles
            g2d.setColor( Color.RED );
            for( obj p : circles ) 
            {
              g2d.drawOval( p.x, p.y, 25, 25 );
            }
            
            g2d.setColor( Color.RED );
            for( obj p : circles2 ) 
            {
                g2d.drawOval( p.x, p.y, 25, 25 );
            }
            
            
            
            // Draw circle
            String name = "Player";
            g2d.setColor(  Color.GREEN );
            g2d.drawOval( me.x, me.y, me.h, me.w);
            g2d.drawString(name, me.x - 5, me.y );
            
             if (gameover == true)
            {
                  g2d.setColor(  Color.GREEN );
                  g2d.drawString( "GAME OVER:", 475, 488 );   
                  if (victory == true)
                  {
                        g2d.drawString( "VICTORY IS", 475, 512 ); 
                        g2d.drawString( "  YOURS!  ", 475, 524 );
                  }
                  
            }
            
            // Blit image and flip...
            graphics = buffer.getDrawGraphics();
            graphics.drawImage( bi, 0, 0, null );
            if( !buffer.contentsLost() )
              buffer.show();
            
              
            // Let the OS have a little time...
            Thread.sleep(10);
            
          } finally {
            // Release resources
            if( graphics != null ) 
              graphics.dispose();
            if( g2d != null ) 
              g2d.dispose();
              
          }
          
        }
        Thread.sleep(3000);
        String x = playAgain.main();
        if (x.equals("Y"))
        {
            Gravity.main(input2, money, bi);
        }
        if (x.equals("N"))
        {
            inputForGame2.main(input2, money, bi);
        }
    }
         
    protected void processInput() throws InterruptedException 
    { 
        int ran = rand.nextInt(15);
        if (ran == 1) 
        {
            int x = rand.nextInt( WIDTH);
            int y = rand.nextInt( HEIGHT - 100);
            while (x == me.x)
            {
                x = rand.nextInt( WIDTH - 100);
            }
            while (y == me.y)
            {
                y = rand.nextInt( WIDTH - 100);
            }
            circles.add( new obj( x, y ) );
        }
        
        int ran1 = rand.nextInt(15);
        if (ran1 == 1)
        {
            int x = rand.nextInt( WIDTH - 100);
            int y = rand.nextInt( HEIGHT - 100);
            while (x == me.x)
            {
                x = rand.nextInt( WIDTH);
            }
            while (y == me.y)
            {
                y = rand.nextInt( WIDTH - 100);
            }
            circles2.add( new obj( x, y) );
        }
        
        int ran3 = rand.nextInt(1000);
        if (ran3 == 1 && eSpeed < 10)
        {
            eSpeed++;
        }
        
        for (int i = 0; i < circles.size(); i++)
        {
            if (circles.get(i).getY() > 25 && circles.get(i).getY() < 975)
            {
                circles.get(i).setY(circles.get(i).getY() + eSpeed);
            }
        }
        
        for (int i = 0; i < circles2.size(); i++)
        {
            if (circles2.get(i).getY() > 0 && circles2.get(i).getY() < 975)
            {
                circles2.get(i).setY(circles2.get(i).getY() - eSpeed);
            }
        } 
        
        if(input2 == "W")
        {
            
            // If moving left
            if( keyboard.keyDown( KeyEvent.VK_LEFT ) || keyboard.keyDown( KeyEvent.VK_A )) 
            {
              me.x -= me.dx;
              // Check collision with left
              if( me.x < 0 )
                me.x = 0;
            }
            // If moving right
            if( keyboard.keyDown( KeyEvent.VK_RIGHT ) || keyboard.keyDown( KeyEvent.VK_D ))
            {
              me.x += me.dx;
              // Check collision with right
              if( me.x + me.w > WIDTH - 1 )
                me.x = WIDTH - me.w - 1;
            }
        }
        if(input2 == "M")
        {
            Point p = MouseInfo.getPointerInfo().getLocation();
            int x = p.x;
            //int y = p.y;
            if (me.x < p.x && me.x + me.dx < WIDTH - 25)
            {
                me.x = me.x + me.dx;
            }
            if (me.x > p.x && me.x - me.dx > 0)
            {
                me.x = me.x - me.dx;
            }
            /*if (me.y < p.y && me.y + me.dy < HEIGHT - 25)
            {
                me.y = me.y + me.dy;
            }
            if (me.y > p.y && me.y - me.dy > 0)
            {
                me.y = me.y - me.dy;
            }*/
            Thread.sleep(10);
        }
        for( obj p : circles2 ) 
        {
           if (me.x - p.getX() <= 20 && me.x - p.getX() >= -20 && me.y - p.getY() <= 20 && me.y - p.getY() >= -20)
            {
                gameover = true;
                break;
            }
        }
        
        for(obj p : circles ) 
        {
           if (me.x - p.getX() <= 20 && me.x - p.getX() >= -20 && me.y - p.getY() <= 20 && me.y - p.getY() >= -20)
            {
                gameover = true;
                break;
            }
        }
        
        
        
        
        
    }
    
    public static void main(String input, int money2, BufferedImage im) throws InterruptedException, IOException
    { 
        input2 = input;
        money = money2;
        bi2 = im;
        Gravity app = new Gravity();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        System.exit( 0 ); 
    }
}
