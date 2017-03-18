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
	private BufferedImage stunnedOgre;
	private BufferedImage entry;

	public GameInterface(){
		super();
		this.print = null;
		try {
			wall = ImageIO.read(new File("src/resources/wall.jpg"));
			ogre = ImageIO.read(new File("src/resources/ogre.jpg"));
			stunnedOgre = ImageIO.read(new File("src/resources/sleepOgre.jpg"));
			hero = ImageIO.read(new File("src/resources/hero.jpg"));
			armedHero = ImageIO.read(new File("src/resources/armed.jpg"));
			keyHero = ImageIO.read(new File("src/resources/DD-Transparent.png"));
			guard = ImageIO.read(new File("src/resources/guard.png"));
			sleepGuard = ImageIO.read(new File("src/resources/sleepguard.jpg"));
			closedDoor = ImageIO.read(new File("src/resources/door.jpg"));
			openDoor = ImageIO.read(new File("src/resources/opendoor.jpg"));
			key = ImageIO.read(new File("src/resources/key.jpg"));
			attack = ImageIO.read(new File("src/resources/attack.jpg"));
			floor = ImageIO.read(new File("src/resources/floor.jpg"));
			defaulti = ImageIO.read(new File("src/resources/default.jpg"));
			entry = ImageIO.read(new File("src/resources/entry.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("load de imagens n�o funcionou");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int j=0,x=0;
		super.paintComponent(g);
		if(print==null){
			g.drawImage(entry,0,0,null);
			return;
		}
		for(int i =0;i<print.length();i++){
			if(print.charAt(i)=='\n'){
				j++;
				x=0;
				continue;
			}
			g.drawImage(getImage(print.charAt(i)),x*55,j*55,55,55,null);
			x++;
		}
	}
	
	public void updatePrint(String print){
		this.print=print;
		repaint();
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
		case '8': return stunnedOgre;
		case ' ': return floor;
		default: return defaulti;
		}
	}
	

}
