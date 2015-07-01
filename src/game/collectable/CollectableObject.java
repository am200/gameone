package game.collectable;

import game.collectable.util.CollectableKey;

/**
 *
 * @author amohamed
 */
public abstract class CollectableObject {

    public abstract CollectableKey getKey();

    private int points;

    protected CollectableObject(int points) {
	this.points = points;
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

    @Override
    public String toString() {
	return "CollectableObject{" + toStringContent() + '}';
    }

    public String toStringContent() {
	return "points=" + points;
    }

}
