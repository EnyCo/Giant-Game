
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
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.applet.Applet;
public class inputForGame2 extends JFrame
{
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    public static BufferedImage G2 = null;
    public static String input2 = "";
    public static String input = "";
    
    KeyboardInput keyboard = new KeyboardInput(); // Keyboard polling 
    Canvas canvas; 
    
    boolean gameover = false;
    public inputForGame2() 
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
        BufferedImage bi = G2;
        
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
            g2d.drawString( "Press R to play Original", 450, 512 ); 
            g2d.drawString( "Press S to play Swarm", 450, 524 );
            g2d.drawString( "Press G to play Gravity", 450, 536 );
            g2d.drawString( "Press F to play Fighter", 450, 548 );
            
            g2d.drawString( "Press ESC to quit", 450, 572 );
            
            if (keyboard.keyDown( KeyEvent.VK_R ))
            {
                input2 = "R";
            }
            else if (keyboard.keyDown( KeyEvent.VK_S ))
            {
                input2 = "S";
            }
            else if (keyboard.keyDown( KeyEvent.VK_G ))
            {
                input2 = "G";
            }
            else if (keyboard.keyDown( KeyEvent.VK_F ))
            {
                input2 = "F";
            }
            else if (keyboard.keyDown( KeyEvent.VK_ESCAPE ))
            {
                input2 = "E";//place save data here
            }
            
            if (input2 != "")
            {
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
    
    public static void main(String input, int money2, BufferedImage G) throws InterruptedException, IOException
    {  
        input2 = "";
        int money = money2;
        BufferedReader br = new BufferedReader(new FileReader("money.txt"));
        int everything = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) 
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            if(sb.toString().indexOf("\"") > 0 && sb.toString().length() > 0)
            {
                String p = sb.toString().substring(sb.toString().indexOf("\""), sb.toString().length());
                everything = Integer.parseInt(p);
            }
        } 
        finally 
        {
            br.close();
        }
        if (everything != 0)
        {   
            money = everything; 	
        }
        System.out.println(money);
        G2 = G;
        inputForGame2 app = new inputForGame2();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        if (input2.equals("R"))
        {
            original.main(input, money, 0, 0);
        }
        if (input2.equals("S"))
        {
            swarm.main(input, money,0 , 0); 
        }
        if (input2.equals("G"))
        {
            Gravity.main(input, money, G); 
        }
        if (input2.equals("F"))
        {
            tpv.main(input, money, G); 
        }
        System.exit( 0 );
    
    } 
}

