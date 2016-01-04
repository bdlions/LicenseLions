/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.db.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.LicenseKey;
import org.bdlions.db.Database;
import org.bdlions.db.query.QueryField;
import org.bdlions.db.query.QueryManager;
import org.bdlions.db.query.helper.EasyStatement;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.utility.DateUtils;

/**
 *
 * @author nazmul hasan
 */
public class LicenseAPI {
    public List<LicenseKey> getAllLicenseKeys() throws DBSetupException, SQLException
    {
        List<LicenseKey> licenseKeys = new ArrayList<>() ;
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_ALL_LICENSE_KEYS)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                LicenseKey licenseKey = new LicenseKey();
                licenseKey.setKey(rs.getString(QueryField.KEY));
                licenseKey.setIsUsed(rs.getBoolean(QueryField.IS_USED));
                licenseKey.setCreatedOn(DateUtils.getUnixToHuman(rs.getInt(QueryField.CREATED_ON)));
                licenseKey.setModifiedOn(DateUtils.getUnixToHuman(rs.getInt(QueryField.MODIFIED_ON)));
                licenseKeys.add(licenseKey);
            }
        }
        return licenseKeys;
    }
    
    public void createLicenseKey(LicenseKey licenseKey)  throws DBSetupException, SQLException
    {
        int currentTime = DateUtils.getCurrentUnixTime();
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.CREATE_LICENSE_KEY)) {
            stmt.setString(QueryField.KEY, licenseKey.getKey());
            stmt.setBoolean(QueryField.IS_USED, Boolean.FALSE);
            stmt.setInt(QueryField.CREATED_ON, currentTime);
            stmt.setInt(QueryField.MODIFIED_ON, currentTime);
            stmt.executeUpdate();
        }
    }
    
    public List<LicenseInfo> getAllLicenses() throws DBSetupException, SQLException
    {
        List<LicenseInfo> licenseList = new ArrayList<>() ;
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_ALL_LICENSES)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                LicenseInfo licenseInfo = new LicenseInfo();
                licenseInfo.setKey(rs.getString(QueryField.KEY));
                licenseInfo.setMacAddress(rs.getString(QueryField.MAC_ADDRESS));
                licenseInfo.setCpuAddress(rs.getString(QueryField.CPU_ADDRESS));
                licenseInfo.setProcessorAddress(rs.getString(QueryField.PROCESSOR_ADDRESS));
                licenseInfo.setSubscriptionDate(DateUtils.getUnixToHuman(rs.getInt(QueryField.SUBSCRIPTION_DATE)));
                licenseInfo.setStartDate(DateUtils.getUnixToHuman(rs.getInt(QueryField.START_DATE)));
                licenseInfo.setEvolutionPeriod(DateUtils.getUnixToHuman(rs.getInt(QueryField.EVOLUTION_PERIOD)));
                licenseList.add(licenseInfo);
            }
        }
        return licenseList;
    }
}
