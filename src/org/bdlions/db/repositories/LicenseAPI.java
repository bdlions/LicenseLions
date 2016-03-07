package org.bdlions.db.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.SerialNumberInfo;
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
    /**
    * This method will return all serial numbers
    * @exception DBSetupException
    * @exception SQLException
    * @return array list
    * @author nazmul hasan
    */
    public List<SerialNumberInfo> getAllSerialNumbers() throws DBSetupException, SQLException
    {
        List<SerialNumberInfo> serialNumberList = new ArrayList<>() ;
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_ALL_LICENSE_SERIAL_NUMBERS)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                SerialNumberInfo serialNumberInfo = new SerialNumberInfo();
                serialNumberInfo.setSerialNumber(rs.getString(QueryField.SERIAL_NUMBER));
                serialNumberInfo.setIsUsed(rs.getBoolean(QueryField.IS_USED));
                serialNumberInfo.setCreatedOn(DateUtils.getUnixToHuman(rs.getInt(QueryField.CREATED_ON)));
                serialNumberInfo.setModifiedOn(DateUtils.getUnixToHuman(rs.getInt(QueryField.MODIFIED_ON)));
                serialNumberList.add(serialNumberInfo);
            }
        }
        return serialNumberList;
    }
    
    /**
     * This method will create a new serial number
     * @param serialNumberInfo
     * @exception DBSetupException
     * @exception SQLException
     * @author nazmul hasan
     */
    public void createSerialNumber(SerialNumberInfo serialNumberInfo)  throws DBSetupException, SQLException
    {
        int currentTime = DateUtils.getCurrentUnixTime();
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.CREATE_SERIAL_NUMBER)) {
            stmt.setString(QueryField.SERIAL_NUMBER, serialNumberInfo.getSerialNumber());
            stmt.setBoolean(QueryField.IS_USED, Boolean.FALSE);
            stmt.setInt(QueryField.CREATED_ON, currentTime);
            stmt.setInt(QueryField.MODIFIED_ON, currentTime);
            stmt.executeUpdate();
        }
    }
    
    /**
     * This method will return all used licenses
     * @return array list
     * @exception DBSetupException
     * @exception SQLException
     * @author nazmul hasan
     */
    public List<LicenseInfo> getAllLicenses() throws DBSetupException, SQLException
    {
        List<LicenseInfo> licenseList = new ArrayList<>() ;
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_ALL_LICENSES)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                LicenseInfo licenseInfo = new LicenseInfo();
                licenseInfo.setSerialNumber(rs.getString(QueryField.SERIAL_NUMBER));
                licenseInfo.setSeed(rs.getInt(QueryField.SEED));
                licenseInfo.setMac(rs.getString(QueryField.MAC));
                licenseInfo.setKey(rs.getString(QueryField.KEY));
                licenseInfo.setSubscriptionDate(DateUtils.getUnixToHuman(rs.getInt(QueryField.SUBSCRIPTION_DATE)));
                licenseInfo.setStartDate(DateUtils.getUnixToHuman(rs.getInt(QueryField.START_DATE)));
                licenseInfo.setEvolutionPeriod(DateUtils.getUnixToHuman(rs.getInt(QueryField.EVOLUTION_PERIOD)));
                licenseList.add(licenseInfo);
            }
        }
        return licenseList;
    }
}
