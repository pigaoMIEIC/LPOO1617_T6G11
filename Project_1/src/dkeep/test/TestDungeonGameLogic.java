package dkeep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import dkeep.logic.*;

public class TestDungeonGameLogic {
	char a = '1';

	char[][] map = { { 'x', 'x', 'x', 'x', 'x' }, 
			{ 'x', /* H */' ', ' ', /* G */' ', 'x' },
			{ 'i', ' ', ' ', ' ', 'x' }, 
			{ 'i', 'k', ' ', ' ', 'x' }, 
			{ 'x', 'x', 'x', 'x', 'x' } };

	char[][] map1 = { { 'x', 'x', 'x', 'x', 'x' }, { 'x', /* H */' ', ' ', /* O */' ', 'x' },
			{ 'i', ' ', ' ', ' ', 'x' }, { 'x', 'k', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x' } };

	char level2[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	char[][] map2 = { { 'x', 'x', 'x' }, { 'i', ' ', 'x' }, { 'x', 'x', 'x' } };

	char[] mov = { 's', 'w' };

	Vector<Entidade> entidades = new Vector<Entidade>();
	boolean flag[] = { false };

	@Test
	public void testMoveHeroIntoToFreeCell() {
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map);
		gameMap.setName("testlevel");
		Hero hero = new Hero(1, 1, 'H');
		Guarda guarda = new Rookie(3, 1, 'G', mov);
		entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertEquals(new CellPosition(1, 1), game.getHeroPosition());
		hero.move('a', gameMap);
		assertEquals(new CellPosition(1, 1), game.getHeroPosition());// test 1.2
		hero.move('s', gameMap);// move hero down
		assertEquals(new CellPosition(1, 2), game.getHeroPosition());// test 1.1
		hero.move('a', gameMap);// move hero left
		assertEquals(new CellPosition(1, 2), game.getHeroPosition());// test 1.4
		// assertFalse(game.end());//test 1.4
		hero.move('s', gameMap);// move hero down
		assertEquals(new CellPosition(1, 3), game.getHeroPosition());// test 1.5
		assertEquals(gameMap.get(0, 3), 's');// test 1.5
		assertEquals(gameMap.get(0, 2), 's');// test 1.5
		hero.move('a', gameMap);
		assertEquals(new CellPosition(0, 3), game.getHeroPosition());// test 1.6
		assertTrue(game.end());// test 1.6
		assertEquals(Game.win, game.getEndStatus());// test 1.6

	}

	@Test
	public void testHeroIsCapturedByGuard() {// test 1.3
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map);
		Hero hero = new Hero(1, 1, 'H');
		Guarda guarda = new Rookie(3, 1, 'G', mov);
		entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('d', gameMap);// move hero to the right
		assertTrue(game.end());
		assertEquals(Game.DEFEAT, game.getEndStatus());
		//gameMap.print(entidades, flag);
	}

	@Test
	public void testHeroIsHitByOgre() {// test 2.1
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map1);
		Hero hero = new Hero(1, 1, 'H');
		Ogre ogre = new Ogre(3, 1, 'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('d', gameMap);// move hero to the right
		assertTrue(game.end());
		assertEquals(Game.DEFEAT, game.getEndStatus());
	}

	@Test
	public void testHeroPicksTheKey() {// test 2.2
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1, 1, 'H');
		Ogre ogre = new Ogre(3, 1, 'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('s', gameMap);
		hero.move('s', gameMap);// move hero to the right
		assertEquals(hero.getTag(), 'K');
	}

	@Test
	public void testHeroTriesTheDoor() {// test 2.3
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1, 1, 'H');
		Ogre ogre = new Ogre(3, 1, 'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('s', gameMap);// move hero down
		hero.move('a', gameMap);// move hero to the left
		assertEquals(gameMap.get(0, 2), 'i');
	}

	@Test
	public void testHeroOpensTheDoor() {// test 2.4 e 2.5
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1, 1, 'H');
		Ogre ogre = new Ogre(3, 1, 'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('s', gameMap);
		hero.move('s', gameMap);
		assertEquals(hero.getTag(), 'K');
		hero.move('w', gameMap);
		hero.move('a', gameMap);
		assertEquals(gameMap.get(0, 2), 's');
		hero.move('a', gameMap);
		assertTrue(game.end());
		assertEquals(Game.win, game.getEndStatus());
	}

	@Test(timeout = 1000)
	public void testSomeRandomBehaviour() {
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(level2);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1, 1, 'H');
		Ogre ogre = new Ogre(1, 3, 'O');
		entidades.add(ogre);
		entidades.add(hero);
		Game game = new Game(gameMap, entidades);

		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {
			x1 = ogre.getX();
			y1 = ogre.getY();
			ogre.move('a', gameMap);// input is ignored
			x2 = ogre.getX();
			y2 = ogre.getY();
			if (x2 == x1 + 1 && y1 == y2) // moved to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2) // moved to the left // removed else
											// from the template
				outcome2 = true;
			if (y2 == y1 + 1 && x1 == x2) // moved up
				outcome3 = true;
			if (y2 == y1 - 1 && x1 == x2) // moved down
				outcome4 = true;
			gameMap.print(entidades, flag);
		}

		assertTrue(outcome1);
		assertTrue(outcome2);
		assertTrue(outcome3);
		assertTrue(outcome4);
	}

