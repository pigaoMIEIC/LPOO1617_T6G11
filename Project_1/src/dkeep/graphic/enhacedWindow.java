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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.awt.Window.Type;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.Box;
import javax.swing.border.LineBorder;

public class enhacedWindow implements Serializable, WindowInfo, MapsInterface {

	private JFrame frmDungeonKeepGame;
	private static LevelEditor editor;
	private BufferedWriter out;
	private Scanner inputFile;

	static Board b;
	static Vector<Entidade> entidades = new Vector<Entidade>();
	static Hero hero;
	static Ogre ogre;
	Game game;
	static Guarda guarda;

	static Integer noOgres = -1;
	static String guardType_str = null;

	// :::::::::::::::::JFRAME OBJECTS::::::::::::::::::::::
	private JLabel GameState, numberOgres, guardType;
	private JButton StartGame;
	private GameInterface gameInterface;
	private JMenuBar menuBar;
	private JLabel lblEditor, lblloadLevel;
	private JMenu mnLoadSave, mnLevelEditing;
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::

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

	public void keyEvents() {
		frmDungeonKeepGame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_UP:buttonEvent('w');break;
				case KeyEvent.VK_DOWN:buttonEvent('s');break;
				case KeyEvent.VK_RIGHT:buttonEvent('d');break;
				case KeyEvent.VK_LEFT:buttonEvent('a');break;
				}
			}
		});
	}

	public void menuBar() {
		frmDungeonKeepGame.setJMenuBar(menuBar);
		guardType = new JLabel("Guard Personality");
		guardType.setHorizontalAlignment(SwingConstants.CENTER);
		guardType.setFont(new Font("Tahoma", Font.PLAIN, 24));
		menuBarEvents();
		
	}

	private void menuBarEvents() {
		guardType.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				guardType_str = (String) JOptionPane.showInputDialog(frmDungeonKeepGame,
						"Which Guard would you like to face in Level 1", "Guard Personality",
						JOptionPane.QUESTION_MESSAGE, null, personalities, null);
				System.out.println(guardType_str);
			}
			public void mouseEntered(MouseEvent e) {
				guardType.setForeground(SystemColor.activeCaption);
			}
			public void mouseExited(MouseEvent e) {
				guardType.setForeground(Color.BLACK);
			}});
	}

	private void initializeFrame(){
		frmDungeonKeepGame.setFocusable(true);
		frmDungeonKeepGame.setIconImage(Toolkit.getDefaultToolkit().getImage(enhacedWindow.class.getResource("/resources/DD-Transparent.png")));
		frmDungeonKeepGame.setTitle("Dungeon Keep Game");
		frmDungeonKeepGame.setBounds(100, 100, 850, 800);
		frmDungeonKeepGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeepGame.getContentPane().setLayout(null);
	}
	
	private void numberOgresLableEvents(){
		numberOgres.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				noOgres = Integer.parseInt((String) JOptionPane.showInputDialog(frmDungeonKeepGame,
						"How many Ogres would you like to face in Level 2", "Number of Ogres",
						JOptionPane.QUESTION_MESSAGE, null, numbers, null));
				System.out.println(noOgres);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				numberOgres.setForeground(SystemColor.activeCaption);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				numberOgres.setForeground(Color.BLACK);
			}
		});
	}
	
	private void numberOgresLable(){
		numberOgres = new JLabel("Number of Ogres");
		numberOgres.setHorizontalAlignment(SwingConstants.CENTER);
		numberOgres.setFont(new Font("Tahoma", Font.PLAIN, 24));
		numberOgresLableEvents();
		Component horizontalStrut = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut);
		menuBar.add(numberOgres);
	}
	
	public void menuLevelEditing(){
		mnLevelEditing = new JMenu("Level Editing");
		mnLevelEditing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mnLevelEditing.setForeground(SystemColor.activeCaption);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mnLevelEditing.setForeground(Color.BLACK);
			}
		});
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);
		mnLevelEditing.setFont(new Font("Tahoma", Font.PLAIN, 24));
		menuBar.add(mnLevelEditing);
		
		editorLevel();

		lblloadLevel = new JLabel("Load Level");
		mnLevelEditing.add(lblloadLevel);
		lblloadLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblloadLevel.setFont(new Font("Tahoma", Font.PLAIN, 24));
	}
	
	private void editorLevel(){
		lblEditor = new JLabel("Editor");
		mnLevelEditing.add(lblEditor);
		editorLevelEvents();
		lblEditor.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditor.setFont(new Font("Tahoma", Font.PLAIN, 24));
	}
	
	private void editorLevelEvents(){
		lblEditor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				editor = new LevelEditor(frmDungeonKeepGame);
				editor.setVisible(true);
				frmDungeonKeepGame.setVisible(false);
				System.out.println("editor pressed");
			}
			public void mouseEntered(MouseEvent e) {
				lblEditor.setForeground(SystemColor.activeCaption);
			}
			public void mouseExited(MouseEvent e) {
				lblEditor.setForeground(Color.BLACK);
			}
		});
	}
	
	private void loadLevelEvents(){
		lblloadLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String levelName;
				File folder = new File(SAVE);
				File[] levels = folder.listFiles();
				if (levels.length == 0) {
					JOptionPane.showMessageDialog(frmDungeonKeepGame,
							"There are no levels available yet. Try out the level editor in order for your level to show up here.",
							"No available levels.", JOptionPane.ERROR_MESSAGE);
				}
				String[] levels_str = new String[levels.length];
				for (int i = 0; i < levels.length; i++) {
					levels_str[i] = levels[i].getName();
				}
				levelName = (String) JOptionPane.showInputDialog(frmDungeonKeepGame,
						"Which level would you like to play?", "Choose a Level", JOptionPane.QUESTION_MESSAGE, null,
						levels_str, null);
				try {
					inputFile = new Scanner(new File(SAVE + levelName));
				} catch (FileNotFoundException e1) {
					System.out.println("File not found in load.");
					return;
				}
				loadLevel(levelName, "level2");
				inputFile.close();
			}

			public void mouseEntered(MouseEvent e) {
				lblloadLevel.setForeground(SystemColor.activeCaption);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblloadLevel.setForeground(Color.BLACK);
			}
		});
	}
	
	public void loadLoadSave(){
		JLabel lblLoadSave = new JLabel("Load Save");
		lblLoadSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblLoadSave.setForeground(SystemColor.activeCaption);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblLoadSave.setForeground(Color.BLACK);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				loadState();
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		lblLoadSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
		mnLoadSave.add(lblLoadSave);
		
	}
	
	public void loadSaveState(){
		JLabel lblSaveState = new JLabel("Save State");
		lblSaveState.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblSaveState.setForeground(SystemColor.activeCaption);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblSaveState.setForeground(Color.BLACK);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				saveState();
			}
		});
		lblSaveState.setFont(new Font("Tahoma", Font.PLAIN, 24));
		mnLoadSave.add(lblSaveState);
	}
	
	public void loadStateMenu(){
		mnLoadSave = new JMenu("Game Saves");
		mnLoadSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
		menuBar.add(mnLoadSave);
		loadLoadSave();
		loadSaveState();	
	}
	
	public void loadStartGame(){
		StartGame = new JButton("Start Game");
		StartGame.setBounds(640, 80, 169, 40);
		StartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (StartGame.getText() == "Continue") {
					reset();
					return;
				}
				
				if (guardType_str == null) {
					JOptionPane.showMessageDialog(frmDungeonKeepGame,
							"Please choose a valid personality for the Guard in Level 1", "Guard has no personality!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (noOgres < 0) {
					JOptionPane.showMessageDialog(frmDungeonKeepGame, "Please choose a valid number of Ogres",
							"Invalid number of Ogres!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				startGameRoutine();
				loadLvl1();
			}
		});
		frmDungeonKeepGame.getContentPane().add(StartGame);
	}
	
	public void loadGameInterface(){
		GameState = new JLabel("");
		GameState.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GameState.setBounds(30, 640, 576, 61);
		GameState.setHorizontalAlignment(SwingConstants.CENTER);
		frmDungeonKeepGame.getContentPane().add(GameState);

		gameInterface = new GameInterface();
		gameInterface.setBorder(new LineBorder(new Color(0, 0, 0)));
		gameInterface.setBounds(40, 80, 550, 550);
		frmDungeonKeepGame.getContentPane().add(gameInterface);
	}
	
	public void loadUpButton(){
		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonEvent('w');
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		btnUp.setBounds(661, 229, 89, 23);
		frmDungeonKeepGame.getContentPane().add(btnUp);
	}
	
	public void loadDownButton(){
		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonEvent('s');
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		btnDown.setBounds(661, 297, 89, 23);
		frmDungeonKeepGame.getContentPane().add(btnDown);
	}
	
	public void loadLeftButton(){
		JButton btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonEvent('a');
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		btnLeft.setBounds(600, 263, 89, 23);
		frmDungeonKeepGame.getContentPane().add(btnLeft);
	}
	
	public void loadRightButton(){
		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonEvent('d');
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		btnRight.setBounds(735, 263, 89, 23);
		frmDungeonKeepGame.getContentPane().add(btnRight);
	}
	
	public void loadGameButtons(){
		loadUpButton();
		loadDownButton();
		loadLeftButton();
		loadRightButton();
	}
	
	public void loadExitButton(){
		JButton Exit = new JButton("Exit Game");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Exit.setBounds(640, 643, 169, 48);
		frmDungeonKeepGame.getContentPane().add(Exit);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if (!directory.exists())directory.mkdir();
		if (!states.exists())states.mkdir();
		frmDungeonKeepGame = new JFrame();
		keyEvents();
		initializeFrame();
		// ::::::::::::::::MENU & MENU LABELS::::::::::::::::::::::
		menuBar = new JMenuBar();
		menuBar();
		menuBar.add(guardType);
		numberOgresLable();
		menuLevelEditing();
		loadLevelEvents();
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_2);
		loadStateMenu();
		// ::::::::::::::GAME INTERFACE:::::::::::::::::::::
		loadStartGame();
		loadGameInterface();
		// :::::::::::::::::::::::::::::::::::::::::::::::::
		loadExitButton();
		loadGameButtons();
	}

	protected void saveState() {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(STATE + "saveGame.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(b);
			out.writeObject(entidades);
			out.writeObject(noOgres);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception w) {
			w.printStackTrace();
		}
		frmDungeonKeepGame.requestFocusInWindow();
		System.out.println("game saved");

	}

	protected void loadState() {
		startGameRoutine();
		if(new File(STATE).listFiles().length==0){
			JOptionPane.showMessageDialog(frmDungeonKeepGame,
					"There is no game save available", "No Saves!",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			FileInputStream fileIn = new FileInputStream(STATE + "saveGame.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			b = (Board) in.readObject();
			entidades = (Vector<Entidade>) in.readObject();
			noOgres = (int) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		this.game = new Game(b, entidades);
		gameInterface.updatePrint(null,game);
		gameInterface.repaint();
	}

	protected void loadLevel(String levelName, String name) {
		char[][] newBoard;
		int rows = inputFile.nextInt();
		int columns = inputFile.nextInt();
		newBoard = new char[rows][columns];
		String row;
		inputFile.nextLine();
		for (int i = 0; i < rows; i++) {
			row = inputFile.nextLine();
			char[] newRow = row.toCharArray();
			newBoard[i] = newRow;
			System.out.println(newRow);
		}
		setGame(newBoard, name);
		gameInterface.updatePrint(null,game);
		frmDungeonKeepGame.setFocusable(true);
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);
		lblloadLevel.setEnabled(false);
		frmDungeonKeepGame.requestFocusInWindow();
	}

	public void setGame(char[][] newBoard, String name) {
		b = new Board(newBoard);
		b.setName(name);
		entidades = b.readBoard();
		game = new Game(b, entidades);
	}

	public void buttonEvent(char input) {
		String temp;
		temp = game.Move(input);
		gameInterface.updatePrint(temp, game);
		if (game.end()) {
			int tempStatus = game.getEndStatus();
			String tempName = game.getBoard().getName();
			if (tempStatus == 0 && tempName.equals("level1")) {
				reset();
				loadLvl2();}
			if (tempStatus == 1) {
				GameState.setText("Perdeu. :(");
				endGameRoutine();}
			if (tempStatus == 0 && tempName.equals("level2")) {
				GameState.setText("Parabens Ganhou! :)");
				endGameRoutine();}
			}
	}

	public void loadLvl2() {
		GameState.setText("Level 2");
		b = new Board(level2);
		b.setName("level2");
		// Entidades
		hero = new Hero(1, 7, 'A');
		entidades.addElement(new Ogre(4, 1, 'O'));
		for (int i = 0; i < noOgres - 1; i++) {
			ogre = new Ogre(4, 1, 'O');
			ogre.setCurrent('O');
			entidades.addElement(ogre);
		}
		entidades.add(hero);
		// Game
		gameLoadRoutine();
	}

	public void loadLvl1() {
		GameState.setText("Level 1");
		b = new Board(level1);
		b.setName("level1");
		// Entidades
		if (guardType_str == "Rookie") {
			guarda = new Rookie(8, 1, 'G', level1_mov);
		} else if (guardType_str == "Suspicious") {
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
		gameLoadRoutine();
	}

	public void reset() {
		b = null;
		hero = null;
		guarda = null;
		ogre = null;
		// Map
		entidades.removeAllElements();
		// Game
		game = new Game(b, entidades);
		gameInterface.updatePrint(null,null);
		StartGame.setText("Start Game");
		GameState.setText("");
		StartGame.setEnabled(true);
		numberOgres.setEnabled(true);
		guardType.setEnabled(true);
		lblEditor.setEnabled(true);
		lblloadLevel.setEnabled(true);
	}
	
	public void startGameRoutine(){
		frmDungeonKeepGame.setFocusable(true);
		reset();				
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);
		frmDungeonKeepGame.requestFocusInWindow();
	}

	public void endGameRoutine() {
		frmDungeonKeepGame.setFocusable(false);
		StartGame.setEnabled(true);
		StartGame.setText("Continue");
	}
	
	public void gameLoadRoutine(){
		game = new Game(b, entidades);
		gameInterface.updatePrint(null,game);
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);
	}
}
