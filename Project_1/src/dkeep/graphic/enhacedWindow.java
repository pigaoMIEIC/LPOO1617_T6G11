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

public class enhacedWindow implements Serializable,WindowInfo,MapsInterface{

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
	
	static Integer noOgres=-1;
	static String guardType_str=null;

	
	
	//:::::::::::::::::JFRAME OBJECTS::::::::::::::::::::::
	private JLabel GameState, numberOgres, guardType;
	private JButton StartGame;
	private GameInterface gameInterface;
	private JMenuBar menuBar;
	private JLabel lblEditor,lblloadLevel;
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::

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
		if(!directory.exists())
			directory.mkdir();
		if(!states.exists())
			states.mkdir();
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
		frmDungeonKeepGame.setFocusable(true);
		frmDungeonKeepGame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(enhacedWindow.class.getResource("/resources/DD-Transparent.png")));
		frmDungeonKeepGame.setTitle("Dungeon Keep Game");
		frmDungeonKeepGame.setBounds(100, 100, 850, 800);
		frmDungeonKeepGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeepGame.getContentPane().setLayout(null);		
		
//		JLabel NumberOfOgres = new JLabel("Number of Ogres");
//		NumberOfOgres.setFont(new Font("Tahoma", Font.PLAIN, 9));
//		NumberOfOgres.setHorizontalAlignment(SwingConstants.CENTER);
//		NumberOfOgres.setBounds(30, 15, 90, 20);
//		frmDungeonKeepGame.getContentPane().add(NumberOfOgres);
//
//		JLabel GuardPersonality = new JLabel("Guard Personality");
//		GuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 9));
//		GuardPersonality.setHorizontalAlignment(SwingConstants.CENTER);
//		GuardPersonality.setBounds(30, 46, 90, 20);
//		frmDungeonKeepGame.getContentPane().add(GuardPersonality);

//		NoOgres = new JTextField();
//		NoOgres.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				int keyCode = e.getKeyCode();
//				if(keyCode == KeyEvent.VK_ENTER){
//					frmDungeonKeepGame.requestFocusInWindow();
//				}
//				
//			}
//			@Override
//			public void keyPressed(KeyEvent e) {
//				int keyCode = e.getKeyCode();
//				if(keyCode == KeyEvent.VK_ENTER){
//					frmDungeonKeepGame.requestFocusInWindow();
//				}
//			}
//		});
//		NoOgres.addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent arg0) {
//				try {
//					Integer.parseInt(NoOgres.getText());
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//					GameState.setText("Invalid number of Ogres");
//					NoOgres.setText("0");
//					return;
//				}
//				if (Integer.parseInt(NoOgres.getText()) > 5) {
//					NoOgres.setText("5");
//					noOgres = 5;
//				}
//				if (Integer.parseInt(NoOgres.getText()) < 1) {
//					NoOgres.setText("1");
//					noOgres = 1;
//				}
//				noOgres = Integer.parseInt(NoOgres.getText());
//			}
//		});
//		NoOgres.setBounds(130, 15, 40, 20);
//		NoOgres.setColumns(1);
//		frmDungeonKeepGame.getContentPane().add(NoOgres);
		

