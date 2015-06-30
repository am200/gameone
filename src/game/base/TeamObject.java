package game.base;

/**
 *
 * @author amohamed
 */
public class TeamObject extends HealthObject {

    private final Team team;

    private final Coordinate home;

    public TeamObject(Team team, Coordinate home, Coordinate center, int width, int height) {
	super(center, width, height);
	this.home = home;
	this.team = team;
    }

    public Team getTeam() {
	return team;
    }

    public void increasePointsByCollectable(CollectableObject collect) {
	team.changePointsByValue(collect.getPoints());
    }

    public Coordinate getHome() {
	return this.home;
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
