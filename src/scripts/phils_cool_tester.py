from player_class import *
from math import *

#phil = Player()
#print(phil, phil.sze, "\n")

#for i in range(9):
#    phil.age_player()
#    print(phil, phil.growth_style, '\n')


for i in range(1000):
    player = Player()
    if player.trait[0] == "Giant":
        print(player.sze)