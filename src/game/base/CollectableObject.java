package game.base;

/**
 *
 * @author amohamed
 */
public class CollectableObject {

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

    @Override
    public String toString() {
	return "CollectableObject{" + toStringContent() + '}';
    }

    public String toStringContent() {
	return "points=" + points;
    }
}
