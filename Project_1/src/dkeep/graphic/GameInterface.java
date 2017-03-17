package dkeep.graphic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameInterface extends JPanel {
	
	String print;

	private BufferedImage wall;
	private BufferedImage ogre;
	private BufferedImage hero;
	private BufferedImage guard;
	private BufferedImage sleepGuard;
	private BufferedImage closedDoor;
	private BufferedImage openDoor;
	private BufferedImage floor;
	private BufferedImage keyHero;
	private BufferedImage armedHero;
	private BufferedImage attack;
	private BufferedImage key;
	private BufferedImage defaulti;

	public GameInterface(){
		super();
		try {
			wall = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			ogre = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			hero = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			armedHero = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			keyHero = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			guard = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			sleepGuard = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			closedDoor = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			openDoor = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			key = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			attack = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			floor = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			defaulti = ImageIO.read(new File("src/resources/DD-Transparent.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int j=0;
		super.paintComponent(g);
		for(int i =0;i<print.length();i++){
			if(print.charAt(i)=='\n'){
				j++;
				continue;
			}
			g.drawImage(getImage(print.charAt(i)),i*10,j*10,10,10,null);
		}
	}

	private Image getImage(char c) {
		switch(c){
		case 'H': return hero;
		case 'x': return wall;
		case 'i': return closedDoor;
		case 's': return openDoor;
		case 'O': return ogre;
		case 'A': return armedHero;
		case 'g': return sleepGuard;
		case 'G': return guard;
		case 'k': return key;
		case 'K': return keyHero;
		case '*': return attack;
		case ' ': return floor;
		default: return defaulti;
		}
	}
	

}
