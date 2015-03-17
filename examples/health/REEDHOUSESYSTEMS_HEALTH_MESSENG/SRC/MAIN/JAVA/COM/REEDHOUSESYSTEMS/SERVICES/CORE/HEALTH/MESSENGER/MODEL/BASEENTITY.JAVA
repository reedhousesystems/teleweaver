package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.util.Assert;

/**
 * Base class for all hibernate Entities
 *
 * @author : C. Gremu
 */
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    /**
     *  All objects will have a unique UUID which allows for the decoupling from DB generated ids
     *
     */
    @Column(length=36)
    private String uuid;

    public BaseEntity() {
        this(UUID.randomUUID());
    }

    public BaseEntity(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        setUuid(guid.toString());
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int hashCode() {
        return getUuid().hashCode();
    }

    /**
     * In most instances we can rely on the UUID to identify the object.
     * Subclasses may want a user friendly identifier for constructing easy to read urls
     *
     * <code>
     *    /user/1883c578-76be-47fb-a5c1-7bbea3bf7fd0 using uuid as the identifier
     *
     *    /user/jsmith using the username as the identifier
     *
     * </code>
     *
     * @return Object unique identifier for the object
     */
    public Object getIdentifier() {
        return getUuid().toString();
    }

}
