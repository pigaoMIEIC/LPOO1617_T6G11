package dkeep.logic;

import java.io.Serializable;
import java.util.Vector;

public class Board implements Serializable{
	char board[/* y */][/* x */];
	

	String name;
	String output[]= new String[1];

	public Board(char[][] layout) {
		board = copy(layout);
	}

	public char[][] getBoard() {
		return board;
	}
	
	private char[][] copy(char[][] layout) {
		char[][] copy = new char[layout.length][layout[0].length];
		for(int i=0;i<layout.length;i++){
			for(int j=0; j<layout[0].length;j++){
				copy[i][j]=layout[i][j];
			}
		}
		return copy;
	}

	public String print(Vector<Entidade> map, boolean endFlag[]) {
		output[0]="";
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (this.printEntidade(x, y, map, endFlag,output)) {
					//output[0]+=' ';
					continue;
				}
				output[0]+=(board[y][x]);
				//output[0]+=' ';
			}
			output[0]+="\n";
		}
		return output[0];
	}

	private boolean printEntidade(int x, int y, Vector<Entidade> map, boolean endFlag[],String output[]) {
		boolean flag = false;
		if (map == null)
			return flag;
		for (Entidade temp : map) {
			if (temp.x == x && temp.y == y) {
				if (endFlag[0]) {
					temp.print(this.name, temp.current, this, output);
				} else {
					endFlag[0] = temp.print(this.name, temp.current, this, output);
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
			case 'O':
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
		if (name.equals("level1")) {
			board[5][0] = 's';
			board[6][0] = 's';
		}
		if (name.equals("level2")) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[0].length; x++) {
					if(board[y][x]=='i'){
						board[y][x]='s';
					}
				}
			}
		}
		if(name.equals("testlevel")){
			board[2][0] = 's';
			board[3][0] = 's';
		}
		if(name.equals("testlevel2")){
			board[2][0] = 's';
		}
	}

	public String getName() {
		return name;
	}

	public Vector<Entidade> readBoard() {
		Hero hero=null;
		boolean hasDoor=false, hasKey=false;
		Vector<Entidade> temp = new Vector<Entidade>();
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				switch(board[y][x]){
				case 'A':
					if(hero!=null)
						return null;
					hero=new Hero(x,y,'A');
					break;
				case 'O':temp.add(new Ogre(x,y,'O')); break;
				case 'i':hasDoor=true;
				case 'k':hasKey=true;
				}
			}
		}
		if(!(hasKey&&hasDoor&&!temp.isEmpty()&&hero!=null)){
			return null;
		}
		temp.add(hero);
		return temp;
	}
}