	@Test(timeout = 1000)
	public void testSomeRandomAttack() {
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(level2);
		gameMap.setName("testlevel2");
		Ogre ogre = new Ogre(3, 3, 'O');
		entidades.add(ogre);
		Game game = new Game(gameMap, entidades);

		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {
			x1 = ogre.getX();
			y1 = ogre.getY();
			ogre.attack(gameMap);
			x2 = ogre.getLastAttack()[0];
			y2 = ogre.getLastAttack()[1];
			if (x2 == x1 + 1 && y1 == y2) // attacked to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2) // attacked to the left // removed
											// else from the template
				outcome2 = true;
			if (y2 == y1 + 1 && x1 == x2) // attacked up
				outcome3 = true;
			if (y2 == y1 - 1 && x1 == x2) // attacked down
				outcome4 = true;
			gameMap.print(entidades, flag);
			game.clearAttack();
		}

		assertTrue(outcome1);
		assertTrue(outcome2);
		assertTrue(outcome3);
		assertTrue(outcome4);

	}

	@Test
	public void testGuarda() {
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(level2);
		gameMap.setName("testlevel2");
		Guarda guarda = new Rookie(2, 1, 'G', move);
		Guarda guarda1 = new Suspicious(4, 1, 'G', move);
		Guarda guarda2 = new Drunken(2, 4, 'G', move);

		entidades.addElement(guarda);
		entidades.addElement(guarda1);
		entidades.addElement(guarda2);

		boolean outcome1 = false, outcome2 = false, outcome3 = false;

		while (!outcome1 || !outcome2 || !outcome3) {
			for (Entidade temp : entidades) {
				if (temp instanceof Rookie)
					((Rookie) temp).move('a', gameMap);
				if (temp instanceof Suspicious)
					((Suspicious) temp).move('a', gameMap);
				if (temp instanceof Drunken)
					((Drunken) temp).move('a', gameMap);
			}
			if (entidades.elementAt(2).getTag() == 'g')
				outcome1 = true;
			if (((Drunken) entidades.elementAt(2)).getDir() == false)
				outcome2 = true;
			if (((Suspicious) entidades.elementAt(1)).getDir() == false)
				outcome3 = true;
		}

		assertTrue(outcome1);
		assertTrue(outcome2);
		assertTrue(outcome3);
	}

	@Test
	public void testGuardaDoesntMove() {
		boolean fail = false;
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(map2);
		gameMap.setName("map2");
		Guarda guarda = new Rookie(1, 1, 'G', move);
		int xi = guarda.getX();
		int yi = guarda.getY();

		entidades.addElement(guarda);

		int i = 0;
		while (i < 50) {
			for (Entidade temp : entidades) {
				if (temp instanceof Rookie)
					((Rookie) temp).move('a', gameMap);

			}
			if (entidades.elementAt(0).getX() != xi || entidades.elementAt(0).getY() != yi) {
				assertTrue(fail);
				return;
			}
			i++;
		}
	}

}
