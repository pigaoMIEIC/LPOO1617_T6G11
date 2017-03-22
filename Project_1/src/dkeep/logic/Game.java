package dkeep.logic;

import java.awt.Component;
import java.util.Vector;

import dkeep.test.CellPosition;

//import dkeep.test.CellPosition;

public class Game implements java.io.Serializable{

	
	public final static int DEFEAT = 1;
	public final static int win = 0;
	int finalstatus = -1;
	
	static Board board;
	private static Vector<Entidade> map;
	boolean endLevel[] = new boolean[1];
	char input;

	public Game(Board b, Vector<Entidade> map) {
		Game.board = b;
		Game.setEntidades(map);
		endLevel[0] = false;
	}

	public void Move(char input) {
		for (Entidade temp : getEntidades()) {
			temp.move(input, board);//input is ignored if Ogre is in random mode
			if (temp instanceof Hero) {
				((Hero) temp).stun(board, getEntidades());
			}
		}
	}

	public void attack() {
		for (Entidade temp : getEntidades()) {
			if (temp instanceof Ogre) {
				((Ogre) temp).attack(board);
			}
		}
	}

	public void clearAttack() {
		for (Entidade temp : getEntidades()) {
			if (temp instanceof Ogre) {
				((Ogre) temp).clearAttack(board);
			}
		}
	}

	public String printBoard() {
		return board.print(getEntidades(), endLevel);
	}

	public boolean end() {
//		if (endLevel[0]) {
//			System.out.print("Parabéns passou o nível!!\n");
//			finalstatus = win;
//			return true;
//		}
		if (getEntidades().lastElement().getCurrent()=='s') {
			System.out.print("Parabéns passou o nível!!\n");
			finalstatus = win;
			return true;
		}
		if (((Hero) getEntidades().lastElement()).checkSurround('G', getEntidades())) {
			System.out.print("Foi apanhado pelo guarda. :(");
			finalstatus = DEFEAT;
			 return true;
		}
		 if (((Hero) getEntidades().lastElement()).checkSurround('O', getEntidades())) {
		 System.out.print("Foi apanhado pelo Ogre. :(");
		 finalstatus = DEFEAT;
		 return true;
		 }
		if (((Hero) getEntidades().lastElement()).checkSurround(board, '*')) {
			System.out.print("Foi atingido pelo Ogre. :(");
			finalstatus = DEFEAT;
			return true;
		}
		
		
		return false;
	}

	public CellPosition getHeroPosition() {
		CellPosition temp = new CellPosition(getEntidades().lastElement().x, getEntidades().lastElement().y);
		return temp;
	}

	public int getEndStatus() {
		// TODO Auto-generated method stub
		return finalstatus;
	}

	public Board getBoard() {
		return board;
	}

	public Vector<Entidade> getEntidades() {
		return map;
	}

	public static void setEntidades(Vector<Entidade> map) {
		Game.map = map;
	}

	public void setBoard(Board b) {
		Game.board=b;
	}

}
