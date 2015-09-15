package io.crm;

/**
 * Created by someone on 18/08/2015.
 */
public enum Indexes {
    campaign_name(mc.campaigns, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    region_name(mc.regions, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    area_name(mc.areas, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    house_name(mc.distributionHouses, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    brand_name(mc.brands, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    location_name(mc.locations, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    user_id(mc.userIndexes, new IndexTouple[]{new IndexTouple(QC.userId, 1)}, true),
    user_type(mc.userTypes, new IndexTouple[]{new IndexTouple(QC.name, 1)}, true),
    user_id_prefix(mc.userTypes, new IndexTouple[]{new IndexTouple(QC.prefix, 1)}, true),
    username(mc.userIndexes, new IndexTouple[]{new IndexTouple(QC.username, 1)}, true);

    public final String collection;
    public final IndexTouple[] kyes;
    public final boolean unique;

    Indexes(final mc collection, final IndexTouple[] kyes, final boolean unique) {
        this.collection = collection + "";
        this.kyes = kyes;
        this.unique = unique;
    }
}

