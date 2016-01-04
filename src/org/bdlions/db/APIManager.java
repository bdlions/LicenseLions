/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.LicenseKey;
import org.bdlions.db.repositories.LicenseAPI;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.utility.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul
 */
public class APIManager {
    private LicenseAPI licenseAPI;
    private final Logger logger = LoggerFactory.getLogger(APIManager.class);
    public APIManager()
    {
        licenseAPI = new LicenseAPI();
    }
    public List<LicenseKey> getAllLicesneKeys()
    {
        List<LicenseKey> licenseKeyList = new ArrayList<>();
        try 
        {
            licenseKeyList = licenseAPI.getAllLicenseKeys();
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
        }
        return licenseKeyList;
    }
    
    public void createLicenseKey(LicenseKey licenseKey)
    {
        try 
        {
            licenseAPI.createLicenseKey(licenseKey);
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public List<LicenseInfo> getAllLicesnes()
    {
        List<LicenseInfo> licenseList = new ArrayList<>();
        try 
        {
            licenseList = licenseAPI.getAllLicenses();
        }
        catch (SQLException | DBSetupException ex) {
            logger.error(ex.getMessage());
        }
        return licenseList;
    }
    
    public static void main(String args[])
    {
        System.out.println(DateUtils.getUnixToHuman(1450903650));
    }
}
