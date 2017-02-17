
public class Board {
	char board[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', 'h', ' ', ' ', 'i', ' ', 'x', ' ', 'g', 'x' }, { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' },
			{ 'x', ' ', 'i', ' ', 'i', ' ', 'x', ' ', ' ', 'x' }, { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' },
			{ 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', ' ', 'x' }, { 'x', ' ', 'i', ' ', 'i', ' ', 'x', 'k', ' ', 'x' },
			{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}

	}
	
	public char get(int x,int y){
		return board[y][x];
	}
	
	public char refresh(int x, int y, char dir,char person,char current) {
		char temp = this.get(x,y);
		switch (dir) {
		case 's':
			this.board[y][x]=person;
			this.board[y-1][x]=	current;
			break;
		case 'w':
			this.board[y][x]=person;
			this.board[y+1][x]=	current;
			break;
		case 'd':
			this.board[y][x]=person;
			this.board[y][x-1]=	current;
			break;
		case 'a':
			this.board[y][x]=person;
			this.board[y][x+1]=	current;
			break;
		}
		return temp;
	}
	
	public void openDoors(){
		this.board[5][0]='s';
		this.board[6][0]='s';
	}
}