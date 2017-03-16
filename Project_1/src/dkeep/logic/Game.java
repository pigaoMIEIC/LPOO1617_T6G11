package dkeep.logic;

import java.awt.Component;
import java.util.Vector;

import dkeep.test.CellPosition;

//import dkeep.test.CellPosition;

public class Game {

	//public enum  finalstatus {DEFEAT,WIN} a maneira do eduardo n funciona :(
	
	
	public final static int DEFEAT = 1;
	public final static int win = 0;
	int finalstatus = -1;
	
	static Board board;
	static Vector<Entidade> map;
	boolean endLevel[] = new boolean[1];
	char input;

	public Game(Board b, Vector<Entidade> map) {
		Game.board = b;
		Game.map = map;
		endLevel[0] = false;
	}

	public void Move(char input) {
		for (Entidade temp : map) {
			temp.move(input, board);//input is ignored if Ogre is in random mode
			if (temp instanceof Hero) {
				((Hero) temp).stun(board, map);
			}
		}
	}

	public void attack() {
		for (Entidade temp : map) {
			if (temp instanceof Ogre) {
				((Ogre) temp).attack(board);
			}
		}
	}

	public void clearAttack() {
		for (Entidade temp : map) {
			if (temp instanceof Ogre) {
				((Ogre) temp).clearAttack(board);
			}
		}
	}

	public String printBoard() {
		return board.print(map, endLevel);
	}

	public boolean end() {
//		if (endLevel[0]) {
//			System.out.print("Parabéns passou o nível!!\n");
//			finalstatus = win;
//			return true;
//		}
		if (map.lastElement().current=='s') {
			System.out.print("Parabéns passou o nível!!\n");
			finalstatus = win;
			return true;
		}
		if (((Hero) map.lastElement()).checkSurround('G', map)) {
			System.out.print("Foi apanhado pelo guarda. :(");
			finalstatus = DEFEAT;
			 return true;
		}
		 if (((Hero) map.lastElement()).checkSurround('O', map)) {
		 System.out.print("Foi apanhado pelo Ogre. :(");
		 finalstatus = DEFEAT;
		 return true;
		 }
		if (((Hero) map.lastElement()).checkSurround(board, '*')) {
			System.out.print("Foi atingido pelo Ogre. :(");
			finalstatus = DEFEAT;
			return true;
		}
		
		
		return false;
	}

	public CellPosition getHeroPosition() {
		CellPosition temp = new CellPosition(map.lastElement().x, map.lastElement().y);
		return temp;
	}

	public int getEndStatus() {
		// TODO Auto-generated method stub
		return finalstatus;
	}

	public Board getBoard() {
		return board;
	}

}
