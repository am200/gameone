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

	while (getPoints() < MAXIMUM && tree.getHealth() > 0) {
	    tree.decreaseHealthByValue(hit);
	    addToPoints(hit);
	    tree.checkForHitPoints();
	    if (getPoints() >= MAXIMUM || tree.getHealth() <= 0) {
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
}
