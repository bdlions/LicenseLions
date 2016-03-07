package org.bdlions.bean;

/**
 *
 * @author nazmul hasan
 */
public class LicenseInfo {
    private String serialNumber;
    private String subscriptionDate;
    private String startDate;
    private String evolutionPeriod;
    private int seed;
    private String mac;
    private String key;
    
    public LicenseInfo()
    {
        
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
    
    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEvolutionPeriod() {
        return evolutionPeriod;
    }

    public void setEvolutionPeriod(String evolutionPeriod) {
        this.evolutionPeriod = evolutionPeriod;
    }
}