//		GuardType = new JComboBox();
//		GuardType.setModel(new DefaultComboBoxModel(new String[] { "Rookie", "Suspicious", "Drunken" }));
//		GuardType.setBounds(130, 46, 90, 20);
//		frmDungeonKeepGame.getContentPane().add(GuardType);
		
		
		//::::::::::::::::MENU & MENU LABELS::::::::::::::::::::::
		menuBar = new JMenuBar();
		frmDungeonKeepGame.setJMenuBar(menuBar);		
		
		guardType = new JLabel("Guard Personality");
		guardType.setHorizontalAlignment(SwingConstants.CENTER);
		guardType.setFont(new Font("Tahoma", Font.PLAIN, 24));
		guardType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				guardType_str = (String)JOptionPane.showInputDialog(frmDungeonKeepGame, "Which Guard would you like to face in Level 1", "Guard Personality", JOptionPane.QUESTION_MESSAGE, null, personalities, null);
				System.out.println(guardType_str);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				guardType.setForeground(SystemColor.activeCaption);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				guardType.setForeground(Color.BLACK);
			}
		});
		menuBar.add(guardType);
		
		numberOgres = new JLabel("Number of Ogres");
		numberOgres.setHorizontalAlignment(SwingConstants.CENTER);
		numberOgres.setFont(new Font("Tahoma", Font.PLAIN, 24));
		numberOgres.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				noOgres = Integer.parseInt((String)JOptionPane.showInputDialog(frmDungeonKeepGame, "How many Ogres would you like to face in Level 2", "Number of Ogres", JOptionPane.QUESTION_MESSAGE, null, numbers, null));
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
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut);
		menuBar.add(numberOgres);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);
		
		JMenu mnLevelEditing = new JMenu("Level Editing");
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
		mnLevelEditing.setFont(new Font("Tahoma", Font.PLAIN, 24));
		menuBar.add(mnLevelEditing);
		
		lblEditor = new JLabel("Editor");
		mnLevelEditing.add(lblEditor);
		lblEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editor = new LevelEditor(frmDungeonKeepGame);
				editor.setVisible(true);
				frmDungeonKeepGame.setVisible(false);
				System.out.println("editor pressed");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblEditor.setForeground(SystemColor.activeCaption);
			}
			public void mouseExited(MouseEvent e) {
				lblEditor.setForeground(Color.BLACK);
			}
		});
		lblEditor.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditor.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		lblloadLevel = new JLabel("Load Level");
		mnLevelEditing.add(lblloadLevel);
		lblloadLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String levelName;
				File folder = new File(PATH);
				File [] levels = folder.listFiles();
				if(levels.length==0){
					JOptionPane.showMessageDialog(frmDungeonKeepGame, "There are no levels available yet. Try out the level editor in order for your level to show up here.", "No available levels.", JOptionPane.ERROR_MESSAGE);
				}
				String [] levels_str = new String[levels.length];
				for(int i = 0;i<levels.length;i++){
					levels_str[i]=levels[i].getName();
				}
				levelName = (String)JOptionPane.showInputDialog(frmDungeonKeepGame, "Which level would you like to play?", "Choose a Level", JOptionPane.QUESTION_MESSAGE, null, levels_str, null);
				try {
					inputFile = new Scanner(new File(PATH + levelName));
				} catch (FileNotFoundException e1) {
					System.out.println("File not found in load.");
					return;
				}
				loadLevel(levelName,"level2");
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
		lblloadLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblloadLevel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_2);
		
		JMenu mnLoadSave = new JMenu("Game Saves");
		mnLoadSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
		menuBar.add(mnLoadSave);
		
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
			}
		});
		lblLoadSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
		mnLoadSave.add(lblLoadSave);
		
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
		// :::::::::::::::::::::::::::::::::::::::::::::::::

		// ::::::::::::::GAME INTERFACE:::::::::::::::::::::
		StartGame = new JButton("Start Game");
		StartGame.setBounds(640, 80, 169, 40);
		StartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StartGame.getText()=="Continue"){
					reset();
					return;
				}
				frmDungeonKeepGame.setFocusable(true);
				reset();
				if(guardType_str==null){
					JOptionPane.showMessageDialog(frmDungeonKeepGame, "Please choose a valid personality for the Guard in Level 1", "Guard has no personality!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(noOgres<0){
					JOptionPane.showMessageDialog(frmDungeonKeepGame, "Please choose a valid number of Ogres", "Invalid number of Ogres!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				loadLvl1();
				numberOgres.setEnabled(false);
				guardType.setEnabled(false);
				lblEditor.setEnabled(false);
				frmDungeonKeepGame.requestFocusInWindow();
			}
		});
		frmDungeonKeepGame.getContentPane().add(StartGame);

		GameState = new JLabel("");
		GameState.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GameState.setBounds(30, 640, 576, 61);
		GameState.setHorizontalAlignment(SwingConstants.CENTER);
		frmDungeonKeepGame.getContentPane().add(GameState);

		gameInterface = new GameInterface();
		gameInterface.setBorder(new LineBorder(new Color(0, 0, 0)));
		gameInterface.setBounds(40, 80, 550, 550);
		frmDungeonKeepGame.getContentPane().add(gameInterface);
		// :::::::::::::::::::::::::::::::::::::::::::::::::
		
		JButton Exit = new JButton("Exit Game");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Exit.setBounds(640, 643, 169, 48);
		frmDungeonKeepGame.getContentPane().add(Exit);

	}

	protected void saveState() {
		FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(SAVE+"saveGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(b);
            out.writeObject(entidades);
            out.writeObject(noOgres);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception w){
            w.printStackTrace();
        }
        frmDungeonKeepGame.requestFocusInWindow();
        System.out.println("game saved");

	}
	
	protected void loadState() {
		try {
            FileInputStream fileIn = new FileInputStream(SAVE+"saveGame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            b = (Board)in.readObject();
            entidades = (Vector<Entidade>)in.readObject();
            noOgres = (int)in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return;
         }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
         }
//		game.setEntidades(entidades);
//		game.setBoard(b);
		this.game = new Game(b,entidades);
		gameInterface.updatePrint(game);
        frmDungeonKeepGame.requestFocusInWindow();
        frmDungeonKeepGame.repaint();
	}
	
	

	protected void loadLevel(String levelName,String name) {
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
		gameInterface.updatePrint(game);
		frmDungeonKeepGame.setFocusable(true);
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);
		lblloadLevel.setEnabled(false);
		frmDungeonKeepGame.requestFocusInWindow();
	}
	

	public void setGame(char[][] newBoard,String name){
		b=new Board(newBoard);
		b.setName(name);
		entidades = b.readBoard();
		game= new Game(b,entidades);
	}

	public void buttonEvent(char input) {
		game.clearAttack();
		game.Move(input);
		game.attack();
		gameInterface.updatePrint(game);
		if (game.end()) {
			if (game.getEndStatus() == 0 && game.getBoard().getName().equals("level1")) {
				reset();
				loadLvl2();
			}
			if (game.getEndStatus() == 1) {
				GameState.setText("Perdeu. :(");
				frmDungeonKeepGame.setFocusable(false);
				StartGame.setEnabled(true);
				StartGame.setText("Continue");
//				reset();
//				StartGame.setEnabled(true);
			}
			if (game.getEndStatus() == 0 && game.getBoard().getName().equals("level2")) {
				GameState.setText("Parabens Ganhou! :)");
				frmDungeonKeepGame.setFocusable(false);
				StartGame.setEnabled(true);
				StartGame.setText("Continue");
//				reset();
			}
		}
		
	}

	public void loadLvl2() {
		GameState.setText("Level 2");
		b = new Board(level2);
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
		gameInterface.updatePrint(game);
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);
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
		game = new Game(b, entidades);
		gameInterface.updatePrint(game);
		numberOgres.setEnabled(false);
		guardType.setEnabled(false);
		lblEditor.setEnabled(false);

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
		gameInterface.updatePrint(null);
		StartGame.setText("Start Game");
		GameState.setText("");
		StartGame.setEnabled(true);
		numberOgres.setEnabled(true);
		guardType.setEnabled(true);
		lblEditor.setEnabled(true);
		lblloadLevel.setEnabled(true);
	}
}
