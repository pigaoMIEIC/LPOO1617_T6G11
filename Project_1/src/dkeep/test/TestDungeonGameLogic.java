package dkeep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import dkeep.logic.*;

public class TestDungeonGameLogic {
	char a = '1';

	char[][] map = { { 'x', 'x', 'x', 'x', 'x' }, { 'x', /* H */' ', ' ', /* G */' ', 'x' },
			{ 'i', ' ', ' ', ' ', 'x' }, { 'i', 'k', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x' } };

	char[][] map1 = { { 'x', 'x', 'x', 'x', 'x' }, { 'x', /* H */' ', ' ', /* O */' ', 'x' },
			{ 'i', ' ', ' ', ' ', 'x' }, { 'x', 'k', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x' } };

	char level2[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	char[][] map2 = { { 'x', 'x', 'x' }, { 'i', ' ', 'x' }, { 'x', 'x', 'x' } };

	char[][] errorMap = { { 'x', 'x', 'x' }, { 'i', 'A', 'x' }, { 'A', 'x', 'x' } };
	char[][] errorMap2 = { { 'x', 'x', 'x' }, { 'i', 'k', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap3 = { { 'x', 'x', 'x' }, { 'i', ' ', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap4 = { { 'x', 'x', 'x' }, { 'i', 'A', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap5 = { { 'x', 'x', 'x' }, { 'i', ' ', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap6 = { { 'x', 'x', 'x' }, { 'i', 'O', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap7 = { { 'x', 'x', 'x' }, { 'x', ' ', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorMap8 = { { 'x', 'x', 'x', 'x' }, { 'i', 'O', 'A', 'k' }, { 'x', 'x', 'x', 'x' } };

	char[][] mapPit = { { 'x', 'x', 'x', 'x' }, { 'x', ' ', ' ', 'x' }, { 'i', ' ', ' ', 'x' }, { 'i', ' ', ' ', 'x' },
			{ 'x', 'x', 'x', 'x' } };

	char[][] errorD = { { 'x', 'x', 'x' }, { 'x', ' ', ' ' }, { 'x', 'x', 'x' } };
	char[][] errorW = { { 'x', ' ', 'x' }, { 'x', ' ', 'x' }, { 'x', 'x', 'x' } };
	char[][] errorS = { { 'x', 'x', 'x' }, { 'x', ' ', 'x' }, { 'x', ' ', 'x' } };
	char[][] errorA = { { 'x', 'x', 'x' }, { ' ', ' ', 'x' }, { 'x', 'x', 'x' } };
	
	char[][] errorLoose = { { 'x', 'x', 'x', 'x', 'x' },{ 'x', ' ', ' ', ' ', 'x' },{ 'x', ' ', ' ', ' ', 'x' },{ 'x', ' ', ' ', ' ', 'x' },{ 'x', 'x', 'x', 'x', 'x' }};

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
		// Guarda guarda = new Rookie(3, 1, 'G', mov);
		// entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		hero.setCurrent(' ');
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
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
		assertTrue(game.end());// test 1.6
		assertEquals(Game.win, game.getEndStatus());// test 1.6
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));

	}

	@Test
	public void testHeroIsCapturedByGuard() {// test 1.3
		entidades.removeAllElements();
		flag[0] = false;
		Board gameMap = new Board(map);
		gameMap.setName("level1");
		Hero hero = new Hero(1, 1, 'H');
		Guarda guarda = new Rookie(3, 1, 'G', mov);
		entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('d', gameMap);// move hero to the right
		assertTrue(game.end());
		assertEquals(Game.DEFEAT, game.getEndStatus());
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
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
		gameMap.setName("level2");
		Hero hero = new Hero(1, 1, 'H');
		// Ogre ogre = new Ogre(3, 1, 'O');
		// entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		game.Move('s');
		game.Move('s');// move hero to the right
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
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
		game.Move('s');// move hero down
		game.Move('a');// move hero to the left
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
		game.Move('s');
		game.Move('s');
		assertEquals(hero.getTag(), 'K');
		game.Move('w');
		game.Move('a');
		assertEquals(gameMap.get(0, 2), 's');
		game.Move('a');
		assertTrue(game.end());
		assertEquals(Game.win, game.getEndStatus());
	}

	@Test // (timeout = 1000)
	public void testSomeRandomBehaviour() {
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(level2);
		gameMap.setName("level2");
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
			assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
		}

		assertTrue(outcome1);
		assertTrue(outcome2);
		assertTrue(outcome3);
		assertTrue(outcome4);
	}

	@Test(timeout = 1000)
	public void testSomeRandomAttackD() {
		char dir;
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(errorD);
		gameMap.setName("level2");
		Ogre ogre = new Ogre(1, 1, 'O');
		entidades.add(ogre);
		Game game = new Game(gameMap, entidades);
		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {

			x1 = ogre.getX();
			y1 = ogre.getY();
			dir = game.attackTest();
			x2 = ogre.getLastAttack()[0];
			y2 = ogre.getLastAttack()[1];
			if (x2 == x1 + 1 && y1 == y2 && dir == 'd') // attacked to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2 && dir == 'x') // attacked to the left
														// // removed else from
														// the template
				outcome2 = true;
			if (y2 == y1 - 1 && x1 == x2 && dir == 'x') // attacked up
				outcome3 = true;
			if (y2 == y1 + 1 && x1 == x2 && dir == 'x') // attacked down
				outcome4 = true;
			assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
			game.clearAttack();
		}

		assertTrue(outcome1);
		assertTrue(outcome2);
		assertTrue(outcome3);
		assertTrue(outcome4);

	}

	@Test(timeout = 1000)
	public void testSomeRandomAttackW() {
		char dir;
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(errorW);
		gameMap.setName("level2");
		Ogre ogre = new Ogre(1, 1, 'O');
		entidades.add(ogre);
		Game game = new Game(gameMap, entidades);
		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {
			
			x1 = ogre.getX();
			y1 = ogre.getY();
			dir = game.attackTest();
			x2 = ogre.getLastAttack()[0];
			y2 = ogre.getLastAttack()[1];
			if (x2 == x1 + 1 && y1 == y2 && dir == 'x') // attacked to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2 && dir == 'x') // attacked to the left // removed else from the template
				outcome2 = true;
			if (y2 == y1 - 1 && x1 == x2 && dir == 'w') // attacked up
				outcome3 = true;
			if (y2 == y1 + 1 && x1 == x2 && dir == 'x') // attacked down
				outcome4 = true;
			assertTrue(stringIsSameSize(gameMap.print(entidades, flag),gameMap));
			game.clearAttack();
		}
	}

	@Test(timeout = 1000)
	public void testSomeRandomAttackA() {
		char dir;
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(errorA);
		gameMap.setName("level2");
		Ogre ogre = new Ogre(1, 1, 'O');
		entidades.add(ogre);
		Game game = new Game(gameMap, entidades);
		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {
			
			x1 = ogre.getX();
			y1 = ogre.getY();
			dir = game.attackTest();
			x2 = ogre.getLastAttack()[0];
			y2 = ogre.getLastAttack()[1];
			if (x2 == x1 + 1 && y1 == y2 && dir == 'x') // attacked to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2 && dir == 'a') // attacked to the left // removed else from the template
				outcome2 = true;
			if (y2 == y1 - 1 && x1 == x2 && dir == 'x') // attacked up
				outcome3 = true;
			if (y2 == y1 + 1 && x1 == x2 && dir == 'x') // attacked down
				outcome4 = true;
			assertTrue(stringIsSameSize(gameMap.print(entidades, flag),gameMap));
			game.clearAttack();
		}
	}
	
	@Test(timeout = 1000)
	public void testSomeRandomAttackS() {
		char dir;
		flag[0] = false;
		entidades.removeAllElements();
		boolean outcome1 = false, outcome2 = false, outcome3 = false, outcome4 = false;
		int x1, x2, y1, y2;
		Board gameMap = new Board(errorS);
		gameMap.setName("level2");
		Ogre ogre = new Ogre(1, 1, 'O');
		entidades.add(ogre);
		Game game = new Game(gameMap, entidades);
		while (!outcome1 || !outcome2 || !outcome3 || !outcome4) {
			
			x1 = ogre.getX();
			y1 = ogre.getY();
			dir = game.attackTest();
			x2 = ogre.getLastAttack()[0];
			y2 = ogre.getLastAttack()[1];
			if (x2 == x1 + 1 && y1 == y2 && dir == 'x') // attacked to the right
				outcome1 = true;
			if (x2 == x1 - 1 && y1 == y2 && dir == 'x') // attacked to the left // removed else from the template
				outcome2 = true;
			if (y2 == y1 - 1 && x1 == x2 && dir == 'x') // attacked up
				outcome3 = true;
			if (y2 == y1 + 1 && x1 == x2 && dir == 's') // attacked down
				outcome4 = true;
			assertTrue(stringIsSameSize(gameMap.print(entidades, flag),gameMap));
			game.clearAttack();
		}
		
	}
	
	@Test
	public void testRookie() {
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(mapPit);
		gameMap.setName("level2");
		Guarda guarda = new Rookie(3, 1, 'G', move);

		entidades.addElement(guarda);

		boolean outcome1 = false, outcome2 = false, outcome3 = false;

		int i = 0;
		while (i < 12) {
			assertTrue(entidades.elementAt(0).getCurrent() == ' ');
			i++;
		}

		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
	}

	@Test
	public void testSuspicious() {
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(mapPit);
		gameMap.setName("level2");
		Guarda guarda = new Suspicious(3, 1, 'G', move);

		entidades.addElement(guarda);

		boolean outcome1 = false;

		while (!outcome1) {
			for (Entidade temp : entidades) {
				if (temp instanceof Suspicious)
					((Suspicious) temp).move('a', gameMap);

			}
			if (((Suspicious) entidades.elementAt(0)).getDir() == false)
				outcome1 = true;

		}

		assertTrue(outcome1);

		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
	}

	@Test
	public void testDrunken() {
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(mapPit);
		gameMap.setName("level2");
		Guarda guarda = new Drunken(3, 1, 'G', move);

		entidades.addElement(guarda);

		boolean outcome1 = false, outcome2 = false;

		int i = 0;
		while (!outcome1 || !outcome2 || !(i > 20)) {
			guarda.move('a', gameMap);

			if (((Drunken) guarda).getDir() == false)
				outcome1 = true;
			if (((Drunken) guarda).getTag() == 'g')
				outcome2 = true;
			i++;
			assertTrue(guarda.getCurrent() == ' ');
		}

		assertTrue(outcome1);
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));
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

	@Test
	public void readBoard() {
		boolean fail = false;
		entidades.removeAllElements();
		flag[0] = false;
		char[] move = { 'a', 's', 'd', 'w' };
		Board gameMap = new Board(errorMap);
		assertNull(gameMap.readBoard());
		Board gameMap2 = new Board(errorMap2);
		assertNull(gameMap2.readBoard());
		Board gameMap3 = new Board(errorMap3);
		assertNull(gameMap3.readBoard());
		Board gameMap4 = new Board(errorMap4);
		assertNull(gameMap4.readBoard());
		Board gameMap5 = new Board(errorMap5);
		assertNull(gameMap5.readBoard());
		Board gameMap6 = new Board(errorMap6);
		assertNull(gameMap6.readBoard());
		Board gameMap7 = new Board(errorMap7);
		assertNull(gameMap7.readBoard());

	}

	@Test
	public void testString() {
		entidades.removeAllElements();
		Board gameMap = new Board(level2);
		assertTrue(stringIsSameSize(gameMap.print(entidades, flag), gameMap));

	}
	
	@Test
	public void testCheckSurround() {
		entidades.removeAllElements();
		Board gameMap = new Board(errorLoose);
		
	}

	boolean stringIsSameSize(String board, Board b) {
		char bod[][] = b.getBoard();

		int l = bod[0].length;
		int h = bod.length;
		int sizeBod = l * h;

		int sizeString = board.length() - bod.length;
		// System.out.print(board);
		// System.out.print("" + sizeString +'\n');
		// System.out.print("" + sizeBod + '\n' + '\n');

		return sizeBod == sizeString;
	}
}
