package game.building;

import game.base.Coordinate;
import game.base.HealthObject;
import game.base.MovableObject;
import game.base.Team;
import game.unit.Citizen;
import game.unit.Unit;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author amohamed
 */
public abstract class Building extends HealthObject {

    private final Set<Unit> units;
    private final Team team;
    private Coordinate unitStart;
    private Set<MovableObject> belongingUnits;

    public Building(Team team, Coordinate center, int width, int height) {
	super(center, width, height);
	this.units = new HashSet<>();
	this.team = team;
	belongingUnits = new HashSet<>();
	defineUnitStart();
	Set<Unit> addedUnits = addProduceAbleUnits();
	if (addedUnits != null && !addedUnits.isEmpty()) {
	    units.addAll(addedUnits);
	}
    }

    public MovableObject produceUnit(Unit unit) {
	if (unit == null || !units.contains(unit)) {
	    return null;
	}
	MovableObject result = null;
	switch (unit) {
	    case CITIZEN:
		result = new Citizen(this, getUnitStart());
		break;
	}
	belongingUnits.add(result);
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

    protected abstract Set<Unit> addProduceAbleUnits();
}
