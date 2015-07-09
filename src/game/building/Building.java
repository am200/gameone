package game.building;

import game.base.Coordinate;
import game.base.HealthObject;
import game.base.MovableObject;
import game.base.Team;
import game.collectable.CollectableObject;
import game.collectable.util.CollectableKey;
import game.unit.Citizen;
import game.unit.Unit;
import game.util.DevelopmentState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public abstract class Building extends HealthObject {

    private final NavigableMap<DevelopmentState, Set<Unit>> units;
    private final Team team;
    private Coordinate unitStart;
    private final Map<Unit, List<MovableObject>> belongingUnits;

    public Building(Team team, Coordinate center, int width, int height) {
	super(center, width, height);
	this.units = new TreeMap<>();
	this.team = team;
	belongingUnits = new HashMap<>();
	defineUnitStart();
	Map<DevelopmentState, Set<Unit>> addedUnits = addProduceAbleUnits();
	if (addedUnits != null && !addedUnits.isEmpty()) {
	    units.putAll(addedUnits);
	}
    }

    public void produceUnit(Unit unit) {
	if (unit == null || !getProducableUnits().contains(unit)) {
	    return;
	}
	if (reduceCollectable(unit)) {
	    switch (unit) {
		case CITIZEN:
		    new Citizen(this, getUnitStart());
		    break;
	    }
	}
    }

    public Set<Unit> getProducableUnits() {
	return getProducableUnits(getTeam().getDevelopmentState());
    }

    private Set<Unit> getProducableUnits(DevelopmentState developmentState) {
	Set<Unit> result = units.get(developmentState);

	if (result == null) {
	    result = new HashSet<>();
	}

	DevelopmentState lowerState = units.lowerKey(developmentState);
	if (lowerState != null) {
	    Set<Unit> lowerUnits = getProducableUnits(lowerState);
	    if (lowerUnits != null) {
		result.addAll(lowerUnits);
	    }
	}
	return result;
    }

    public Team getTeam() {
	return team;
    }

    private void defineUnitStart() {
	int x = getCenter().getX();
	int y = getCenter().getY();

	int size = belongingUnits.size() > 0 ? belongingUnits.size() % 8 : 0;
	x += size + 1;
	y += size + 1;
	unitStart = new Coordinate(x, y);
    }

    public Coordinate getUnitStart() {
	return unitStart;
    }

    public void setUnitStart(Coordinate unitStart) {
	this.unitStart = unitStart;
    }

    protected abstract Map<DevelopmentState, Set<Unit>> addProduceAbleUnits();

    private boolean reduceCollectable(Unit unit) {
	for (Pair<CollectableKey, Integer> cost : unit.getCosts()) {
	    CollectableObject collectable = getTeam().getCollected(cost.getKey());
	    if (collectable == null || collectable.getPoints() < cost.getValue()) {
		return false;
	    }
	}

	for (Pair<CollectableKey, Integer> cost : unit.getCosts()) {
	    getTeam().addPoints(cost.getKey(), -cost.getValue());
	}
	return true;
    }

    public void addToCollectable(Citizen citizen) {
	CollectableObject collectable = citizen.getCollected();
	if (team.containsCollectable(collectable.getKey())) {
	    team.addPoints(collectable.getKey(), collectable.getPoints());
	    collectable.clearPoints();
	}
    }

    public int getCollectableMaximum(CollectableKey key) {
	return team.getCollectableMaximum(key);
    }

    public void showCollected() {
	System.out.println("####################################################");
	for (CollectableKey key : team.getCollectableMap().keySet()) {
	    int points = team.getCollectedPoints(key);

	    System.out.println("Collected " + points + " tree parts");
	    if (points >= getCollectableMaximum(key)) {
		produce();
	    }
	    points = team.getCollectedPoints(key);
	    System.out.println("Collected " + points + " tree parts");
	}
	for (Entry<Unit, List<MovableObject>> entry : belongingUnits.entrySet()) {
	    System.out.println("There are " + entry.getValue().size() + " " + entry.getKey() + "(s)");
	}

	execute();
	System.out.println("####################################################");
    }

    protected abstract void produce();

    protected abstract boolean checkForExecute();

    public void execute() {
	if (checkForExecute()) {
	    for (List<MovableObject> movableSet : belongingUnits.values()) {
		for (MovableObject movable : movableSet) {
		    movable.execute();
		}
	    }
	}
    }

    public void addUnitToBuilding(Unit unit, MovableObject result) {
	List<MovableObject> set;
	if (belongingUnits.containsKey(unit)) {
	    set = belongingUnits.get(unit);
	} else {
	    set = new ArrayList<>();
	}
	set.add(result);
	Thread t = new Thread(result);
	result.setThread(t);
	belongingUnits.put(unit, set);
    }

}
