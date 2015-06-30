package game;

import game.base.Citizen;
import game.base.CollectableKey;
import game.base.Coordinate;
import game.base.GameField;
import game.base.HomeBase;
import game.base.Team;
import game.base.TreeObject;
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

    public static GameField getGameField() {
	return gameField;
    }

    @Override
    public void start(Stage primaryStage) {
	try {
	    gameField = new GameField(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

	    Random rand = new Random();
	    int treeCounter = rand.nextInt((GAME_FIELD_WIDTH * GAME_FIELD_HEIGHT) / 3);

	    Map<Integer, Map<Integer, String>> fieldMap = new HashMap<>();

	    for (int line = 0; line <= GAME_FIELD_HEIGHT; line++) {
		Map<Integer, String> row = new HashMap<>();
		for (int x = 0; x <= GAME_FIELD_WIDTH; x++) {
		    row.put(x, " ");
		}
		fieldMap.put(line, row);
	    }

	    System.out.println("################## Start the game #################");
	    System.out.println("####################################################");
	    Map<Coordinate, TreeObject> treeCoorinates = new TreeMap<>();
	    for (int i = 0; i < treeCounter; i++) {

		int randX = rand.nextInt(GAME_FIELD_WIDTH);
		int randY = rand.nextInt(GAME_FIELD_HEIGHT);
		boolean emptyField = false;
		int tempCounter = 0;
		Coordinate actualCenter = new Coordinate(randX, randY);
		while (!emptyField) {
		    if (gameField.getObjectByCooridante(actualCenter) != null) {
			randX = rand.nextInt(GAME_FIELD_WIDTH);
			randY = rand.nextInt(GAME_FIELD_HEIGHT);
			actualCenter = new Coordinate(randX, randY);
		    } else {
			emptyField = true;
			break;
		    }
		    if (tempCounter >= 100) {
			break;
		    }
		}

		Coordinate treeCoordinate = new Coordinate(randX, randY);
		TreeObject tree = new TreeObject(treeCoordinate, 1, 1);

		treeCoorinates.put(treeCoordinate, tree);
		gameField.addObject(tree);
		fieldMap.get(randY).put(randX, "*");
	    }

	    int treeCounting = 1;
	    for (Coordinate coord : treeCoorinates.keySet()) {
		System.out.println("Add Tree nr " + treeCounting + " at X: " + coord.getX() + ", Y: " + coord.getY());
		treeCounting++;
	    }

	    int randX = rand.nextInt(GAME_FIELD_WIDTH);
	    int randY = rand.nextInt(GAME_FIELD_HEIGHT);
	    boolean emptyField = false;
	    int tempCounter = 0;
	    Coordinate actualCenter = new Coordinate(randX, randY);
	    while (!emptyField) {
		if (gameField.getObjectByCooridante(actualCenter) != null) {
		    randX = rand.nextInt(GAME_FIELD_WIDTH);
		    randY = rand.nextInt(GAME_FIELD_HEIGHT);
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

	    HomeBase homeBase = new HomeBase(actualCenter);
	    fieldMap.get(actualCenter.getY()).put(actualCenter.getX(), "B");
	    Coordinate startPoint = new Coordinate(actualCenter.getX() + 1, actualCenter.getY());

	    Citizen cit = new Citizen(team, actualCenter, startPoint);

	    System.out.println("Add Citizen at X: " + actualCenter.getX() + ", Y: " + actualCenter.getY() + " with home X: 0, Y: 0");
	    gameField.addObject(cit);
	    fieldMap.get(startPoint.getY()).put(startPoint.getX(), "C");

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
	    System.out.println(firstAndLastRow + "\n" + field + firstAndLastRow);
	    System.out.println("####################################################");

	    System.out.println("####################################################");

	    int steps = 0;
	    int collected = 0;
	    int loopCounter = 0;

	    while (steps < 1000) {
		if (cit.getTotalCollected() >= 300) {
		    cit.goHome();
		    if (cit.isAtHome()) {
			System.out.println("Citizen " + cit.getId() + " is at home");
			homeBase.addToCollectable(cit);
			collected = homeBase.getCollectedPoints(CollectableKey.TREE);
		    }
		} else {
		    cit.findNextObject();
		    if (loopCounter >= 1000) {
			break;
		    }
		    cit.moveForward();
		}

		steps++;
		loopCounter++;
	    }

	    System.out.println("####################################################");

	    System.out.println("Made " + steps + " steps");
	    System.out.println("Collected " + collected + " tree parts");
	    System.out.println("Break counter is " + loopCounter);

	    System.out.println("####################################################");

//	Button btn = new Button();
//	btn.setText("Say 'Hello World'");
//	btn.setOnAction(new EventHandler<ActionEvent>() {
//
//	    @Override
//	    public void handle(ActionEvent event) {
//		System.out.println("Hello World!");
//	    }
//	});
//	
//	Pane pane = new Pane();
//	
//	StackPane root = new StackPane();
//	root.getChildren().add(pane);
////	root.getChildren().add(btn);
//
//	Scene scene = new Scene(root, GAME_FIELD_WIDTH+50, GAME_FIELD_HEIGHT+50);
//
//	primaryStage.setTitle("Hello World!");
//	primaryStage.setScene(scene);
//	primaryStage.show();
	} catch (Exception e) {
	    System.out.println("Damn an error occured");
	    for (StackTraceElement ele : e.getStackTrace()) {
		System.out.println(ele.toString());
	    }
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

}
