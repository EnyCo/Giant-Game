
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.applet.Applet;
public class tpv extends JFrame
{
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    static int money2 = 0;
    static String input2 = "";
    public static BufferedImage bi2 = null;
    public static int level = 0;
    public static int blueHealth = 20;
    public static int redHealth = 20;
    public static int blueC = 0;
    public static int redC = 0;
    static boolean revival = false;
    class user
    {
       int x, y;
       int w, h;
       int dx, dy;
    }
    
    KeyboardInput keyboard = new KeyboardInput(); // Keyboard polling 
    Canvas canvas;
    
    user me = new user();// my circle
    user me2 = new user();// my circle
    Random rand = new Random(); // Used for random circle locations
    
    int x1 = WIDTH/2;//rand.nextInt( WIDTH - 25);
    int y1 = HEIGHT/2;//rand.nextInt( HEIGHT - 25);
    int Gpo = 0;
    int Rpo = 0;
    
    boolean gameover = false;
    boolean alive0 = true;
    boolean check = false;
    
    String victor = "";
    public tpv() 
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
        
        me.x = me.y = 0;
        me.dx = me.dy = 5;
        me.w = me.h = 25;
        
        me2.x = me2.y = WIDTH-25;
        me2.dx = me2.dy = 5;
        me2.w = me2.h = 25;
        level = 0;
    }
    
    public void run() throws InterruptedException, IOException
    {
      
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = bi2;
        
        Graphics graphics = null;
        Graphics2D visuals = null;
        Color background = Color.BLACK;
        
        
        while(gameover == false ) {
          try {
              
            // use the keyboard
            keyboard.poll();
            if( keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) )
              inputForGame2.main(input2, money2, image);
            
            // Clear the back buffer          
            visuals = image.createGraphics();
            visuals.setColor( background );
            visuals.fillRect( 0, 0, WIDTH, HEIGHT );
            
            // Draw help
            visuals.setColor(  Color.WHITE ); 
            visuals.drawString( "Try to get all the pink circles", 20, 20 );
            visuals.drawString( "Use the arrow keys or WASD to move your circle", 20, 32 );
            visuals.drawString( "Find what each key does!", 20, 48 );
            visuals.drawString( "Press ESC to exit", 20, 60 );
            visuals.drawString( "Green's Points:" + Gpo, 20, 72 );
            visuals.drawString( "Red's Points: " + Rpo, 20, 84 );
            visuals.drawString( input2, 20, 96 );
            
            // move add circles
            processInput();
           
            visuals.setColor( Color.CYAN );
            if (alive0 == true)
            {
                visuals.drawOval( x1, y1, 25, 25 ); 
            }
            
            
            // Draw user
            String name = "Blue:";
            visuals.setColor(  Color.GREEN );
            visuals.drawOval( me.x, me.y, me.h, me.w);
            visuals.drawString(name + " " + blueHealth, me.x - 10, me.y );
            /*for (int i = 0; i < wall1.size(); i++) 
            {
                visuals.drawOval( (int)wall1.get(i).getX(), (int)wall1.get(i).getY(), 3, 3); 
            }*/
            
            String name2 = "Red:";
            visuals.setColor(  Color.RED );
            visuals.drawOval( me2.x, me2.y, me2.h, me2.w);
            visuals.drawString(name2 + " " + redHealth, me2.x - 10, me2.y );
            if (gameover == true && Gpo > 2)
            {
                  visuals.setColor(  Color.GREEN );
                  visuals.drawString( "Winner is: " + victor, 475, 470 ); 
                  
            }
            if (gameover == true && Rpo > 2)
            {
                  visuals.setColor(  Color.RED );
                  visuals.drawString( "Winner is: " + victor, 475, 470 ); 
            }
            graphics = buffer.getDrawGraphics();
            graphics.drawImage( image, 0, 0, null );
            if( !buffer.contentsLost() )
            {
              buffer.show();
            }
            Thread.sleep(10);
            
          } 
            finally 
          {
            if( graphics != null ) 
            {
               graphics.dispose();
            }
            if( visuals != null ) 
            {
               visuals.dispose();
            }
          }
          
        }
        Thread.sleep(3000);
        money2 = money2 + 5;
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
        
        FileWriter fileWriter = new FileWriter("money.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print((everything+money2)+"");
        printWriter.close();
        
        String x = playAgain.main();
        if (x.equals("Y"))
        {
            tpv.main(input2, money2, image);
        } 
        if (x.equals("N"))
        {
            inputForGame2.main(input2, money2, image);
        }
    }
        
    protected void processInput() throws InterruptedException 
    {
        int ranBluSpwn = rand.nextInt(10000);
        ranBluSpwn = 1;
        
        if (ranBluSpwn == 1 && alive0 == false && (Gpo > 0 || Rpo > 0))
        {
            alive0 = true;
            x1 = rand.nextInt( WIDTH - 25);
            y1 = rand.nextInt( HEIGHT - 25);
            check = false;
        }
        if (me.x - x1 <= 10 && me.x - x1 >= -10 && me.y - y1 <= 10 && me.y - y1 >= -10 && check == false)
        {
            alive0 = false;
            check = true;
            Gpo++;
        }
        if (me2.x - x1 <= 10 && me2.x - x1 >= -10 && me2.y - y1 <= 10 && me2.y - y1 >= -10 && check == false)
        {
            alive0 = false;
            check = true;
            Rpo++;
        }
        
        //circle blue
        
        
        if (x1 < me.x || x1 >= HEIGHT - 25)
        {
            x1--;   
        }
        if (x1 > me.x || x1 <= 0)
        {
            x1++;   
        }
        if (y1 < me.y || y1 >= WIDTH - 25)
        {
            y1--;   
        }
        if (y1 > me.y || y1 <= 0)
        {
            y1++;   
        }
        
        if (x1 < me2.x || x1 >= HEIGHT - 25)
        {
            x1--;   
        }
        if (x1 > me2.x || x1 <= 0)
        {
            x1++;   
        }
        if (y1 < me2.y || y1 >= WIDTH - 25)
        {
            y1--;   
        }
        if (y1 > me2.y || y1 <= 0)
        {
            y1++;   
        }
        // If moving down
        if(input2 == "W" || input2 == "M")
        {
            int gCh = 0;
            int rCh = 0;
            if( keyboard.keyDown( KeyEvent.VK_S)) 
            {
              me.y += me.dy;
              gCh = 1;
              // Check collision with botton
              if( me.y + me.h > HEIGHT - 1 )
                me.y = HEIGHT - me.h - 1;
            }
            // If moving up
            if(keyboard.keyDown( KeyEvent.VK_W )) 
            {
              me.y -= me.dy;
              gCh = 1;
              // Check collision with top
              if( me.y < 0 )
                me.y = 0;
            }
            // If moving left
            if( keyboard.keyDown( KeyEvent.VK_A )) 
            {
              me.x -= me.dx;
              gCh = 1;
              // Check collision with left
              if( me.x < 0 )
                me.x = 0;
            }
            // If moving right
            if( keyboard.keyDown( KeyEvent.VK_D )) 
            {
              me.x += me.dx;
              gCh = 1;
              // Check collision with right
              if( me.x + me.w > WIDTH - 1 )
                me.x = WIDTH - me.w - 1;
            }
            
            if( keyboard.keyDown( KeyEvent.VK_SPACE ) && blueC < 3) 
            {
              x1 = rand.nextInt( WIDTH - 25);
              y1 = rand.nextInt( HEIGHT - 25);
              blueC++;
            }
            if( keyboard.keyDown( KeyEvent.VK_Q) && gCh == 0)  
            {
               me.x = WIDTH - me.x;
               me.y = HEIGHT - me.y;
               gCh = 0;
            }
            //player 2
            
            if( keyboard.keyDown( KeyEvent.VK_DOWN )) 
            {
              me2.y += me2.dy;
              // Check collision with botton
              if( me2.y + me2.h > HEIGHT - 1 )
                me2.y = HEIGHT - me2.h - 1;
            }
            // If moving up
            if( keyboard.keyDown( KeyEvent.VK_UP )) 
            {
              me2.y -= me2.dy;
              // Check collision with top
              if( me2.y < 0 )
                me2.y = 0;
            }
            // If moving left
            if( keyboard.keyDown( KeyEvent.VK_LEFT )) 
            {
              me2.x -= me2.dx;
              // Check collision with left
              if( me2.x < 0 )
                me2.x = 0;
            }
            // If moving right
            if( keyboard.keyDown( KeyEvent.VK_RIGHT ))
            {
              me2.x += me2.dx;
              // Check collision with right
              if( me2.x + me2.w > WIDTH - 1 )
                me2.x = WIDTH - me2.w - 1;
            }
            if( keyboard.keyDown( KeyEvent.VK_CONTROL ) && redC < 3) 
            {
               x1 = rand.nextInt( WIDTH - 25);
               y1 = rand.nextInt( HEIGHT - 25);//wall2.add(new Point(me2.x, me2.y));
               redC++;
            }
            if( keyboard.keyDown( KeyEvent.VK_SHIFT ) && rCh == 0) 
            {
               me2.x = WIDTH - me2.x;
               me2.y = HEIGHT - me2.y;
               rCh = 0;
            }
        }
        
        
        
        if (Rpo == 3)
        {
            gameover = true; 
            victor = "Red";
        }
        
        if (Gpo == 3)
        {
            gameover = true; 
            victor = "Green";
        }
    }
    
    public static void main(String input, int money, BufferedImage im) throws InterruptedException,IOException
    {
        input2 = input;
        money2 = money;
        bi2 = im;
        tpv app = new tpv();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        money = money + level;
        System.exit( 0 );
    }
}
