import java.util.Random;

public class Enemy extends Person {

	static Random rand = new Random();
	static char direction[] = { 'a', 's', 'w', 'd' };
	static int lastAttack[] = { 0, 0 };

	public Enemy(char tag, int x, int y) {
		super(tag, x, y);
	}

	public boolean tryMove(char direction, Board b) {
		switch (direction) {
		case 's':
			if (b.get(x, y + 1) == 'x' || b.get(x, y + 1) == 'i') {
				break;
			}
			current = b.refresh(x, y + 1, direction, tag, current);
			y++;
			if (current == 'k')
				tag = 'o';
			return true;
		case 'w':
			if (b.get(x, y - 1) == 'x' || b.get(x, y - 1) == 'i') {
				break;
			}
			current = b.refresh(x, y - 1, direction, tag, current);
			y--;
			if (current == 'k')
				tag = 'o';
			return true;
		case 'd':
			if (b.get(x + 1, y) == 'x' || b.get(x + 1, y) == 'i') {
				break;
			}
			current = b.refresh(x + 1, y, direction, tag, current);
			x++;
			if (current == 'k')
				tag = 'o';
			return true;
		case 'a':
			if (b.get(x - 1, y) == 'x' || b.get(x - 1, y) == 'i') {
				break;
			}
			current = b.refresh(x - 1, y, direction, tag, current);
			x--;
			if (current == 'k')
				tag = 'o';
			return true;
		default:
			System.out.println("default input");
		}
		return false;
	}

	public boolean attack(Board b) {
		switch (direction[rand.nextInt(4)]) {
		case 's':
			if (b.get(x, y + 1) == 'x' || b.get(x, y + 1) == 'i') {
				break;
			}
			lastAttack[0] = x;
			lastAttack[1] = y + 1;
			if (b.get(x, y + 1) == 'k')
				b.set(x, y + 1, '$');
			else
				b.set(x, y + 1, '*');
			return true;
		case 'w':
			if (b.get(x, y - 1) == 'x' || b.get(x, y - 1) == 'i') {
				break;
			}
			lastAttack[0] = x;
			lastAttack[1] = y - 1;
			if (b.get(x, y - 1) == 'k')
				b.set(x, y - 1, '$');
			else
				b.set(x, y - 1, '*');
			return true;
		case 'd':
			if (b.get(x + 1, y) == 'x' || b.get(x + 1, y) == 'i') {
				break;
			}
			lastAttack[0] = x + 1;
			lastAttack[1] = y;
			if (b.get(x + 1, y) == 'k')
				b.set(x + 1, y, '$');
			else
				b.set(x + 1, y, '*');
			return true;
		case 'a':
			if (b.get(x - 1, y) == 'x' || b.get(x - 1, y) == 'i') {
				break;
			}
			lastAttack[0] = x - 1;
			lastAttack[1] = y;
			if (b.get(x - 1, y) == 'k'){
				b.set(x - 1, y, '$');
			}
			else
				b.set(x - 1, y, '*');
			return true;
		default:
			System.out.println("default attack");
		}
		return false;
	}

	public void clearAttack(Board b) {
		if (b.get(lastAttack[0], lastAttack[1]) == '$') {
			b.set(lastAttack[0], lastAttack[1], 'k');
		}
		b.set(lastAttack[0], lastAttack[1], '.');
	}

	public void move(Board b) {
		while (!tryMove(direction[rand.nextInt(4)], b)) {
		}
	}

}
