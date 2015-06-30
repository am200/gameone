package game.base;

import java.util.UUID;

/**
 *
 * @author amohamed
 */
public class AbstractBaseObject {

    private String id;

    protected AbstractBaseObject() {
	super();
	generateId();
    }

    protected void generateId() {
	this.id = this.getClass().getSimpleName()+ UUID.randomUUID();
    }

    public String getId() {
	return this.id;
    }

    @Override
    public String toString() {
	return "BaseObject{" +toStringContent()+'}';
    }
    
    protected String toStringContent(){
	return  "id=" + id;
    }

}
