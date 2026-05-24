#Main Method for PoolballGMs
from player_class import *

p1 = Player(5,1,3)

for i in range(10):
  print("{0} {1} {2} {3} {4}".format(p1, p1.gsss, p1.pmss, p1.dbss, p1.stat_string()))
  p1.age_player()