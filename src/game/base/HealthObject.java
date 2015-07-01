package game.base;

import game.application;

/**
 *
 * @author amohamed
 */
public class HealthObject extends PositionObject {

    private int health;

    public HealthObject(Coordinate center, int width, int height) {
	super(center, width, height);
	this.health = GameUtil.MAXIMUM_POINTS;
    }

    public int getHealth() {
	return health;
    }

    public void setHealth(int health) {
	this.health = health;
    }

    public void increaseHealth() {
	changeHealthByValue(1);
    }

    public void decreaseHealth() {
	changeHealthByValue(1);
    }

    public void increaseHealthByValue(int value) {
	if (value < 0) {
	    changeHealthByValue(-value);
	} else {
	    changeHealthByValue(value);
	}
    }

    public int decreaseHealthByValue(int value) {
	if (value >= 0) {
	    changeHealthByValue(-value);
	} else {
	    changeHealthByValue(value);
	}
	if (health < 0) {
	    return value + health;
	} else {
	    return value;
	}
    }

    public void changeHealthByValue(int value) {
	health += value;
    }

    protected void checkForHitPoints() {
	if (health <= 0) {
	    System.out.println(this.getId() + " died");
	    application.getGameField().removeObject(this.getId());
	}
    }

    @Override
    public String toString() {
	return "HealthObject{" + toStringContent() + '}';
    }

    @Override
    protected String toStringContent() {
	return "health=" + health + ", "
		+ super.toStringContent();
    }

}
