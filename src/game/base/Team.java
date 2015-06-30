package game.base;

import java.util.Objects;

/**
 *
 * @author amohamed
 */
public class Team extends AbstractBaseObject {

    private String name;

    private String color;

    private String actualPoints;

    private String image;

    public Team(String id, String name, String color) {
	super();
    }

    public Team(String id, String name, String color, String image) {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    public String getActualPoints() {
	return actualPoints;
    }

    public void setActualPoints(String actualPoints) {
	this.actualPoints = actualPoints;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public void increasePointsByCollectable(CollectableObject collect) {
	changePointsByValue(collect.getPoints());
    }

    public void increasePointsByValue(int value) {
	if (value < 0) {
	    changePointsByValue(-value);
	} else {
	    changePointsByValue(value);
	}
    }

    public void decreasePointsByValue(int value) {
	if (value >= 0) {
	    changePointsByValue(-value);
	} else {
	    changePointsByValue(value);
	}
    }

    public void changePointsByValue(int value) {
	actualPoints += value;
    }

    @Override
    public String toString() {
	return "Team{" + "name=" + name + ", color=" + color + ", actualPoints=" + actualPoints + ", image=" + image + '}';
    }

    @Override
    public void generateId() {
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash += super.hashCode();
	hash = 17 * hash + Objects.hashCode(this.name);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Team other = (Team) obj;
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	return true;
    }

}
