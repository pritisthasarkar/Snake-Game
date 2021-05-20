# Snake-Game
This project is based on the classic Snake-Game. The logic behind the game is there is a snake whose length increases as it goes on eating apples. 
The game is terminated if the snake touches itself or if it exceeds beyond the boundaries. The snake and the apple is generated using images onto the frame.
The apple is generated at different positions in the frame using the Math.random() function. The concept is based on the Cartesian Co-ordinate System. The 
snake has a collection of dot images as its body.

The length of the snake increases if the coordinates of the apple matches with the coordinates of the head of the snake(that is when it eats an apple). 
Collision occurs if the coordinates of the snake's head becomes equal to the coordinates of any of the dots in its body(Collision with itself) of if the coordinates
of the snake's head exceeds the upper and lower bounds of the frame and then the game is over(Collision with frame).

Technologies Used:
Java, Swing, AWT
