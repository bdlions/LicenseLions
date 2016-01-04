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
import java.util.List;
import org.bdlions.bean.LicenseInfo;
import org.bdlions.bean.LicenseKey;
import org.bdlions.constants.ResponseCodes;
import org.bdlions.db.APIManager;
import org.bdlions.db.Database;
import org.bdlions.exceptions.DBSetupException;
import org.bdlions.response.ResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul hasan
 */
public class APIManageServer extends AbstractVerticle {
    final static Logger logger = LoggerFactory.getLogger(AbstractVerticle.class);
    private final int SERVER_PORT = 7070;
    
    @Override
    public void start() {
        
        
        
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

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
        
        
        router.route("/getalllicensekeys*").handler(BodyHandler.create());
        router.post("/getalllicensekeys").handler((RoutingContext routingContext) -> {
            
            //String userName = routingContext.request().getParam("username");
            //String password = routingContext.request().getParam("password");
            //String limit = routingContext.request().getParam("limit");
            //String offset = routingContext.request().getParam("offset");
            APIManager apiManager = new APIManager();
            List<LicenseKey> licenseKeyList = apiManager.getAllLicesneKeys();
            ResultEvent resultEvent = new ResultEvent();
            resultEvent.setResponseCode(ResponseCodes.SUCCESS);
            resultEvent.setResult(licenseKeyList);
            
            HttpServerResponse response = routingContext.response();
            response.end(resultEvent.toString());
        });
        
        router.route("/createlicensekey*").handler(BodyHandler.create());
        router.post("/createlicensekey").handler((RoutingContext routingContext) -> {
            
            String key = routingContext.request().getParam("key");
            
            APIManager apiManager = new APIManager();
            LicenseKey licenseKey = new LicenseKey();
            licenseKey.setKey(key);
            apiManager.createLicenseKey(licenseKey);
            ResultEvent resultEvent = new ResultEvent();
            resultEvent.setResponseCode(ResponseCodes.SUCCESS);
            
            HttpServerResponse response = routingContext.response();
            response.end(resultEvent.toString());
        });
        
        router.route("/getalllicenses*").handler(BodyHandler.create());
        router.post("/getalllicenses").handler((RoutingContext routingContext) -> {
            
            //String userName = routingContext.request().getParam("username");
            //String password = routingContext.request().getParam("password");
            //String limit = routingContext.request().getParam("limit");
            //String offset = routingContext.request().getParam("offset");
            APIManager apiManager = new APIManager();
            List<LicenseInfo> licenseList = apiManager.getAllLicesnes();
            ResultEvent resultEvent = new ResultEvent();
            resultEvent.setResponseCode(ResponseCodes.SUCCESS);
            resultEvent.setResult(licenseList);
            
            HttpServerResponse response = routingContext.response();
            response.end(resultEvent.toString());
        });
        
        
        server.requestHandler(router::accept).listen(SERVER_PORT);
    }

}
