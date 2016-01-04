package org.bdlions.bean;

/**
 *
 * @author nazmul hasan
 */
public class LicenseInfo {
    private String key;
    private String subscriptionDate;
    private String startDate;
    private String evolutionPeriod;
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
