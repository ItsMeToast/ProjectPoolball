package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import playertypes.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
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
    public void testMoveAction() {
        System.out.println("\n\ntestMoveAction:");

        new MoveAction(pool, homeLine.getFirst(), 1,2).execute();
        assertEquals(1,homeLine.getFirst().getX());
        assertEquals(2,awayLine.getFirst().getX());
    }

    @Test
    public void testMoveActionOutOfBounds() {
        System.out.println("\n\ntestMoveActionOutOfBounds:");

        try {
            new MoveAction(pool, homeLine.getFirst(), -1,0).execute();
            fail();
        } catch (Exception ignored) {}

        try {
            new MoveAction(pool, homeLine.getFirst(), 0,-1).execute();
            fail();
        } catch (Exception ignored) {}

        try {
            new MoveAction(pool, homeLine.getFirst(), 5,0).execute();
            fail();
        } catch (Exception ignored) {}

        try {
            new MoveAction(pool, homeLine.getFirst(), 0,11).execute();
            fail();
        } catch (Exception ignored) {}
    }

    @Test
    public void testOpenPassAction() {
        System.out.println("\n\ntestOpenPassAction:");

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 20,10,0,0,0);
        pAction.execute();

        assertEquals(homeLine.get(1), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 20,10,0,0,0);
        pAction.execute();

        assertEquals(awayLine.get(1), pool.getBallHolder().get());
    }

    @Test
    public void testFumbleOpenPassAction() {
        System.out.println("\n\ntestFumbleOpenPassAction:");

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 20,9,0,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 20,9,0,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testFumbleBlockedPassAction() {
        System.out.println("\n\ntestFumbleBlockedPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 4);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(homeLine.get(2), 2, 2);
        pool.movePlayer(awayLine.get(0), 2, 6);
        pool.movePlayer(awayLine.get(1), 2, 0);
        pool.movePlayer(awayLine.get(2), 2, 8);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 20,9,0,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 20,9,0,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testInterceptPassAction() {
        System.out.println("\n\ntestInterceptPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 6);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(awayLine.get(0), 2, 4);
        pool.movePlayer(awayLine.get(1), 2, 0);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 0,10,24,0,0);
        pAction.execute();

        assertEquals(awayLine.get(2), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 0,10,24,0,0);
        pAction.execute();

        assertEquals(homeLine.get(2), pool.getBallHolder().get());
    }

    @Test
    public void testBlockedPassAction() {
        System.out.println("\n\ntestBlockedPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 6);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(awayLine.get(0), 2, 4);
        pool.movePlayer(awayLine.get(1), 2, 0);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 0,10,23,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 0,10,23,0,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testContestedPassAction() {
        System.out.println("\n\ntestContestedPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 4);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(homeLine.get(2), 2, 2);
        pool.movePlayer(awayLine.get(0), 2, 6);
        pool.movePlayer(awayLine.get(1), 2, 0);
        pool.movePlayer(awayLine.get(2), 2, 8);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 3,10,0,0,0);
        pAction.execute();

        assertEquals(homeLine.get(1), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 3,10,0,0,0);
        pAction.execute();

        assertEquals(awayLine.get(1), pool.getBallHolder().get());
    }

    @Test
    public void testThruInterceptPassAction() {
        System.out.println("\n\ntestThruInterceptPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 4);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(homeLine.get(2), 2, 2);
        pool.movePlayer(awayLine.get(0), 2, 6);
        pool.movePlayer(awayLine.get(1), 2, 0);
        pool.movePlayer(awayLine.get(2), 2, 8);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 3,10,0,21,0);
        pAction.execute();

        assertEquals(awayLine.get(2), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 3,10,0,21,0);
        pAction.execute();

        assertEquals(homeLine.get(2), pool.getBallHolder().get());
    }

    @Test
    public void testThruBlockPassAction() {
        System.out.println("\n\ntestThruBlockPassAction:");

        pool.movePlayer(homeLine.get(0), 2, 4);
        pool.movePlayer(homeLine.get(1), 2, 10);
        pool.movePlayer(homeLine.get(2), 2, 2);
        pool.movePlayer(awayLine.get(0), 2, 6);
        pool.movePlayer(awayLine.get(1), 2, 0);
        pool.movePlayer(awayLine.get(2), 2, 8);

        System.out.println(pool);

        pool.setBallHolder(homeLine.getFirst());
        PassAction pAction = new PassAction(pool, homeLine.getFirst(), homeLine.get(1), 3,10,0,20,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());
        pAction = new PassAction(pool, awayLine.getFirst(), awayLine.get(1), 3,10,0,20,0);
        pAction.execute();

        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testFaceoffHomeGreatWin() {
        System.out.println("\n\ntestFaceoffHomeGreatWin:");
        new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst(), 21, 0, false).execute();
        assertEquals(homeLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testFaceoffAwayGreatWin() {
        System.out.println("\n\ntestFaceoffHomeGreatWin:");
        new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst(), 0, 21, false).execute();
        assertEquals(awayLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testFaceoffHomeWin() {
        System.out.println("\n\ntestFaceoffHomeWin:");
        new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst(), 0, 0, false).execute();
        assert(pool.getBallHolder().isEmpty());
        assert(pool.getBallCoordinates()[1] < pool.getPoolYHalf());
    }

    @Test
    public void testFaceoffAwayWin() {
        System.out.println("\n\ntestFaceoffAwayWin:");
        new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst(), 0, 1, false).execute();
        assert(pool.getBallHolder().isEmpty());
        assert(pool.getBallCoordinates()[1] > pool.getPoolYHalf());
    }

    @Test
    public void testStealAction() {
        System.out.println("\n\ntestStealAction:");

        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 1, 2);
        pool.setBallHolder(homeLine.getFirst());

        new StealAction(pool, awayLine.getFirst(), homeLine.getFirst(), 31, 0).execute();
        assertEquals(awayLine.get(0), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());

        new StealAction(pool, homeLine.getFirst(), awayLine.getFirst(), 31, 0).execute();
        assertEquals(homeLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testFailedStealAction() {
        System.out.println("\n\ntestFailedStealAction:");

        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 1, 1);
        pool.setBallHolder(homeLine.getFirst());

        new StealAction(pool, awayLine.getFirst(), homeLine.getFirst(), 0, 0).execute();
        assertEquals(homeLine.get(0), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());

        new StealAction(pool, homeLine.getFirst(), awayLine.getFirst(), 0, 0).execute();
        assertEquals(awayLine.get(0), pool.getBallHolder().get());

        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 2, 2);
        pool.setBallHolder(homeLine.getFirst());

        new StealAction(pool, awayLine.getFirst(), homeLine.getFirst(), 20, 0).execute();
        assertEquals(homeLine.get(0), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());

        new StealAction(pool, homeLine.getFirst(), awayLine.getFirst(), 20, 0).execute();
        assertEquals(awayLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testKnockedStealAction() {
        System.out.println("\n\ntestKnockedStealAction:");

        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 1, 1);
        pool.setBallHolder(homeLine.getFirst());

        new StealAction(pool, awayLine.getFirst(), homeLine.getFirst(), 1, 0).execute();
        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());

        new StealAction(pool, homeLine.getFirst(), awayLine.getFirst(), 1, 0).execute();
        assert(pool.getBallHolder().isEmpty());

        pool.movePlayer(homeLine.getFirst(), 1, 1);
        pool.movePlayer(awayLine.getFirst(), 2, 2);
        pool.setBallHolder(homeLine.getFirst());

        new StealAction(pool, awayLine.getFirst(), homeLine.getFirst(), 21, 0).execute();
        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());

        new StealAction(pool, homeLine.getFirst(), awayLine.getFirst(), 21, 0).execute();
        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testDumpAction() {
        System.out.println("\n\ntestDumpAction:");

        pool.setBallHolder(homeLine.getFirst());
        new DumpAction(pool, homeLine.getFirst(), 2, 10, 3, 0,0,0).execute();
        assertEquals(2, pool.getBallCoordinates()[0]);
        assertEquals(10, pool.getBallCoordinates()[1]);
        assert(pool.getBallHolder().isEmpty());

        pool.setBallHolder(awayLine.getFirst());
        new DumpAction(pool, awayLine.getFirst(), 2, 0, 3, 0,0,0).execute();
        assertEquals(2, pool.getBallCoordinates()[0]);
        assertEquals(0, pool.getBallCoordinates()[1]);
        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testKnockedDumpAction() {
        System.out.println("\n\ntestKnockedDumpAction:");

        pool.setBallHolder(homeLine.getFirst());
        new DumpAction(pool, homeLine.getFirst(), 2, 10, 3, 7,0,0).execute();
        assertEquals(awayLine.get(0), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        new DumpAction(pool, awayLine.getFirst(), 2, 0, 3, 7,0,0).execute();
        assertEquals(homeLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testThruKnockedDumpAction() {
        System.out.println("\n\ntestThruKnockedDumpAction:");

        pool.setBallHolder(homeLine.getFirst());
        new DumpAction(pool, homeLine.getFirst(), 2, 10, 3, 0,0,1).execute();
        assertEquals(awayLine.get(2), pool.getBallHolder().get());

        pool.setBallHolder(awayLine.getFirst());
        new DumpAction(pool, awayLine.getFirst(), 2, 0, 3, 0,0,1).execute();
        assertEquals(homeLine.get(2), pool.getBallHolder().get());
    }

    @Test
    public void testOpenShotScore() {
        System.out.println("\n\ntestOpenShotScore:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.setBallHolder(homeLine.getFirst());
        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), -30,0,0,0);
        assert(sAction.execute());

        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.getFirst());
        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), -30,0,0,0);
        assert(sAction.execute());
    }

    @Test
    public void testOpenShotMiss() {
        System.out.println("\n\ntestOpenShotMiss:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.setBallHolder(homeLine.getFirst());
        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), -31,0,0,0);
        assert(!sAction.execute() && pool.getBallHolder().isEmpty());

        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.getFirst());
        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), -31,0,0,0);
        assert(!sAction.execute() && pool.getBallHolder().isEmpty());
    }

    @Test
    public void testShotCleanBlocked() {
        System.out.println("\n\ntestShotCleanBlocked:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 8,39,0,0);
        assert(!sAction.execute());
        assertEquals(awayLine.get(0), pool.getBallHolder().get());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 8,39,0,0);
        assert(!sAction.execute());
        assertEquals(homeLine.get(0), pool.getBallHolder().get());
    }

    @Test
    public void testContestedShotRebound() {
        System.out.println("\n\ntestContestedShotRebound:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 9,20,0,0);
        assert(!sAction.execute());
        assert(pool.getBallHolder().isEmpty());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 9,20,0,0);
        assert(!sAction.execute());
        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testContestedShotScore() {
        System.out.println("\n\ntestContestedShotScore:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 9,0,0,0);
        assert(sAction.execute());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 9,0,0,0);
        assert(sAction.execute());
    }

    @Test
    public void testGrabThruBlock() {
        System.out.println("\n\ntestGrabThruBlock:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.movePlayer(awayLine.get(1), 0, 8);
        pool.movePlayer(awayLine.get(2), 1, 9);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 19,-29,-32,40);
        sAction.execute();
        assertEquals(awayLine.get(2), pool.getBallHolder().get());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(homeLine.get(1), 0, 2);
        pool.movePlayer(homeLine.get(2), 1, 1);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 19,-29,-32,40);
        sAction.execute();
        assertEquals(homeLine.get(2), pool.getBallHolder().get());
    }

    @Test
    public void testReboundThruBlock() {
        System.out.println("\n\ntestReboundThruBlock:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.movePlayer(awayLine.get(1), 0, 8);
        pool.movePlayer(awayLine.get(2), 1, 9);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 19,10,-25,20);
        assert(!sAction.execute());
        assert(pool.getBallHolder().isEmpty());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(homeLine.get(1), 0, 2);
        pool.movePlayer(homeLine.get(2), 1, 1);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 19,10,-25,20);
        assert(!sAction.execute());
        assert(pool.getBallHolder().isEmpty());
    }

    @Test
    public void testCleanConeShot() {
        System.out.println("\n\ntestCleanConeShot:");

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(awayLine.get(0), 1, 7);
        pool.movePlayer(awayLine.get(1), 1, 8);
        pool.movePlayer(awayLine.get(2), 2, 9);
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 58,-31,-20,-30);
        assert(sAction.execute());

        pool.movePlayer(homeLine.get(0), 1, 3);
        pool.movePlayer(homeLine.get(1), 1, 2);
        pool.movePlayer(homeLine.get(2), 2, 1);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 58,-31,-20,-30);
        assert(sAction.execute());
    }

    @Test
    public void testAssistedShotStats() {
        System.out.println("\n\ntestAssistedShotStats:");

        pool.movePlayer(homeLine.get(0), 0, 6);
        pool.movePlayer(awayLine.get(0), 0, 7);
        pool.setBallHolder(homeLine.get(1));
        pool.setBallHolder(homeLine.get(0));

        ShotAction sAction = new ShotAction(pool, homeLine.getFirst(), 0, pool.getTarget(homeLine.getFirst()), 9,0,0,0);
        assert(sAction.execute());
        assertEquals(1, homeLine.get(0).getGoals());
        assertEquals(1, homeLine.get(1).getAssists());

        pool.movePlayer(homeLine.get(0), 0, 3);
        pool.movePlayer(awayLine.get(0), 0, 4);
        pool.setBallHolder(awayLine.get(1));
        pool.setBallHolder(awayLine.get(0));

        sAction = new ShotAction(pool, awayLine.getFirst(), 0, pool.getTarget(awayLine.getFirst()), 9,0,0,0);
        assert(sAction.execute());
        assertEquals(1, awayLine.get(0).getGoals());
        assertEquals(1, awayLine.get(1).getAssists());
    }
}
