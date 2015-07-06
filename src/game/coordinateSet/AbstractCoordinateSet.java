package game.coordinateSet;

import game.application;
import game.base.Coordinate;
import game.terrain.GameField;
import game.base.PositionObject;
import game.collectable.util.CollectableKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public abstract class AbstractCoordinateSet {

    private Map<String, Coordinate> coordinateSet;
    private Coordinate center;

    public AbstractCoordinateSet(Coordinate center) {
	super();
	this.center = center;
	this.coordinateSet = new HashMap<>();
	setCoordinateSet();
    }

    private void setCoordinateSet() {

	Pair<Integer, Integer> borders = application.getGameField().getBorders();
	for (RangeSet range : defineCoordinateRange()) {
	    Coordinate coord = calcNewPosition(range.getX(), range.getY());
	    if (coord.getX() <= borders.getKey() && coord.getX() >= 0 && coord.getY() <= borders.getValue() && coord.getY() >= 0) {
		coordinateSet.put(getCoordinatesAsString(coord), coord);
	    }
	}
	coordinateSet.put(getCoordinatesAsString(center), center);
    }

    public void updateCoordinateSet(Coordinate newCenter) {
	this.center = newCenter;
	coordinateSet.clear();
	setCoordinateSet();
    }

    private Coordinate calcNewPosition(int x, int y) {
	return new Coordinate(center.getX() + x, center.getY() + y);
    }

    private String getCoordinatesAsString(Coordinate coord) {
	return coord.getX() + "_" + coord.getY();
    }

    protected abstract RangeSet[] defineCoordinateRange();

    public PositionObject findNextObject(Coordinate objectCenter, GameField gameField) {
	updateCoordinateSet(objectCenter);
	for (Entry<String, Coordinate> entry : coordinateSet.entrySet()) {
	    PositionObject temp = gameField.getObjectByCooridante(entry.getValue());
	    if (temp != null) {
		return temp;
	    }
	}
	return null;
    }
    
    public PositionObject findNextObject(Coordinate objectCenter, CollectableKey key, GameField gameField) {
	updateCoordinateSet(objectCenter);
	for (Entry<String, Coordinate> entry : coordinateSet.entrySet()) {
	    PositionObject temp = gameField.getObjectByCooridante(entry.getValue());
	    
	    if (temp != null && temp.getIdPrefix().toLowerCase().contains(key.name().toLowerCase())) {
		return temp;
	    }
	}
	return null;
    }

}
