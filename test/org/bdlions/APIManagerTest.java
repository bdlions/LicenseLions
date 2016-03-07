package org.bdlions;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.SerialNumberInfo;
import org.bdlions.constants.ResponseCodes;
import org.bdlions.db.APIManager;
import org.bdlions.db.Database;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.response.ResultEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nazmul hasan
 */
public class APIManagerTest {
    
    public APIManagerTest() {
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

    @Test
    public void validLicenseTest() throws DBSetupException {
        Database.getInstance();

        APIManager apiManager = new APIManager();
        
        SerialNumberInfo serialNumberInfo = new SerialNumberInfo();
        SecureRandom random = new SecureRandom();
        serialNumberInfo.setSerialNumber(new BigInteger(130, random).toString(32));
        apiManager.createSerialNumber(serialNumberInfo);
        
        List<SerialNumberInfo> serialNumberList = apiManager.getAllSerialNumbers();
        ResultEvent resultEvent = new ResultEvent();
        resultEvent.setResponseCode(ResponseCodes.SUCCESS);
        resultEvent.setResult(serialNumberList);
        System.out.println(resultEvent.toString());
        
        List<LicenseInfo> licenseList = apiManager.getAllLicesnes();
        resultEvent = new ResultEvent();
        resultEvent.setResponseCode(ResponseCodes.SUCCESS);
        resultEvent.setResult(licenseList);
        System.out.println(resultEvent.toString());
     }
}
