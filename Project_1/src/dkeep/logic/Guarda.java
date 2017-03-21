package dkeep.logic;

public abstract class Guarda extends Entidade {

	ComportamentoGuarda compGuarda;

	public Guarda(int x, int y, char tag, char mov[]) {
		super(x, y, tag);
		compGuarda = new ComportamentoGuarda(mov);
	}

	@Override
	public boolean print(String level, char current, Board b,String output[]) {
		if (level.equals("level1"))
			output[0]+=(tag);
		if (level.equals("level2")) {
			switch (current) {
			case 'k':
				output[0]+=('$');
				break;
			case 'A':
				output[0]+=(tag);
				break;
			default:
				output[0]+=(tag);
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
