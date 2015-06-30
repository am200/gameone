package game.base;

/**
 *
 * @author amohamed
 */
public class Citizen extends MovableObject {

    private TreeCollectable treeCollectable;

    public Citizen(Team team, Coordinate home, Coordinate center) {
	super(team, home, 1, center, 3, 3);
	setCoordinateSet(new CoordinateSetFactory<>().getCoordinateSet(this));
	treeCollectable = new TreeCollectable(0);
    }

    private int strength = 4;

    @Override
    protected void collect(PositionObject object) throws Exception {
	if (object.getId().startsWith("TreeObject")) {

	    TreeObject treeObj = (TreeObject) object;
	    this.treeCollectable.getPoints();
	    treeCollectable.collect(strength, treeObj);

	}
    }

    public int getCollected() throws Exception {
	return treeCollectable.getPoints();
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
		+ (treeCollectable != null ? "treeCollectable=" + treeCollectable + ", " : "")
		+ super.toStringContent();
    }

}
