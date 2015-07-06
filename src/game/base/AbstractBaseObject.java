package game.base;

import java.util.UUID;

/**
 *
 * @author amohamed
 */
public class AbstractBaseObject {

    private String id;

    private String idPrefix;

    protected AbstractBaseObject() {
	super();
	generateId();
    }

    protected void generateId() {
	this.idPrefix = this.getClass().getSimpleName();
	this.id = idPrefix + UUID.randomUUID();
    }

    public String getId() {
	return this.id;
    }

    public String getIdPrefix() {
	return this.idPrefix;
    }

    @Override
    public String toString() {
	return "BaseObject{" + toStringContent() + '}';
    }

    protected String toStringContent() {
	return "id=" + id + ", " + "idPrefix="+idPrefix;
    }

}
