/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_game;
import javax.swing.*;
/**
 *
 * @author pritistha sarkar
 */
public class snake extends JFrame{
    snake(){
        board b=new board();
        add(b);
        pack(); //used to set the size of the frame. Internally calls the getPrefferedSize()method
        setLocationRelativeTo(null);//opens the window right at the center of the screen 
        //setLocation(x-axis,y-axis)
        
        setTitle("Snake Game");//sets name of the frame
        setResizable(false);//prevents the frame from getting resized
    }
    public static void main(String[] args){
        /*by default JFrame is not visible so it is made visible by the following*/
        new snake().setVisible(true);
    }
    
}
