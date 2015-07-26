package game.base;

/**
 *
 * @author amohamed
 */
public class Coordinate implements Comparable {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
	super();
	this.x = x;
	this.y = y;
    }

    public Coordinate(int x, int y, int offSetX, int offSetY) {
	super();
	this.x = x + offSetX;
	this.y = y + offSetY;
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Coordinate other = (Coordinate) obj;
	if (this.x != other.x) {
	    return false;
	}
	if (this.y != other.y) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 19 * hash + this.x;
	hash = 19 * hash + this.y;
	return hash;
    }

    @Override
    public int compareTo(Object o) {
	Coordinate other = (Coordinate) o;
	if (new Integer(this.getX()).equals(other.getX())) {
	    return new Integer(this.getY()).compareTo(other.getY());
	}
	return new Integer(this.getX()).compareTo(other.getX());
    }

}
