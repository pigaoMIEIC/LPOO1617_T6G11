package dkeep.cli;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import dkeep.logic.Board;
import dkeep.logic.Enemy;
import dkeep.logic.Person;

public class Project_1 {
	//project

	private static Scanner s = new Scanner(System.in);
	private static Person hero = new Person('h', 1, 1);

	
	public static void main(String[] args) throws InterruptedException {
		char[] guardMovement = { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd',
				'd', 'd', 'w', 'w', 'w', 'w', 'w' };
		char[] heroMovement = { 'd', 'd', 's', 's', 's', 's', 's', 's', 's', 'w', 'w', 'd', 'd', 'd', 'd', 'd', 's',
				's', 'a', 'd', 'w', 'w', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a' };
		int i = 0;
		int j = 0;
		char input;
		boolean end = false;
		Board b1 = new Board(level1);
		//:::::::::::::::::::::::::::::::::::::::::::::::::::::::
		//Level 1
		Enemy enemy = new Enemy('g', 8, 1);
		b1.print();
		b1.setName("level1");
		while (!end) {
			// input = s.next().charAt(0);
			input = heroMovement[j];
			j++;
			end = hero.move(b1, input);
			if (hero.getCurrent() == 'k')
				b1.openDoors();
			if (end) {
				break;
			}
			input = guardMovement[i];
			if (++i == guardMovement.length)
				i = 0;
			enemy.move(b1, input);
			end = enemy.checkSurround(b1, 'h');
			b1.print();
			TimeUnit.MILLISECONDS.sleep(250);
		}
		end = false;
		//::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		//Level 2
		b1.updateLevel(level2);
		b1.setName("level2");
		hero.reset(1,7,'h',b1);
		enemy.reset(4,1,'o',b1);
		b1.print();
		while (!end) {
			input = s.next().charAt(0);
			// input = heroMovement[i];
			end = hero.move(b1, input);
			if (hero.getState() == 'K')
				hero.setTag('K');
			if (end)
				break;
			enemy.move(b1);
			end = enemy.checkSurround(b1, 'h');
			end = enemy.checkSurround(b1, 'K');
			while(!enemy.attack(b1));
			end = hero.checkSurround(b1,'*');
			b1.print();
			enemy.clearAttack(b1);
			// TimeUnit.MILLISECONDS.sleep(1000);
		}
	}
}
