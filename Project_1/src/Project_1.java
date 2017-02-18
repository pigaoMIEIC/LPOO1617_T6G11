import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Project_1 {

	private static Scanner s = new Scanner(System.in);
	private static Person hero = new Person('h', 1, 1);

	static char level1[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', 'h', ' ', ' ', 'i', ' ', 'x', ' ', 'g', 'x' }, { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' },
			{ 'x', ' ', 'i', ' ', 'i', ' ', 'x', ' ', ' ', 'x' }, { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' },
			{ 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', ' ', 'x' }, { 'x', ' ', 'i', ' ', 'i', ' ', 'x', 'k', ' ', 'x' },
			{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	static char level2[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'i', ' ', ' ', ' ', 'o', ' ', ' ', 'k', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', 'h', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	public static void main(String[] args) throws InterruptedException {
		char[] guardMovement = { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd',
				'd', 'd', 'w', 'w', 'w', 'w', 'w' };
		char[] heroMovement = { 'd', 'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd',
				'a', 'd', 'a', 'd', 'a', 'd', 'a', 'd' };
		int i = 0;
		char input;
		boolean end = false;
		Person guard = new Person('g', 8, 1);
		Board b1 = new Board(level1);
		b1.print();
		b1.setName("level1");
		while (!end) {
			b1.print();
			input = s.next().charAt(0);
			// input = heroMovement[i];
			end = hero.move(b1, input);
			if (hero.current == 'k')
				b1.openDoors();
			if (end) {
				b1.print();
				return;
			}
			input = guardMovement[i];
			if (++i == guardMovement.length)
				i = 0;
			guard.move(b1, input);
			end = guard.checkSurround(b1, 'h');
			// TimeUnit.MILLISECONDS.sleep(1000);
		}
		// b1.updateLevel(level2);
		// b1.print();
		// while(!end){
		// input = s.next().charAt(0);
		// //input = heroMovement[i];
		// end = hero.move(b1,input);
		// if(end)
		// return;
		// input = guardMovement[i];
		// if(++i == guardMovement.length)
		// i=0;
		// guard.move(b1,input);
		// end=guard.checkSurround(b1,'h');
		// //TimeUnit.MILLISECONDS.sleep(1000);
		// }
	}
}
