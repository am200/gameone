package game.base;

/**
 *
 * @author amohamed
 */
public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
	super();
	this.x = x;
	this.y = y;
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

}
