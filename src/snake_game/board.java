/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_game;
import java.awt.*; //package of Dimension()
import javax.swing.*; //package of JPanel and JFrame
import java.awt.event.*; //package of ActionListener

/**
 *
 * @author pritistha sarkar
 */

/*this class is extending to JPanel and not JFrame because JPanel is a container 
  and you can open a container in a window but since JFrame is a GUI window you cannot open
  one window over another window since the snake class is already extending to the JFrame class*/
public class board extends JPanel implements ActionListener{
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private int dots;
    
    /*frame size is 300x300 which is equal to 90000 and DOT_SIZE is 10x10 so the maximum number of dots
      that can fit in our frame is 90000/100 which is equal to 900*/
    private final int DOT_SIZE=10;
    private final int NO_OF_DOTS=900;
    
    //Co-ordinates of snake in the x and y axis
    private final int x[]=new int[NO_OF_DOTS];
    private final int y[]=new int[NO_OF_DOTS];
    
    private final int RANDOM_POSITION=29;
    
    /*apple's position is not fianl because it is going to appear at 
     random positions in our frame*/
    private int apple_x; 
    private int apple_y;
    
    //creating a reference variable of class Timer so that we can apply a delay to the snake's movements
    private Timer timer;
    
    /*these variables are used to check which keys are being pressed in the keyboard*/
    private boolean left=false;
    private boolean right=true;//snake initially moves to the right
    private boolean up=false;
    private boolean down=false;
    
