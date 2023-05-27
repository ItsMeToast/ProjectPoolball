import math
from random import *
from external_info import *

class Player:
    def __init__(self):
        self.firstname = fn[randint(0, len(fn)-1)]
        self.lastname = ln[randint(0, len(ln)-1)]
        self.pow = randint(50, 70)
        self.int = randint(50, 70)
        self.sze = randint(50, 70)
        self.spd = randint(50, 70)
        self.acc = randint(50, 70)
        self.exp = randint(50, 70)
        self.blc = randint(50, 70)
        self.end = randint(50, 70)
        self.GS = (self.pow + self.acc + self.exp)/3
        self.PM = (self.int + self.acc + self.blc)/3
        self.SW = (self.spd + self.exp + self.end)/3
        self.DB = (self.sze + self.blc + self.end)/3
        self.overall = round((self.GS + self.PM + self.SW + self.DB)/4)
        self.GS = round(self.GS)
        self.PM = round(self.PM)
        self.SW = round(self.SW)
        self.DB = round(self.DB)
        #Determining Style Based on Stats
        list = [[self.GS, 'gs'], [self.PM, 'pm'], [self.SW, 'sw'], [self.DB, 'db']]
        for i in range(len(list)):
            max = i
            for j in range(i+1, len(list)):
                if list[j][0] > list[max][0]:
                    max = j
            
            list[i], list[max] = list[max], list[i]
        str = list[0][1]+list[1][1]
        if 'gs' in str and 'pm' in str:
            self.style = "Attacker"
        elif 'gs' in str and 'sw' in str:
            self.style = "Finisher"
        elif 'gs' in str and 'db' in str:
            self.style = "All Around"
        elif 'pm' in str and 'sw' in str:
            self.style = "Playmaker"
        elif 'pm' in str and 'db' in str:
            self.style = "Distributer"
        elif 'sw' in str and 'db' in str:
            self.style = "Defender"
        #Trait
        self.trait = traits[randint(0, len(traits)-1)]
        self.age = 20
        self.injury = random()*10
        self.potential = randint(1, 10)
        self.team = None
        self.contract = None

    def __str__(self):
        return "{0} {1}, Age: {2}, Style: {3}, Trait: {4}, {5} Star Potential, {6}, \nStats({7}, {8}, {9}, {10}, {11})".format(self.firstname, self.lastname, self.age, self.style, self.trait[0], self.potential, self.team, self.GS, self.PM, self.SW, self.DB, self.overall)
    
new_player = Player()

print(new_player)