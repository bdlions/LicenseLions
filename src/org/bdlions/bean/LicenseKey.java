package org.bdlions.bean;

/**
 *
 * @author nazmul hasan
 */
public class LicenseKey {
    private String key;
    private Boolean isUsed;
    private int createdOn;
    private int modifiedOn;
    public LicenseKey()
    {
    
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(int createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public int getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(int modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
    
}
