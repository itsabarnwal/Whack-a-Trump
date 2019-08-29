# DESIGN for Whack-a-Trump

## Hole
The `Hole` object keeps track of its coordinate, whether it is filled by a mole and which mole it is filled by, the size of the hole, and whether or not it has been clicked. 

### Methods
* `drawHole` draws the Hole. if it is filled with a mole we draw the mole, and if it was hit then we draw the mole saying something before it disappears. 
* `sameCoordinates` determines if we clicked this Hole
* `fillDem` and `fillRep` fill the hole with a candidate from a political party
* from there, we have a number of getter methods returning each of the elements of the object. 

### Usage
To draw the Hole, we simply call drawHole. We continuously refresh the hole with threads and randomly fill each Hole, continuously detecting if a candidate has been hit. We use the getter method `isFilled` to determine if the hole had a mole in it when it was hit. 

## Board
The `Board` object is essentially the game board. It contains a 2D array of holes and an integer holding the number of holes filled. 

### Methods
* `paintBoard` draws each hole in the array
* `fillRandomHoles` randomly puts a candidate in various holes in the board for the user to attempt to whack
* `filledHoleContainsCoords` makes the hammer appear on the screen to show that the hole was whacked, and if we whacked a hole with a mole in it, return true
* the `isFilled___` methods return whether that hole was filled, and if it was filled with a particular type of mole
* `emptyHole` removes a mole from a hole
* lastly, we have getter and setter methods for how many holes we should fill

### Usage
To draw the Board, call paintBoard. From there, use the functions described above in conjunction with threads to continuously update the board with information including what holes are filled, what spaces were hit by the user, which candidate was in which hole, etc. 
