package game.unit;

import game.base.Coordinate;
import game.building.HomeBase;
import game.base.MovableObject;
import game.base.Team;

/**
 *
 * @author amohamed
 */
public class Soldier extends MovableObject {

    public Soldier(Team team, HomeBase home, Coordinate center) {
	super(home, 1, center, 3, 3);
    }

    @Override
    public void execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repaint() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
