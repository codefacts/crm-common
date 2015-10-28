package io.crm;

/**
 * Created by someone on 23/08/2015.
 */
final public class QC {
    public static final String id = "_id";
    public static final String exists = "exists";
    public static final String withId = "withId";
    public static final String name = "name";

    public static final String brs = "brs";
    public static final String locations = "locations";
    public static final String regions = "regions";
    public static final String regionCount = "regionCount";
    public static final String areaCount = "areaCount";
    public static final String houseCount = "houseCount";
    public static final String brCount = "brCount";
    public static final String locationCount = "locationCount";
    public static final String elementCount = "elementCount";
    public static final String region = "region";
    public static final String createDate = "createDate";
    public static final String modifyDate = "modifyDate";
    public static final String createdBy = "createdBy";
    public static final String modifiedBy = "modifiedBy";
    public static final String message = "message";
    public static final String area = "area";
    public static final String distributionHouse = "distributionHouse";
    public static final String distributionHouseId = "distributionHouseId";
    public static final String label = "label";
    public static final String userId = "userId";
    public static final String params = "params";
    public static final String count = "count";
    public static final String caption = "caption";
    public static final String brSupervisors = "brSupervisors";
    public static final String areaCoordinators = "areaCoordinators";
    public static final String acCount = "acCount";
    public static final String supCount = "supCount";
    public static final String areas = "areas";
    public static final String houses = "houses";
    public static final String acs = "acs";
    public static final String location = "location";
    public static final String ac = "ac";
    public static final String house = "house";

    public static final String system_indexes = "system.indexes";
    public static final String indexes = "indexes";
    public static final String unique = "unique";
    public static final String key = "key";
    public static final String username = "username";
    public static final String field = "field";
    public static final String $set = "$set";
    public static final String areaName = "areaName";
    public static final String assigned_id = "assigned_id";
    public static final String userType = "userType";
    public static final String br = "br";
    public static final String brand = "brand";
    public static final String prefix = "prefix";
    public static final String required = "required";
    public static final String $date = "$date";
    public static final String $in = "$in";
    public static final String areaCoordinator = "areaCoordinator";
    public static final String brSupervisor = "brSupervisor";

    public static final String contactCount = "contactCount";
    public static final String ptrCount = "ptrCount";
    public static final String swpCount = "swpCount";
    public static final String __self = "__self";

    public static final String _all = "_all";
    public static final String _all_region_area_id = "_all_region_area_id";
    public static final String _all_house_sup_id = "_all_house_sup_id";
    public static final String _all_house_br_id = "_all_house_br_id";
    public static final String _all_house_location_id = "_all_house_location_id";

    public static final String _all_area_house_id = "_all_area_house_id";
    public static final String _all_area_ac_id = "_all_area_ac_id";
    public static final String tree = "tree";
    public static final String parent = "parent";
    public static final String closeDate = "closeDate";
    public static final String launchDate = "launchDate";
    public static final String salaryStartDate = "salaryStartDate";
    public static final String salaryEndDate = "salaryEndDate";
    public static final String invalidValue = "invalidValue";
    public static final String constraintDescriptor = "constraintDescriptor";
    public static final String messageTemplate = "messageTemplate";
    public static final String propertyPath = "propertyPath";
    public static final String rootBean = "rootBean";
    public static final String rootBeanClass = "rootBeanClass";
    public static final String leafBean = "leafBean";

    public static final String brandId = concat(brand, id);
    public static final String areaCoordinatorId = concat(areaCoordinator, id);
    public static final String locationId = concat(location, id);
    public static final String brSupervisorId = concat(brSupervisor, id);
    public static final String areaRegionId = concat(area, region, id);
    public static final String distributionHouseAreaId = concat(distributionHouse, area, id);
    public static final String locationDistributionHouseId = concat(location, distributionHouse, id);
    public static final String brDistributionHouseId = concat(br, distributionHouse, id);
    public static final String brSupervisorDistributionHouseId = concat(brSupervisor, distributionHouse, id);
    public static final String areaCoordinatorAreaId = concat(areaCoordinator, area, id);
    public static final String regionId = concat(region, id);
    public static final String areaId = concat(area, id);
    public static final String houseId = concat(distributionHouse, id);
    public static final String brId = concat(br, id);
    public static final String userTypeId = concat(userType, id);
    public static final String db_name = "db_name";
    public static final String violations = "violations";
    public static final String data = "data";
    public static final String operation = "operation";
    public static final String cause = "cause";
    public static final String fullName = "fullName";
    public static final String simpleName = "simpleName";
    public static final String stackTrace = "stackTrace";
    public static final String className = "className";
    public static final String fieldName = "fieldName";
    public static final String methodName = "methodName";
    public static final String lineNumber = "lineNumber";
    public static final String page = "page";
    public static final String size = "size";
    public static final String total = "total";
    public static final String length = "length";

    public static final String concat(String... strings) {
        return String.join(".", strings);
    }
}
