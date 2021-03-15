package com.furkanayaz.groupspad;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String categoryname;
    private String categorydescription;

    public Category(String id, String categoryname, String categorydescription) {
        this.id = id;
        this.categoryname = categoryname;
        this.categorydescription = categorydescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorydescription() {
        return categorydescription;
    }

    public void setCategorydescription(String categorydescription) {
        this.categorydescription = categorydescription;
    }
}

