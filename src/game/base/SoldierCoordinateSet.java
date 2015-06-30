package game.base;

/**
 *
 * @author amohamed
 *
 *
 * 00100
 * 01110
 * 11111
 * 01110
 * 00100
 * 
 */
public class SoldierCoordinateSet extends AbstractCoordinateSet {

    public SoldierCoordinateSet(Coordinate center) {
	super(center);
    }

    //		00100
    //		01110
    //		11111
    //		01110
    //		00100
    @Override
    protected RangeSet[] defineCoordinateRange() {
	return new RangeSet[]{
	    new RangeSet(-2, 0),
	    new RangeSet(-1, -1), new RangeSet(-1, 0), new RangeSet(-1, 1),
	    new RangeSet(0, -2), new RangeSet(0, -1), new RangeSet(0, 1), new RangeSet(0, 2),
	    new RangeSet(1, -1), new RangeSet(1, 0), new RangeSet(1, 1),
	    new RangeSet(0, -1), new RangeSet(0, 1), new RangeSet(1, 0)
	};
    }

}
