package edu.usp.planex.model;

/**
 * Created by giulianoprado on 24/07/17.
 */

//@Entity
//@Table(name="Provider")
public class Provider {
    int id;
    String shortName;
    String fullName;
    int typeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int providerId) {
        this.typeId = providerId;
    }
}
