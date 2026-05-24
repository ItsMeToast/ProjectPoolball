package gamesimulator;

import playertypes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PoolTest {
    ArrayList<MovablePlayer> homeLine = new ArrayList<>();
    ArrayList<MovablePlayer> awayLine = new ArrayList<>();
    Pool pool;

    @BeforeEach
    public void setupPool() {
        int stat = 80;
        Statline stats = new Statline(stat, stat, stat, stat, stat, stat, stat, stat, 0);
        Statline stats2 = new Statline(stat, stat, stat, stat, stat, stat, stat, stat, 0);

        for (int j = 0; j < 3; j++) {
            homeLine.add(new MovablePlayer(new Player("Home", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.AGILE, stats, 10)));
            awayLine.add(new MovablePlayer(new Player("Away", String.valueOf(j+4), 20, Playstyle.ATTACKER, Trait.AGILE, stats2, 10)));
        }

        pool = new Pool(homeLine, awayLine);
    }

    @Test
    public void testPoolSetup() {
        pool.setDefaultState();

        assertEquals(homeLine.get(0).getY(), 4);
        assertEquals(homeLine.get(1).getY(), 3);
        assertEquals(homeLine.get(2).getY(), 2);

        assertEquals(awayLine.get(0).getY(), 6);
        assertEquals(awayLine.get(1).getY(), 7);
        assertEquals(awayLine.get(2).getY(), 8);

        assertEquals(pool.getBallCoordinates()[0], 2);
        assertEquals(pool.getBallCoordinates()[1], 5);
    }

    @Test
    public void testPoolToString() {
        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 1, 1);

        String oracleString =
                "---|---|---|---|---|\n" +
                "---|---|---|---|---|\n" +
                "---|---|-6-|---|---|\n" +
                "---|---|-5-|---|---|\n" +
                "---|---|---|---|---|\n" +
                "---|---|--B|---|---|\n" +
                "---|---|---|---|---|\n" +
                "---|---|2--|---|---|\n" +
                "---|---|3--|---|---|\n" +
                "---|14-|---|---|---|\n" +
                "---|---|---|---|---|\n";

        assertEquals(oracleString, pool.toString());
    }

    @Test
    public void testMovePlayer() {
        MovablePlayer home1 = homeLine.getFirst();
        pool.movePlayer(home1, 1,2);
        assertEquals(1, home1.getX());
        assertEquals(2, home1.getY());
    }

    @Test
    public void testMovePlayerOutOfBounds() {
        try {
            pool.movePlayer(homeLine.getFirst(), 5,0);
            fail();
        } catch (Exception ignored) {}

        try {
            pool.movePlayer(awayLine.getFirst(), 5,0);
            fail();
        } catch (Exception ignored) {}


        try {
            pool.movePlayer(homeLine.getFirst(), -1,10);
            fail();
        } catch (Exception ignored) {}

        try {
            pool.movePlayer(awayLine.getFirst(), -1,10);
            fail();
        } catch (Exception ignored) {}

        // Y Out of Bounds

        try {
            pool.movePlayer(homeLine.getFirst(), 4,-1);
            fail();
        } catch (Exception ignored) {}

        try {
            pool.movePlayer(awayLine.getFirst(), 4,-1);
            fail();
        } catch (Exception ignored) {}


        try {
            pool.movePlayer(homeLine.getFirst(), 0,11);
            fail();
        } catch (Exception ignored) {}

        try {
            pool.movePlayer(awayLine.getFirst(), 0,11);
            fail();
        } catch (Exception ignored) {}
    }

    @Test
    public void testGetPoolX() {
        assertEquals(5, pool.getPoolX());
    }

    @Test
    public void testGetPoolY() {
        assertEquals(11, pool.getPoolY());
    }

    @Test
    public void testGetPoolXHalf() {
        assertEquals(2, pool.getPoolXHalf());
    }

    @Test
    public void testGetPoolYHalf() {
        assertEquals(5, pool.getPoolYHalf());
    }

    @Test
    public void testMoveBall() {
        pool.moveBall(1, 2);
        assertEquals(1, pool.getBallCoordinates()[0]);
        assertEquals(2, pool.getBallCoordinates()[1]);
    }

    @Test
    public void testMoveBallOutOfBounds() {
        pool.moveBall(-1, 5);
        assertEquals(0, pool.getBallCoordinates()[0]);
        assertEquals(5, pool.getBallCoordinates()[1]);

        pool.moveBall(5, 5);
        assertEquals(4, pool.getBallCoordinates()[0]);
        assertEquals(5, pool.getBallCoordinates()[1]);

        pool.moveBall(2, -1);
        assertEquals(2, pool.getBallCoordinates()[0]);
        assertEquals(0, pool.getBallCoordinates()[1]);

        pool.moveBall(2, 11);
        assertEquals(2, pool.getBallCoordinates()[0]);
        assertEquals(10, pool.getBallCoordinates()[1]);
    }

    @Test
    public void testBallCoordinates() {
        pool.moveBall(4, 3);
        assertEquals(4, pool.getBallCoordinates()[0]);
        assertEquals(3, pool.getBallCoordinates()[1]);
    }

    @Test
    public void testBallCoordinatesCarrier() {
        pool.setBallHolder(homeLine.getFirst());
        pool.movePlayer(homeLine.getFirst(), 1, 2);
        assertEquals(1, pool.getBallCoordinates()[0]);
        assertEquals(2, pool.getBallCoordinates()[1]);
    }

    @Test
    public void testBallCoordinatesPrevCarrier() {
        pool.setBallHolder(homeLine.getFirst());
        pool.movePlayer(homeLine.getFirst(), 1, 2);
        pool.removeBallHolder();
        pool.movePlayer(homeLine.getFirst(), 3,3);
        assertEquals(1, pool.getBallCoordinates()[0]);
        assertEquals(2, pool.getBallCoordinates()[1]);
    }

    @Test
    public void testSetBallHolder() {
        pool.setBallHolder(homeLine.getFirst());

        if (pool.getBallHolder().isEmpty()) {
            fail();
        }

        assertEquals(homeLine.getFirst(), pool.getBallHolder().get());


        pool.setBallHolder(awayLine.getFirst());

        if (pool.getBallHolder().isEmpty()) {
            fail();
        }

        assertEquals(awayLine.getFirst(), pool.getBallHolder().get());
    }

    @Test
    public void testPrevBallHolder() {
        pool.setBallHolder(homeLine.getFirst());
        pool.setBallHolder(awayLine.getFirst());

        if (pool.getPrevBallHolder().isEmpty()) {
            fail();
        }

        assertEquals(homeLine.getFirst(), pool.getPrevBallHolder().get());

        pool.setBallHolder(awayLine.get(1));

        if (pool.getPrevBallHolder().isEmpty()) {
            fail();
        }

        assertEquals(awayLine.getFirst(), pool.getPrevBallHolder().get());
    }

    @Test
    public void testRemoveBallHolder() {
        MovablePlayer home1 = homeLine.getFirst();
        MovablePlayer away4 = awayLine.getFirst();
        pool.setBallHolder(home1);
        pool.setBallHolder(away4);
        pool.removeBallHolder();

        if (pool.getBallHolder().isPresent()) {
            fail();
        }

        if (pool.getPrevBallHolder().isEmpty()) {
            fail();
        }

        assertEquals(away4, pool.getPrevBallHolder().get());
    }

    @Test
    public void testContainsPlayer() {
        assertTrue(pool.containsPlayer(homeLine.getFirst()));
    }

    @Test
    public void testGetDistancePlayer() {
        assertEquals(2, pool.getDistance(homeLine.getFirst(), awayLine.getFirst()));
        assertEquals(2, pool.getDistance(awayLine.getFirst(), homeLine.getFirst()));
        assertEquals(3, pool.getDistance(homeLine.getFirst(), awayLine.get(1)));
        assertEquals(3, pool.getDistance(awayLine.get(1), homeLine.getFirst()));

        pool.movePlayer(homeLine.getFirst(), 1, 3);
        pool.movePlayer(awayLine.getFirst(), 4, 7);

        assertEquals(5, pool.getDistance(homeLine.getFirst(), awayLine.getFirst()));
        assertEquals(5, pool.getDistance(awayLine.getFirst(), homeLine.getFirst()));

        pool.movePlayer(homeLine.getFirst(), 0, 3);
        pool.movePlayer(awayLine.getFirst(), 3, 3);

        assertEquals(3, pool.getDistance(homeLine.getFirst(), awayLine.getFirst()));
        assertEquals(3, pool.getDistance(awayLine.getFirst(), homeLine.getFirst()));

        pool.movePlayer(homeLine.getFirst(), 4, 5);
        pool.movePlayer(awayLine.getFirst(), 0, 1);

        assertEquals(Math.sqrt(32), pool.getDistance(homeLine.getFirst(), awayLine.getFirst()));
        assertEquals(Math.sqrt(32), pool.getDistance(awayLine.getFirst(), homeLine.getFirst()));
    }

    @Test
    public void testGetDistancePoint() {
        pool.movePlayer(homeLine.getFirst(), 4,3);
        pool.movePlayer(awayLine.getFirst(), 4,3);

        assertEquals(pool.getDistance(homeLine.getFirst(), 2, 6), Math.sqrt(13));
        assertEquals(pool.getDistance(awayLine.getFirst(), 2, 6), Math.sqrt(13));

        pool.movePlayer(homeLine.getFirst(), 1,9);
        pool.movePlayer(awayLine.getFirst(), 1,9);

        assertEquals(Math.sqrt(10), pool.getDistance(homeLine.getFirst(), 2, 6));
        assertEquals(Math.sqrt(10), pool.getDistance(awayLine.getFirst(), 2, 6));
    }

    @Test
    public void testGetZeroDistance() {
        pool.movePlayer(homeLine.getFirst(), 0, 3);
        pool.movePlayer(awayLine.getFirst(), 0, 3);

        assertEquals(0, pool.getDistance(homeLine.getFirst(), awayLine.getFirst()));
        assertEquals(0, pool.getDistance(awayLine.getFirst(), homeLine.getFirst()));
        assertEquals(0, pool.getDistance(homeLine.getFirst(), 0,3));
        assertEquals(0, pool.getDistance(awayLine.getFirst(), 0,3));
    }

    @Test
    public void testOpponentsAlongLine() {
        pool.movePlayer(homeLine.get(0), 0,10);
        pool.movePlayer(homeLine.get(1), 4,0);
        pool.movePlayer(homeLine.get(2), 2,0);

        pool.movePlayer(awayLine.get(0), 4,3);
        pool.movePlayer(awayLine.get(1), 1,6);
        pool.movePlayer(awayLine.get(2), 1,10);

        List<MovablePlayer> actual = pool.getOpponentsAlongLine(homeLine.get(1),0,10);
        List<MovablePlayer> expected = new ArrayList<>();
        expected.add(awayLine.get(1));
        expected.add(awayLine.get(2));

        assertEquals(expected, actual);
    }

