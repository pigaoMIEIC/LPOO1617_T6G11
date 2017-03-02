package dkeep.logic;

import java.util.Vector;

public class Game {
	
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
			temp.move(input, board);
			if (temp instanceof Hero) {
				((Hero) temp).stun(board,map);
			}
		}
	}
	
	public void attack(){
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

	public void printBoard() {
		board.print(map, endLevel);
	}

	public boolean end() {
		if (((Hero) map.lastElement()).checkSurround('G', map)) {
			System.out.print("Foi apanhado pelo guarda. :(");
			return true;
		}
//		if (((Hero) map.lastElement()).checkSurround('O', map)) {
//			System.out.print("Foi apanhado pelo Ogre. :(");
//			return true;
//		}
		if (((Hero) map.lastElement()).checkSurround(board, '*')) {
			System.out.print("Foi atingido pelo Ogre. :(");
			return true;
		}
		if (endLevel[0]) {
			System.out.print("Parabéns passou o nível!!\n");
			return true;
		}
		return false;
	}

}
