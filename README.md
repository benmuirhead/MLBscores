# MLBscores
This program is a collaboration of Ben Muirhead and Samuel Doud

This program displays MLB scores for a given period.
This period can be a single date or a range of dates

If a single date is given, then the program will show the scores of all the
games played on that day. The games are divided into their respective leagues.

If a range of dates is selected the user must also select a team, then the
program will display the scores of that team over the given period.

If the user clicks on a game, a detailed line score will appear at the bottom
of the GUI.

The classes to the program are organized as such

GUI: a GUI to display the graphics components of the games

GameDay: a class which organizes the games of a given date and sends Swing
components of score to the GUI in an organized fashion

GameRange: Same function as GameDay except the given information is a single
team over a range of dates

Game: A class which finds the information for a certain game as delegated by
the GameDay or GameRange classes. Creates the Swing components to display the
games.

Team: A class which stores the information for curent MLB teams. This
information includes abbreviation, name, city, and logo. To quickly assign
information, a binary search is implemented.

