package dkeep.logic;

import java.util.Scanner;
import java.util.Vector;

public class Game {

	private static Scanner s = new Scanner(System.in);

	static Board board;
	static Vector<Entidade> map;
	boolean endLevel[] = new boolean[1];
	char input;

	public Game(Board b, Vector<Entidade> map) {
		Game.board = b;
		Game.map = map;
		endLevel[0]=false;
	}

	public void Run() {
		board.print(map, endLevel);
		while (!endLevel[0]) {
			input = s.next().charAt(0);
			for (Entidade temp : map) {
				temp.move(input, board);
				if(temp instanceof Hero){
				endLevel[0]=((Hero) temp).checkSurround(board, 'G',map);
				}
			}
			board.print(map, endLevel);
		}
	}

}
