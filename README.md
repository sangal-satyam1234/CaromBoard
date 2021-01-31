# CaromBoard

The jar file is placed at ./jar/board-runner.jar. There are 2 commands to run

1) java -jar board-runner.jar 
  This will use the default input given at src/main/resources/CleanStrike folder
  
2) java -jar board-runner.jar -f /path/to/inputfile
  Provide customer input to simulate the board


Abbreviations :-
B => BlackStrike
SS => StrikerStrike
R => RedStrike
M => MultiStrike
D => DefunctStrike
  
Input format :
[Player] [Strike]
[Player] [MultiStrike] [Count] [Strike]
[Player] [RedStrike] [Count] [Strike]
[Player] [DefunctStrike] [Strike]

Input Example :-
1 B   => Player 1 pockets black coin (Turn 1)
2 B   => Player 2 pockets another black coin in next turn (Turn 2)
1 SS  => Player 1 pockets striker (Turn 3)
2 B
1 SS
1 R 3 B => Player 1 pockets a red coin followed by 3 black coin (Turn 6)
2 B
1 B
2 M 3 B => Player 2 pockets 3 black coins as a multi-strike (Turn 9)
1 D B  => Player 1 strikes a black coin out of the board (Turn 10)
2 B 

