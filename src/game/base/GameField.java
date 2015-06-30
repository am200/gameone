package game.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public class GameField {

    private final int width;

    private final int height;

    private final Map<String, PositionObject> objects;

    private final Map<String, Map<String, Coordinate>> coordinateObjects;
    private final Map<Coordinate, String> coordinateIds;

    public GameField(int width, int height) {
	this.width = width;
	this.height = height;
	this.objects = new HashMap<>();
	this.coordinateObjects = new HashMap<>();
	this.coordinateIds = new HashMap<>();
    }

    public void addObject(PositionObject object) throws Exception {
	this.objects.put(object.getId(), object);
//	System.out.println("Object id " + object.getId() + " object : " + object);

	Map<String, Coordinate> coordinateMap = new HashMap<>();
	if (coordinateObjects.containsKey(object.getId())) {
	    coordinateMap = coordinateObjects.get(object.getId());
	}
	this.coordinateObjects.put(object.getId(), coordinateMap);

	if (!(object instanceof MovableObject)) {
	    coordinateIds.put(object.getCenter(), object.getId());
	}

    }

    public Pair<Integer, Integer> getBorders() {
	return new Pair<>(getWidth(), getHeight());
    }

    public void removeObject(String id) throws Exception {
	this.objects.remove(id);

	Map<Coordinate, String> result = new HashMap<>();
	for (Entry<Coordinate, String> coord : coordinateIds.entrySet()) {
	    if (!coord.getValue().equals(id)) {
		result.put(coord.getKey(), coord.getValue());
	    }
	}

	this.coordinateObjects.remove(id);

    }

    public PositionObject getObjectById(String id) throws Exception {
	if (objects.containsKey(id)) {
	    return objects.get(id);
	}
	return null;
    }

    public PositionObject getObjectByCooridante(Coordinate coord) throws Exception {
//	System.out.println("getObjectByCoordinate " + coord);
//	System.out.println("coordinateIds.containsKey(coord) " + coordinateIds.containsKey(coord));
//	System.out.println("coordinateIds.containsKey(coord) && objects.containsKey(coordinateIds.get(coord) " + (coordinateIds.containsKey(coord) && objects.containsKey(coordinateIds.get(coord))));
	if (coordinateIds.containsKey(coord) && objects.containsKey(coordinateIds.get(coord))) {
	    return objects.get(coordinateIds.get(coord));
	}
	return null;
    }

    public Integer getWidth() {
	return this.width;
    }

    public Integer getHeight() {
	return this.width;
    }

}
