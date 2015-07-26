package game.building;

import game.GameData;
import game.base.Coordinate;
import game.base.Team;
import game.collectable.CollectableObject;
import game.collectable.util.CollectableKey;
import game.unit.Unit;
import game.util.DevelopmentState;
import game.util.ProductionMode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author amohamed
 */
public class HomeBase extends Building {

    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;

    private final ProductionMode mode;

    public HomeBase(Coordinate center, Team team, ProductionMode mode) {
	super(team, center, WIDTH, HEIGHT);
	this.mode = mode;
    }

    @Override
    protected Map<DevelopmentState, Set<Unit>> addProduceAbleUnits() {
	return new TreeMap<DevelopmentState, Set<Unit>>() {
	    private static final long serialVersionUID = 3109256773218160485L;

	    {
		put(DevelopmentState.STATE_0, new HashSet<Unit>() {
		    private static final long serialVersionUID = 3109256773218160485L;

		    {
			add(Unit.CITIZEN);
		    }
		});
	    }
	};
    }

    public int getCollectableDifference(CollectableKey key) {
	return getTeam().getCollectableDifference(key);
    }

    @Override
    public void produce() {
	if (ProductionMode.AUTO.equals(mode)) {
	    produceUnit(Unit.CITIZEN);
	}
    }

    @Override
    protected boolean checkForExecute() {
	boolean result = false;

	for (CollectableObject collect : getTeam().getCollectableMap().values()) {
	    if (collect.getPointDifference() > 0 && GameData.getGameField().getObjectCountByType(collect.getKey().name()) > 0) {
		result = true;
	    }
	}

	return result;
    }

}
