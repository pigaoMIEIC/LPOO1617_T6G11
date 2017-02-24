package dkeep.logic;

public class Guarda extends Entidade {
	ComportamentoGuarda compGuarda;
	char tag;
	public Guarda(int x, int y, char tag,char mov[]) {
		super(x, y);
		this.tag = tag;
		compGuarda = new ComportamentoGuarda(mov);
	}

	public boolean checkSurround(Board b, char enemy) {
		if (b.get(x + 1, y) == enemy || b.get(x - 1, y) == enemy || b.get(x, y + 1) == enemy
				|| b.get(x, y - 1) == enemy) {
			System.out.println("You got caught! :(");
			return true;
		} else
			return false;
	}
}
