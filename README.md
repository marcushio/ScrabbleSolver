_SCRABBLE_


__How to run__
It is very important to note that both of these need to be able to find the dictionary file that you're reading in. There needs to be a directory named "res" in the same directory as the jar with the dictionary file inside it. The text file with the information about the tiles should be in there as well. I used
getClass().getClassLoader().getResourceAsStream(resource) to try to prevent this so
that you only needed to use the jar. However, it never worked properly for me. Using these conditions described (that are basically with a directory setup similar to where we had our project) both programs worked just fine.

**Running the solver...**
All testing I've done was from the windows command line. I used the following command...
java -jar ScrabbleSolver_marcustrujillo.jar sowpods.txt < example_input.txt
Where we use a text file with our boards we wish to solve as standard input. The
format is important. The text file has to be written in the same format as the
example input we were given. Once the command is written, it processes all boards, and outputs the solutions to the terminal.

**Running the game...**
java -jar ScrabbleSolver_marcustrujillo.jar sowpods.text
this command kicks it off.
__GAMEPLAY__
The way to actually play is by clicking a tile in your
tray so that it's selected then clicking the place on the board that you want to play it and it will be placed there. As you lay your tiles in order, click the tile on the board when you would want it in your word. Then continue laying tiles.
ORDER IS IMPORTANT - the order you lay tiles and click the tile from the board you
want to play is the order in which your move is constructed.

**Example** we're playing off of an 'a' tile on the board and we want to play the word "bat" with the 'b' and 't' from our tray. We click the space to the left of the 'a' on the board and it will highlight, we then click the b tile from our tray. The b will be moved there. Then we click the 'a' square on the board. Then we click the empty space to the right of the 'a' on the board, followed by the 't' in our tray. The word "bat" will now be on the board. We then click the "execute across" button and it plays it on the board across. Of course, we could have done the same process but down, we would have clicked the "execute down" button after placing the tiles while selecting the one we're going to play on the board.

It sums up like this. Click the blank space you want, then the tile you want to place there. After it's been placed and you have to use the board, click the tile on the board, then click the space after it and click the tile you want to place there. Click the button for the direction you want to play after you've spelled the word.  



Bugs/Issues
1. Each space on the board is assumed to have no more than one type of
multiplyer. It can have either a word multiplyer or a letter multiplyer or none
at all, but not more than one of those.
2. The first move isn't protected to where it has to run through the center space.
3. The solver does not run "cross check" scenarios. Right now it runs one dimensional. It finds the highest scoring single word. It isn't capable of finding moves the make more than one word, such as playing a 'd' at the across end of one word while spelling another word down that connects to that d.
4. Wildcards... sigh... I ran out of time before implementing them, so there is no
wildcard functionality at the moment.
5. Tiles have to be played in order. You can't just add the tiles to the board in any order. 
