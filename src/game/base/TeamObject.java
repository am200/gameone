package game.base;

import game.collectable.CollectableObject;
import game.building.HomeBase;

/**
 *
 * @author amohamed
 */
public class TeamObject extends HealthObject {

    private final Team team;

    private final HomeBase home;

    public TeamObject(HomeBase home, Coordinate center, int width, int height) {
	super(center, width, height);
	this.home = home;
	this.team = home.getTeam();
    }

    public Team getTeam() {
	return team;
    }

    public void increasePointsByCollectable(CollectableObject collect) {
	team.changePointsByValue(collect.getPoints());
    }

    public HomeBase getHome() {
	return this.home;
    }

    public Coordinate getHomeCoordinate() {
	return this.home.getCenter();
    }

    @Override
    public String toString() {
	return "TeamObject{"
		+ toStringContent()
		+ '}';
    }

    @Override
    protected String toStringContent() {

	return (home != null ? "home=" + home + ", " : "")
		+ (team != null ? "team=" + team + ", " : "")
		+ super.toStringContent();
    }

}
