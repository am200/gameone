package game.coordinateSet;

import game.base.Coordinate;

/**
 *
 * @author amohamed
 *
 * 010
 * 111
 * 010
 * 
 */
public class CitizenCoordinateSet extends AbstractCoordinateSet {

    public CitizenCoordinateSet(Coordinate center) {
	super(center);
    }

    @Override
    protected RangeSet[] defineCoordinateRange() {
	return new RangeSet[]{new RangeSet(-1, 0), new RangeSet(0, 1), new RangeSet(0, -1), new RangeSet(1, 0)};
    }

}
