# slither.io

1.Introduction

	The aim of this project is to implement a simple Slither.IO game based on Java using no 3rd party libraries. The gameplay should be as following. 

	You are a worm trying to survive by eating foods and avoiding non foods. Eating makes you lose speed and gain size. You can boost up to increase your speed for a short amount of time but this will reduce your size. Game is over when you hit other players.

2.Method

	Due to time constraints our main approach was to use building blocks from similar example games and patch them into a viable Slither.IO clone. 
- We have used ArrayList<Point> objects to hold our snake, foods and nonfoods.
- We have used JoptionPane for user dialogue interaction.
- We have utilized threads to implement boosting mechanism and the replacement of foods and non foods.
- We have used Jframe to build GUI elements.
- We have used Graphics2D to build 2D graphics of the game.
- We have failed to implement a network gameplay, advanced animations and proper OO hierarchy.

3.Conclusion

	The game runs smoothly and provides a fun gameplay especially with the boosting mechanic. Due to lack of other players the game ends when you boost too much or eat too much non foods. 
