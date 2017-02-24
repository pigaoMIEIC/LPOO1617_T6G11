package dkeep.logic;

public class ComportamentoGuarda {
	char mov[];
	int i = 0;

	public ComportamentoGuarda(char mov[]) {
		this.mov = mov;
	}

	public void move(boolean dir, Guarda guarda) {
		char input = mov[i];
		if (!dir) {
			switch (input) {
			case 's':
				input = 'w';
			case 'a':
				input = 'd';
			case 'd':
				input = 'a';
			case 'w':
				input = 's';
			}
			i--;
		}
		else i++;
		switch (input) {
		case 's':
			if (b.get(guarda.x, guarda.y + 1) == 'x') {
				break;
			}
			if (b.get(guarda.x, guarda.y + 1) == 'i') {
				break;
			}
			current = b.refresh(guarda.x, guarda.y + 1, input, guarda.tag, current);
			guarda.y++;
			break;
		case 'w':
			if (b.get(guarda.x, guarda.y - 1) == 'x') {
				break;
			}
			if (b.get(guarda.x, guarda.y - 1) == 'i') {
				break;
			}
			current = b.refresh(guarda.x, guarda.y - 1, input, guarda.tag, current);
			guarda.y--;
			break;
		case 'd':
			if (b.get(guarda.x + 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x + 1, guarda.y) == 'i') {
				if (state == 'K')
					b.openDoors();
				break;
			}
			current = b.refresh(guarda.x + 1, guarda.y, input, guarda.tag, current);
			guarda.x++;
			break;
		case 'a':
			if (b.get(guarda.x - 1, guarda.y) == 'x') {
				break;
			}
			if (b.get(guarda.x - 1, guarda.y) == 'i') {
				break;
			}
			current = b.refresh(guarda.x - 1, guarda.y, input, guarda.tag, current);
			guarda.x--;
			break;
		default:
			System.out.println("default input");
		}
	}

}
