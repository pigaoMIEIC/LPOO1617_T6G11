package dkeep.logic;

import java.util.Random;
import java.util.Scanner;

/**
 * Ogre.java - A subclass of Entidade representing the ogre character.
 */
public class Ogre extends Entidade {

	private static Scanner s = new Scanner(System.in);
	static char moves[] = { 'a', 's', 'd', 'w' };
	int lastAttack[] = new int[2];
	Random rand = new Random();
	int stunned = 0;
	
	/**  
	* Constructor of the class Ogre.
	* @param x X position of the Entidade.
	* @param y Y position of the Entidade.
	* @param tag The simple graphic representation of the Entidade.
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
	 * @return int array in the format [x coordinate, y coordinate].
	 */
	public int[] getLastAttack() {
		return lastAttack;
	}
	/**
	 * Verifies if the Ogre has an enemy in his surrounding positions.
	 * @param b Board where the enemy will be looked for.
	 * @param enemy char data type that represents the enemy to be looked for.
	 * @return True if the enemy is in the Hero surroundings, False otherwise.
	 */
	public boolean checkSurround(Board b, char enemy){
		if (b.get(x + 1, y) == enemy | b.get(x - 1, y) == enemy | b.get(x, y + 1) == enemy
				| b.get(x, y - 1) == enemy| getCurrent()==enemy) {
			return true;
		} else
			return false;

	}	
	
	/**
	 * Method to print the Ogre tag.
	 * @param level Name of the game level for mechanics purposes.
	 * @param char the Ogre is currently on.
	 * @param b Board where the Ogre will be moved.
	 * @param output Single String array to save the output String. 
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

	/**
	 * Method to move the the Ogre.
	 * @param direction Not used in the class Ogre.
	 * @param b Board in which the Ogre will move.
	 */
	public void move(char direction, Board b) {
		char input = 'w'; // initializing cause eclipse

		if (isStunned()) {
			stunned--;
			return;
		} else {
			while (true) {
				// input = s.next().charAt(0); //manual input

				int random, max = 4, min = 1; /// random input
				random = rand.nextInt(max - min + 1) + min;
				switch (random) {
				case 1:
					input = 'a';
					break;
				case 2:
					input = 's';
					break;
				case 3:
					input = 'd';
					break;
				case 4:
					input = 'w';
					break;

				}
				// input = moves[rand.nextInt(4)];
				switch (input) {
				case 's':
					if (b.get(x, y + 1) == 'x') {
						break;
					}
					if (b.get(x, y + 1) == 'i') {
						break;
					}
					setCurrent((char) b.refresh(x, y + 1, input, getTag(), getCurrent()));
					y++;
					if (getCurrent() == 'k')
						setTag('$');
					return;
				case 'w':
					if (b.get(x, y - 1) == 'x') {
						break;
					}
					if (b.get(x, y - 1) == 'i') {
						break;
					}
					setCurrent((char) b.refresh(x, y - 1, input, getTag(), getCurrent()));
					y--;
					if (getCurrent() == 'k')
						setTag('$');
					return;
				case 'd':
					if (b.get(x + 1, y) == 'x') {
						break;
					}
					if (b.get(x + 1, y) == 'i') {
						break;
					}
					setCurrent((char) b.refresh(x + 1, y, input, getTag(), getCurrent()));
					x++;
					if (getCurrent() == 'k')
						setTag('$');
					return;
				case 'a':
					if (b.get(x - 1, y) == 'x') {
						break;
					}
					if (b.get(x - 1, y) == 'i') {
						break;
					}
					setCurrent((char) b.refresh(x - 1, y, input, getTag(), getCurrent()));
					x--;
					if (getCurrent() == 'k')
						setTag('$');
					return;
				default:
					System.out.println("default input");
				}
			}
		}
	}

	/**
	 * Method to make the Ogre randomly attack its surroundings.
	 * @param b Board which will be modified with the Ogre attack.
	 */
	public char attack(Board b) {
		switch (moves[rand.nextInt(4)]) {
		case 's':
			lastAttack[0] = x;
			lastAttack[1] = y + 1;
			if (b.get(x, y + 1) == 'x' | b.get(x, y + 1) == 'i' | b.get(x, y + 1) == 'O' |b.get(x, y + 1) == 's') {
				return 'x';
			}
			
			if (b.get(x, y + 1) == 'k' | b.get(x, y + 1) == '$')
				b.set(x, y + 1, '$');
			else
				b.set(x, y + 1, '*');
			return 's';
		case 'w':
			lastAttack[0] = x;
			lastAttack[1] = y - 1;
			if (b.get(x, y - 1) == 'x' | b.get(x, y - 1) == 'i' | b.get(x, y - 1) == 'O' | b.get(x, y - 1) == 's') {
				return 'x';
			}
			
			if (b.get(x, y - 1) == 'k' | b.get(x, y - 1) == '$')
				b.set(x, y - 1, '$');
			else
				b.set(x, y - 1, '*');
			return 'w';
		case 'd':
			lastAttack[0] = x + 1;
			lastAttack[1] = y;
			if (b.get(x + 1, y) == 'x' | b.get(x + 1, y) == 'i' | b.get(x + 1, y) == 'O' | b.get(x + 1, y) == 's') {
				return 'x';
			}
			
			if (b.get(x + 1, y) == 'k' | b.get(x + 1, y) == '$')
				b.set(x + 1, y, '$');
			else
				b.set(x + 1, y, '*');
			return 'd';
		case 'a':
			lastAttack[0] = x - 1;
			lastAttack[1] = y;
			if (b.get(x - 1, y) == 'x' | b.get(x - 1, y) == 'i' | b.get(x - 1, y) == 'O' | b.get(x - 1, y) == 's') {
				return 'x';
			}
			
			if (b.get(x - 1, y) == 'k' | b.get(x - 1, y) == '$') {
				b.set(x - 1, y, '$');
			} else
				b.set(x - 1, y, '*');
			return 'a';
		default:
			System.out.println("default attack");
		}
		return ' ';
	}

	/**
	 * Method to clear the Ogre last attack.
	 * @param b Board in which the attack will be cleared.
	 */
	public void clearAttack(Board b) {
		if (b.get(lastAttack[0], lastAttack[1]) == '$') {
			b.set(lastAttack[0], lastAttack[1], 'k');
		} else
			b.set(lastAttack[0], lastAttack[1], ' ');
	}

}
