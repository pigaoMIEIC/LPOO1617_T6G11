package dkeep.logic;

import java.io.Serializable;

public class ComportamentoGuarda implements Serializable{
	char mov[];
	int i = 0;

	public ComportamentoGuarda(char mov[]) {
		this.mov = mov;
	}

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
			guarda.current = b.refresh(guarda.x, guarda.y + 1, input, guarda.tag, guarda.current);
			guarda.y++;
			break;
		case 'w':
			if (b.get(guarda.x, guarda.y - 1) == 'x') {
				break;
			}
			if (b.get(guarda.x, guarda.y - 1) == 'i') {
				break;
			}
			guarda.current = b.refresh(guarda.x, guarda.y - 1, input, guarda.tag, guarda.current);
			guarda.y--;
			break;
		case 'd':
			if (b.get(guarda.x + 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x + 1, guarda.y) == 'i') {
				break;
			}
			guarda.current = b.refresh(guarda.x + 1, guarda.y, input, guarda.tag, guarda.current);
			guarda.x++;
			break;
		case 'a':
			if (b.get(guarda.x - 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x - 1, guarda.y) == 'i') {
				break;
			}
			guarda.current = b.refresh(guarda.x - 1, guarda.y, input, guarda.tag, guarda.current);
			guarda.x--;
			break;
		default:
			System.out.println("default input");
		}
	}

}
