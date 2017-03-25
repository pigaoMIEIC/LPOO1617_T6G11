package dkeep.logic;

import java.util.Random;
import java.util.Scanner;

/**
 * Ogre.java - A subclass of Entidade representing the ogre character.
 */
public class Ogre extends Entidade{

	private static Scanner s = new Scanner(System.in);
	static char moves[] = { 'a', 's', 'd', 'w' };
	int lastAttack[] = new int[2];
	Random rand = new Random();
	int stunned = 0;

	/**
	 * Constructor of the class Ogre.
	 * 
	 * @param x
	 *            X position of the Entidade.
	 * @param y
	 *            Y position of the Entidade.
	 * @param tag
	 *            The simple graphic representation of the Entidade.
	 */
	public Ogre(int x, int y, char tag) {
		super(x, y, tag);
		lastAttack[0] = x;
		lastAttack[1] = y;
	}

	/**
	 * Stuns the Ogre.
	 */
	public void stun() {
		stunned = 2;
	}

	/**
	 * Checks if the Ogre is stunned and changes its tag.
	 * 
	 * @return True if stunned, False otherwise.
	 */
	public boolean isStunned() {
		if (stunned == 0) {
			setTag('O');
			return false;
		} else {
			setTag('8');
			return true;
		}
	}

	/**
	 * Method to get the last Ogre attack.
	 * 
	 * @return int array in the format [x coordinate, y coordinate].
	 */
	public int[] getLastAttack() {
		return lastAttack;
	}

	/**
	 * Verifies if the Ogre has an enemy in his surrounding positions.
	 * 
	 * @param b
	 *            Board where the enemy will be looked for.
	 * @param enemy
	 *            char data type that represents the enemy to be looked for.
	 * @return True if the enemy is in the Hero surroundings, False otherwise.
	 */
	public boolean checkSurround(Board b, char enemy) {
		char[][] temp = b.getBoard();
		if (temp[y][x + 1]== enemy | temp[y][x - 1] == enemy | temp[y + 1][x] == enemy | temp[y - 1][x] == enemy
				| getCurrent() == enemy) {
			return true;
		} else
			return false;

	}

	/**
	 * Method to print the Ogre tag.
	 * 
	 * @param level
	 *            Name of the game level for mechanics purposes.
	 * @param char
	 *            the Ogre is currently on.
	 * @param b
	 *            Board where the Ogre will be moved.
	 * @param output
	 *            Single String array to save the output String.
	 */
	public boolean print(String level, char current, Board b, String output[]) {
		isStunned();
		if (level == "level2") {
			switch (current) {
			case 'k':
				output[0] += ('$');
				break;
			case 'O':
				this.setCurrent(' ');
				break;
			case '$':
				this.setCurrent('k');
				break;
			default:
				output[0] += (getTag());
			}
		}
		return false;
	}
	
	private char getInput(){
		int random, max = 4, min = 1; /// random input
		random = rand.nextInt(max - min + 1) + min;
		switch (random) {
		case 1:return'a';
		case 2:return's';
		case 3:return'd';
		case 4:return'w';
		}
		System.out.println("Ogre get input error");
		return ' ';
	}

	/**
	 * Method to move the the Ogre.
	 * 
	 * @param direction
	 *            Not used in the class Ogre.
	 * @param b
	 *            Board in which the Ogre will move.
	 */
	public void move(char direction, Board b) {
		int a[] = new int [2];
		a[0]= this.getX();
		a[1]= this.getY();
		char input = 'w';
		if (isStunned()) {
			stunned--;
			return;
		}

		while (true) {
			input = getInput();

			caseFunction(input, a);
			if (moveCondition(a[0], a[1], b)) {break;}
			caseFunctionEntidade(input, this);
			setCurrent((char) b.refresh(this, input));
			if (getCurrent() == 'k')
				setTag('$');
			return;
		}
	}

