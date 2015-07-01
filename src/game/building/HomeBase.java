package game.base;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author amohamed
 */
public class HomeBase extends HealthObject {

    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;

    private Map<CollectableKey, TreeCollectable> collectableMap;

    public HomeBase(Coordinate center) {
	super(center, WIDTH, HEIGHT);
	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new TreeCollectable(0, 1000));
    }

    public int getCollectableMaximum(CollectableKey key) {
	return collectableMap.containsKey(key) ? collectableMap.get(key).getPoints() : 0;
    }

    public void addToCollectable(Citizen citizen) {
	for (CollectableKey key : CollectableKey.values()) {
	    CollectableObject collectable = citizen.getCollected(key);
	    if (!collectableMap.containsKey(key)) {
		continue;
	    }
	    collectableMap.get(key).addPoints(collectable.getPoints());
	    collectable.clearPoints();
	}
    }

    public CollectableObject getCollected(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey);
    }

    public int getCollectedPoints(CollectableKey collectableKey) {
	return collectableMap.get(collectableKey).getPoints();
    }

    public int getTotalCollectableMaximum() {
	int result = 0;
	for (CollectableObject collectable : collectableMap.values()) {
	    result += collectable.getPoints();
	}
	return result;
    }
}
