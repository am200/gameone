package game;

import game.unit.Citizen;
import game.collectable.util.CollectableKey;
import game.base.Coordinate;
import game.terrain.GameField;
import game.building.HomeBase;
import game.base.Team;
import game.base.Tree;
import game.util.DevelopmentState;
import game.util.ProductionMode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author amohamed
 */
public class application extends Application {

    private static GameField gameField;
    public static final int GAME_FIELD_WIDTH = 10;
    public static final int GAME_FIELD_HEIGHT = 10;
    public static final int MAXIMUM_STEPS = 1000;
    public static final int MAXIMUM_LOOP_COUNTER = 10000;
    public static final int TEMPORARY_COUNTER = 100;

    private final static String filePath = "/Users/amohamed/Desktop/output.txt";

    public static DevelopmentState getStartDevelopmentState() {
	return DevelopmentState.STATE_1;
    }

    private String[][] stringArray;

    public static GameField getGameField() {
	return gameField;
    }

    @Override
    public void start(Stage primaryStage) {
	try {
	    gameField = new GameField(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

	    stringArray = new String[GAME_FIELD_HEIGHT + 1][GAME_FIELD_WIDTH + 1];

	    Random rand = new Random();
	    int treeCounter = rand.nextInt((GAME_FIELD_WIDTH * GAME_FIELD_HEIGHT) / 2) + 10;

	    Map<Integer, Map<Integer, String>> fieldMap = new HashMap<>();

	    for (int line = 0; line < GAME_FIELD_HEIGHT; line++) {
		Map<Integer, String> row = new HashMap<>();
		for (int x = 0; x < GAME_FIELD_WIDTH; x++) {
		    row.put(x, " ");
		}
		fieldMap.put(line, row);
	    }

	    System.out.println("################## Start the game #################");
	    System.out.println("####################################################");
	    Map<Coordinate, Tree> treeCoorinates = new TreeMap<>();
	    for (int i = 0; i < treeCounter; i++) {

		int randX = rand.nextInt(GAME_FIELD_WIDTH - 1);
		int randY = rand.nextInt(GAME_FIELD_HEIGHT - 1);
		boolean emptyField = false;
		int tempCounter = 0;
		Coordinate actualCenter = new Coordinate(randX, randY);
		while (!emptyField) {
		    if (gameField.getObjectByCooridante(actualCenter) != null) {
			randX = rand.nextInt(GAME_FIELD_WIDTH - 1);
			randY = rand.nextInt(GAME_FIELD_HEIGHT - 1);
			actualCenter = new Coordinate(randX, randY);
		    } else {
			emptyField = true;
			break;
		    }
		    if (tempCounter >= TEMPORARY_COUNTER) {
			break;
		    }
		}

		Coordinate treeCoordinate = new Coordinate(randX, randY);
		Tree tree = new Tree(treeCoordinate, 1, 1);

		treeCoorinates.put(treeCoordinate, tree);
		gameField.addObject(tree);
		fieldMap.get(randY).put(randX, "*");
		addToGrid(treeCoordinate, "*");
	    }

	    int treeCounting = 1;
	    for (Coordinate coord : treeCoorinates.keySet()) {
		System.out.println("Add Tree nr " + treeCounting + " at X: " + coord.getX() + ", Y: " + coord.getY());
		treeCounting++;
	    }

	    int randX = rand.nextInt(GAME_FIELD_WIDTH - 1);
	    int randY = rand.nextInt(GAME_FIELD_HEIGHT - 1);
	    boolean emptyField = false;
	    int tempCounter = 0;
	    Coordinate actualCenter = new Coordinate(randX, randY);
	    while (!emptyField) {
		if (gameField.getObjectByCooridante(actualCenter) != null) {
		    randX = rand.nextInt(GAME_FIELD_WIDTH - 1);
		    randY = rand.nextInt(GAME_FIELD_HEIGHT - 1);
		    actualCenter = new Coordinate(randX, randY);
		} else {
		    emptyField = true;
		    break;
		}
		if (tempCounter >= 100) {
		    break;
		}
	    }

	    System.out.println("####################################################");

	    Team team = new Team("1234", "MyTeam", "Blue");

	    System.out.println("Add Team " + team);

	    HomeBase homeBase = new HomeBase(actualCenter, team, ProductionMode.AUTO);
	    fieldMap.get(actualCenter.getY()).put(actualCenter.getX(), "B");
	    addToGrid(actualCenter, "B");
	    Coordinate startPoint = new Coordinate(actualCenter.getX() + 1, actualCenter.getY());
	    Coordinate startPoint2 = new Coordinate(actualCenter.getX(), actualCenter.getY() + 1);

	    Citizen cit = new Citizen(homeBase, startPoint);
	    cit.setCollect(CollectableKey.TREE);

	    Citizen cit2 = new Citizen(homeBase, startPoint2);
	    cit2.setCollect(CollectableKey.TREE);

	    System.out.println("Add Citizen at X: " + actualCenter.getX() + ", Y: " + actualCenter.getY() + " with home X: 0, Y: 0");
	    gameField.addObject(cit);
	    gameField.addObject(cit2);
	    fieldMap.get(startPoint.getY()).put(startPoint.getX(), "C");
	    addToGrid(startPoint, "C");
	    fieldMap.get(startPoint2.getY()).put(startPoint2.getX(), "C");
	    addToGrid(startPoint2, "C");

	    System.out.println("####################### FIELD #######################");
	    String field = "";
	    String firstAndLastRow = "--";
	    for (Entry<Integer, Map<Integer, String>> entry : fieldMap.entrySet()) {
		field += "|";
		for (Entry<Integer, String> entry2 : entry.getValue().entrySet()) {
		    field += entry2.getValue();
		    if (firstAndLastRow.length() <= entry.getValue().size() + 2) {
			firstAndLastRow += "-";
		    }
		}
		field += "|\n";
	    }

	    printGrid();
	    System.out.println(firstAndLastRow + "\n" + field + firstAndLastRow);
	    System.out.println("####################################################");

	    System.out.println("####################################################");

	    int timer = 0;

	    while (true) {
//		cit.execute();
//		cit2.execute();
		homeBase.execute();

		if (timer >= 100) {
		    homeBase.showCollected();
		    timer = 0;
		}
		timer++;
	    }

	} catch (Exception e) {
	    System.out.println("Damn an error occured");
	    e.printStackTrace();
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

    private void addToGrid(Coordinate coord, String value) {
	addToGrid(coord.getY(), coord.getX(), value);
    }

    private void addToGrid(int y, int x, String value) {
	stringArray[y][x] = value;
    }

    public void printGrid() {
	String firstAndLastRow = "";
	for (int i = 0; i < GAME_FIELD_WIDTH + 2; i++) {
	    firstAndLastRow += "-";
	}
	System.out.println(firstAndLastRow);
	for (String[] y : stringArray) {
	    System.out.printf("|");
	    for (String x : y) {
		System.out.printf(x != null ? x : " ");
	    }
	    System.out.println("|");
	}
	System.out.println(firstAndLastRow);
    }

    public void printGridToFile() {

	OutputStreamWriter output = null;
	try {
	    output = new OutputStreamWriter(new FileOutputStream(new File(filePath)));
	    String firstAndLastRow = "";
	    for (int i = 0; i < GAME_FIELD_WIDTH + 2; i++) {
		firstAndLastRow += "-";
	    }
	    output.append(firstAndLastRow);
	    output.append("\n");
	    for (String[] y : stringArray) {
		output.append("|");
		for (String x : y) {
		    output.append(x != null ? x : " ");
		}
		output.append("|\n");
	    }
	    output.append(firstAndLastRow);
	} catch (IOException e) {
	    System.out.println("IOException for " + filePath);
	    e.printStackTrace();
	} finally {
	    try {
		if (output != null) {
		    output.close();
		}
	    } catch (IOException e) {
		System.out.println("IOException for " + filePath);
		e.printStackTrace();
	    }
	}

    }

}
