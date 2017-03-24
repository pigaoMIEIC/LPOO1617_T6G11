package dkeep.logic;

public abstract class Guarda extends Entidade {

	ComportamentoGuarda compGuarda;
	boolean dir;

	/**
	 * Constructor of the class Guarda
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param tag char for the Guarda representation.
	 * @param mov char array for the Guarda movement.
	 * @see ComportamentoGuarda
	 */
	public Guarda(int x, int y, char tag, char mov[]) {
		super(x, y, tag);
		compGuarda = new ComportamentoGuarda(mov);
		dir = true;
	}

	public boolean getDir(){
		return dir;
	}
	
	public void setDir(boolean dir){
		this.dir = dir;
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
