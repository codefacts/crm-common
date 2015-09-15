package io.crm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sohan on 7/29/2015.
 */
public enum mc {
    areas("area", "Area"),
    brands("brand", "Region"),
    clients("client", "Client"),
    consumerContacts("consumer_contact", "Consumer Contact"),
    consumers("consumer", "Consumer"),
    distributionHouses("distributionHouse", "Distribution House"),
    employees("employee", "Employee"),
    regions("region", "Region"),
    locations("location", "Location"),
    campaigns("campaign", "Campaign"),
    userTypes("userType", "User Type"),
    userIndexes("user_index", "User Index", -1, true);

    public final String label;
    public final String fieldName;
    private final boolean internal;
    private AtomicLong nextId = new AtomicLong(0);

    mc(final String fieldName, final String label, final long nextId, final boolean internal) {
        this.fieldName = fieldName;
        this.label = label;
        this.nextId.set(nextId);
        this.internal = internal;
    }

    mc(String fieldName, String label, long nextId) {
        this.fieldName = fieldName;
        this.label = label;
        this.nextId.set(nextId);
        this.internal = false;
    }

    mc(String fieldName, String label) {
        this.fieldName = fieldName;
        this.label = label;
        this.internal = false;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setNextId(final long nextId) {
        this.nextId.set(nextId);
        ;
    }

    public long getNextId() {
        return nextId.get();
    }

    public long incrementNextId() {
        return nextId.incrementAndGet();
    }

    public long decrementNextId() {
        return nextId.decrementAndGet();
    }

    public long decrementNextId(final long amount) {
        return nextId.addAndGet(-amount);
    }

    public long incrementNextId(final long amount) {
        return nextId.addAndGet(amount);
    }

    public boolean isIdTypeLong() {
        return nextId.get() >= 0;
    }
}