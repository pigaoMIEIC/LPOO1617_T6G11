package dkeep.logic;

public abstract class Guarda extends Entidade {

	ComportamentoGuarda compGuarda;

	public Guarda(int x, int y, char tag, char mov[]) {
		super(x, y, tag);
		compGuarda = new ComportamentoGuarda(mov);
	}

	public boolean print(String level, char current, Board b) {
		if (level == "level1")
			System.out.print(tag);
		if (level == "level2") {
			switch (current) {
			case 'k':
				System.out.print('$');
				break;
			case 'A':
				System.out.print(tag);
				break;
			default:
				System.out.print(tag);
			}
		}
		return false;
	}

	public boolean checkSurround(Board b, char enemy) {
		if (b.get(x + 1, y) == enemy || b.get(x - 1, y) == enemy || b.get(x, y + 1) == enemy
				|| b.get(x, y - 1) == enemy) {
			return true;
		} else
			return false;

	}

}
