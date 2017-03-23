package dkeep.logic;

import java.util.Vector;


/**  
* Entidade.java - A class to store moving parts of the game.
*/ 
public class Hero extends Entidade {

	private char state = ' ';

	/**
	 * Getter for the field state.
	 * @return
	 */
	public char getState() {
		return state;
	}

	/**
	 * Setter for the field state.
	 * @param state
	 */
	public void setState(char state) {
		this.state = state;
	}

	/**  
	* Constructor of the class Hero.
	* @param x X position of the Entidade.
	* @param y Y position of the Entidade.
	* @param tag The simple graphic representation of the Entidade.
	*/ 
	public Hero(int x, int y, char tag) {
		super(x, y, tag);
	}
	
	/**
	 * @param level Name of the current level for game mechanics purposes.
	 * @param current Game board key the hero is currently.
	 * @param b Game Board  to print. 
	 * @param output One element array of Sting to save the output of the Game state.
	 * @return Returns true if game has ended in victory, false otherwise.
	 */
	public boolean print(String level, char current, Board b, String output[]) {
		if (level.equals("level1") && current != ' ') {
			switch (current) {
			case 'k':
				b.openDoors();
				output[0]+=(this.getTag());
				break;
			case 's':
				output[0]+=(this.getTag());
				return true;
			}
		} else if (level.equals("level2") && current != ' ') {
			switch (current) {
			case 'k':
				this.setTag('K');
				this.setState('K');
				output[0]+=(this.getTag());
				break;
			case 's':
				output[0]+=(this.getTag());
				return true;
			}
		}else 
				if(b.get(x, y)==this.getTag()|b.get(x, y)==' '){
			output[0]+=(this.getTag());
		}
		if(b.get(x, y)=='*'){
			output[0]+='*';
		}

		return false;
	}
	
	/**
	 * Method responsible for moving the Hero in the game Board.
	 * @param b Game Board  to print. 
	 * @param output One element array of Sting to save the output of the Game state.
	 * @return Returns true if game has ended in victory. False otherwise.
	 */
	public void move(char input, Board b) {
		switch (input) {
		case 's':
//			if (b.get(x, y + 1) == 'x') {
//				break;
//			}
//			if (b.get(x, y + 1) == 'i') {
//				if (state == 'K')
//					b.openDoors();
//				break;
//			}
			if(!moveCondition(x,y+1,b))
				break;
			setCurrent((char) b.refresh(x, y + 1, input, getTag(), getCurrent()));
			if (getCurrent() == 'k' && b.getName().equals("testlevel")) {
				state = 'K';
				b.openDoors();
			}
			if (getCurrent() == 'k' && b.getName().equals("testlevel2")) {
				state = 'K';
				setTag('K');
			}
			y++;
			break;
		case 'w':
//			if (b.get(x, y - 1) == 'x') {
//				break;
//			}
//			if (b.get(x, y - 1) == 'i') {
//				if (state == 'K')
//					b.openDoors();
//				break;
//			}
			if(!moveCondition(x,y-1,b))
				break;
			setCurrent((char) b.refresh(x, y - 1, input, getTag(), getCurrent()));
			y--;
			break;
		case 'd':
//			if (b.get(x + 1, y) == 'x') {
//				break;
//			}
//			if (b.get(x + 1, y) == 'i') {
//				if (state == 'K')
//					b.openDoors();
//				break;
//			}
			if(!moveCondition(x+1,y,b))
				break;
			setCurrent((char) b.refresh(x + 1, y, input, getTag(), getCurrent()));
			this.x++;
			break;
		case 'a':
//			if (b.get(x - 1, y) == 'x') {
//				break;
//			}
//			if (b.get(x - 1, y) == 'i') {
//				if (state == 'K')
//					b.openDoors();
//				break;
//			}
			if(!moveCondition(x-1,y,b))
				break;
			setCurrent((char) b.refresh(x - 1, y, input, getTag(), getCurrent()));
			this.x--;
			break;
		default:
			System.out.println("default input");
		}
	}

	/**
	 * Verifies if the Hero has an enemy in his surrounding positions.
	 * @param enemy char data type that represents the enemy to be looked for.
	 * @param map Vector of Entidade's where the enemy will be looked for.
	 * @return True if the enemy is in the Hero surroundings, False otherwise.
	 */
	public boolean checkSurround(char enemy, Vector<Entidade> map) {
		for (Entidade temp : map) {
			if (temp.getTag() == enemy) {
				if ((temp.x == x + 1 && temp.y == y) | (temp.x == x - 1 && temp.y == y)
						| (temp.x == x && temp.y == y + 1) | (temp.x == x && temp.y == y - 1)
						| (temp.x == x && temp.y == y))
					return true;
			} else
				return false;
		}
		return false;
	}

	/**
	 * Verifies if the Hero has an enemy in his surrounding positions.
	 * @param b Board where the enemy will be looked for.
	 * @param enemy char data type that represents the enemy to be looked for.
	 * @return True if the enemy is in the Hero surroundings, False otherwise.
	 */
	public boolean checkSurround(Board b, char enemy) {
		if (b.get(x + 1, y) == enemy | b.get(x - 1, y) == enemy | b.get(x, y + 1) == enemy | b.get(x, y - 1) == enemy
				| b.get(x, y) == enemy) {
			return true;
		} else
			return false;
	}

	
	public boolean stun(Board b, Vector<Entidade> map) {
		if (!((Hero) map.lastElement()).checkSurround('O', map)) {
			return false;
		}
		for (Entidade temp : map) {
			if (temp instanceof Ogre) {
				if (((Ogre) temp).checkSurround(b, getTag())) {
					((Ogre) temp).stun();
				}
			}
		}
		return true;
	}
	
	public boolean moveCondition(int x,int y,Board b){
		if (b.get(x, y) == 'x') {
			return false;
		}
		if (b.get(x, y) == 'i') {
			if (state == 'K')
				b.openDoors();
			return false;
		}
		return true;
	}
}
