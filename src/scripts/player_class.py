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
        self.trait = determine_trait(self.style, self.potential)
        if self.trait[0] == "Giant":
            self.sze += 10
            if self.sze > 99:
                self.sze = 99
        self.GS = (self.pow + self.acc + self.exp)/3
        self.PM = (self.int + self.acc + self.blc)/3
        self.SW = (self.spd + self.exp + self.end)/3
        self.DF = (self.sze + self.blc + self.end)/3
        self.overall = round((self.GS + self.PM + self.SW + self.DF)/4)
        self.GS = round(self.GS)
        self.PM = round(self.PM)
        self.SW = round(self.SW)
        self.DF = round(self.DF)
        
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
                if self.trait[0] == "Prodigy":
                    nums[i] += 1
                if self.trait[0] == "Stunted":
                    a = randint(1,3)
                    if a == 3:
                        nums[i] -= 1
            if self.age >= 24:
                rand = randint(1, 100)
                if self.age == 24 and rand >= 86 or self.age == 25 and rand >=41 or self.age == 26:
                    self.growth_style = "Prime"
            self.injury -= randint(1, 20)/10
        elif self.growth_style == "Prime":
            nums = []
            for i in range(7):
                num = randint(1, 11)-self.potential
                if num <= 0:
                    nums.append(math.ceil(random()*(self.potential)/3))
                else:
                    nums.append(-1*math.ceil(random()*(3-(self.potential/4))))
            if self.age >= 26:
                rand = randint(1, 100)
                if self.age == 26 and rand >= 86 or self.age == 27 and rand >=41 or self.age == 28:
                    self.growth_style = "Veteran"
            self.injury -= randint(-15, 15)/10
        else:
            #VETERAN
            nums = []
            for i in range(7):
                nums.append(-1*math.ceil(random()*(6-(self.potential/2))))
                if self.trait[0] == "Dedicated" and nums[i] < 0:
                    nums[i] += 1
                if self.trait[0] == "Short-Lived":
                    nums[i] -= 2
            self.injury -= randint(-20, -1)/10

        if self.trait[0]=="Superstar":
            for i in range(len(nums)):
                if nums[i] < 1:
                    nums[i] += 2
                else:
                    nums[i] += 1

        if self.trait[0]=="Consistent":
            for i in range(len(nums)):
                if nums[i] < -2:
                    nums[i] += 1
                elif nums[i] > 3:
                    nums[i] -= 1

        if self.trait[0]=="Wildcard":
            for i in range(len(nums)):
                nums[i] += randint(-2, 2)
           

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

        if self.trait[0] == "Scared":
            self.blc -= 1
            self.end -= 1
        if self.trait[0] == "Speedster":
            self.spd += 3
            self.exp += 1
            self.blc -= 1
        if self.trait[0] == "Sniper":
            self.pow += 1
            self.acc += 1
            self.exp += 1
        if self.trait[0] == "Dead Weight":
            self.spd -= 1
            self.exp -= 1
            self.end -= 1
        if self.trait[0] == "Hardened":
            self.end += 1
            self.int += 1
            self.injury -= 0.5
        if self.trait[0] == "Genius":
            self.int += 2
            self.acc += 1
            self.blc += 1
        if self.trait[0] == "Pass-First":
            self.int += 2
            self.blc += 1
            self.pow -= 1
        if self.trait[0] == "Whimp":
            self.pow -= 1
            self.acc -= 1
            self.exp -= 1
        if self.trait[0] == "Giant":
            self.blc += 1
            self.end += 1
        if self.trait[0] == "Agile":
            self.injury += 0.5
            self.spd += 1
            self.exp += 1
        if self.trait[0] == "Fish-Like":
            self.spd += 1
            self.exp += 1
            self.end += 1
        if self.trait[0] == "Selfish":
            self.pow += 2
            self.exp += 1
            self.int -= 1
        if self.trait[0] == "Dumb":
            self.int -= 1
            self.acc -= 1
            self.blc -= 1
        if self.trait[0] == "Brick Wall":
            self.blc += 2
            self.end += 1
            self.spd -= 1
        if self.trait[0] == "Hard Worker":
            self.acc += 1
            self.blc += 1

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
        self.injury = round(self.injury, 1)
        if self.injury <= 0.1:
            self.injury == 0.1

    def __str__(self):
        #return "{0} {1}, Age: {2}, Style: {3}, Trait: {4}, {5} Star Potential, {6}, \nStats(GS {7}, PM {8}, SW {9}, DF {10}, {11}) Injury: {12}".format(self.firstname, self.lastname, self.age, self.style, self.trait[0], self.potential, self.team, self.GS, self.PM, self.SW, self.DF, self.overall, self.injury)
        return "Age: {0}, GS {1}; PM {2}; SW {3}; DF {4}; Overall {5}; Potential {6}; STYLE - {7}; TRAIT - {8} ; ".format(self.age, self.GS, self.PM, self.SW, self.DF, self.overall, self.potential, self.style, self.trait[0])