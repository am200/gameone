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

/**
 *
 * @author amohamed
 */
public class GameData implements Runnable{
//    /**
//     * static logger-object
//     */
////    private static final Logger LOGGER = Logger.getLogger(application.class);

    private static GameField gameField;
    private Map<Integer, Map<Integer, String>> fieldMap;
    public static final int GAME_FIELD_WIDTH = 10;
    public static final int GAME_FIELD_HEIGHT = 10;
    public static final int MAXIMUM_STEPS = 1000;
    public static final int MAXIMUM_LOOP_COUNTER = 10000;
    public static final int TEMPORARY_COUNTER = 100;

    private final static String filePath = "/Users/amohamed/Desktop/output.txt";

    private static boolean gameRunning = true;
    private static long lastFpsTime = 0L;

    private static long fps = 0L;

    private HomeBase homeBase;

    public static DevelopmentState getStartDevelopmentState() {
	return DevelopmentState.STATE_1;
    }

    private String[][] stringArray;

    public static GameField getGameField() {
	return gameField;
    }

    public void init() {
	try {
	    gameField = new GameField(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

	    stringArray = new String[GAME_FIELD_HEIGHT + 1][GAME_FIELD_WIDTH + 1];
	    fieldMap = new HashMap<>();

	    initTrees();

	    System.out.println("####################################################");

	    Team team = new Team("1234", "MyTeam", "Blue");

	    System.out.println("Add Team " + team);

	    Coordinate actualCenter = findEmptyCoordinate();

	    homeBase = new HomeBase(actualCenter, team, ProductionMode.AUTO);
	    fieldMap.get(actualCenter.getY()).put(actualCenter.getX(), "B");
	    addToGrid(actualCenter, "B");

	    Coordinate startPoint = new Coordinate(actualCenter.getX(), actualCenter.getY(), 1, 0);

	    addCitizen(startPoint);

	    Coordinate startPoint2 = new Coordinate(actualCenter.getX(), actualCenter.getY(), 0, 1);

	    addCitizen(startPoint2);

	    initGrid();
	} catch (Exception e) {
	    System.out.println("Damn an error occured");
	    e.printStackTrace();
	}
    }

    public void startGame() {
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	// keep looping round til the game ends
	while (gameRunning) {
	    // work out how long its been since the last update, this
	    // will be used to calculate how far the entities should
	    // move this loop
	    long now = System.nanoTime();
	    long updateLength = now - lastLoopTime;
	    lastLoopTime = now;
	    double delta = updateLength / ((double) OPTIMAL_TIME);

	    // update the frame counter
	    lastFpsTime += updateLength;
	    fps++;

	    // update our FPS counter if a second has passed since
	    // we last recorded
	    if (lastFpsTime >= 1000000000) {
		System.out.println("(FPS: " + fps + ")");
		homeBase.showCollected();
		lastFpsTime = 0;
		fps = 0;
	    }

	    // update the game logic
	    doGameUpdates(delta);

	    // draw everyting
	    render();

	    // we want each frame to take 10 milliseconds, to do this
	    // we've recorded when we started the frame. We add 10 milliseconds
	    // to this and then factor in the current time to give 
	    // us our final value to wait for
	    // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
	    try {
		long timeToSleep = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
		Thread.sleep(timeToSleep < 0 ? 0 : timeToSleep);
	    } catch (InterruptedException e) {

	    }
	}
    }

    private void doGameUpdates(double delta) {
	homeBase.execute();
//	for (int i = 0; i < stuff.size(); i++) {
//	    // all time-related values must be multiplied by delta!
//	    Stuff s = stuff.get(i);
//	    s.velocity += Gravity.VELOCITY * delta;
//	    s.position += s.velocity * delta;
//
//	    // stuff that isn't time-related doesn't care about delta...
//	    if (s.velocity >= 1000) {
//		s.color = Color.RED;
//	    } else {
//		s.color = Color.BLUE;
//	    }
//	}
    }

    private void render() {
	// render
    }

    private void addToGrid(Coordinate coord, String value) {
	addToGrid(coord.getY(), coord.getX(), value);
    }

    private void addToGrid(int y, int x, String value) {
	stringArray[y][x] = value;
    }

    private void initGrid() {
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

    private void initTrees() {
	Random rand = new Random();
	int treeCounter = rand.nextInt((GAME_FIELD_WIDTH * GAME_FIELD_HEIGHT) / 2) + 10;

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

	    Coordinate treeCoordinate = findEmptyCoordinate();

	    Tree tree = new Tree(treeCoordinate, 1, 1);

	    treeCoorinates.put(treeCoordinate, tree);
	    gameField.addObject(tree);
	    fieldMap.get(treeCoordinate.getY()).put(treeCoordinate.getX(), "*");
	    addToGrid(treeCoordinate, "*");
	}

	int treeCounting = 1;
	for (Coordinate coord : treeCoorinates.keySet()) {
	    System.out.println("Add Tree nr " + treeCounting + " at X: " + coord.getX() + ", Y: " + coord.getY());
	    treeCounting++;
	}
    }

    private void addCitizen(Coordinate startPoint) {
	Citizen cit = new Citizen(homeBase, startPoint);
	cit.setCollect(CollectableKey.TREE);

	System.out.println("Add Citizen at X: " + startPoint.getX() + ", Y: " + startPoint.getY() + " with home X: 0, Y: 0");
	gameField.addObject(cit);
	fieldMap.get(startPoint.getY()).put(startPoint.getX(), "C");
	addToGrid(startPoint, "C");
    }

    private Coordinate findEmptyCoordinate() {
	Random rand = new Random();
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
	return actualCenter;
    }

    @Override
    public void run() {
	startGame();
    }

}
