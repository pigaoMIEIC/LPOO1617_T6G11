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

import dkeep.logic.Board;
import dkeep.logic.Entidade;
import dkeep.logic.Game;
import dkeep.logic.Guarda;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;

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
	private JTextField textField;

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
		frmDungeonKeepGame.setIconImage(Toolkit.getDefaultToolkit().getImage(graphicWindow.class.getResource("/resources/DD-Transparent.png")));
		frmDungeonKeepGame.setTitle("Dungeon Keep Game");
		frmDungeonKeepGame.setBounds(100, 100, 689, 533);
		frmDungeonKeepGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeepGame.getContentPane().setLayout(null);
		
		JLabel NumberOfOgres = new JLabel("Number of Ogres");
		NumberOfOgres.setFont(new Font("Tahoma", Font.PLAIN, 9));
		NumberOfOgres.setHorizontalAlignment(SwingConstants.CENTER);
		NumberOfOgres.setBounds(30, 15, 90, 20);
		frmDungeonKeepGame.getContentPane().add(NumberOfOgres);
		
		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblGuardPersonality.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardPersonality.setBounds(30, 46, 90, 20);
		frmDungeonKeepGame.getContentPane().add(lblGuardPersonality);
		
		JLabel GameState = new JLabel("");
		GameState.setHorizontalAlignment(SwingConstants.CENTER);
		GameState.setBounds(30, 366, 350, 38);
		frmDungeonKeepGame.getContentPane().add(GameState);
		
		textField = new JTextField();
		textField.setBounds(130, 15, 40, 20);
		frmDungeonKeepGame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Rookie", "Suspicious", "Drunken"}));
		comboBox.setBounds(130, 46, 90, 20);
		frmDungeonKeepGame.getContentPane().add(comboBox);
		
		JTextArea txtrGameInterface = new JTextArea();
		txtrGameInterface.setFont(new Font("Courier New", Font.PLAIN, 20));
		txtrGameInterface.setText("Start Game");
		txtrGameInterface.setBounds(30, 80, 350, 275);
		frmDungeonKeepGame.getContentPane().add(txtrGameInterface);
		
		JButton btnUP = new JButton("UP");
		btnUP.setEnabled(false);
		btnUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUP.setBounds(460, 130, 70, 30);
		frmDungeonKeepGame.getContentPane().add(btnUP);
		
		JButton btnDOWN = new JButton("DOWN");
		btnDOWN.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnDOWN.setEnabled(false);
		btnDOWN.setBounds(460, 218, 70, 30);
		frmDungeonKeepGame.getContentPane().add(btnDOWN);
		
		JButton btnRIGHT = new JButton("RIGHT");
		btnRIGHT.setEnabled(false);
		btnRIGHT.setBounds(500, 175, 70, 30);
		frmDungeonKeepGame.getContentPane().add(btnRIGHT);
		
		JButton btnLEFT = new JButton("LEFT");
		btnLEFT.setEnabled(false);
		btnLEFT.setBounds(420, 175, 70, 30);
		frmDungeonKeepGame.getContentPane().add(btnLEFT);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(450, 80, 90, 40);
		frmDungeonKeepGame.getContentPane().add(btnStartGame);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnUP.setEnabled(true);
				btnDOWN.setEnabled(true);
				btnLEFT.setEnabled(true);
				btnRIGHT.setEnabled(true);
			}
		});
		
		JButton btnExit = new JButton("Exit Game");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(450, 325, 90, 30);
		frmDungeonKeepGame.getContentPane().add(btnExit);
		
	}
}
