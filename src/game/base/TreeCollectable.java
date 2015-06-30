package game.base;

/**
 *
 * @author amohamed
 */
public class TreeCollectable extends CollectableObject {

    public TreeCollectable(int points) {
	super(points);
    }

    private static final int MAXIMUM = 300;

    public void collect(int hit, TreeObject tree) throws Exception {
	if (getPoints() < MAXIMUM) {
	    if (tree.getHealth() > 0) {
		tree.decreaseHealthByValue(hit);
		addToPoints(hit);

		tree.checkForHitPoints();
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
}
