package org.bdlions.db;

import java.sql.SQLException;
import org.bdlions.bean.LicenseInfo;
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
}
