package org.bdlions.bean;

/**
 *
 * @author nazmul hasan
 */
public class LicenseInfo {
    private String key;
    private int subscriptionDate;
    private int startDate;
    private int evolutionPeriod;
    private String macAddress;
    private String cpuAddress;
    private String processorAddress;
    public LicenseInfo()
    {
    
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(int subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEvolutionPeriod() {
        return evolutionPeriod;
    }

    public void setEvolutionPeriod(int evolutionPeriod) {
        this.evolutionPeriod = evolutionPeriod;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCpuAddress() {
        return cpuAddress;
    }

    public void setCpuAddress(String cpuAddress) {
        this.cpuAddress = cpuAddress;
    }

    public String getProcessorAddress() {
        return processorAddress;
    }

    public void setProcessorAddress(String processorAddress) {
        this.processorAddress = processorAddress;
    }
    
}
