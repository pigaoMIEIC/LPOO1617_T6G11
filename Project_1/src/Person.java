import java.util.Scanner;

public class Person {
	char tag;
	int x;
	int y;
	char current=' ';

	Person(char tag, int x, int y) {
		this.tag = tag;
		this.x = x;
		this.y = y;
	}

	public boolean move(Board b) {
		char input;
		Scanner s = new Scanner(System.in);
		input = s.next().charAt(0);
		switch (input) {
		case 's':
			if (b.get(x, y + 1) != 'x') {
				current = b.refresh(x, y + 1, input, tag, current);
				y++;
			}
			break;
		case 'w':
			if (b.get(x, y - 1) != 'x') {
				current = b.refresh(x, y - 1, input, tag, current);
				y--;
			}
			break;
		case 'd':
			if (b.get(x + 1, y) != 'x') {
				current = b.refresh(x + 1, y, input, tag, current);
				x++;
			}
			break;
		case 'a':
			if (b.get(x - 1, y) != 'x') {
				current = b.refresh(x - 1, y, input, tag, current);
				x--;
			}
			break;
		default:
			System.out.println("default input");
		}
		
		b.print();
		
		if (current == 'k')
			b.openDoors();
		if(current == 's'){
			System.out.println("Parabens passou o nivel!!");
			return true;}
		return false;
		
	}
}
