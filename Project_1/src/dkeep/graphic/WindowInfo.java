package dkeep.graphic;

import java.io.File;

public interface WindowInfo {
	static String[] personalities = { "Rookie", "Suspicious", "Drunken" };
	static String[] numbers = {"1","2","3","4","5"};
	static String PATH = "userLevels/";
	static String SAVE = "savedLevels/";
	static String STATE = "gameStates/";
	static File directory=new File(SAVE);
	static File states=new File(STATE);

}
