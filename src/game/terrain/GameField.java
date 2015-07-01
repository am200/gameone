package game.terrain;

import game.base.Coordinate;
import game.base.PositionObject;
import game.base.TreeObject;
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

    public void addObject(PositionObject object) {
	this.objects.put(object.getId(), object);
//	System.out.println("Object id " + object.getId() + " object : " + object);

	Map<String, Coordinate> coordinateMap = new HashMap<>();
	if (coordinateObjects.containsKey(object.getId())) {
	    coordinateMap = coordinateObjects.get(object.getId());
	}
	this.coordinateObjects.put(object.getId(), coordinateMap);

	if (object instanceof TreeObject) {
	    coordinateIds.put(object.getCenter(), object.getId());
	}

    }

    public Pair<Integer, Integer> getBorders() {
	return new Pair<>(getWidth(), getHeight());
    }

    public void removeObject(String id) {
	this.objects.remove(id);

	Map<Coordinate, String> result = new HashMap<>();
	for (Entry<Coordinate, String> coord : coordinateIds.entrySet()) {
	    if (!coord.getValue().equals(id)) {
		result.put(coord.getKey(), coord.getValue());
	    }
	}

	this.coordinateObjects.remove(id);

    }

    public PositionObject getObjectById(String id) {
	if (objects.containsKey(id)) {
	    return objects.get(id);
	}
	return null;
    }

    public PositionObject getObjectByCooridante(Coordinate coord) {
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

    public int getObjectCountByType(String type) {
	int result = 0;
	for (String id : objects.keySet()) {
	    if (id.toLowerCase().startsWith(type.toLowerCase())) {
		result++;
	    }
	}
	return result;
    }

}
