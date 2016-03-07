package org.bdlions.db.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.constants.ResponseCodes;
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
    
    /**
     * This method will check validation of a licnese key
     * @param licenseInfo, license info
     * @return String, response code
     * @throws DBSetupException, db setup exception
     * @throws SQLException, sql exception
     * @throws EvolutionPeriodExpirationException, evolution period exception
     */
    public int isValidLicense(LicenseInfo licenseInfo) throws DBSetupException, SQLException, EvolutionPeriodExpirationException
    {
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_LICENSE_INFO);){
            stmt.setString(QueryField.SERIAL_NUMBER, licenseInfo.getSerialNumber());
            ResultSet rs = stmt.executeQuery();
            //license key is previously consumed and valid
            if(rs.next())
            {
//                int evolutionPeriod = Integer.parseInt(rs.getString(QueryField.EVOLUTION_PERIOD));
//                int currentTime = DateUtils.getCurrentUnixTime();
//                if(evolutionPeriod <= currentTime)
//                {
//                    //Evolution Period is over
//                    logger.error("Evolution period is over for the key:"+licenseInfo.getKey());
//                    throw new EvolutionPeriodExpirationException();
//                }
                int seed = rs.getInt(QueryField.SEED);
                //String mac = rs.getString(QueryField.MAC);
                String key = rs.getString(QueryField.KEY);
                if(licenseInfo.getSeed() == seed && key.equals(licenseInfo.getKey())) 
                {
                    logger.debug("License is validated for the serial number:"+licenseInfo.getSerialNumber());
                    return ResponseCodes.SUCCESS;
                }
                else
                {
                    logger.error("Invalid authentication for the serial number:"+licenseInfo.getSerialNumber());
                    return ResponseCodes.ERROR_CODE_UNAUTHENTIC_REQUEST;
                }
            }
        }
        //check the key is available at license keys table
        try (EasyStatement stmt = new EasyStatement(Database.getInstance().getConnection(), QueryManager.GET_UNUSED_LICENSE_SERIAL_NUMBER_INFO);){
            stmt.setString(QueryField.SERIAL_NUMBER, licenseInfo.getSerialNumber());
            stmt.setBoolean(QueryField.IS_USED, Boolean.FALSE);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                //if license key is available then create a license
                int currentTime = DateUtils.getCurrentUnixTime();
                try (EasyStatement stmt2 = new EasyStatement(Database.getInstance().getConnection(), QueryManager.CREATE_LICENSE)) {
                    stmt2.setString(QueryField.SERIAL_NUMBER, licenseInfo.getSerialNumber());
                    stmt2.setInt(QueryField.SUBSCRIPTION_DATE, currentTime);
                    stmt2.setInt(QueryField.START_DATE, currentTime);
                    stmt2.setInt(QueryField.EVOLUTION_PERIOD, currentTime+(86400*365*10));
                    stmt2.setInt(QueryField.SEED, licenseInfo.getSeed());
                    stmt2.setString(QueryField.MAC, licenseInfo.getMac());
                    stmt2.setString(QueryField.KEY, licenseInfo.getKey());
                    stmt2.executeUpdate();
                }
                //update license key info
                try (EasyStatement stmt3 = new EasyStatement(Database.getInstance().getConnection(), QueryManager.UPDATE_LICENSE_SERIAL_NUMBER_INFO)) {
                    stmt3.setBoolean(QueryField.IS_USED, Boolean.TRUE);
                    stmt3.setInt(QueryField.MODIFIED_ON, currentTime);
                    stmt3.setString(QueryField.SERIAL_NUMBER, licenseInfo.getSerialNumber());
                    stmt3.executeUpdate();
                }
                logger.debug("License is inatialized and validated for the serial number:"+licenseInfo.getSerialNumber());
                return ResponseCodes.SUCCESS;
            }
            else
            {
                logger.error("Invalid license for the serial number:"+licenseInfo.getSerialNumber());
                return ResponseCodes.ERROR_CODE_INVALID_LICENSE_KEY;
            }
        }
    }
}
