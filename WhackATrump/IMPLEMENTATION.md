# IMPLEMENTATION for Whack-a-Trump
We had two classes to create the game: a TrumpCode.java file and a TrumpApplet.java file. 

The TrumpApplet file extended Applet and just created a TrumpCode object and ran it with a MouseListener, while playing an audio file of The Entertainer by Scott Joplin. 

## TrumpCode
TrumpCode extended the Canvas.java file in the java.awt library. Essentially, it created a board, a thread, a Graphics object, and a score that was calculated using integers that tracked the number of times each candidate was hit. It also kept two timekeepers— one for the board itself, and one for each hole that told the mole to leave the hole after it had been hit. Finally, it contained booleans keeping track of which screen/menu we were on. 

### Graphics
This is a pretty straightforward usage of Canvas. We use the Draw method with BufferedImage and Graphics to draw images representing each of the different screens, as well as the board, holes, and moles. 

We then use the paint method to start a thread that allows for real-time input and output, necessary to actually play the game. 

We finally use the run method to sleep the given thread and continuously draw everything. 

### Menus / Screens
The game starts at a main menu/start screen where the user must press space to begin. 

From there, the user selects a political affiliation (Republican or Democrat) and then a difficulty level. The difficulty level determines how fast the moles appear and disappear. 

From there, the user is taken to the game screen, which consists of a board of "holes" that each of the "moles" pop out of. The user is supposed to use the mouse and click the candidates that are against their party to whack them. The user's score increases if they whack opposing candidates and decreases if they whack candidates from their own party. 

After time is up, the user is taken to the score calculation screen, where the candidate who was hit the fewest number of times is "elected president", and the user's score is given. If the user set a high score for the given difficulty level, we ask for their name and display the current high score at the top of the window. 

### Gameplay
We used the `mouseClicked` method in the MouseListener class  to determine when and where the user clicked the board. 

If the user clicked a hole, we determined which candidate was in the hole, if any. From there, we considered what the user's political affiliation was (having been set in a previous screen), and decided whether this hit would add to or subract from the user's current score. We update the score, displayed next to the board, accordingly.

We keep track of high scores in a separate file and read these files once the game is over to display what the high score is. 