package game.collectable;

import game.collectable.util.CollectableKey;
import game.base.TreeObject;

/**
 *
 * @author amohamed
 */
public class TreeCollectable extends CollectableObject {

    private int maximum = 300;

    public TreeCollectable(int points) {
	super(points);
    }

    public TreeCollectable(int points, int maximum) {
	super(points);
	this.maximum = maximum;
    }

    public void collect(int hit, TreeObject tree) {

	while (getPoints() < maximum && tree.getHealth() > 0) {
	    tree.decreaseHealthByValue(hit);
	    addToPoints(hit);
	    tree.checkForHitPoints();
	    if (getPoints() >= maximum || tree.getHealth() <= 0) {
		break;
	    }
	}
    }

    @Override
    public String toString() {
	return "TreeCollectable{" + toStringContent() + '}';
    }

    @Override
    public String toStringContent() {
	return super.toStringContent();
    }

    public void addPoints(int points) {
	addToPoints(points);
    }

    @Override
    public CollectableKey getKey() {
	return CollectableKey.TREE;
    }
}
