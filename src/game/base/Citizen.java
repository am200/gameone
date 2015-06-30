package game.base;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author amohamed
 */
public class Citizen extends MovableObject {

    private Map<CollectableKey, CollectableObject> collectableMap;

    public Citizen(Team team, Coordinate home, Coordinate center) {
	super(team, home, 1, center, 3, 3);
	setCoordinateSet(new CoordinateSetFactory<>().getCoordinateSet(this));
	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new TreeCollectable(0));
    }

    private int strength = 4;

    @Override
    protected void collect(PositionObject object) throws Exception {
	if (object.getId().startsWith("TreeObject")) {
	    TreeCollectable treeCollectable = (TreeCollectable) collectableMap.get(CollectableKey.TREE);
	    TreeObject treeObj = (TreeObject) object;
	    treeCollectable.getPoints();
	    treeCollectable.collect(strength, treeObj);

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
	for (CollectableKey key : CollectableKey.values()) {
	    result += collectableMap.containsKey(key) ? collectableMap.get(key).getPoints() : 0;
	}
	return result;
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
