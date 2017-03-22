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
			output[0]+=(getTag());
		if (level.equals("level2")) {
			switch (current) {
			case 'k':
				output[0]+=('$');
				break;
			case 'A':
				output[0]+=(getTag());
				break;
			default:
				output[0]+=(getTag());
			}
		}
		return false;
	}

	

}
