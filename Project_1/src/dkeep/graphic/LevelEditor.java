package dkeep.graphic;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dkeep.logic.Board;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import java.awt.Canvas;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.MouseMotionAdapter;

public class LevelEditor extends JFrame {
	
	JFrame parent;
	
	private static String PATH = "userLevels/";
	private int noColumns, noRows;
	private String filename;
	protected BufferedWriter out;
	private int xSize=0,ySize=0;
	private char[][] editorBoard;
	private char selectedTile;
	private static File directory=new File(PATH);
	
	//:::::::::::::::::::PANEL ELEMENTS::::::::::::::::::::
	private JComboBox rows, columns;
	private JTextField name;
	private JPanel editorPanel, editorPart, intro;
	private JButton hero,wall,key,ogre,floor,door;
	private final ButtonGroup tileButton = new ButtonGroup();
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::

	public LevelEditor(JFrame parent) {
		if(!directory.exists())
			directory.mkdir();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Level Editor");
		setBounds(100, 100, 850, 800);
		this.parent=parent;
		
		// ::::::::::::::::::MASTER:::::::::::::::::::::::::
		hero = new JButton("Hero");
		tileButton.add(hero);
		hero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedTile = 'A';
			}
		});
		hero.setEnabled(false);
		hero.setBounds(620, 65, 130, 30);
		getContentPane().add(hero);

		ogre = new JButton("Ogre");
		tileButton.add(ogre);
		ogre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTile = 'O';
			}
		});
		ogre.setEnabled(false);
		ogre.setBounds(620, 115, 130, 30);
		getContentPane().add(ogre);

		wall = new JButton("Wall");
		tileButton.add(wall);
		wall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTile = 'x';
			}
		});
		wall.setEnabled(false);
		wall.setBounds(620, 215, 130, 30);
		getContentPane().add(wall);

		door = new JButton("Door");
		tileButton.add(door);
		door.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTile = 'i';
			}
		});
		door.setEnabled(false);
		door.setBounds(620, 165, 130, 30);
		getContentPane().add(door);

		key = new JButton("Key");
		tileButton.add(key);
		key.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTile = 'k';
			}
		});
		key.setEnabled(false);
		key.setBounds(620, 265, 130, 30);
		getContentPane().add(key);

		floor = new JButton("Floor");
		tileButton.add(floor);
		floor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTile = ' ';
			}
		});
		floor.setEnabled(false);
		floor.setBounds(620, 314, 131, 31);
		getContentPane().add(floor);
		// :::::::::::::::::::::::::::::::::::::::::::::::::

		
		// ::::::::::::::::::INTRO PANEL::::::::::::::::::::
		intro = new JPanel();
		intro.setBorder(new LineBorder(new Color(0, 0, 0)));
		intro.setBounds(30, 65, 500, 200);
		getContentPane().add(intro);
		intro.setLayout(null);

		JLabel labelRows = new JLabel("Rows");
		labelRows.setHorizontalAlignment(SwingConstants.CENTER);
		labelRows.setBounds(324, 0, 159, 64);
		intro.add(labelRows);

		JLabel labelColumns = new JLabel("Columns");
		labelColumns.setHorizontalAlignment(SwingConstants.CENTER);
		labelColumns.setBounds(17, 0, 159, 64);
		intro.add(labelColumns);

		columns = new JComboBox();
		columns.setModel(new DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
		columns.setBounds(17, 83, 159, 29);
		intro.add(columns);

		rows = new JComboBox();
		rows.setModel(new DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
		rows.setBounds(324, 83, 159, 29);
		intro.add(rows);

		JLabel labelName = new JLabel("Level Name");
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setBounds(193, 0, 114, 64);
		intro.add(labelName);

		name = new JTextField();
		name.setBounds(175, 83, 151, 29);
		name.setColumns(10);
		intro.add(name);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		btnExit.setBounds(363, 150, 120, 30);
		intro.add(btnExit);

		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(new File(PATH+name.getText()+".txt").exists()){
					JOptionPane.showMessageDialog(btnExit.getParent(), "That Level Name is already in use.", "Invalid Level Name!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH+name.getText()+".txt"), "utf-8"));
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					System.out.println("erro a abrir a file");
					JOptionPane.showMessageDialog(btnExit.getParent(), "That Level Name is not valid", "Invalid Level Name!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				start();
			}
		});
		start.setBounds(17, 150, 120, 31);
		intro.add(start);
		intro.setVisible(true);
		// ::::::::::::::::::::::::::::::::::::::::::::::::
		
		
		//:::::::::::::::::EDITOR PANEL::::::::::::::::::::

		editorPanel = new JPanel();
		editorPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int i = 0, j = 0;
				i = arg0.getX() / xSize;
				j = arg0.getY() / ySize;
				editorPanel.getComponents()[(i+(j*noColumns))].getGraphics().drawImage(getImage(selectedTile), 0, 0, xSize-1,ySize-1, editorPanel);
				editorPanel.revalidate();
				editorBoard[j][i] = selectedTile;
			}
		});
		editorPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = 0, j = 0;
				i = e.getX() / xSize;
				j = e.getY() / ySize;
				editorPanel.getComponents()[(i+(j*noColumns))].getGraphics().drawImage(getImage(selectedTile), 0, 0, xSize-1,ySize-1, editorPanel);
				editorPanel.revalidate();
				editorBoard[j][i] = selectedTile;
			}
		});
		editorPanel.setBorder(null);
		editorPanel.setBounds(30, 65, 500, 500);
		getContentPane().add(editorPanel);
		editorPanel.setLayout(null);
		
		//:::::::::::::::::::::::::::::::::::::::::::::::::
		
		
		JButton btnExitAndSave = new JButton("Exit and Save");
		btnExitAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Board b = new Board(editorBoard);
				try {
					
					if(b.readBoard()==null){
						JOptionPane.showMessageDialog(btnExitAndSave.getParent(), "The current board is not a complete one, please place at least one Hero, one key, one Ogre and one door.", "Invalid Level Board!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					saveLevel();
				} catch (IOException e1) {
					System.out.println("não conseguiu guardar o nivel");
					e1.printStackTrace();
				}
				exit();
			}
		});
		btnExitAndSave.setBounds(30, 584, 212, 43);
		getContentPane().add(btnExitAndSave);
		
		JButton btnExitWithoutSaving = new JButton("Exit without Saving");
		btnExitWithoutSaving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		btnExitWithoutSaving.setBounds(30, 646, 212, 43);
		getContentPane().add(btnExitWithoutSaving);
		
		JTextPane txtpnanyBlankCells = new JTextPane();
		txtpnanyBlankCells.setText("-Any blank cells are automatticly filled with the floor tile.\r\n-Press one of the buttons on the right to select a tile.\r\n-A valid game board has one hero, one key and at least\r\n one ogre and one door. \r\n");
		txtpnanyBlankCells.setEditable(false);
		txtpnanyBlankCells.setBounds(259, 584, 498, 105);
		getContentPane().add(txtpnanyBlankCells);
		
	}

	//:::::::::::::::::::::::EXIT AND START::::::::::::::::
	@Override
	public void dispose() {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.dispose();
		parent.setVisible(true);
	}
	
	protected void exit() {
		dispose();
	}

	public void start() {
		noRows=Integer.parseInt((String) rows.getSelectedItem());
		noColumns=Integer.parseInt((String)columns.getSelectedItem());
		System.out.print(noRows);
		System.out.print(noColumns);
		try {
			out.write((String) rows.getSelectedItem());
			out.write(' ');
			out.write((String) columns.getSelectedItem());
			out.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xSize=500/noColumns;
		ySize=500/noRows;
		editorBoard = new char[noRows][noColumns];
		initializeBoard();
		loadEditor();
		hero.setEnabled(true);
		ogre.setEnabled(true);
		door.setEnabled(true);
		wall.setEnabled(true);
		floor.setEnabled(true);
		key.setEnabled(true);
		
	}
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	public void loadEditor(){
		for(int j = 0; j<noRows;j++){
			for(int i = 0; i<noColumns;i++){
				editorPart = new JPanel();
				editorPart.setBorder(new LineBorder(new Color(0, 0, 0)));
				editorPart.setBounds(i*xSize,j*ySize,xSize,ySize);
				editorPanel.add(editorPart);
			}
		}
		intro.setVisible(false);
		editorPanel.setVisible(true);
	}
	
	public void saveLevel() throws IOException{
		for(int i = 0; i<editorBoard.length;i++){
			for(int j=0;j<editorBoard[0].length;j++){
				out.write(editorBoard[i][j]);
			}
			out.newLine();
		}		
	}
	
	public void initializeBoard(){
		for(int i = 0; i<editorBoard.length;i++){
			for(int j=0;j<editorBoard[0].length;j++){
				editorBoard[i][j]=' ';
			}
		}
	}
	
	protected Image getImage(char c) {
		try {
			switch (c) {
			case 'A':return ImageIO.read(new File("src/resources/heroArmed.jpg"));
			case 'x':return ImageIO.read(new File("src/resources/wall.jpg"));
			case 'i':return ImageIO.read(new File("src/resources/door.jpg"));
			case 'O':return ImageIO.read(new File("src/resources/ogre.jpg"));
			case 'k':return ImageIO.read(new File("src/resources/key.jpg"));
			case 'K':return ImageIO.read(new File("src/resources/heroArmedKey.jpg"));
			case '*':return ImageIO.read(new File("src/resources/attack.jpg"));
			case '8':return ImageIO.read(new File("src/resources/sleepOgre.jpg"));
			case ' ':return ImageIO.read(new File("src/resources/floor.jpg"));
			default:return ImageIO.read(new File("src/resources/default.jpg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
