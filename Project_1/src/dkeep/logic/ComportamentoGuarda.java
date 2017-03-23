package dkeep.logic;

import java.io.Serializable;
/**
 * ComportamentoGuarda.java - Class that is responsible for storing a guard movement routine and moving him.
 */
public class ComportamentoGuarda implements Serializable{
	char mov[];
	int i = 0;

	/**
	 * Constructor of the class ComportamentoGuarda.
	 * @param mov Array of chars that represents the Guarda movement.
	 */
	public ComportamentoGuarda(char mov[]) {
		this.mov = mov;
	}

	/**
	 * Method responsible for moving a Guarda.
	 * @param dir Flag to indicate the direction the Guarda is taking in its movement array.
	 * @param guarda Guard that is going to move.
	 * @param b Board in which the to move the Guarda.
	 */
	public void move(boolean dir, Guarda guarda, Board b) {
		char input = mov[i];
		if (!dir) {
			i--;
			if (i < 0)
				i = mov.length - 1;
			input = mov[i];
			switch (input) {
			case 's':
				input = 'w';
				break;
			case 'a':
				input = 'd';
				break;
			case 'd':
				input = 'a';
				break;
			case 'w':
				input = 's';
				break;
			}
		} else
			i++;
		if (i == mov.length)
			i = 0;
		switch (input) {
		case 's':
			if (b.get(guarda.x, guarda.y + 1) == 'x') {
				break;
			}
			if (b.get(guarda.x, guarda.y + 1) == 'i') {
				break;
			}
			guarda.setCurrent((char) b.refresh(guarda.x, guarda.y + 1, input, guarda.getTag(), guarda.getCurrent()));
			guarda.y++;
			break;
		case 'w':
			if (b.get(guarda.x, guarda.y - 1) == 'x') {
				break;
			}
			if (b.get(guarda.x, guarda.y - 1) == 'i') {
				break;
			}
			guarda.setCurrent((char) b.refresh(guarda.x, guarda.y - 1, input, guarda.getTag(), guarda.getCurrent()));
			guarda.y--;
			break;
		case 'd':
			if (b.get(guarda.x + 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x + 1, guarda.y) == 'i') {
				break;
			}
			guarda.setCurrent((char) b.refresh(guarda.x + 1, guarda.y, input, guarda.getTag(), guarda.getCurrent()));
			guarda.x++;
			break;
		case 'a':
			if (b.get(guarda.x - 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x - 1, guarda.y) == 'i') {
				break;
			}
			guarda.setCurrent((char) b.refresh(guarda.x - 1, guarda.y, input, guarda.getTag(), guarda.getCurrent()));
			guarda.x--;
			break;
		default:
			System.out.println("default input");
		}
	}

}
