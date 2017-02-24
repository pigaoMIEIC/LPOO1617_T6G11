package dkeep.logic;

public class Board {
	char board[][];
	String name;
			
	public Board(char[][] layout){
		board=layout;
	}

	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void updateLevel(char[][] level){
		board=level;
	}
	
	public void set(int x,int y, char value){
		board[y][x]= value;
	}
	
	public char get(int x,int y){
		return board[y][x];
	}
	
	public char refresh(int x, int y, char dir,char tag,char current) {
		char temp = this.get(x,y);
		if(temp=='k'&&name=="level2"){
			switch(tag){
			case 'h':
				tag='K';
				break;
			case 'o':
				tag='$';
				break;
			}		
		}
		switch (dir) {
		case 's':
			this.board[y][x]=tag;
			this.board[y-1][x]=	current;
			break;
		case 'w':
			this.board[y][x]=tag;
			this.board[y+1][x]=	current;
			break;
		case 'd':
			this.board[y][x]=tag;
			this.board[y][x-1]=	current;
			break;
		case 'a':
			this.board[y][x]=tag;
			this.board[y][x+1]=	current;
			break;
		}
		return temp;
	}
	
	public void openDoors(){		
//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board[0].length; j++) {
//				if(board[j][i]=='i')
//					board[j][i]='s';
//			}
//		}
		if (name == "level1") {
			board[5][0] = 's';
			board[6][0] = 's';
		}
		if (name == "level2") {
			board[1][0] = 's';
		}
	}
}