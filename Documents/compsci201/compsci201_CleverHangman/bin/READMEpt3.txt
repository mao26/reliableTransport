Mario Oliver
Sunday February 22nd to Sunday March 1st
Worked on part 3 of Clever Hangman for 5 hours by myself, and then an hour with my tutor. 
It would be nice to know what it is that we needed to include in this README; however, I am not sure
what is required of us to include in this file, so I will instead talk about how I implemented
the snarky, cheating version of clever hangman. I extended regular hangman in order to be able to access
both the methods and variables created in regular hangman. I used a new class called a stringbuilder
in order to facilitate the creation of my templates being displayed. I created a map that had each key
be the template of the word that placed the letter being guessed in each possible position of the word
and then I mapped all of the words that could fit under that template as the values (a string array)
that belong to that map. I then picked the map that had the most possible options as my remaining wordlist
and gave the player that template as the one he belived was his secret word (however, the actual secret word
did not matter). If the template with the most possible words mapped to it was one that required adding
a character to the template, then the template was changed to that template. I then created a CleverHangman
Executer to hold my main function that allowed the user to implement my snarky Hangman Game. 