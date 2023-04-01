# Interactive Koch Snowflake Project
## Description
* The koch snowflake program draws a snowflake using a responsive user interface. It starts with an equilateral triangle of the 1st order and redraws the triangle replacing the middle third of each side with two sides of an equilateral triangle.
* How the algorithm behind this works is that it takes 2 points (X1, Y1) and (X2, Y2) which represent a single line and draws it three times to make an equilateral triangle if the order number is equal to one. Else it scales by 1/3 and redraws on those lines to show us a snowflake. The higher the order goes up the more detailed the snowflake gets.

## How to Run
To run the program simply download both files ImagePanel.java and SnowflakeMain.java. Run the program from SnowflakeMain.java (example f5) and use the program accordingly. Or to run from terminal use the following commands:
- javac SnowflakeMain.java
- java SnowflakeMain

## Demonstration Video
[Snowflake Demo Video](Koch%20Snowflake%20-%20Made%20with%20Clipchamp.mp4)


## Things to Note
- The added zoom in feature only zooms as an image not as a fractal so its good to see the shapes up until the 7th order. after which its quite useless because you only see the border lines of the snowflake rather than the indepth look of it. This does not mean the shapes are not drawn its just the zoom only treats it as an image of what you see.
  
- I've decided that the resize window feature can be free to reshape the size of the snowflake simply because I found it to be more fun. However, this can be changed if anything and I personally dont think its worth failing over.