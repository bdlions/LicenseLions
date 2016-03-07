package org.bdlions;

import org.bdlions.bean.LicenseInfo;
import org.bdlions.db.Database;
import org.bdlions.db.LicenseManager;
import org.bdlions.exceptions.DBSetupException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nazmul hasan
 */
public class LicenseManagerTest {
    
    public LicenseManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void validLicenseTest() throws DBSetupException {
         Database.getInstance();
         
         LicenseManager licenseManager = new LicenseManager();
         LicenseInfo licenseInfo = new LicenseInfo();
         licenseInfo.setSerialNumber("serial1");
         licenseInfo.setSeed(51);
         licenseInfo.setKey("key1");
         int responseCode = licenseManager.isValidLicense(licenseInfo);
         System.out.println(responseCode);
     }
}
