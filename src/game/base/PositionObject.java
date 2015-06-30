package game.base;

/**
 *
 * @author amohamed
 */
public class PositionObject extends AbstractBaseObject {

    public PositionObject(Coordinate center, int width, int height) {
	super();
	this.center = center;
	this.width = width;
	this.height = height;

    }

    private int width;
    private int height;
    private Coordinate center;

    public Coordinate getCenter() {
	return center;
    }

    public void setCenter(Coordinate center) {
	this.center = center;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    @Override
    public String toString() {
	return "PositionObject{"
		+ toStringContent()
		+ '}';
    }

    @Override
    protected String toStringContent() {
	return "width=" + width + ", "
		+ "height=" + height + ", "
		+ (center != null ? "center=" + center : "")
		+ super.toStringContent();
    }

}
