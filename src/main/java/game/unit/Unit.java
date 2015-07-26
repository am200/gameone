package game.unit;

import game.collectable.util.CollectableKey;
import javafx.util.Pair;

/**
 *
 * @author amohamed
 */
public enum Unit {

    CITIZEN(new Pair<>(CollectableKey.TREE, 100)),
    SOLDIER(
	    new Pair<>(CollectableKey.TREE, 100),
	    new Pair<>(CollectableKey.IRON, 100),
	    new Pair<>(CollectableKey.GOLD, 100)
    );

    private final Pair<CollectableKey, Integer>[] costs;

    private Unit(Pair<CollectableKey, Integer>... costs) {
	this.costs = costs;
    }

    public Pair<CollectableKey, Integer>[] getCosts() {
	return costs;
    }

}
