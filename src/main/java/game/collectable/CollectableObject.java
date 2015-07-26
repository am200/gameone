package game.collectable;

import game.base.HealthObject;
import game.collectable.util.CollectableKey;

/**
 *
 * @author amohamed
 */
public class CollectableObject {

    private int points;
    private int maximum;
    private final CollectableKey key;

    public CollectableObject(int points, int maximum, CollectableKey key) {
	this.points = points;
	this.maximum = maximum;
	this.key = key;
    }

    public CollectableKey getKey() {
	return key;
    }

    public int getPoints() {
	return points;
    }

    protected void addToPoints(int hit) {
	this.points += hit;
    }

    public void clearPoints() {
	this.points = 0;
    }

    public void setMaximum(int maximum) {
	this.maximum = maximum;
    }

    public int getMaximum() {
	return maximum;
    }

    public int getPointDifference() {
	int diff = maximum - points;
	int result = diff > 0 ? Math.abs(diff) : 0;
	return result;
    }

    public void addPoints(int points) {
	addToPoints(points);
    }

    public void collect(int hit, HealthObject object) {

	while (getPoints() < maximum && object.getHealth() > 0) {
	    object.decreaseHealthByValue(hit);
	    addToPoints(hit);
	    object.checkForHitPoints();
	    if (getPoints() >= maximum || object.getHealth() <= 0) {
		break;
	    }
	}
    }

    @Override
    public String toString() {
	return "CollectableObject{" + toStringContent() + '}';
    }

    public String toStringContent() {
	return "points=" + points;
    }

}
