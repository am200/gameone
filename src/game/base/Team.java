package game.base;

import game.application;
import game.collectable.CollectableObject;
import game.collectable.util.CollectableKey;
import game.util.DevelopmentState;
import game.util.GameUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author amohamed
 */
public class Team extends AbstractBaseObject {

    private String name;

    private String color;

    private String actualPoints;

    private String image;

    private DevelopmentState actualDevelopmentState;

    private final Map<CollectableKey, CollectableObject> collectableMap;

    public Team(String id, String name, String color) {
	super();
	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new CollectableObject(0, 1000, CollectableKey.TREE));
	this.actualDevelopmentState = application.getStartDevelopmentState();
    }

    public Team(String id, String name, String color, String image) {
	super();

	collectableMap = new HashMap<>();
	collectableMap.put(CollectableKey.TREE, new CollectableObject(0, 1000, CollectableKey.TREE));
    }

    public int getCollectableMaximum(CollectableKey key) {
	return collectableMap.containsKey(key) ? collectableMap.get(key).getMaximum() : 0;
    }

    public CollectableObject getCollected(CollectableKey key) {
	return collectableMap.containsKey(key) ? collectableMap.get(key) : null;
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

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    public String getActualPoints() {
	return actualPoints;
    }

    public void setActualPoints(String actualPoints) {
	this.actualPoints = actualPoints;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public void increasePointsByCollectable(CollectableObject collect) {
	changePointsByValue(collect.getPoints());
    }

    public void increasePointsByValue(int value) {
	if (value < 0) {
	    changePointsByValue(-value);
	} else {
	    changePointsByValue(value);
	}
    }

    public void decreasePointsByValue(int value) {
	if (value >= 0) {
	    changePointsByValue(-value);
	} else {
	    changePointsByValue(value);
	}
    }

    public void changePointsByValue(int value) {
	actualPoints += value;
    }

    @Override
    public String toString() {
	return "Team{" + "name=" + name + ", color=" + color + ", actualPoints=" + actualPoints + ", image=" + image + '}';
    }

    @Override
    public void generateId() {
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash += super.hashCode();
	hash = 17 * hash + Objects.hashCode(this.name);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Team other = (Team) obj;
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	return true;
    }

    public boolean containsCollectable(CollectableKey key) {
	return collectableMap.containsKey(key);
    }

    public Map<CollectableKey, CollectableObject> getCollectableMap() {
	return collectableMap;
    }

    public void addPoints(CollectableKey key, int points) {
	if (!containsCollectable(key)) {
	    collectableMap.put(key, new CollectableObject(points, GameUtil.MAXIMUM_POINTS, key));
	} else {
	    CollectableObject collectable = getCollected(key);
	    collectable.addPoints(points);
	    collectableMap.put(key, collectable);
	}

    }

    public DevelopmentState getDevelopmentState() {
	return actualDevelopmentState;
    }

    public void increaseDevelopementState() {
	this.actualDevelopmentState = DevelopmentState.getNextState(actualDevelopmentState);
    }

}