//    @Test
//    public void testOpponentsAlongLine2() {
//        pool.movePlayer(homeLine.get(0), 2,4);
//        pool.movePlayer(homeLine.get(1), 0,0);
//        pool.movePlayer(homeLine.get(2), 0,0);
//
//        pool.movePlayer(awayLine.get(0), 1,5);
//        pool.movePlayer(awayLine.get(1), 0,10);
//        pool.movePlayer(awayLine.get(2), 4,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getOpponentsAlongLine(homeLine.getFirst(), 2, pool.getTarget(homeLine.getFirst())).size());
//        System.out.println("AWAY");
//        System.out.println(pool.getOpponentsAlongLine(awayLine.getFirst(), 4, pool.getTarget(awayLine.getFirst())).size());
//        fail();
//    }
//
//    @Test
//    public void testOpponentsAlongLine3() {
//        pool.movePlayer(homeLine.get(0), 0,6);
//        pool.movePlayer(homeLine.get(1), 0,0);
//        pool.movePlayer(homeLine.get(2), 0,0);
//
//        pool.movePlayer(awayLine.get(0), 3,7);
//        pool.movePlayer(awayLine.get(1), 1,8);
//        pool.movePlayer(awayLine.get(2), 0,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getOpponentsAlongLine(homeLine.getFirst(), 4, pool.getTarget(homeLine.getFirst())).size());
//        System.out.println("AWAY");
//        System.out.println(pool.getOpponentsAlongLine(awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst())).size());
//        fail();
//    }
//
//    @Test
//    public void testOpponentsAlongLine4() {
//        pool.movePlayer(homeLine.get(0), 3,8);
//        pool.movePlayer(homeLine.get(1), 3,2);
//        pool.movePlayer(homeLine.get(2), 1,0);
//
//        pool.movePlayer(awayLine.get(0), 1,2);
//        pool.movePlayer(awayLine.get(1), 1,8);
//        pool.movePlayer(awayLine.get(2), 3,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getOpponentsAlongLine(homeLine.getFirst(), 1, pool.getTarget(homeLine.getFirst())).size());
//        System.out.println("AWAY");
//        System.out.println(pool.getOpponentsAlongLine(awayLine.getFirst(), 3, pool.getTarget(awayLine.getFirst())).size());
//        fail();
//    }

    @Test
    public void testOpponentsAlongCone() {
        pool.movePlayer(homeLine.get(0), 0,6);

        pool.movePlayer(awayLine.get(0), 3,7);
        pool.movePlayer(awayLine.get(1), 0,10);
        pool.movePlayer(awayLine.get(2), 0,7);

        List<MovablePlayer> actual = pool.getOpponentsOnCone(homeLine.getFirst(),4,11);
        List<MovablePlayer> expected = new ArrayList<>();
        expected.add(awayLine.get(2));

        assertEquals(expected, actual);

        pool.movePlayer(awayLine.get(0), 4,4);

        pool.movePlayer(homeLine.get(0), 1,3);
        pool.movePlayer(homeLine.get(1), 4,0);
        pool.movePlayer(homeLine.get(2), 4,3);

        actual = pool.getOpponentsOnCone(awayLine.getFirst(),0,-1);
        expected = new ArrayList<>();
        expected.add(homeLine.get(2));

        assertEquals(expected, actual);
    }