    /*Checks if the game is over or not*/
    private boolean inGame=true;
    
  
    board(){
        
        addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(300,300));//sets the size of the frame
        setBackground(Color.BLACK);//sets the background color of the frame
        loadImage();
        
       
        setFocusable(true);//KeyListener doesn't work without setFocusable(true) as initially its false
        initGame();
    }
    
    //creating a separate method to load the image icon in our frame
    public void loadImage(){
        
        //to access any icon from the system we need to create an object of class ImageIcon
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/apple.png"));
        apple=i1.getImage();
        
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/dot.png"));
        dot=i2.getImage();
        
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/head.png"));
        head=i3.getImage();
        
    }
    
    public void initGame(){
        dots=3;//initially the snake has 3 images of dots as its body
        
        /*Since the snake has initially 3 dots as
          the size of its body the first thing that we need to do is to position the dots
          one after the other in the frame. The head of the snake is at (x[50],y[50]).
          all the three dots are at the same y co-ordinate but at different x co-ordinates. the
          second dot is at x[50-DOT_SIZE] and the third dot is at x[50-2*DOT_SIZE]*/
        for(int i=0;i<dots;i++){
            x[i]=50-DOT_SIZE*i;//head:50, 2nd dot:50-10, 3rd dot:50-20
            y[i]=50;
        }
        
        locateApple();
        
        /*creates a delay of 140s*/
        /*timer calls actionPerformed()*/
        timer=new Timer(140,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg="Game Over";
        String restart="Press SPACE to restart";
        Font font=new Font("SAN_SERIF", Font.BOLD, 13);
        FontMetrics m= getFontMetrics(font);
        
       g.setColor(Color.WHITE);
       g.setFont(font);
       g.drawString(msg,(300-m.stringWidth(msg))/2 , 300/2);
       g.drawString(restart,(300-m.stringWidth(restart))/2, 330/2);
       
        
    }
    
    /*this method generates a new apple everytime it gets eaten*/
    public void locateApple(){
        /*the Math.random() method generates a random number between 0 and 1. The
          value of the RANDOM_POSITION should be between 1 and 30 or else the calculated 
          co-ordinates of the apple will exceed the JFrame size of 300x300*/
          
        /*The RANDOM_POSITION value should be taken in such a way so that the apple covers the
          maximum area of the frame and is not restricted only to a certain section of the frame.
          the maximum value selected is 29 such that the apple's co-ordinates can be anywhere
          between (1-290) and since the DOT_SIZE is 10 (290+10=300) the apple remains within the frame
          */
        int r;
        r= (int)(Math.random()*RANDOM_POSITION);
        apple_x=(r*DOT_SIZE);
        
        r=(int)(Math.random()*RANDOM_POSITION);
        apple_y=(r*DOT_SIZE);
    }
    
   /*this method checks if the snake got an apple or not. the snake is said to get
     an apple if the co-ordinates of the apple is same as that of the co-ordinates
     of the snake's head(Because it cant't eat from its tail.DUH!). Everytime the snake
     gets an apple its size will increase by 1 dot. Once it has eaten one apple the
     locateApple() method is called to generate a new apple in a random position.*/
   public void gotApple(){
       if(x[0]==apple_x && y[0]==apple_y){
           dots++;
           locateApple();
       }
   }
   
   /*this method checks if the snake has collided or not. Collison occurs when the snake
    collides with itself or collides with the frame
    1st: Checks if the snake has collided with the right part of the frame
    2nd: Checks if the snake has collided with the downward part of the frame
    3rd: Checks if the snake has collided with the left part of the frame
    4th: Checks if the snake has collided with the upward part of the frame
    5th: Checks if the the snake has collided with itself or not(the 
         first condition out of the three is for ensuring that the length of 
         the snake is atleast 5 dots because it wont't collide with itself
         if its less than that. The remaining conditions check if the co-ordinates 
         of the head is same as that of the co-ordinates of any other dot of its body
         i.e. if it has collided with itself or not
   
   Once the inGame becomes false that means the snake has collided and then the timer stops
   */
   public void checkCollison(){
       
       if(x[0]>=300){
           inGame=false;
       }
       if(y[0]>=300){
           inGame=false;
       }
       if(x[0]<0){
           inGame=false;
       }
       if(y[0]<0){
           inGame=false;
       }
       for(int j=dots;j>0;j--){
           if(j>4 && x[0]==x[j] && y[0]==y[j]){
               inGame=false;
               break;
           }
       }
       if(inGame==false){
           timer.stop();
       }
   }
   
   /******MAIN FUNCTION*******/
   /*this function is responsible for making the snake move by changing its co-ordinates
     If left key is pressed, the y co-ordinate remains unchanged but the position of the head 
     changes as NEW_POSITION=INITIAL_POSITION-DOT_SIZE
   
     If right key is pressed it changes as NEW_POSITION=INITIAL_POSITION+DOT_SIZE and 
     same for up and down. So this wss only for the head of the snake. The remaining
     dots will also be changing their positions. The second dot will come at the position
     of the first dot and the third dot comes at the position of the second dot and so on.
     To do this the for loop is used*/
   
   public void move(){
       for(int i=dots;i>0;i--){
           x[i]=x[i-1];
           y[i]=y[i-1];
       }
       if(left){
           x[0]-=DOT_SIZE;
       }
       if(right){
           x[0]+=DOT_SIZE;
       }
       if(up){
           y[0]-=DOT_SIZE;
       }
       if(down){
           y[0]+=DOT_SIZE;
       }
       
   }
    /*whenever we implement an interface in java we need to override its abstract methods in the implementing
      class*/
      @Override
      public void actionPerformed(ActionEvent e){
        
        /*first we need to check if we are still playing the game or not*/
        if(inGame){
            /*then we need to check if the snake got an apple or not*/
            gotApple();
            checkCollison();
            move();
            
        }
        
        /*Since the look of the componenet is changing everytime as the image icons are
          changing hence the repaint() method is called*/
        repaint();
        
    }
    
    /*KeyAdapter class has methods that gives the control of the frame over to the
      keys in the keyboard since we want to control the game using the up/down/left/right keys*/
    private class TAdapter extends KeyAdapter{
        
       @Override
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode(); //returns an integer that denotes which key is pressed
            
            /*the second condition implies that if the snake is moving up it cannot change its
              direction to down and if its moving left it cannot change its direction to right*/
            if(key==KeyEvent.VK_SPACE){
                inGame=true;
                right=true;
                left=false;
                up=false;
                down=false;
                initGame();
            }
            if(key==KeyEvent.VK_LEFT && (!right)){
                left=true;
                up=false;
                down=false;
            }
             if(key==KeyEvent.VK_RIGHT && (!left)){
                right=true;
                up=false;
                down=false;
            }
              if(key==KeyEvent.VK_UP && (!down)){
                up=true;
                left=false;
                right=false;
            }
               if(key==KeyEvent.VK_DOWN && (!up)){
                down=true;
                left=false;
                right=false;
            }
               
        }
    }
    
}
