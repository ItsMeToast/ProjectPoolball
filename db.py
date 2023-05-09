import sqlite3
from player_class import *

conn = sqlite3.connect('poolball.db')
c = conn.cursor()

c.execute("DELETE FROM players")
conn.commit()

p = []

for i in range(10):
    p.insert(0, Player(randint(1, 5), randint(0,1), randint(1,4)))
    p[0].insert_into_db()

c.execute("SELECT * FROM players")
print(c.fetchall())
    
playerA = p[0]
playerB = Player()
playerB.load_from_db(playerA.id)
print(playerA, playerB)