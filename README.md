# Threading Game Project
This project is intended to demonstrate how to effectively and safely use threads in Java.

## The game
The user is prompted to enter the number of individuals (players) the
game should simulate with (between 0 and 20) and then enter the location of each bag file (csv format).
The game follows the following flow:
- create the bag objects
- create the players (a thread pool where every player is a thread)
- run the game:
    0. check if the game has been won (threadsafe boolean gameOver)
    1. select a random bag
    2. take a pebble out of the bag (create a pebble object and add it to pebHand array of the player):
        i.    check that hand size doesn't exceed 10 pebbles
        ii.   if bag is empty, refill it (by using the discard pile of that bag)
        iii.  take a pebble out of the bag
        iv.   keep doing this until the bag has 10 pebbles in it
    3. discard a pebble:
        i.    take a pebble out of the hand of a player
        ii.   put the pebble in the discard pile of that bag
    4. repeat [0] to [3] until the game has been won 
    5. if the bag total matches the target, set gameOver to true and print winning statement
    6. all threads will end at the end of beggining of their next cycle if gameOver is true

## Report
Finally, the justification for the specifics of our implementation can be fuond in 'ECM2414 Report.odt'
where we covered what our different classes were and why, explained why it's thread safe, and talked about
testing and code coverage.