//    @Test
//    public void testOpponentsAlongCone2() {
//        pool.movePlayer(homeLine.get(0), 2,4);
//        pool.movePlayer(homeLine.get(1), 0,0);
//        pool.movePlayer(homeLine.get(2), 0,0);
//
//        pool.movePlayer(awayLine.get(0), 1,5);
//        pool.movePlayer(awayLine.get(1), 0,10);
//        pool.movePlayer(awayLine.get(2), 4,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 2));
//        System.out.println("AWAY");
//        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 4));
//        fail();
//    }
//
//    @Test
//    public void testOpponentsAlongCone3() {
//        pool.movePlayer(homeLine.get(0), 0,6);
//        pool.movePlayer(homeLine.get(1), 0,0);
//        pool.movePlayer(homeLine.get(2), 0,0);
//
//        pool.movePlayer(awayLine.get(0), 3,7);
//        pool.movePlayer(awayLine.get(1), 1,8);
//        pool.movePlayer(awayLine.get(2), 0,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 4));
//        System.out.println("AWAY");
//        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 0));
//        fail();
//    }
//
//    @Test
//    public void testOpponentsAlongCone4() {
//        pool.movePlayer(homeLine.get(0), 3,8);
//        pool.movePlayer(homeLine.get(1), 3,2);
//        pool.movePlayer(homeLine.get(2), 1,0);
//
//        pool.movePlayer(awayLine.get(0), 1,2);
//        pool.movePlayer(awayLine.get(1), 1,8);
//        pool.movePlayer(awayLine.get(2), 3,10);
//
//        System.out.println("HOME");
//        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 1));
//        System.out.println("AWAY");
//        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 3));
//        fail();
//    }

    @Test
    public void testGetTarget() {
        assertEquals(11, pool.getTarget(homeLine.get(0)));
        assertEquals(11, pool.getTarget(homeLine.get(1)));
        assertEquals(11, pool.getTarget(homeLine.get(2)));

        assertEquals(-1, pool.getTarget(awayLine.get(0)));
        assertEquals(-1, pool.getTarget(awayLine.get(1)));
        assertEquals(-1, pool.getTarget(awayLine.get(2)));
    }

    @Test
    public void testGetDistanceFromDefensive() {
        pool.movePlayer(homeLine.get(0), 3,6);
        pool.movePlayer(homeLine.get(1), 1,5);
        pool.movePlayer(homeLine.get(2), 3,1);

        pool.movePlayer(awayLine.get(0), 2,3);
        pool.movePlayer(awayLine.get(1), 1,8);
        pool.movePlayer(awayLine.get(2), 4,9);

        assertEquals(10, pool.getDistanceFromDefensive(homeLine.get(0), 9));
        assertEquals(6, pool.getDistanceFromDefensive(homeLine.get(1), 5));
        assertEquals(2, pool.getDistanceFromDefensive(homeLine.get(2), 1));

        assertEquals(10, pool.getDistanceFromDefensive(awayLine.get(0), 1));
        assertEquals(6, pool.getDistanceFromDefensive(awayLine.get(1), 5));
        assertEquals(2, pool.getDistanceFromDefensive(awayLine.get(2), 9));
    }

    @Test
    public void testGetOffensiveOffset() {
        assertEquals(6, pool.getOffensiveOffset(homeLine.getFirst(), 5, 1));
        assertEquals(8, pool.getOffensiveOffset(homeLine.getFirst(), 6, 2));
        assertEquals(4, pool.getOffensiveOffset(homeLine.getFirst(), 5, -1));
        assertEquals(10, pool.getOffensiveOffset(homeLine.getFirst(), 1, 11));

        assertEquals(4, pool.getOffensiveOffset(awayLine.getFirst(), 5, 1));
        assertEquals(2, pool.getOffensiveOffset(awayLine.getFirst(), 4, 2));
        assertEquals(6, pool.getOffensiveOffset(awayLine.getFirst(), 4, -2));
        assertEquals(0, pool.getOffensiveOffset(awayLine.getFirst(), 3, 10));
    }

    @Test
    public void testGetEnemyBalance() {
        pool.movePlayer(homeLine.get(0), 2,4);
        pool.movePlayer(homeLine.get(1), 0,3);
        pool.movePlayer(homeLine.get(2), 4,1);

        pool.movePlayer(awayLine.get(0), 3,5);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 0,9);

        assertEquals(-2, pool.getEnemyBalance(homeLine.getFirst()));
        assertEquals(-4, pool.getEnemyBalance(awayLine.getFirst()));
    }

    @Test
    public void testGetEqualEnemyBalance() {
        pool.movePlayer(homeLine.get(0), 2,6);
        pool.movePlayer(homeLine.get(1), 0,3);
        pool.movePlayer(homeLine.get(2), 4,1);

        pool.movePlayer(awayLine.get(0), 2,4);
        pool.movePlayer(awayLine.get(1), 4,7);
        pool.movePlayer(awayLine.get(2), 0,9);

        assertEquals(4, pool.getEnemyBalance(homeLine.getFirst()));
        assertEquals(-4, pool.getEnemyBalance(awayLine.getFirst()));
    }

    @Test
    public void testEnemyForwardBalance() {
        pool.movePlayer(homeLine.get(0), 2,6);
        pool.movePlayer(homeLine.get(1), 0,3);
        pool.movePlayer(homeLine.get(2), 4,1);

        pool.movePlayer(awayLine.get(0), 2,4);
        pool.movePlayer(awayLine.get(1), 4,7);
        pool.movePlayer(awayLine.get(2), 0,9);

        assertEquals(4, pool.getEnemyForwardBalance(homeLine.getFirst()));
        assertEquals(-4, pool.getEnemyForwardBalance(awayLine.getFirst()));
    }

    @Test
    public void testEnemyForwardBalance2() {
        pool.movePlayer(homeLine.get(0), 2,5);
        pool.movePlayer(homeLine.get(1), 1,3);
        pool.movePlayer(homeLine.get(2), 1,2);

        pool.movePlayer(awayLine.get(0), 1,4);
        pool.movePlayer(awayLine.get(1), 3,6);
        pool.movePlayer(awayLine.get(2), 3,9);

        assertEquals(17, pool.getEnemyForwardBalance(homeLine.getFirst()));
        assertEquals(-19, pool.getEnemyForwardBalance(awayLine.getFirst()));
    }

    @Test
    public void testBalanceOffset() {
        assertEquals(2, pool.getBalanceOffset(1,0));
        assertEquals(1, pool.getBalanceOffset(2,1));
        assertEquals(0, pool.getBalanceOffset(10,2));
        assertEquals(2, pool.getBalanceOffset(-1,0));
        assertEquals(3, pool.getBalanceOffset(-2,1));
        assertEquals(4, pool.getBalanceOffset(-10,2));
    }

    @Test
    public void testFirstMan() {
        pool.movePlayer(homeLine.get(0), 1,2);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,4);

        MovablePlayer firstMan = pool.getFirstMan(homeLine.getFirst());
        assertEquals(homeLine.get(1), firstMan);


        pool.movePlayer(awayLine.get(0), 1,2);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 3,4);

        firstMan = pool.getFirstMan(awayLine.getFirst());
        assertEquals(awayLine.get(0), firstMan);
    }

    @Test
    public void testSecondMan() {
        pool.movePlayer(homeLine.get(0), 1,2);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,4);

        MovablePlayer secondMan = pool.getSecondMan(homeLine.getFirst());
        assertEquals(homeLine.get(2), secondMan);


        pool.movePlayer(awayLine.get(0), 1,2);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 3,4);

        secondMan = pool.getSecondMan(awayLine.getFirst());
        assertEquals(awayLine.get(2), secondMan);
    }

    @Test
    public void testThirdMan() {
        pool.movePlayer(homeLine.get(0), 1,2);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,4);

        MovablePlayer thirdMan = pool.getThirdMan(homeLine.getFirst());
        assertEquals(homeLine.get(0), thirdMan);


        pool.movePlayer(awayLine.get(0), 1,2);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 3,4);

        thirdMan = pool.getThirdMan(awayLine.getFirst());
        assertEquals(awayLine.get(1), thirdMan);
    }

    @Test
    public void testFirstManTieBreaker() {
        pool.movePlayer(homeLine.get(0), 1,5);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,5);

        MovablePlayer firstMan = pool.getFirstMan(homeLine.getFirst());
        assertEquals(homeLine.get(0), firstMan);


        pool.movePlayer(awayLine.get(0), 1,5);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 3,5);

        firstMan = pool.getFirstMan(awayLine.getFirst());
        assertEquals(awayLine.get(0), firstMan);
    }

    @Test
    public void testSecondManTieBreaker() {
        pool.movePlayer(homeLine.get(0), 1,5);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,5);

        MovablePlayer secondMan = pool.getSecondMan(homeLine.getFirst());
        assertEquals(homeLine.get(1), secondMan);


        pool.movePlayer(awayLine.get(0), 1,5);
        pool.movePlayer(awayLine.get(1), 2,5);
        pool.movePlayer(awayLine.get(2), 3,5);

        secondMan = pool.getSecondMan(awayLine.getFirst());
        assertEquals(awayLine.get(1), secondMan);
    }

    @Test
    public void testThirdManTieBreaker() {
        pool.movePlayer(homeLine.get(0), 1,5);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 3,5);

        MovablePlayer thirdMan = pool.getThirdMan(homeLine.getFirst());
        assertEquals(homeLine.get(2), thirdMan);


        pool.movePlayer(awayLine.get(0), 1,5);
        pool.movePlayer(awayLine.get(2), 3,5);
        pool.movePlayer(awayLine.get(1), 2,5);

        thirdMan = pool.getThirdMan(awayLine.getFirst());
        assertEquals(awayLine.get(2), thirdMan);
    }

    @Test
    public void testGetEnemyPlayer() {
        pool.movePlayer(homeLine.get(0), 1,4);
        pool.movePlayer(homeLine.get(1), 2,6);
        pool.movePlayer(homeLine.get(2), 3,5);

        pool.movePlayer(awayLine.get(0), 1,7);
        pool.movePlayer(awayLine.get(1), 2,10);
        pool.movePlayer(awayLine.get(2), 3,6);

        assertEquals(homeLine.get(1), pool.getEnemyPlayer(1, awayLine.getFirst()));
        assertEquals(homeLine.get(2), pool.getEnemyPlayer(2, awayLine.getFirst()));
        assertEquals(homeLine.get(0), pool.getEnemyPlayer(3, awayLine.getFirst()));

        assertEquals(awayLine.get(2), pool.getEnemyPlayer(1, homeLine.getFirst()));
        assertEquals(awayLine.get(0), pool.getEnemyPlayer(2, homeLine.getFirst()));
        assertEquals(awayLine.get(1), pool.getEnemyPlayer(3, homeLine.getFirst()));
    }

    @Test
    public void testGetEnemyPlayerTieBreaker() {
        pool.movePlayer(homeLine.get(0), 2,5);
        pool.movePlayer(homeLine.get(1), 2,5);
        pool.movePlayer(homeLine.get(2), 2,5);

        pool.movePlayer(awayLine.get(0), 2,7);
        pool.movePlayer(awayLine.get(1), 2,7);
        pool.movePlayer(awayLine.get(2), 2,7);

        assertEquals(homeLine.get(0), pool.getEnemyPlayer(1, awayLine.getFirst()));
        assertEquals(homeLine.get(1), pool.getEnemyPlayer(2, awayLine.getFirst()));
        assertEquals(homeLine.get(2), pool.getEnemyPlayer(3, awayLine.getFirst()));

        assertEquals(awayLine.get(0), pool.getEnemyPlayer(1, homeLine.getFirst()));
        assertEquals(awayLine.get(1), pool.getEnemyPlayer(2, homeLine.getFirst()));
        assertEquals(awayLine.get(2), pool.getEnemyPlayer(3, homeLine.getFirst()));
    }

    @Test
    public void testGetNextOpponent() {
        pool.movePlayer(homeLine.get(0), 2,5);
        pool.movePlayer(homeLine.get(1), 1,3);
        pool.movePlayer(homeLine.get(2), 1,2);

        pool.movePlayer(awayLine.get(0), 1,4);
        pool.movePlayer(awayLine.get(1), 3,7);
        pool.movePlayer(awayLine.get(2), 2,7);

        assertEquals(awayLine.get(2), pool.getNextOpponent(homeLine.get(0)));
        assertEquals(awayLine.get(0), pool.getNextOpponent(homeLine.get(1)));
        assertEquals(awayLine.get(0), pool.getNextOpponent(homeLine.get(2)));
        assertEquals(homeLine.get(1), pool.getNextOpponent(awayLine.get(0)));
        assertEquals(homeLine.get(0), pool.getNextOpponent(awayLine.get(1)));
        assertEquals(homeLine.get(0), pool.getNextOpponent(awayLine.get(2)));
    }

    @Test
    public void testGetNextOpponentBehindCloser() {
        pool.movePlayer(homeLine.get(0), 2,8);
        pool.movePlayer(homeLine.get(1), 2,3);
        pool.movePlayer(homeLine.get(2), 1,1);

        pool.movePlayer(awayLine.get(0), 2,2);
        pool.movePlayer(awayLine.get(1), 2,7);
        pool.movePlayer(awayLine.get(2), 3,9);

        assertEquals(awayLine.get(2), pool.getNextOpponent(homeLine.get(0)));
        assertEquals(homeLine.get(2), pool.getNextOpponent(awayLine.get(0)));
    }

    @Test
    public void testGetNextOpponentBehindCloser2() {
        pool.movePlayer(homeLine.get(0), 1,9);
        pool.movePlayer(homeLine.get(1), 2,3);
        pool.movePlayer(homeLine.get(2), 2,8);

        pool.movePlayer(awayLine.get(0), 1,1);
        pool.movePlayer(awayLine.get(1), 2,7);
        pool.movePlayer(awayLine.get(2), 2,2);

        assertEquals(awayLine.get(1), pool.getNextOpponent(homeLine.get(1)));
        assertEquals(homeLine.get(1), pool.getNextOpponent(awayLine.get(1)));
    }

    @Test
    public void testGetNextOpponentAllBehind() {
        pool.movePlayer(homeLine.get(0), 3,2);
        pool.movePlayer(homeLine.get(1), 2,2);
        pool.movePlayer(homeLine.get(2), 3,9);

        pool.movePlayer(awayLine.get(0), 3,8);
        pool.movePlayer(awayLine.get(1), 2,8);
        pool.movePlayer(awayLine.get(2), 3,1);

        assertEquals(awayLine.get(0), pool.getNextOpponent(homeLine.get(2)));
        assertEquals(homeLine.get(0), pool.getNextOpponent(awayLine.get(2)));
    }

    @Test
    public void testGetPassCongestion() {
        pool.movePlayer(homeLine.get(0), 0,10);
        pool.movePlayer(homeLine.get(1), 4,0);
        pool.movePlayer(homeLine.get(2), 2,0);

        pool.movePlayer(awayLine.get(0), 4,3);
        pool.movePlayer(awayLine.get(1), 1,6);
        pool.movePlayer(awayLine.get(2), 1,10);

        assertEquals(2, pool.getPassCongestion(homeLine.get(1), homeLine.get(0)));
    }

    @Test
    public void testGetShotCongestion() {
        pool.movePlayer(homeLine.get(0), 3,6);
        pool.movePlayer(homeLine.get(1), 2,7);
        pool.movePlayer(homeLine.get(2), 3,7);

        pool.movePlayer(awayLine.get(0), 2,3);
        pool.movePlayer(awayLine.get(1), 1,9);
        pool.movePlayer(awayLine.get(2), 4,10);

        assertEquals(2, pool.getShotCongestion(homeLine.getFirst(),2));
    }

    @Test
    public void testFaceoffShotCongestion() {
        pool.setDefaultState();

        assertEquals(3, pool.getShotCongestion(homeLine.getFirst(), 0));
        assertEquals(3, pool.getShotCongestion(homeLine.getFirst(), 1));
        assertEquals(3, pool.getShotCongestion(homeLine.getFirst(), 2));
        assertEquals(3, pool.getShotCongestion(homeLine.getFirst(), 3));
        assertEquals(3, pool.getShotCongestion(homeLine.getFirst(), 4));

        assertEquals(3, pool.getShotCongestion(awayLine.getFirst(), 0));
        assertEquals(3, pool.getShotCongestion(awayLine.getFirst(), 1));
        assertEquals(3, pool.getShotCongestion(awayLine.getFirst(), 2));
        assertEquals(3, pool.getShotCongestion(awayLine.getFirst(), 3));
        assertEquals(3, pool.getShotCongestion(awayLine.getFirst(), 4));
    }

    @Test
    public void testGetBestShot() {
        pool.movePlayer(homeLine.get(0), 3,6);
        pool.movePlayer(homeLine.get(1), 0,1);
        pool.movePlayer(homeLine.get(2), 1,0);

        pool.movePlayer(awayLine.get(0), 3,7);
        pool.movePlayer(awayLine.get(1), 1,8);
        pool.movePlayer(awayLine.get(2), 3,10);

        assertEquals(3, pool.getBestShot(homeLine.getFirst()));
        assertEquals(1, pool.getBestShot(awayLine.get(1)));
    }

    @Test
    public void testGetBestShotMirrored() {
        pool.movePlayer(homeLine.get(0), 3,8);
        pool.movePlayer(homeLine.get(1), 3,2);
        pool.movePlayer(homeLine.get(2), 1,0);

        pool.movePlayer(awayLine.get(0), 1,2);
        pool.movePlayer(awayLine.get(1), 1,8);
        pool.movePlayer(awayLine.get(2), 3,10);

        System.out.println("HOME");
        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 0));
        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 1));
        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 2));
        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 3));
        System.out.println(pool.getShotCongestion(homeLine.getFirst(), 4));
        System.out.println("AWAY");
        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 0));
        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 1));
        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 2));
        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 3));
        System.out.println(pool.getShotCongestion(awayLine.getFirst(), 4));

        assertEquals(1, pool.getBestShot(homeLine.getFirst()));
        assertEquals(3, pool.getBestShot(awayLine.getFirst()));
    }

    @Test
    public void testGetBestShotTieBreaker() {
        pool.movePlayer(homeLine.get(0), 0,0);
        pool.movePlayer(homeLine.get(1), 0,0);
        pool.movePlayer(homeLine.get(2), 0,0);
        pool.movePlayer(awayLine.get(0), 0,10);
        pool.movePlayer(awayLine.get(1), 0,10);
        pool.movePlayer(awayLine.get(2), 0,10);
        assertEquals(0, pool.getBestShot(homeLine.getFirst()));
        assertEquals(0, pool.getBestShot(awayLine.getFirst()));

        pool.movePlayer(homeLine.get(0), 1,0);
        pool.movePlayer(homeLine.get(1), 1,0);
        pool.movePlayer(homeLine.get(2), 1,0);
        pool.movePlayer(awayLine.get(0), 1,10);
        pool.movePlayer(awayLine.get(1), 1,10);
        pool.movePlayer(awayLine.get(2), 1,10);
        assertEquals(1, pool.getBestShot(homeLine.getFirst()));
        assertEquals(1, pool.getBestShot(awayLine.getFirst()));

        pool.movePlayer(homeLine.get(0), 2,0);
        pool.movePlayer(homeLine.get(1), 2,0);
        pool.movePlayer(homeLine.get(2), 2,0);
        pool.movePlayer(awayLine.get(0), 2,10);
        pool.movePlayer(awayLine.get(1), 2,10);
        pool.movePlayer(awayLine.get(2), 2,10);
        assertEquals(2, pool.getBestShot(homeLine.getFirst()));
        assertEquals(2, pool.getBestShot(awayLine.getFirst()));

        pool.movePlayer(homeLine.get(0), 3,0);
        pool.movePlayer(homeLine.get(1), 3,0);
        pool.movePlayer(homeLine.get(2), 3,0);
        pool.movePlayer(awayLine.get(0), 3,10);
        pool.movePlayer(awayLine.get(1), 3,10);
        pool.movePlayer(awayLine.get(2), 3,10);
        assertEquals(3, pool.getBestShot(homeLine.getFirst()));
        assertEquals(3, pool.getBestShot(awayLine.getFirst()));

        pool.movePlayer(homeLine.get(0), 4,0);
        pool.movePlayer(homeLine.get(1), 4,0);
        pool.movePlayer(homeLine.get(2), 4,0);
        pool.movePlayer(awayLine.get(0), 4,10);
        pool.movePlayer(awayLine.get(1), 4,10);
        pool.movePlayer(awayLine.get(2), 4,10);
        assertEquals(4, pool.getBestShot(homeLine.getFirst()));
        assertEquals(4, pool.getBestShot(awayLine.getFirst()));
    }

    @Test
    public void testGetClosestTeammateToBall() {
        pool.movePlayer(homeLine.get(0), 3,6);
        pool.movePlayer(homeLine.get(1), 0,1);
        pool.movePlayer(homeLine.get(2), 1,0);

        pool.movePlayer(awayLine.get(0), 3,7);
        pool.movePlayer(awayLine.get(1), 1,8);
        pool.movePlayer(awayLine.get(2), 3,10);

        pool.moveBall(2,8);

        assertEquals(awayLine.get(1), pool.getClosestTeammateToBall(awayLine.get(1)));
        assertEquals(homeLine.get(0), pool.getClosestTeammateToBall(homeLine.get(1)));
    }

    @Test
    public void testGetClosestTeammateToBall2() {
        pool.movePlayer(homeLine.get(0), 3,7);
        pool.movePlayer(homeLine.get(1), 0,3);
        pool.movePlayer(homeLine.get(2), 1,10);

        pool.movePlayer(awayLine.get(0), 3,7);
        pool.movePlayer(awayLine.get(1), 0,3);
        pool.movePlayer(awayLine.get(2), 1,10);

        pool.moveBall(2,8);

        assertEquals(awayLine.get(0), pool.getClosestTeammateToBall(awayLine.get(2)));
        assertEquals(homeLine.get(0), pool.getClosestTeammateToBall(homeLine.get(2)));
    }

    @Test
    public void testGetClosestTeammateToBallTieBreaker() {
        pool.movePlayer(homeLine.get(0), 3,4);
        pool.movePlayer(homeLine.get(1), 3,6);
        pool.movePlayer(homeLine.get(2), 1,6);

        pool.movePlayer(awayLine.get(0), 3,7);
        pool.movePlayer(awayLine.get(1), 1,7);
        pool.movePlayer(awayLine.get(2), 0,4);

        pool.moveBall(2,5);

        assertEquals(awayLine.get(0), pool.getClosestTeammateToBall(awayLine.get(0)));
        assertEquals(homeLine.get(0), pool.getClosestTeammateToBall(homeLine.get(0)));
        assertEquals(awayLine.get(1), pool.getClosestTeammateToBall(awayLine.get(1)));
        assertEquals(homeLine.get(1), pool.getClosestTeammateToBall(homeLine.get(1)));
        assertEquals(awayLine.get(2), pool.getClosestTeammateToBall(awayLine.get(2)));
        assertEquals(homeLine.get(2), pool.getClosestTeammateToBall(homeLine.get(2)));
    }

    @Test
    public void testGetBestMoveDown() {
        pool.movePlayer(homeLine.getFirst(), 4,2);

        int[] movement;

        movement = pool.getBestMove(homeLine.getFirst(), 0,0,0);
        assertEquals(4,movement[0]);
        assertEquals(2,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 1,0,0);
        assertEquals(3,movement[0]);
        assertEquals(2,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 2,0,0);
        assertEquals(2,movement[0]);
        assertEquals(1,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3,0,0);
        assertEquals(1,movement[0]);
        assertEquals(1,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 4,0,0);
        assertEquals(0,movement[0]);
        assertEquals(0,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 5,0,0);
        assertEquals(0,movement[0]);
        assertEquals(0,movement[1]);
    }

    @Test
    public void testGetBestMoveUp() {
        pool.movePlayer(homeLine.getFirst(), 4,3);

        int xTarget = 0;
        int yTarget = 9;

        int[] movement;

        movement = pool.getBestMove(homeLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(4,movement[0]);
        assertEquals(3,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(3,movement[0]);
        assertEquals(4,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(3,movement[0]);
        assertEquals(5,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(2,movement[0]);
        assertEquals(5,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(2,movement[0]);
        assertEquals(6,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 5, xTarget, yTarget);
        assertEquals(1,movement[0]);
        assertEquals(7,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 6, xTarget, yTarget);
        assertEquals(1,movement[0]);
        assertEquals(8,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 7, xTarget, yTarget);
        assertEquals(0,movement[0]);
        assertEquals(9,movement[1]);
    }

    @Test
    public void testGetBestMoveMatch() {
        pool.movePlayer(homeLine.getFirst(), 0,0);
        pool.movePlayer(awayLine.getFirst(), 0,0);

        int xTarget = 4;
        int yTarget = 2;

        int[] movement;
        int[] movement2;

        movement = pool.getBestMove(homeLine.getFirst(), 0, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 1, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 2, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 4, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 5, xTarget, yTarget);
        movement2 = pool.getBestMove(awayLine.getFirst(), 5, xTarget, yTarget);
        assertEquals(movement[0], movement2[0]);
        assertEquals(movement[1], movement2[1]);
    }

    @Test
    public void testGetBestMoveCompete() {
        pool.movePlayer(homeLine.getFirst(), 0,0);
        pool.movePlayer(awayLine.getFirst(), 0,4);

        int xTarget = 4;
        int yTarget = 2;

        int[] movement;
        int[] movement2;

        movement = pool.getBestMove(homeLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(0,movement[0]);
        assertEquals(0,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(0,movement2[0]);
        assertEquals(4,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(1,movement[0]);
        assertEquals(0,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(1,movement2[0]);
        assertEquals(4,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(2,movement[0]);
        assertEquals(1,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(2,movement2[0]);
        assertEquals(3,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(3,movement[0]);
        assertEquals(1,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(3,movement2[0]);
        assertEquals(3,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(4,movement[0]);
        assertEquals(2,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(4,movement2[0]);
        assertEquals(2,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 5, xTarget, yTarget);
        assertEquals(4,movement[0]);
        assertEquals(2,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 5, xTarget, yTarget);
        assertEquals(4,movement2[0]);
        assertEquals(2,movement2[1]);
    }

    @Test
    public void testGetBestMoveCompeteVertical() {
        pool.movePlayer(homeLine.getFirst(), 4,3);
        pool.movePlayer(awayLine.getFirst(), 0,9);

        int xTarget = 2;
        int yTarget = 6;

        int[] movement;
        int[] movement2;

        movement = pool.getBestMove(homeLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(4,movement[0]);
        assertEquals(3,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 0, xTarget, yTarget);
        assertEquals(0,movement2[0]);
        assertEquals(9,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(3,movement[0]);
        assertEquals(4,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 1, xTarget, yTarget);
        assertEquals(1,movement2[0]);
        assertEquals(8,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(3,movement[0]);
        assertEquals(5,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 2, xTarget, yTarget);
        assertEquals(1,movement2[0]);
        assertEquals(7,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(2,movement[0]);
        assertEquals(5,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 3, xTarget, yTarget);
        assertEquals(2,movement2[0]);
        assertEquals(7,movement2[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(2,movement[0]);
        assertEquals(6,movement[1]);
        movement2 = pool.getBestMove(awayLine.getFirst(), 4, xTarget, yTarget);
        assertEquals(2,movement2[0]);
        assertEquals(6,movement2[1]);
    }

    @Test
    public void testGetBestMoveOutOfBounds() {
        pool.movePlayer(homeLine.getFirst(), 4,2);

        int[] movement = pool.getBestMove(homeLine.getFirst(), 3,5,2);
        assertEquals(4,movement[0]);
        assertEquals(2,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3,20,2);
        assertEquals(4,movement[0]);
        assertEquals(2,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 10,5,-1);
        assertEquals(4,movement[0]);
        assertEquals(0,movement[1]);

        movement = pool.getBestMove(homeLine.getFirst(), 3,5,-20);
        assertEquals(4,movement[0]);
        assertEquals(0,movement[1]);
    }
}
