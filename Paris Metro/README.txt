To run, run ParisMetro with arguments N1, and optionally N2 and N3

If only N1 is given, the program outputs every station number on the same line as the given station number

Ex: java ParisMetro 0

If N1 and N2 is given, the program outputs the shortest path between the 2 stations

Ex: java ParisMetro 42 287

If N1, N2, and N3 is given, the program outputs the shortest path between stations N1 and N2, with the assumption that the line N3 is connected to is not operational.

Ex: java ParisMetro 42 287 19

This program is accurate to the real Paris metro system as of 2023. To convert to station names instead of code numbers, simply look up the number in metro.txt

For Example to get from the Louvre to the Eiffel tower via subway look up the codes "0227" (Palais Royal, Musée du Louvre) and "0325" (Saint-Lazare) and the program will be able to give you the shortest path between them
