import java.util.Scanner;

public class Project_1 {

	public static void main(String[] args) {
		boolean end = false;
		Person hero = new Person('h',1,1);
		char input;
		Board b1 = new Board();
		b1.print();
		while(!end){
			end = hero.move(b1);
		}
	}

}
