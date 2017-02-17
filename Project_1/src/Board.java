
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
			}
		}

	}
}