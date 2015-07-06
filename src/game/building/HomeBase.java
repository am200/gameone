package game.building;

import game.collectable.util.CollectableKey;
import game.collectable.CollectableObject;
import game.base.Coordinate;
import game.base.Team;
import game.unit.Citizen;
import game.unit.Unit;
import game.util.ProductionMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author amohamed
 */
public class HomeBase extends Building {

    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;

    private static ProductionMode mode;

    private final Map<CollectableKey, CollectableObject> collectableMap;

    public HomeBase(Coordinate center, Team team, ProductionMode mode) {
	super(team, center, WIDTH, HEIGHT);
	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new CollectableObject(0, 1000, CollectableKey.TREE));
	this.mode = mode;
    }

    public int getCollectableMaximum(CollectableKey key) {
	return collectableMap.containsKey(key) ? collectableMap.get(key).getMaximum(): 0;
    }

    public void addToCollectable(Citizen citizen) {
	CollectableObject collectable = citizen.getCollected();
	if (collectableMap.containsKey(collectable.getKey())) {
	    collectableMap.get(collectable.getKey()).addPoints(collectable.getPoints());
	    collectable.clearPoints();
	}
    }

    public CollectableObject getCollected(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey);
    }

    public int getCollectedPoints(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey).getPoints();
    }

    public int getCollectableDifference(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey).getPointDifference();
    }

    public int getTotalCollectableMaximum() {
	int result = 0;
	for (CollectableObject collectable : collectableMap.values()) {
	    result += collectable.getPoints();
	}
	return result;
    }

    @Override
    protected Set<Unit> addProduceAbleUnits() {
	return new HashSet<Unit>() {
	    private static final long serialVersionUID = 3109256773218160485L;

	    {
		add(Unit.CITIZEN);
	    }
	};
    }

    public void showCollected() {
	System.out.println("####################################################");
	for (CollectableKey key : collectableMap.keySet()) {
	    int points = getCollectedPoints(key);
	    if (points >= getCollectableMaximum(key)) {
		produce();
	    }
	    System.out.println("Collected " + points + " tree parts");
	}
	System.out.println("####################################################");
    }

    public void produce() {
	if (ProductionMode.AUTO.equals(mode)) {
	    produceUnit(Unit.CITIZEN);
	}
    }

}
