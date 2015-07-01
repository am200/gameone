package game.coordinateSet;

import game.base.PositionObject;
import game.coordinateSet.CitizenCoordinateSet;
import game.coordinateSet.SoldierCoordinateSet;
import game.coordinateSet.AbstractCoordinateSet;
import game.unit.Soldier;
import game.unit.Citizen;

/**
 *
 * @author amohamed
 */
public class CoordinateSetFactory<COORDINATE_SET extends AbstractCoordinateSet> {

    public COORDINATE_SET getCoordinateSet(PositionObject object) {

	COORDINATE_SET result = null;
	if (object instanceof Citizen) {
	    result = (COORDINATE_SET) new CitizenCoordinateSet(object.getCenter());
	} else if (object instanceof Soldier) {
	    result = (COORDINATE_SET) new SoldierCoordinateSet(object.getCenter());
	}
	return result;
    }
}
