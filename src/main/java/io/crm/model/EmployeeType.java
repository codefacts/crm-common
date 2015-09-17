package io.crm.model;

/**
 * Created by someone on 29-Jul-2015.
 */
public enum EmployeeType {
    admin(1, "ad", "Admin"),
    headOffice(2, "ho", "Head Office User"),
    areaCoordinator(3, "ac", "Area Coordinator"),
    brSupervisor(4, "sp", "Br Supervisor"),
    callOperator(5, "co", "Call Operator"),
    callCenterSupervisor(6, "cs", "Call Center Supervisor"),
    br(7, "br", "BR");

    public final Long id;
    public final String prefix;
    public final String label;

    EmployeeType(long id, String prefix, String label) {
        this.id = id;
        this.prefix = prefix;
        this.label = label;
    }
}
