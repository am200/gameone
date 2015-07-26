package game.util;

/**
 *
 * @author amohamed
 */
public enum DevelopmentState {

    STATE_0,
    STATE_1,
    STATE_2,
    STATE_3,
    STATE_4,
    STATE_5,
    STATE_6,
    STATE_7,
    STATE_8,
    STATE_9,
    STATE_10;

    private final static String state = "STATE_";

    public static DevelopmentState getNextState(DevelopmentState actualDevelopmentState) {
	Integer stateNr = Integer.valueOf(actualDevelopmentState.name().replace(state, ""));

	for (DevelopmentState devState : values()) {
	    if (devState.name().equals(state + stateNr)) {
		return devState;
	    }
	}
	return actualDevelopmentState;
    }

}
