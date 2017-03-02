package dkeep.cli;

import java.util.Scanner;
import java.util.Vector;

import dkeep.logic.Board;
import dkeep.logic.Drunken;
import dkeep.logic.Entidade;
import dkeep.logic.Game;
import dkeep.logic.Guarda;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import dkeep.logic.Suspicious;

public class Project_1 {

	private static Scanner s = new Scanner(System.in);
	static Board b;
	static Vector<Entidade> entidades;
	static Hero hero;
	static Game game;
	static char input;

	static char level1[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', /* 'h' */' ', ' ', ' ', 'i', ' ', 'x', ' ', /* 'g' */' ', 'x' },
			{ 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' }, { 'x', ' ', 'i', ' ', 'i', ' ', 'x', ' ', ' ', 'x' },
			{ 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x' }, { 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', ' ', 'x' },
			{ 'x', ' ', 'i', ' ', 'i', ' ', 'x', 'k', ' ', 'x' },
			{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

	static char level1_mov[] = { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd',
			'd', 'd', 'w', 'w', 'w', 'w', 'w' };

	static char level2[][] = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'i', ' ', ' ', ' ', /* O */' ', ' ', ' ', 'k', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
			{ 'x', /* h */' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }, { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

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
//		 System.out.print("Level 1");
//		 b = new Board(level1);
//		 b.setName("level1");
//		 // Entidades
//		 Guarda guarda = new Drunken(8, 1, 'G', level1_mov);
//		 hero = new Hero(1, 1, 'H');
//		 // Map
//		 entidades = new Vector<Entidade>();
//		 entidades.add(guarda);
//		 entidades.add(hero);
//		 // Game
//		 game = new Game(b, entidades);
//		 game.printBoard();
//		 while (!game.end()) {
//		 System.out.print("Mover para(a,s,d,w):");
//		 input = s.next().charAt(0);
//		 game.Move(input);
//		 game.printBoard();
//		 }
		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		// Level 2
		System.out.print("Level 2\n");
		b = new Board(level2);
		b.setName("level2");
		// Entidades
		Ogre ogre = new Ogre(5, 1, 'O');
		Ogre ogre1 = new Ogre(3, 1, 'O');
		hero = new Hero(1, 7, 'A');
		// Map
		entidades = new Vector<Entidade>();
		entidades.add(ogre1);
		entidades.add(ogre);
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