	/**
	 * Method to make the Ogre randomly attack its surroundings.
	 * 
	 * @param b
	 *            Board which will be modified with the Ogre attack.
	 */
	public char attack(Board b) {
		switch (moves[rand.nextInt(4)]) {
		case 's':
			// lastAttack[0] = x;
			// lastAttack[1] = y + 1;
			// if (b.get(x, y + 1) == 'x' | b.get(x, y + 1) == 'i' | b.get(x, y
			// + 1) == 'O' | b.get(x, y + 1) == 's') {
			// return 'x';
			// }
			//
			// if (b.get(x, y + 1) == 'k' | b.get(x, y + 1) == '$')
			// b.set(x, y + 1, '$');
			// else
			// b.set(x, y + 1, '*');
			if (attackCondition(x, y + 1, b)) {
				return 'x';
			} else {
				return 's';
			}
		case 'w':
			// lastAttack[0] = x;
			// lastAttack[1] = y - 1;
			// if (b.get(x, y - 1) == 'x' | b.get(x, y - 1) == 'i' | b.get(x, y
			// - 1) == 'O' | b.get(x, y - 1) == 's') {
			// return 'x';
			// }
			//
			// if (b.get(x, y - 1) == 'k' | b.get(x, y - 1) == '$')
			// b.set(x, y - 1, '$');
			// else
			// b.set(x, y - 1, '*');
			if (attackCondition(x, y - 1, b)) {
				return 'x';
			} else {
				return 'w';
			}
		case 'd':
			// lastAttack[0] = x + 1;
			// lastAttack[1] = y;
			// if (b.get(x + 1, y) == 'x' | b.get(x + 1, y) == 'i' | b.get(x +
			// 1, y) == 'O' | b.get(x + 1, y) == 's') {
			// return 'x';
			// }
			//
			// if (b.get(x + 1, y) == 'k' | b.get(x + 1, y) == '$')
			// b.set(x + 1, y, '$');
			// else
			// b.set(x + 1, y, '*');
			if (attackCondition(x + 1, y, b)) {
				return 'x';
			} else {
				return 'd';
			}
		case 'a':
			// lastAttack[0] = x - 1;
			// lastAttack[1] = y;
			// if (b.get(x - 1, y) == 'x' | b.get(x - 1, y) == 'i' | b.get(x -
			// 1, y) == 'O' | b.get(x - 1, y) == 's') {
			// return 'x';
			// }
			//
			// if (b.get(x - 1, y) == 'k' | b.get(x - 1, y) == '$') {
			// b.set(x - 1, y, '$');
			// } else
			// b.set(x - 1, y, '*');
			if (attackCondition(x - 1, y, b)) {
				return 'x';
			} else {
				return 'a';
			}
		default:
			System.out.println("default attack");
		}
		return ' ';
	}

	/**
	 * Method to clear the Ogre last attack.
	 * 
	 * @param b
	 *            Board in which the attack will be cleared.
	 */
	public void clearAttack(Board b) {
		if (b.get(lastAttack[0], lastAttack[1]) == '$') {
			b.set(lastAttack[0], lastAttack[1], 'k');
		} else
			b.set(lastAttack[0], lastAttack[1], ' ');
	}

	/**
	 * Method to verify if the Ogre can attack.
	 * 
	 * @param x
	 *            x coordinate to attack
	 * @param y
	 *            y coordinate to attack
	 * @param b
	 *            board where the attack will be registered
	 * @return True if the attack is invalid, false otherwise.
	 */
	public boolean attackCondition(int x, int y, Board b) {
		char temp = b.get(x, y);
		lastAttack[0] = x;
		lastAttack[1] = y;
		if (temp == 'x' | temp == 'i' | temp == 'O' | temp == 's') {
			return true;
		}

		if (temp == 'k' | temp == '$') {
			b.set(x, y, '$');
		} else {
			b.set(x, y, '*');
		}
		return false;
	}

	public boolean moveCondition(int x, int y, Board b) {
		char temp = b.get(x, y);
		if (temp == 'x') {
			return true;
		}
		if (temp == 'i') {
			return true;
		}
		return false;
	}
}

