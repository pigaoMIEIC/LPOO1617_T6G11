package dkeep.graphic;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class LevelEditor extends JFrame {
	
	JFrame parent;

	private int noColumns, noRows;
	private String filename;
	protected FileOutputStream out;
	
	//:::::::::::::::::::PANEL ELEMENTS::::::::::::::::::::
	private JComboBox rows;
	private JComboBox columns;
	private JTextField name;
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::

	public LevelEditor(JFrame parent) throws HeadlessException {
		getContentPane().setLayout(null);
		setTitle("Level Editor");
		setBounds(100, 100, 850, 800);
		this.parent=parent;

		// ::::::::::::::::::MASTER:::::::::::::::::::::::::
		JButton hero = new JButton("Hero");
		hero.setEnabled(false);
		hero.setBounds(620, 65, 130, 30);
		getContentPane().add(hero);

		JButton ogre = new JButton("Ogre");
		ogre.setEnabled(false);
		ogre.setBounds(620, 115, 130, 30);
		getContentPane().add(ogre);

		JButton wall = new JButton("Wall");
		wall.setEnabled(false);
		wall.setBounds(620, 215, 130, 30);
		getContentPane().add(wall);

		JButton Door = new JButton("Door");
		Door.setEnabled(false);
		Door.setBounds(620, 165, 130, 30);
		getContentPane().add(Door);

		JButton Key = new JButton("Key");
		Key.setEnabled(false);
		Key.setBounds(620, 265, 130, 30);
		getContentPane().add(Key);
		// :::::::::::::::::::::::::::::::::::::::::::::::::

		// ::::::::::::::::::INTRO PANEL::::::::::::::::::::
		JPanel panel = new JPanel();
		panel.setBounds(30, 65, 500, 200);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel labelRows = new JLabel("Rows");
		labelRows.setHorizontalAlignment(SwingConstants.CENTER);
		labelRows.setBounds(324, 0, 159, 64);
		panel.add(labelRows);

		JLabel labelColumns = new JLabel("Columns");
		labelColumns.setHorizontalAlignment(SwingConstants.CENTER);
		labelColumns.setBounds(17, 0, 159, 64);
		panel.add(labelColumns);

		columns = new JComboBox();
		columns.setModel(new DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
		columns.setBounds(17, 83, 159, 29);
		panel.add(columns);

		rows = new JComboBox();
		rows.setModel(new DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
		rows.setBounds(324, 83, 159, 29);
		panel.add(rows);

		JLabel labelName = new JLabel("Level Name");
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setBounds(193, 0, 114, 64);
		panel.add(labelName);

		name = new JTextField();
		name.setBounds(175, 83, 151, 29);
		name.setColumns(10);
		panel.add(name);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		btnExit.setBounds(363, 150, 120, 30);
		panel.add(btnExit);

		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					File f = new File(name.getText());
					out = new FileOutputStream(f);
				} catch (FileNotFoundException e1) {
					System.out.println("file not found");
				}catch(NullPointerException e2){
					System.out.println("path invalido");
				}
				start();
			}
		});
		start.setBounds(17, 150, 120, 31);
		panel.add(start);

		panel.setVisible(true);
		// :::::::::::::::::::::::::::::::::::::::::::::::::
	}

	protected void exit() {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parent.setVisible(true);
		this.setVisible(false);
		dispose();
	}

	public void start() {
		noRows=Integer.parseInt((String) rows.getSelectedItem());
		noColumns=Integer.parseInt((String)columns.getSelectedItem());
		try {
			out.write(noRows);
			out.write(noColumns);
		} catch (IOException e) {
			System.out.println("out.write manda exceção");
		}
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
	}
}
