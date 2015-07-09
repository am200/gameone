package game.unit;

import game.application;
import game.collectable.util.CollectableKey;
import game.collectable.CollectableObject;
import game.base.Coordinate;
import game.base.HealthObject;
import game.coordinateSet.CoordinateSetFactory;
import game.base.MovableObject;
import game.building.Building;
import game.building.HomeBase;

/**
 *
 * @author amohamed
 */
public class Citizen extends MovableObject {

    private CollectableObject collectable;

    public Citizen(Building home, Coordinate center) {
	super((HomeBase) home, 1, center, 3, 3);
	setCoordinateSet(new CoordinateSetFactory<>().getCoordinateSet(this));
	System.out.println("New Citizen at " + center);
    }

    private final int MAXIMUM_WEARABLE = 100;

    private final int strength = 4;

    public void setCollect(CollectableKey key) {
	if (CollectableKey.TREE.equals(key)) {
	    collectable = new CollectableObject(0, MAXIMUM_WEARABLE, CollectableKey.TREE);
	}
    }

    protected void collect(HealthObject object) {
	if (object != null) {
	    collectable.collect(strength, object);
	}
    }

    public HealthObject findNextObject() {
	HealthObject found;
	found = (HealthObject) getCoordinateSet().findNextObject(getCenter(), collectable.getKey(), application.getGameField());
	if (found != null) {
	    collect(found);
	}
	return found;
    }

    public CollectableObject getCollected() {
	return collectable;
    }

    public int getCollectedPoints() {
	return collectable.getPoints();
    }

    public void deliverCollected() {
	if (isAtHome()) {
	    System.out.println(getIdPrefix() + " with id " + getId() + " arrived at home");
	    getHome().addToCollectable(this);
	} else {
	    goHome();
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
	return ("strength=" + strength+ ", ")
		+ (collectable != null ? "collectable=" + collectable + ", " : "")
		+ super.toStringContent();
    }

    @Override
    public void execute() {
	if (getCollected() == null) {
	    yield();
	    return;
	}
	if (getHome().getCollectableDifference(CollectableKey.TREE) > 0) {
	    if (getCollectedPoints() >= MAXIMUM_WEARABLE) {
		deliverCollected();
	    } else {
		findNextObject();
		moveForward();
	    }
	} else {
	    if (isAtHome()) {
		deliverCollected();
		yield();
	    } else {
		goHome();
	    }
	}
    }

    @Override
    public void repaint() {
	System.out.println("Repaint for " + getIdPrefix() + " with id " + getId());
    }

    @Override
    protected Unit getUnit() {
	return Unit.CITIZEN;
    }

}
