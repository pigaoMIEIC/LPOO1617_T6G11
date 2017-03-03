package dkeep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import dkeep.logic.*;

public class TestDungeonGameLogic {

	char [][] map={{'x','x','x','x','x'},
			{'x',/*H*/' ',' ',/*G*/' ','x'},
			{'i',' ',' ',' ','x'},
			{'i','k',' ',' ','x'},
			{'x','x','x','x','x'}};
	
	char [][] map1={{'x','x','x','x','x'},
			{'x',/*H*/' ',' ',/*O*/' ','x'},
			{'i',' ',' ',' ','x'},
			{'x','k',' ',' ','x'},
			{'x','x','x','x','x'}};
	
	char [] mov = {'s','w'};
	
	Vector<Entidade> entidades = new Vector<Entidade>(); 
	@Test
	public void testMoveHeroIntoToFreeCell() {
		Board gameMap = new Board(map);
		gameMap.setName("testlevel");
		Hero hero = new Hero(1,1,'H');
		Guarda guarda = new Rookie(3,1,'G',mov);
		entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertEquals(new CellPosition(1, 1), game.getHeroPosition());
		hero.move('a', gameMap);
		assertEquals(new CellPosition(1, 1), game.getHeroPosition());//test 1.2
		hero.move('s', gameMap);// move hero down
		assertEquals(new CellPosition(1, 2), game.getHeroPosition());//test 1.1
		hero.move('a', gameMap);// move hero left
		assertEquals(new CellPosition(1, 2), game.getHeroPosition());//test 1.4
		//assertFalse(game.end());//test 1.4
		hero.move('s', gameMap);// move hero down
		assertEquals(new CellPosition(1, 3), game.getHeroPosition());//test 1.5
		assertEquals(gameMap.get(0, 3),'s');//test 1.5
		assertEquals(gameMap.get(0, 2),'s');//test 1.5
		hero.move('a', gameMap);
		assertEquals(new CellPosition(0, 3), game.getHeroPosition());//test 1.6
		assertTrue(game.end());//test 1.6
		assertEquals(Game.win, game.getEndStatus());//test 1.6
		
	}

	@Test
	public void testHeroIsCapturedByGuard() {//test 1.3

		Board gameMap = new Board(map);
		Hero hero = new Hero(1,1,'H');
		Guarda guarda = new Rookie(3,1,'G',mov);
		entidades.add(guarda);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('d', gameMap);// move hero to the right
		assertTrue(game.end());
		assertEquals(Game.DEFEAT, game.getEndStatus());
	}
	
	@Test
	public void testHeroIsHitByOgre() {//test 2.1
		Board gameMap = new Board(map1);
		Hero hero = new Hero(1,1,'H');
		Ogre ogre = new Ogre(3,1,'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('d', gameMap);// move hero to the right
		assertTrue(game.end());
		assertEquals(Game.DEFEAT, game.getEndStatus());
	}
	
	@Test
	public void testHeroPicksTheKey() {//test 2.2
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1,1,'H');
		Ogre ogre = new Ogre(3,1,'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('s', gameMap);
		hero.move('s', gameMap);// move hero to the right
		assertEquals(hero.getTag(), 'K');
	}
	
	@Test
	public void testHeroTriesTheDoor() {//test 2.3
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1,1,'H');
		Ogre ogre = new Ogre(3,1,'O');
		entidades.add(ogre);
		entidades.addElement(hero);
		Game game = new Game(gameMap, entidades);
		assertFalse(game.end());
		hero.move('s', gameMap);// move hero down
		hero.move('a', gameMap);// move hero to the left
		assertEquals(gameMap.get(0, 2), 'i');
	}
	@Test
	public void testHeroOpensTheDoor() {//test 2.4 e 2.5
		Board gameMap = new Board(map1);
		gameMap.setName("testlevel2");
		Hero hero = new Hero(1,1,'H');
		Ogre ogre = new Ogre(3,1,'O');
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
}
