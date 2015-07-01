package game.unit;

import game.collectable.util.CollectableKey;
import game.collectable.CollectableObject;
import game.base.Coordinate;
import game.coordinateSet.CoordinateSetFactory;
import game.building.HomeBase;
import game.base.MovableObject;
import game.base.PositionObject;
import game.base.Team;
import game.collectable.TreeCollectable;
import game.base.TreeObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author amohamed
 */
public class Citizen extends MovableObject {

    private Map<CollectableKey, CollectableObject> collectableMap;

    public Citizen(Team team, HomeBase home, Coordinate center) {
	super(team, home, 1, center, 3, 3);
	setCoordinateSet(new CoordinateSetFactory<>().getCoordinateSet(this));
	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new TreeCollectable(0));
    }

    private final static int MAXIMUM_WEARABLE = 300;

    private int strength = 4;

    @Override
    protected void collect(PositionObject object) {
	if (object != null) {
	    if (object.getId().startsWith("TreeObject")) {
		TreeCollectable treeCollectable = (TreeCollectable) collectableMap.get(CollectableKey.TREE);
		TreeObject treeObj = (TreeObject) object;
		treeCollectable.getPoints();
		treeCollectable.collect(strength, treeObj);

	    }
	}
    }

    public CollectableObject getCollected(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey);
    }

    public int getCollectedPoints(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey).getPoints();
    }

    public int getTotalCollected() {
	int result = 0;
	for (CollectableObject collectable : collectableMap.values()) {
	    result += collectable.getPoints();
	}
	return result;
    }

    public void startCollecting(CollectableKey key) {
	if (getTotalCollected() >= MAXIMUM_WEARABLE) {
	    goHome();
	    if (isAtHome()) {
		System.out.println("Citizen " + getId() + " is at home");
		getHome().addToCollectable(this);
	    }
	} else {
	    findNextObject();
	    moveForward();
	}
    }

    @Override
    public String toString() {
	return "Citizen{"
		+ toStringContent()
		+ '}';
    }

    @Override
    public String toStringContent() {
	return ("strength=" + strength)
		+ (collectableMap != null ? "collectableMap=" + collectableMap + ", " : "")
		+ super.toStringContent();
    }

}
