/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import java.sql.Connection;
import java.sql.SQLException;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.constants.ResponseCodes;
import org.bdlions.db.Database;
import org.bdlions.db.LicenseManager;
import org.bdlions.exceptions.DBSetupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul hasan
 */
public class LicenseServer extends AbstractVerticle {
    final static Logger logger = LoggerFactory.getLogger(LicenseServer.class);
    private final int SERVER_PORT = 6060;
    
    @Override
    public void start() {
        
        
        
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/isvalidlicense*").handler(BodyHandler.create());
        router.post("/isvalidlicense").handler((RoutingContext routingContext) -> {
            
//            String license = routingContext.request().getParam("license");
//            String motherboard = routingContext.request().getParam("motherboard");
//            String processor = routingContext.request().getParam("processor");
//            String mac = routingContext.request().getParam("mac");
//            
//            LicenseInfo licenseInfo = new LicenseInfo();
//            licenseInfo.setKey(license);
//            licenseInfo.setMacAddress(mac);
//            licenseInfo.setProcessorAddress(processor);
//            licenseInfo.setCpuAddress(motherboard);
//            
//            LicenseManager licenseManager = new LicenseManager();
//            int responseCode = licenseManager.isValidLicense(licenseInfo);
            
            HttpServerResponse response = routingContext.response();
            
            String serial_number = routingContext.request().getParam("serial_number");
            String seed = routingContext.request().getParam("seed");
            String mac = routingContext.request().getParam("mac");
            String key = routingContext.request().getParam("key");   
            
            LicenseInfo licenseInfo = new LicenseInfo();
            licenseInfo.setSerialNumber(serial_number);
            try
            {
                licenseInfo.setSeed(Integer.parseInt(seed));
            }
            catch(Exception ex)
            {
                logger.error("Invalid seed :"+seed+" for the serial number: "+serial_number+" ,mac: "+mac+" and key: "+key);
                logger.error(ex.toString());
                response.end(""+ResponseCodes.ERROR_CODE_INVALID_SEED);
                return;
            }
            
            licenseInfo.setMac(mac);
            licenseInfo.setKey(key);
            LicenseManager licenseManager = new LicenseManager();
            int responseCode = licenseManager.isValidLicense(licenseInfo);
            
            
            response.end(""+responseCode);
        });
        
        
        //this is a sample function, this will not go into production
        router.route("/").handler((RoutingContext routingContext) -> {
            HttpServerResponse response = routingContext.response();
            response.end("Running Initial Java web server");
        });
        
        //this is a sample function, this will not go into production
        router.route("/setupdatabase").handler((RoutingContext routingContext) -> {
            try {
                HttpServerResponse response = routingContext.response();
                
                Database db = Database.getInstance();
                Connection connection = db.getConnection();
                if(connection == null){
                    logger.info("Db connection not set.");
                }
                response.end("Database setup completed");
            } catch (DBSetupException | SQLException ex) {
                logger.error(ex.getMessage());
            }
        });
        
        //this is a sample function, this will not go into production
        router.route("/samplepost*").handler(BodyHandler.create());
        router.post("/samplepost").handler((RoutingContext routingContext) -> {
            
            String param1 = routingContext.request().getParam("param1");
            String param2 = routingContext.request().getParam("param2");
            
            HttpServerResponse response = routingContext.response();
            response.end("Param1 " + param1 + " " + " Param2: " + param2);
        });
        
        //this is a sample function, this will not go into production
        router.route("/sampleget").handler((RoutingContext routingContext) -> {
            String param1 = routingContext.request().getParam("param1"); 
            HttpServerResponse response = routingContext.response();
            response.end("Param1: " + param1);
        });
        
        server.requestHandler(router::accept).listen(SERVER_PORT);
    }

}
