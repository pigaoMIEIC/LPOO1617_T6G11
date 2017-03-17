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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Window.Type;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class enhacedWindow {

	private JFrame frmDungeonKeepGame;

	char currentLevel[][];
	
	private static Scanner s = new Scanner(System.in);
	static Board b;
	static int noOgres;
	static Vector<Entidade> entidades = new Vector<Entidade>();
	static Hero hero;
	static Ogre ogre;
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
	
	private JLabel GameState;
	private JButton StartGame;
	private JComboBox GuardType;
	private JTextField NoOgres;
	private GameInterface gameInterface;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enhacedWindow window = new enhacedWindow();
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
	public enhacedWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeepGame = new JFrame();
		frmDungeonKeepGame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP: buttonEvent('w'); break;
			        case KeyEvent.VK_DOWN: buttonEvent('s'); break;
			        case KeyEvent.VK_RIGHT: buttonEvent('d'); break;
			        case KeyEvent.VK_LEFT: buttonEvent('a'); break;
			    }
			}
		});
		

		frmDungeonKeepGame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(enhacedWindow.class.getResource("/resources/DD-Transparent.png")));
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
		GameState.setBounds(40, 375, 350, 38);
		frmDungeonKeepGame.getContentPane().add(GameState);

		NoOgres = new JTextField();
		NoOgres.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER){
					frmDungeonKeepGame.requestFocusInWindow();
				}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER){
					frmDungeonKeepGame.requestFocusInWindow();
				}
			}
		});
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

		StartGame = new JButton("Start Game");
		StartGame.setBounds(450, 80, 90, 40);
		frmDungeonKeepGame.getContentPane().add(StartGame);
		StartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				loadLvl1();
				StartGame.setEnabled(false);
//				UP.setEnabled(true);
//				DOWN.setEnabled(true);
//				LEFT.setEnabled(true);
//				RIGHT.setEnabled(true);
				NoOgres.setEnabled(false);
				GuardType.setEnabled(false);
				frmDungeonKeepGame.setFocusable(true);
				frmDungeonKeepGame.requestFocusInWindow();
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
		
		gameInterface = new GameInterface();
		gameInterface.setBounds(40, 80, 400, 274);
		frmDungeonKeepGame.getContentPane().add(gameInterface);
		
		

	}

	public void buttonEvent(char input) {
		game.clearAttack();
		game.Move(input);
		game.attack();
		//GameInterface.setText(game.printBoard());
		if (game.end()) {
			if (game.getEndStatus() == 0 && game.getBoard().getName() == "level1") {
				reset();
				loadLvl2();
			}
			if (game.getEndStatus() == 1) {
				GameState.setText("Perdeu. :(");
				reset();
				//GameInterface.setText("Start Game");
				StartGame.setEnabled(true);
			}
			if (game.getEndStatus() == 0 && game.getBoard().getName() == "level2") {
				reset();
				GameState.setText("Parabens Ganhou! :)");
				//GameInterface.setText("Start Game");
			}
		}
	}

	public void loadLvl2() {
		currentLevel=level2.clone();
		GameState.setText("Level 2");
		b = new Board(currentLevel);
		b.setName("level2");
		// Entidades
		hero = new Hero(1, 7, 'A');
		entidades.addElement(new Ogre(4, 1, 'O'));
		for (int i = 0; i < noOgres-1; i++) {
			ogre = new Ogre(4, 1, 'O');
			ogre.setCurrent('O');
			entidades.addElement(ogre);
		}
		entidades.add(hero);
		// Game
		game = new Game(b, entidades);
//		GameInterface.setText(game.printBoard());
//		StartGame.setEnabled(false);
//		UP.setEnabled(true);
//		DOWN.setEnabled(true);
//		LEFT.setEnabled(true);
//		RIGHT.setEnabled(true);
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
		//GameInterface.setText(game.printBoard());
		StartGame.setEnabled(false);
	}

	public void reset() {
		b = null;
		hero=null;
		guarda=null;
		ogre = null;
		// Map
		entidades.removeAllElements();
		// Game
		game = new Game(b, entidades);
//		GameInterface.setText("Reseting");
//		StartGame.setEnabled(true);
//		UP.setEnabled(false);
//		DOWN.setEnabled(false);
//		LEFT.setEnabled(false);
//		RIGHT.setEnabled(false);
		NoOgres.setEnabled(true);
		GuardType.setEnabled(true);
	}
}
