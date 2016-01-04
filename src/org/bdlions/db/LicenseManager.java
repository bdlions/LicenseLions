/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.db;

import java.sql.SQLException;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.LicenseKey;
import org.bdlions.constants.ResponseCodes;
import org.bdlions.db.query.helper.EasyStatement;
import org.bdlions.db.repositories.License;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.exceptions.EvolutionPeriodExpirationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author nazmul hasan
 */
public class LicenseManager {
    License license;
    private final Logger logger = LoggerFactory.getLogger(EasyStatement.class);
    public LicenseManager()
    {
        license = new License();
    }
    
    public void getAllLicesneKeys()
    {
        try 
        {
            license.getAllLicenseKeys();
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public void createLicenseKey(LicenseKey licenseKey)
    {
        try 
        {
            license.createLicenseKey(licenseKey);
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    /**
     * This method will validate license
     * @param licenseInfo, license Info
     * @return String, response code
     */
    public int isValidLicense(LicenseInfo licenseInfo)
    {
        
        try 
        {
            return license.isValidLicense(licenseInfo);
        }
        catch(EvolutionPeriodExpirationException epex)
        {
            logger.error(epex.getMessage());
            return ResponseCodes.ERROR_CODE_EVOLUTION_PERIOD_EXPIRED_EXCEPTION;
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
            return ResponseCodes.ERROR_CODE_DB_EXCEPTION;
        }
    }
    
//    public static void main(String args[])
//    {
//        LicenseInfo licenseInfo = new LicenseInfo();
//        licenseInfo.setKey("key1");
//        licenseInfo.setEvolutionPeriod(1469912345);
//        licenseInfo.setStartDate(1443312345);
//        licenseInfo.setStartDate(1442212345);
//        licenseInfo.setMacAddress("mac22");
//        licenseInfo.setCpuAddress("cpu22");
//        licenseInfo.setProcessorAddress("processor22");
//        
//        LicenseManager licenseManager = new LicenseManager();
//        licenseManager.isValidLicense(licenseInfo);
//    }
}
