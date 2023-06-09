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
        
        self.trait = determine_trait(self.style, self.potential)
        self.growth_style = "Development"
        self.age = 20
        self.injury = round(randint(6, 9)+random(), 1)
        self.team = None
        self.contract = [None, None]

    def age_player(self):
        self.age += 1
        if self.age == 30:
            #retire
            print("Retire")
        
        if self.growth_style == "Development":
            nums = []
            for i in range(7):
                nums.append(math.ceil(random()*(self.potential)/2))
            if self.age == 25:
                self.growth_style = "Prime"
        elif self.growth_style == "Prime":
            nums = []
            for i in range(7):
                num = randint(1, 11)-self.potential
                if num <= 0:
                    nums.append(math.ceil(random()*(self.potential)/3))
                else:
                    nums.append(-1*math.ceil(random()*(3-(self.potential/4))))
            if self.age == 27:
                self.growth_style = "Veteran"
        else:
            nums = []
            for i in range(7):
                nums.append(-1*math.ceil(random()*(5-(self.potential/2))))
        if self.style == "Attacker":
            self.pow += (nums[0]+randint(0, 3))
            self.int += (nums[1]+randint(0, 3))
            self.spd += (nums[2]+randint(-1, 1))
            self.acc += (nums[3]+randint(0, 4))
            self.exp += (nums[4]+randint(-1, 2))
            self.blc += (nums[5]+randint(-1, 2))
            self.end += (nums[6]+randint(-2, 1))
        elif self.style == "Finisher":
            self.pow += (nums[0]+randint(0, 3))
            self.int += (nums[1]+randint(-1, 1))
            self.spd += (nums[2]+randint(0, 3))
            self.acc += (nums[3]+randint(-1, 2))
            self.exp += (nums[4]+randint(0, 4))
            self.blc += (nums[5]+randint(-2, 1))
            self.end += (nums[6]+randint(-1, 2))
        elif self.style == "Two-Way":
            self.pow += (nums[0]+randint(0, 3))
            self.int += (nums[1]+randint(-1, 1))
            self.spd += (nums[2]+randint(-1, 1))
            self.acc += (nums[3]+randint(-1, 2))
            self.exp += (nums[4]+randint(-1, 2))
            self.blc += (nums[5]+randint(-1, 3))
            self.end += (nums[6]+randint(-1, 3))
        elif self.style == "Playmaker":
            self.pow += (nums[0]+randint(-1, 1))
            self.int += (nums[1]+randint(0, 3))
            self.spd += (nums[2]+randint(0, 3))
            self.acc += (nums[3]+randint(-1, 2))
            self.exp += (nums[4]+randint(-1, 2))
            self.blc += (nums[5]+randint(-1, 2))
            self.end += (nums[6]+randint(-1, 2))
        elif self.style == "Distributor":
            self.pow += (nums[0]+randint(-1, 1))
            self.int += (nums[1]+randint(0, 3))
            self.spd += (nums[2]+randint(-1, 1))
            self.acc += (nums[3]+randint(-1, 2))
            self.exp += (nums[4]+randint(-2, 1))
            self.blc += (nums[5]+randint(0, 5))
            self.end += (nums[6]+randint(-1, 3))
        else:
            self.pow += (nums[0]+randint(-1, 1))
            self.int += (nums[1]+randint(-1, 1))
            self.spd += (nums[2]+randint(0, 3))
            self.acc += (nums[3]+randint(-2, 1))
            self.exp += (nums[4]+randint(-1, 2))
            self.blc += (nums[5]+randint(-1, 3))
            self.end += (nums[6]+randint(0, 5))
        if self.pow > 99:
            self.pow = 99
        if self.int > 99:
            self.int = 99
        if self.spd > 99:
            self.spd = 99
        if self.acc > 99:
            self.acc = 99
        if self.exp > 99:
            self.exp = 99
        if self.blc > 99:
            self.blc = 99
        if self.end > 99:
            self.end = 99
        
        self.GS = (self.pow + self.acc + self.exp)/3
        self.PM = (self.int + self.acc + self.blc)/3
        self.SW = (self.spd + self.exp + self.end)/3
        self.DF = (self.sze + self.blc + self.end)/3
        self.overall = round((self.GS + self.PM + self.SW + self.DF)/4)
        self.GS = round(self.GS)
        self.PM = round(self.PM)
        self.SW = round(self.SW)
        self.DF = round(self.DF)
        
    def __str__(self):
        #return "{0} {1}, Age: {2}, Style: {3}, Trait: {4}, {5} Star Potential, {6}, \nStats(GS {7}, PM {8}, SW {9}, DF {10}, {11}) Injury: {12}".format(self.firstname, self.lastname, self.age, self.style, self.trait[0], self.potential, self.team, self.GS, self.PM, self.SW, self.DF, self.overall, self.injury)
        return "Age: {0}, GS {1}; PM {2}; SW {3}; DF {4}; Overall {5}; Potential {6}; STYLE - {7}".format(self.age, self.GS, self.PM, self.SW, self.DF, self.overall, self.potential, self.style)