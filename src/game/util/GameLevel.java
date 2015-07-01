package game.base;

/**
 *
 * @author amohamed
 */
public enum GameLevel {

    EASY(1, 2),
    MEDIUM(4, 2),
    HARD(10, 2);

    private int attack;

    private int regeneration;

    private GameLevel(int attack, int regeneration) {
	this.attack = attack;
	this.regeneration = regeneration;
    }

    public int getAttack() {
	return attack;
    }

    public void setAttack(int attack) {
	this.attack = attack;
    }

    public int getRegeneration() {
	return regeneration;
    }

    public void setRegeneration(int regeneration) {
	this.regeneration = regeneration;
    }
    
    

}
