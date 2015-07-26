package game.base;

import game.coordinateSet.AbstractCoordinateSet;
import game.building.HomeBase;
import game.GameData;
import game.unit.Unit;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public abstract class MovableObject extends TeamObject implements Runnable {

    private AbstractCoordinateSet coordinateSet;

    private Thread actualThread;

    private final static int DEFAULT_TIME = 1000;

    public MovableObject(HomeBase home, int velocity, Coordinate center, int width, int height) {
	super(home, center, width, height);
	this.velocity = velocity;
	addToHomeBase();
    }

    private int velocity;
    private boolean wait;
    private int waitTime;

    public int getVelocity() {
	return velocity;
    }

    public void setVelocity(int velocity) {
	this.velocity = velocity;
    }

    public void moveTo(Coordinate movingTo) {

	Pair<Integer, Integer> borders = GameData.getGameField().getBorders();
	if (movingTo.getX() <= borders.getKey() && movingTo.getX() >= 0 && movingTo.getY() <= borders.getValue() && movingTo.getY() >= 0) {
	    setCenter(movingTo);
	    updateCoordinateSet();
	}
    }

    public AbstractCoordinateSet getCoordinateSet() {
	return coordinateSet;
    }

    public void setCoordinateSet(AbstractCoordinateSet coordinateSet) {
	this.coordinateSet = coordinateSet;
    }

    private void updateCoordinateSet() {
	coordinateSet.updateCoordinateSet(getCenter());
    }

    public void goHome() {
	if (!getCenter().equals(getHome().getCenter())) {
	    int newX = getCenter().getX();
	    int newY = getCenter().getY();
	    int deltaX = getCenter().getX() - getHomeCoordinate().getX();
	    int deltaY = getCenter().getY() - getHomeCoordinate().getY();

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

    public void moveForward() {
	int newX = getCenter().getX();
	int newY = getCenter().getY();

	Pair<Integer, Integer> borders = GameData.getGameField().getBorders();

	int temp = new Random().nextInt(15) % 8;

	int addToX;
	int addToY;

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
	int deltaX = getCenter().getX() - getHomeCoordinate().getX();
	int deltaY = getCenter().getY() - getHomeCoordinate().getY();
	return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1;
    }

    @Override
    public void run() {
	try {
	    if (hasToWait()) {
		yield();
	    } else {
		actualThread.interrupt();
		execute();
	    }
	    repaint();

	} catch (Exception e) {
	    System.out.println("Exception occured at run of " + getIdPrefix() + " with id " + getId());
	    e.printStackTrace();
	}
    }

    public boolean hasToWait() {
	return this.wait;
    }

    public void setToWait(boolean wait) {
	this.wait = wait;
    }

    public void waitFor() {
	this.waitFor(DEFAULT_TIME);
	setToWait(true);
    }

    public void waitFor(int time) {
	this.waitTime = time;
	setToWait(true);
	try {
	    actualThread.sleep(waitTime);
	} catch (InterruptedException e) {
	    System.out.println("InterruptedException at sleep");
	    e.printStackTrace();
	}
    }

    public abstract void execute();

    public void yield() {
	getHome().showCollected();
	waitFor(waitTime);
    }

    public abstract void repaint();

    protected abstract Unit getUnit();

    public void setThread(Thread actualThread) {
	this.actualThread = actualThread;
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
		+ super.toStringContent();
    }

    private void addToHomeBase() {
	getHome().addUnitToBuilding(getUnit(), this);
    }

}
