package game.base;

import game.application;
import java.util.List;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public abstract class MovableObject extends TeamObject {

    private AbstractCoordinateSet coordinateSet;

    private int steps = 0;

    protected List<? extends PositionObject> usableObjects;

    public MovableObject(Team team, Coordinate home, int velocity, Coordinate center, int width, int height) {
	super(team, center, home, width, height);
	this.velocity = velocity;
    }

    private int velocity;

    public int getVelocity() {
	return velocity;
    }

    public void setVelocity(int velocity) {
	this.velocity = velocity;
    }

    public void moveTo(Coordinate movingTo) throws Exception {

	Pair<Integer, Integer> borders = application.getGameField().getBorders();
	if (movingTo.getX() <= borders.getKey() && movingTo.getX() >= 0 && movingTo.getY() <= borders.getValue() && movingTo.getY() >= 0) {
	    setCenter(movingTo);
	    updateCoordinateSet();
	    steps++;
	}
    }

    public AbstractCoordinateSet getCoordinateSet() {
	return coordinateSet;
    }

    public void setCoordinateSet(AbstractCoordinateSet coordinateSet) {
	this.coordinateSet = coordinateSet;
    }

    public PositionObject findNextObject() throws Exception {
	PositionObject found = null;
	if (steps <= 1000) {
	    found = coordinateSet.findNextObject(getCenter(), application.getGameField());
	    if (found != null) {
		collect(found);
	    }
	}

	return found;
    }

    private void updateCoordinateSet() {
	coordinateSet.updateCoordinateSet(getCenter());
    }

    protected abstract void collect(PositionObject object) throws Exception;

    public void goHome() throws Exception {
	if (!getCenter().equals(getHome())) {
	    int newX = getCenter().getX();
	    int newY = getCenter().getY();
	    int deltaX = getCenter().getX() - getHome().getX();
	    int deltaY = getCenter().getY() - getHome().getY();

	    if (Math.abs(deltaX) >= Math.abs(deltaY)) {
		if (deltaX >= 0) {
		    newX -= velocity;
		} else {
		    newX += velocity;
		}
	    } else {
		if (deltaY >= 0) {
		    newY -= velocity;
		} else {
		    newY += velocity;
		}
	    }

	    moveTo(new Coordinate(newX, newY));

	}
    }

    public void moveForward() throws Exception {
	int newX = getCenter().getX();
	int newY = getCenter().getY();

	Pair<Integer, Integer> borders = application.getGameField().getBorders();

	int temp = new Random().nextInt(15) % 8;

	int addToX = 0;
	int addToY = 0;

	switch (temp) {
	    case 0:
		addToX = -1;
		addToY = 1;
		break;
	    case 1:
		addToX = -1;
		addToY = 0;
		break;
	    case 2:
		addToX = -1;
		addToY = -1;
		break;
	    case 3:
		addToX = 0;
		addToY = 1;
		break;
	    case 4:
		addToX = 0;
		addToY = -1;
		break;
	    case 5:
		addToX = 1;
		addToY = 1;
		break;
	    case 6:
		addToX = 1;
		addToY = 0;
		break;
	    case 7:
		addToX = 1;
		addToY = -1;
		break;
	    default:
		addToX = 0;
		addToY = 1;
		break;
	}

	int tempX = newX + addToX;
	int tempY = newY + addToY;

	if (tempX <= borders.getKey() || tempX >= 0) {
	    newX = tempX;
	} else {
	    newX -= addToX;
	}
	if (tempY <= borders.getValue() || tempY >= 0) {
	    newY = tempY;
	} else {
	    newY -= addToY;
	}

	moveTo(new Coordinate(newX, newY));
    }

    public boolean isAtHome() {
	int deltaX = getCenter().getX() - getHome().getX();
	int deltaY = getCenter().getY() - getHome().getY();

	return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1;
    }

    @Override
    public String toString() {
	return "MovableObject{"
		+ toStringContent()
		+ '}';
    }

    @Override
    public String toStringContent() {
	return "velocity=" + velocity
		+ (coordinateSet != null ? coordinateSet + ", " : "")
		+ "steps=" + steps
		+ (usableObjects != null ? usableObjects + ", " : "")
		+ super.toStringContent();
    }

}
