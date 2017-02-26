package dkeep.logic;

import java.util.Vector;

public class Board {
	char board[/* y */][/* x */];
	String name;

	public Board(char[][] layout) {
		board = layout;
	}

	public void print(Vector<Entidade> map, boolean endFlag[]) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (printEntidade(x, y, map, endFlag)) {
					System.out.print(' ');
					continue;
				}
				System.out.print(board[y][x]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	private boolean printEntidade(int x, int y, Vector<Entidade> map, boolean endFlag[]) {
		boolean flag = false;
		if (map == null)
			return flag;
		for (Entidade temp : map) {
			if (temp.x == x && temp.y == y) {
				if (endFlag[0]) {
					temp.print(this.name, board[y][x], this);
				} else {
					endFlag[0] = temp.print(this.name, board[y][x], this);
				}
				flag = true;
			}
		}
		return flag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateLevel(char[][] level) {
		board = level;
	}

	public void set(int x, int y, char value) {
		board[y][x] = value;
	}

	public char get(int x, int y) {
		return board[y][x];
	}

	public char refresh(int x, int y, char dir, char tag, char current) {
		char temp = this.get(x, y);
		if (temp == 'k' && name == "level2") {
			switch (tag) {
			case 'h':
				tag = 'K';
				break;
			case 'o':
				tag = '$';
				break;
			}
		}
		switch (dir) {
		case 's':
			this.board[y][x] = tag;
			this.board[y - 1][x] = current;
			break;
		case 'w':
			this.board[y][x] = tag;
			this.board[y + 1][x] = current;
			break;
		case 'd':
			this.board[y][x] = tag;
			this.board[y][x - 1] = current;
			break;
		case 'a':
			this.board[y][x] = tag;
			this.board[y][x + 1] = current;
			break;
		}
		return temp;
	}

	public void openDoors() {
		if (name == "level1") {
			board[5][0] = 's';
			board[6][0] = 's';
		}
		if (name == "level2") {
			board[1][0] = 's';
		}
	}
}