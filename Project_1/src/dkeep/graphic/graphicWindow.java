package dkeep.graphic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import dkeep.logic.*;
//import dkeep.logic.Board;
//import dkeep.logic.Entidade;
//import dkeep.logic.Game;
//import dkeep.logic.Guarda;
//import dkeep.logic.Hero;
//import dkeep.logic.Ogre;

import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSplitPane;
import java.awt.Panel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class graphicWindow {

	private JFrame frmDungeonKeepGame;

	private static Scanner s = new Scanner(System.in);
	static Board b;
	static int noOgres;
	static Vector<Entidade> entidades = new Vector<Entidade>();
	static Hero hero;
	static Game game;
	static Guarda guarda;

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

	JTextArea GameInterface;
	JLabel GameState;
	JButton UP, DOWN, LEFT, RIGHT, StartGame;
	JComboBox GuardType;

	private JTextField NoOgres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					graphicWindow window = new graphicWindow();
					window.frmDungeonKeepGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public graphicWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeepGame = new JFrame();
		frmDungeonKeepGame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(graphicWindow.class.getResource("/resources/DD-Transparent.png")));
		frmDungeonKeepGame.setTitle("Dungeon Keep Game");
		frmDungeonKeepGame.setBounds(100, 100, 689, 533);
		frmDungeonKeepGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeepGame.getContentPane().setLayout(null);

		JLabel NumberOfOgres = new JLabel("Number of Ogres");
		NumberOfOgres.setFont(new Font("Tahoma", Font.PLAIN, 9));
		NumberOfOgres.setHorizontalAlignment(SwingConstants.CENTER);
		NumberOfOgres.setBounds(30, 15, 90, 20);
		frmDungeonKeepGame.getContentPane().add(NumberOfOgres);

		JLabel GuardPersonality = new JLabel("Guard Personality");
		GuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GuardPersonality.setHorizontalAlignment(SwingConstants.CENTER);
		GuardPersonality.setBounds(30, 46, 90, 20);
		frmDungeonKeepGame.getContentPane().add(GuardPersonality);

		GameState = new JLabel("");
		GameState.setHorizontalAlignment(SwingConstants.CENTER);
		GameState.setBounds(30, 366, 350, 38);
		frmDungeonKeepGame.getContentPane().add(GameState);

		NoOgres = new JTextField();
		NoOgres.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				try {
					Integer.parseInt(NoOgres.getText());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					GameState.setText("Invalid number of Ogres");
					NoOgres.setText("0");
					return;
				}
				if (Integer.parseInt(NoOgres.getText()) > 5) {
					NoOgres.setText("5");
					noOgres = 5;
				}
				if (Integer.parseInt(NoOgres.getText()) < 1) {
					NoOgres.setText("1");
					noOgres = 1;
				}
				noOgres = Integer.parseInt(NoOgres.getText());
			}
		});
		NoOgres.setBounds(130, 15, 40, 20);
		frmDungeonKeepGame.getContentPane().add(NoOgres);
		NoOgres.setColumns(1);

		GuardType = new JComboBox();
		GuardType.setModel(new DefaultComboBoxModel(new String[] { "Rookie", "Suspicious", "Drunken" }));
		GuardType.setBounds(130, 46, 90, 20);
		frmDungeonKeepGame.getContentPane().add(GuardType);

		GameInterface = new JTextArea();
		GameInterface.setFont(new Font("Courier New", Font.PLAIN, 20));
		GameInterface.setText("Start Game");
		GameInterface.setBounds(30, 80, 350, 275);
		frmDungeonKeepGame.getContentPane().add(GameInterface);

		UP = new JButton("UP");
		UP.setEnabled(false);
		UP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.Move('w');
				GameInterface.setText(game.printBoard());
				if (game.end()) {
					if (game.getEndStatus() == 0 && game.getBoard().getName() == "level1") {
						reset();
						loadLvl2();
					}
					if (game.getEndStatus() == 1) {
						GameState.setText("GG WP nice try EZ PZ");
						GameInterface.setText("Start Game");

					}

				}
			}
		});
		UP.setBounds(460, 130, 70, 30);
		frmDungeonKeepGame.getContentPane().add(UP);

		DOWN = new JButton("DOWN");
		DOWN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.Move('s');
				GameInterface.setText(game.printBoard());
				if (game.end()) {
					if (game.getEndStatus() == 0 && game.getBoard().getName() == "level1") {
						reset();
						loadLvl2();
					}
					if (game.getEndStatus() == 1) {
						GameState.setText("GG WP nice try EZ PZ");
					}

				}
			}
		});
		DOWN.setEnabled(false);
		DOWN.setBounds(460, 218, 70, 30);
		frmDungeonKeepGame.getContentPane().add(DOWN);

		RIGHT = new JButton("RIGHT");
		RIGHT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.Move('d');
				GameInterface.setText(game.printBoard());
				if (game.end()) {
					if (game.getEndStatus() == 0 && game.getBoard().getName() == "level1") {
						reset();
						loadLvl2();
					}
					if (game.getEndStatus() == 1) {
						GameState.setText("GG WP nice try EZ PZ");
					}

				}
			}
		});
		RIGHT.setEnabled(false);
		RIGHT.setBounds(500, 175, 70, 30);
		frmDungeonKeepGame.getContentPane().add(RIGHT);

		LEFT = new JButton("LEFT");
		LEFT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.Move('a');
				GameInterface.setText(game.printBoard());
				if (game.end()) {
					if (game.getEndStatus() == 0 && game.getBoard().getName() == "level1") {
						reset();
						loadLvl2();
					}
					if (game.getEndStatus() == 1) {
						GameState.setText("GG WP nice try EZ PZ");
					}

				}
			}
		});
		LEFT.setEnabled(false);
		LEFT.setBounds(420, 175, 70, 30);
		frmDungeonKeepGame.getContentPane().add(LEFT);

		StartGame = new JButton("Start Game");
		StartGame.setBounds(450, 80, 90, 40);
		frmDungeonKeepGame.getContentPane().add(StartGame);
		StartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UP.setEnabled(true);
				DOWN.setEnabled(true);
				LEFT.setEnabled(true);
				RIGHT.setEnabled(true);
				reset();
				loadLvl1();
			}
		});

		JButton Exit = new JButton("Exit Game");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Exit.setBounds(450, 325, 90, 30);
		frmDungeonKeepGame.getContentPane().add(Exit);

	}

	public void loadLvl2() {
		GameState.setText("Level 2");
		b = new Board(level2);
		b.setName("level2");
		// Entidades
		hero = new Hero(1, 7, 'A');
		for (int i = 0; i < noOgres; i++) {
			entidades.addElement(new Ogre(4, 1, 'O'));
		}
		entidades.add(hero);
		// Game
		game = new Game(b, entidades);
		GameInterface.setText(game.printBoard());
	}

	public void loadLvl1() {
		GameState.setText("Level 1");
		b = new Board(level1);
		b.setName("level1");
		// Entidades
		if (GuardType.getSelectedItem() == "Rookie") {
			guarda = new Rookie(8, 1, 'G', level1_mov);
		} else if (GuardType.getSelectedItem() == "Suspicious") {
			guarda = new Suspicious(8, 1, 'G', level1_mov);
		} else {
			guarda = new Drunken(8, 1, 'G', level1_mov);
		}
		hero = new Hero(1, 1, 'H');
		// Map
		entidades = new Vector<Entidade>();
		entidades.add(guarda);
		entidades.add(hero);
		// Game
		game = new Game(b, entidades);
		GameInterface.setText(game.printBoard());
	}

	public void reset() {
		b = null;
		// Map
		entidades.removeAllElements();
		// Game
		game = new Game(b, entidades);
		GameInterface.setText("Reseting");
	}

}
