package org.bdlions.db.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.LicenseKey;
import org.bdlions.constant.ResponseCode;
import org.bdlions.db.Database;
import org.bdlions.db.query.QueryField;
import org.bdlions.db.query.QueryManager;
import org.bdlions.db.query.helper.EasyStatement;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.exceptions.EvolutionPeriodExpirationException;
import org.bdlions.utility.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul hasan
 */
public class License {
    private final Logger logger = LoggerFactory.getLogger(EasyStatement.class);
    public License()
    {
    
    }
    public void createLicenseKey(LicenseKey licenseKey)  throws DBSetupException, SQLException
    {
        int currentTime = DateUtils.getCurrentUnixTime();
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.CREATE_LICENSE_KEY)) {
            stmt.setString(QueryField.KEY, licenseKey.getKey());
            stmt.setBoolean(QueryField.IS_USED, Boolean.FALSE);
            stmt.setInt(QueryField.CREATED_ON, currentTime);
            stmt.executeUpdate();
        }
    }
    
    public List<LicenseKey> getAllLicenseKeys() throws DBSetupException, SQLException
    {
        List<LicenseKey> licenseKeys = new ArrayList<>() ;
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_ALL_LICENSE_KEYS)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                LicenseKey licenseKey = new LicenseKey();
                licenseKey.setKey(rs.getString(QueryField.KEY));
                licenseKey.setCreatedOn(rs.getInt(QueryField.CREATED_ON));
                licenseKeys.add(licenseKey);
            }
        }
        return licenseKeys;
    }
    
    /**
     * This method will check validation of a licnese key
     * @param licenseInfo, license info
     * @return String, response code
     * @throws DBSetupException, db setup exception
     * @throws SQLException, sql exception
     * @throws EvolutionPeriodExpirationException, evolution period exception
     */
    public String isValidLicense(LicenseInfo licenseInfo) throws DBSetupException, SQLException, EvolutionPeriodExpirationException
    {
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_LICENSE_INFO);){
            stmt.setString(QueryField.KEY, licenseInfo.getKey());
            ResultSet rs = stmt.executeQuery();
            //license key is previously consumed and valid
            if(rs.next())
            {
                int evolutionPeriod = Integer.parseInt(rs.getString(QueryField.EVOLUTION_PERIOD));
                int currentTime = DateUtils.getCurrentUnixTime();
//                if(evolutionPeriod <= currentTime)
//                {
//                    //Evolution Period is over
//                    logger.error("Evolution period is over for the key:"+licenseInfo.getKey());
//                    throw new EvolutionPeriodExpirationException();
//                }
                String macAddress = rs.getString(QueryField.MAC_ADDRESS);
                String cpuAddress = rs.getString(QueryField.CPU_ADDRESS);
                String processorAddress = rs.getString(QueryField.PROCESSOR_ADDRESS);
                if(macAddress.equals(licenseInfo.getMacAddress()) || cpuAddress.equals(licenseInfo.getCpuAddress()) || processorAddress.equals(licenseInfo.getProcessorAddress())) 
                {
                    return ResponseCode.SUCCESS;
                }
                else
                {
                    logger.error("Invalid authentication for the key:"+licenseInfo.getKey());
                    return ResponseCode.ERROR_CODE_UNAUTHENTIC_REQUEST;
                }
            }
        }
        //check the key is available at license keys table
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_UNUSED_LICENSE_KEY_INFO);){
            stmt.setString(QueryField.KEY, licenseInfo.getKey());
            stmt.setBoolean(QueryField.IS_USED, Boolean.FALSE);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                //if license key is available then create a license
                int currentTime = DateUtils.getCurrentUnixTime();
                try (EasyStatement stmt2 = new EasyStatement(Database.getInstance().getConnection(), QueryManager.CREATE_LICENSE)) {
                    stmt2.setString(QueryField.KEY, licenseInfo.getKey());
                    stmt2.setInt(QueryField.SUBSCRIPTION_DATE, currentTime);
                    stmt2.setInt(QueryField.START_DATE, currentTime);
                    stmt2.setInt(QueryField.EVOLUTION_PERIOD, currentTime+(86400*365*10));
                    stmt2.setString(QueryField.MAC_ADDRESS, licenseInfo.getMacAddress());
                    stmt2.setString(QueryField.CPU_ADDRESS, licenseInfo.getCpuAddress());
                    stmt2.setString(QueryField.PROCESSOR_ADDRESS, licenseInfo.getProcessorAddress());
                    stmt2.executeUpdate();
                }
                //update license key info
                try (EasyStatement stmt3 = new EasyStatement(Database.getInstance().getConnection(), QueryManager.UPDATE_LICENSE_KEY_INFO)) {
                    stmt3.setBoolean(QueryField.IS_USED, Boolean.TRUE);
                    stmt3.setInt(QueryField.MODIFIED_ON, currentTime);
                    stmt3.setString(QueryField.KEY, licenseInfo.getKey());
                    stmt3.executeUpdate();
                }
                return ResponseCode.SUCCESS;
            }
            else
            {
                logger.error("Invalid license for the key:"+licenseInfo.getKey());
                return ResponseCode.ERROR_CODE_INVALID_LICENSE_KEY;
            }
        }
    }
}
