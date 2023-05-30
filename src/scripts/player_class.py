import math
from random import *
from external_info import *

class Player:
    def __init__(self):
        self.firstname = fn[randint(0, len(fn)-1)]
        self.lastname = ln[randint(0, len(ln)-1)]
        self.style = styles[randint(0,5)]
        determine_starting_stats(self, self.style)
        self.potential = curved_potential()
        self.pow += math.floor(random()/10*6*self.potential)
        self.int += math.floor(random()/10*6*self.potential)
        self.sze += math.floor(random()/10*6*self.potential)
        self.spd += math.floor(random()/10*6*self.potential)
        self.acc += math.floor(random()/10*6*self.potential)
        self.exp += math.floor(random()/10*6*self.potential)
        self.blc += math.floor(random()/10*6*self.potential)
        self.end += math.floor(random()/10*6*self.potential)
        self.GS = (self.pow + self.acc + self.exp)/3
        self.PM = (self.int + self.acc + self.blc)/3
        self.SW = (self.spd + self.exp + self.end)/3
        self.DF = (self.sze + self.blc + self.end)/3
        self.overall = round((self.GS + self.PM + self.SW + self.DF)/4)
        self.GS = round(self.GS)
        self.PM = round(self.PM)
        self.SW = round(self.SW)
        self.DF = round(self.DF)
        
        #Trait
        self.trait = determine_trait(self.style, self.potential)
        self.age = 20
        self.injury = round(randint(6, 9)+random(), 1)
        self.team = None
        self.contract = [None, None]

    """def age_player(self):
        self.age += 1
        if self.age <= 25:
            self.pow += randint(1, )
        elif self.age <= 27:

        elif self.age <= 29:

        else:
            #retire"""

    def __str__(self):
        return "{0} {1}, Age: {2}, Style: {3}, Trait: {4}, {5} Star Potential, {6}, \nStats(GS {7}, PM {8}, SW {9}, DF {10}, {11}) Injury: {12}".format(self.firstname, self.lastname, self.age, self.style, self.trait[0], self.potential, self.team, self.GS, self.PM, self.SW, self.DF, self.overall, self.injury)
    
new_player = Player()

print(new_player)