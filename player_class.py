import math
from random import randint
from external_info import *
import sqlite3

class Player:
    def __init__(this, stars = -1, top = -1, position = -1):
        this.firstname = fn[randint(0, (len(fn)-1))]
        this.lastname = ln[randint(0, (len(ln)-1))]
        this.age = 20
        this.stats = []
        this.injury = randint(6,9)
        this.stars = round(stars, 1)
        this.top = bool(top)
        this.position = round(position, 1)
        this.id = id(this)

        this.stats.append(randint(50, 70)) #Explosiveness
        this.stats.append(randint(50, 70)) #Strength
        this.stats.append(randint(50, 70)) #Accuracy
        this.stats.append(randint(50, 70)) #Reflexes
        this.stats.append(randint(50, 90)) #Size
        this.stats.append(randint(50, 70)) #IQ

        if this.position <= 1: #Goal Scorer
            this.position = 1
            this.stats[4] += randint(2*top, 5)

        elif this.position == 2: #Play Maker
            this.stats[4] += randint(5*top, 15)
            
        elif this.position == 3: #Defender
            this.stats[4] += max(randint(80+stars+(5*top)-this.stats[4], 90+stars-this.stats[4]), 10)

        else: #All Around
            this.position = 4
            this.stats[4] += randint(10*top, 20)

        for i in range(6):
            if this.stats[i] > 99:
                this.stats[i] = 99

        #Set big 3 stats
        this.gsss = math.floor((this.stats[0]+this.stats[1]+this.stats[2])/3)
        this.pmss = math.floor((this.stats[2]+this.stats[3]+this.stats[5])/3)
        this.dbss = math.floor((this.stats[0]+this.stats[3]+this.stats[4])/3)

        #database
        this.conn = sqlite3.connect('poolball.db')
        this.c = this.conn.cursor()



    #Sets a players stats to specific values
    def set_stats(self, fname, lname, pos, stars, top, age, ex, st, ac, rx, sz, iq, injury):
        self.firstname = fname
        self.lastname = lname
        self.position = int(pos)
        self.stars = int(stars)
        self.top = bool(top)
        self.age = int(age)
        self.stats[0] = int(ex)
        self.stats[1] = int(st)
        self.stats[2] = int(ac)
        self.stats[3] = int(rx)
        self.stats[4] = int(sz)
        self.stats[5] = int(iq)
        self.injury = int(injury)

        self.gsss = math.floor((self.stats[0]+self.stats[1]+self.stats[2])/3)
        self.pmss = math.floor((self.stats[2]+self.stats[3]+self.stats[5])/3)
        self.dbss = math.floor((self.stats[0]+self.stats[3]+self.stats[4])/3)


    #String output for a player
    def __str__(self):
        posName = ""

        if self.position == 1:
            posName = "GS"
        elif self.position == 2:
            posName = "PM"
        elif self.position == 3:
            posName = "DB"
        else:
            posName = "AA"

        return "{0} {1} ({2}, {3})".format(self.firstname,self.lastname,posName,self.age)


    #Outputs the stats of the player
    def stat_string(self):
        return "[{0}, {1}, {2}, {3}, {4}, {5}, {6}]".format(self.stats[0],self.stats[1],self.stats[2],self.stats[3],self.stats[4],self.stats[5], str(self.injury)+"%")


    #Grows a player 1 year
    def age_player(this):

        if this.age < 25:
            mod = this.age-20
            this.stats[0] += randint(max(this.stars-mod+this.top, 1), 4-mod+(this.top*2)+this.stars)
            this.stats[1] += randint(max(this.stars-mod+this.top, 1), 5-mod+this.top+this.stars)
            this.stats[2] += randint(max(this.stars-mod+this.top, 1), 4-mod+(this.top*2)+this.stars)
            this.stats[3] += randint(max(this.stars-mod+this.top, 1), 4-mod+(this.top*2)+this.stars)
            this.stats[5] += randint(max(this.stars-mod+this.top, 1), 5-mod+this.top+this.stars)

            #Offensive (Goal Scorer)
            if this.position == 1:
                this.stats[1] += randint(0,2)
                this.stats[2] += min(randint(0,2),randint(0,2))

            #Playmaking (Playmaker)
            elif this.position == 2:
                this.stats[5] += randint(0,2)
                this.stats[0] += min(randint(0,2),randint(0,2))
                this.stats[1] -= randint(0,2)
            
            #Defensive (Defender)
            elif this.position == 3:
                this.stats[3] += randint(0,2)
                this.stats[0] += min(randint(0,2),randint(0,2))
                this.stats[1] -= max(randint(0,2),randint(0,2))
                this.stats[2] -= randint(0,2)
                
            #Balanced (All-Around)
            else:
                this.stats[0] += (randint(0,2)%2)*2
                this.stats[2] += (randint(0,2)%2)*2
                this.stats[3] += (randint(0,2)%2)*2

            this.injury -= randint(0,2)
            if this.injury < 1:
                this.injury = 1

        #25 or older
        else:
            mod = this.age-25
            this.stats[0] += randint(-3*mod, 2-mod+this.top)
            this.stats[1] += randint(-2*mod, 2-mod+this.top)
            this.stats[2] += randint(-2*mod, 2-mod+this.top)
            this.stats[3] += randint(-3*mod, 2-mod+this.top)
            this.stats[5] += randint(1,3+(2*this.top))

            this.injury += min(randint(0, 3), mod+1)
            if this.injury > 9:
                this.injury = 9
            elif this.injury < 1:
                this.injury = 1

        for i in range(6):
            if this.stats[i] > 99:
                this.stats[i] = 99

        this.gsss = math.floor((this.stats[0]+this.stats[1]+this.stats[2])/3)
        this.pmss = math.floor((this.stats[2]+this.stats[3]+this.stats[5])/3)
        this.dbss = math.floor((this.stats[0]+this.stats[3]+this.stats[4])/3)

        this.age += 1

    #insert into the database
    def insert_into_db(this):
        this.c.execute(f"SELECT * FROM players WHERE id = '{this.id}'")
        x = this.c.fetchone()
        if x == None:
            this.c.execute(f"""INSERT INTO players VALUES (
                '{this.firstname}',
                 '{this.lastname}',
                 '{this.age}',
                 '{this.stats[0]}',
                 '{this.stats[1]}',
                 '{this.stats[2]}',
                 '{this.stats[3]}',
                 '{this.stats[4]}',
                 '{this.stats[5]}',
                 '{this.injury}',
                 '{this.stars}',
                 '{this.top}',
                 '{this.position}',
                 '{this.id}'
                 )""")
            this.conn.commit()

    #load a player from the database
    def load_from_db(this, id):
        this.c.execute(f"SELECT * FROM players WHERE id = '{id}'")
        x = this.c.fetchone()
        print(x)
        this.firstname = x[0]
        this.lastname = x[1]
        this.age = x[2]
        this.stats[0] = x[3]
        this.stats[1] = x[4]
        this.stats[2] = x[5]
        this.stats[3] = x[6]
        this.stats[4] = x[7]
        this.stats[5] = x[8]
        this.injury = x[9]
        this.stars = x[10]
        this.top = x[11]
        this.position = x[12]
        this.id = x[13]

        this.gsss = math.floor((this.stats[0]+this.stats[1]+this.stats[2])/3)
        this.pmss = math.floor((this.stats[2]+this.stats[3]+this.stats[5])/3)
        this.dbss = math.floor((this.stats[0]+this.stats[3]+this.stats[4])/3)

