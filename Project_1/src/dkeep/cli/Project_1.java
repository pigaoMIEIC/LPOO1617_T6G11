package dkeep.cli;

import java.util.Scanner;
import java.util.Vector;

import dkeep.logic.Board;
import dkeep.logic.Drunken;
import dkeep.logic.Entidade;
import dkeep.logic.Game;
import dkeep.logic.Guarda;
import dkeep.logic.Hero;
import dkeep.graphic.MapsInterface;
import dkeep.logic.Ogre;
import dkeep.logic.Rookie;
import dkeep.logic.Suspicious;

public class Project_1 implements MapsInterface{

	private static Scanner s = new Scanner(System.in);
	static Board b;
	static int noOgres;
	static Vector<Entidade> entidades;
	static Hero hero;
	static Game game;
	static Guarda guarda;
	
	static char input;
	
	

	// b = new Board(level);
	// hero = new Hero(1,1,'H');
	// guarda = new Rookie(8,1,'G',level1_mov);
	// map.add(hero);
	// map.add(guarda);

	public static void main(String[] args) {
		char[] guardMovement = { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd',
				'd', 'd', 'w', 'w', 'w', 'w', 'w' };
		char[] heroMovement = { 'd', 'd', 's', 's', 's', 's', 's', 's', 's', 'w', 'w', 'd', 'd', 'd', 'd', 'd', 's',
				's', 'a', 'd', 'w', 'w', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a' };

		// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Introduction
		System.out.print("  _______________________\n" + "=(_______________________)=\n" + "  |                     |\n"
				+ "  |      THE            |\n" + "  |                     |\n" + "  |       DUNGEON       |\n"
				+ "  |                     |\n" + "  |           KEEP      |\n" + "  |_____________________|\n"
				+ "=(_______________________)=\n\n");
		System.out.print("O Heroi quer escapar das masmorras mas para isso vai precisar da sua ajuda.\n");
		System.out.print(
				"Para ajudar o Heroi tem que lhe indicar o caminho com as teclas:\n" + "'A'-andar para a direita\n"
						+ "'D'-andar para a esquerda\n" + "'W'-andar para cima\n" + "'S'-andar para baixo\n\n"
						+ "Para passar de nivel tem que chegar à porta representado por 's' quando aberta.\n"
						+ "Para sobreviver evite os guardas(G) e os ogres(O).\n\n");
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Level 1
		 System.out.print("Level 1");
		 b = new Board(level1);
		 b.setName("level1");
		 // Entidades
		 Guarda guarda = new Drunken(8, 1, 'G', level1_mov);
		 hero = new Hero(1, 1, 'H');
		 // Map
		 entidades = new Vector<Entidade>();
		 entidades.add(guarda);
		 entidades.add(hero);
		 // Game
		 game = new Game(b, entidades);
		 game.printBoard();
		 while (!game.end()) {
		 System.out.print("Mover para(a,s,d,w):");
		 input = s.next().charAt(0);
		 game.Move(input);
		 game.printBoard();
		 }
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Level 2
		//reset vector
		entidades.removeAllElements();
		System.out.print("Level 2\n");
		System.out.print("Contra quantos ogres pretende jogar?\n");
		try{
		noOgres = s.nextInt();}
		catch (Exception e){
			System.out.println("Valor invalido numero de ogres definido com o valor 1.");
			noOgres = 1;
		}		
		b = new Board(level2);
		b.setName("level2");
		// Entidades
		hero = new Hero(1, 7, 'A');
		for(int i=0; i<noOgres;i++){
			entidades.addElement(new Ogre(4, 1, 'O'));
		}
		entidades.add(hero);
		// Game
		game = new Game(b, entidades);
		game.printBoard();
		while (!game.end()) {
			game.clearAttack();
			System.out.print("Mover para(a,s,d,w):");
			input = s.next().charAt(0);
			game.Move(input);
			game.attack();
			game.printBoard();
		}
	}
}
