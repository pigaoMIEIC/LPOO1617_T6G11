package dkeep.logic;

import java.util.Random;
import java.util.Scanner;

public class Ogre extends Entidade {

	private static Scanner s = new Scanner(System.in);
	static char moves[] = { 'a', 's', 'd', 'w' };
	int lastAttack[] = new int [2];
	Random rand = new Random();
	int stunned = 0;

	public Ogre(int x, int y, char tag) {
		super(x, y, tag);
		lastAttack[0]=x;
		lastAttack[1]=y;
	}

	public void stun() {
			stunned = 2;
	}

	public boolean isStunned() {
		if (stunned == 0) {
			tag = 'O';
			return false;
		} else {
			tag = '8';
			return true;
		}
	}
	
	public int[] getLastAttack() {
		return lastAttack;
	}
	
	public boolean checkSurround(Board b, char enemy){
		if (b.get(x + 1, y) == enemy | b.get(x - 1, y) == enemy | b.get(x, y + 1) == enemy
				| b.get(x, y - 1) == enemy| current==enemy) {
			return true;
		} else
			return false;
	}	
	
	
	public boolean print(String level, char current, Board b) {
		isStunned();
		if (level == "level2") {
			switch (current) {
			case 'k':
				System.out.print('$');
				break;
			case 'O':
				this.current=' ';
				break;
			case '$':
				this.current='k';
				break;
			default:
				System.out.print(tag);
			}
		}
		return false;
	}

	public void move(char direction, Board b) {
		char input = 'w'; //initializing cause eclipse
		
		if (isStunned()) {
			stunned--;
			return;
		} else {
			while (true) {
				// input = s.next().charAt(0); //manual input

				int random, max = 4, min = 1;      ///random input
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
					current = b.refresh(x, y + 1, input, tag, current);
					y++;
					if(current=='k')
						tag='$';
					return;
				case 'w':
					if (b.get(x, y - 1) == 'x') {
						break;
					}
					if (b.get(x, y - 1) == 'i') {
						break;
					}
					current = b.refresh(x, y - 1, input, tag, current);
					y--;
					if(current=='k')
						tag='$';
					return;
				case 'd':
					if (b.get(x + 1, y) == 'x') {
						break;
					}
					if (b.get(x + 1, y) == 'i') {
						break;
					}
					current = b.refresh(x + 1, y, input, tag, current);
					x++;
					if(current=='k')
						tag='$';
					return;
				case 'a':
					if (b.get(x - 1, y) == 'x') {
						break;
					}
					if (b.get(x - 1, y) == 'i') {
						break;
					}
					current = b.refresh(x - 1, y, input, tag, current);
					x--;
					if(current=='k')
						tag='$';
					return;
				default:
					System.out.println("default input");
				}
			}
		}
	}

	

	public void attack(Board b) {
		while (true) {
			switch (moves[rand.nextInt(4)]) {
			case 's':
				if (b.get(x, y + 1) == 'x' | b.get(x, y + 1) == 'i'|b.get(x, y + 1) == 'O') {
					break;
				}
				lastAttack[0] = x;
				lastAttack[1] = y + 1;
				if (b.get(x, y + 1) == 'k'|b.get(x, y + 1) == '$')
					b.set(x, y + 1, '$');
				else
					b.set(x, y + 1, '*');
				return;
			case 'w':
				if (b.get(x, y - 1) == 'x' | b.get(x, y - 1) == 'i'|b.get(x, y - 1) == 'O') {
					break;
				}
				lastAttack[0] = x;
				lastAttack[1] = y - 1;
				if (b.get(x, y - 1) == 'k'|b.get(x, y - 1) == '$')
					b.set(x, y - 1, '$');
				else
					b.set(x, y - 1, '*');
				return;
			case 'd':
				if (b.get(x + 1, y) == 'x' | b.get(x + 1, y) == 'i'|b.get(x+ 1, y ) == 'O') {
					break;
				}
				lastAttack[0] = x + 1;
				lastAttack[1] = y;
				if (b.get(x + 1, y) == 'k'|b.get(x+ 1, y ) == '$')
					b.set(x + 1, y, '$');
				else
					b.set(x + 1, y, '*');
				return;
			case 'a':
				if (b.get(x - 1, y) == 'x' | b.get(x - 1, y) == 'i'|b.get(x- 1, y ) == 'O') {
					break;
				}
				lastAttack[0] = x - 1;
				lastAttack[1] = y;
				if (b.get(x - 1, y) == 'k'|b.get(x- 1, y ) == '$') {
					b.set(x - 1, y, '$');
				} else
					b.set(x - 1, y, '*');
				return;
			default:
				System.out.println("default attack");
			}
		}
	}

	public void clearAttack(Board b) {
		if (b.get(lastAttack[0], lastAttack[1]) == '$') {
			b.set(lastAttack[0], lastAttack[1], 'k');
		} else
			b.set(lastAttack[0], lastAttack[1], ' ');
	}

}
