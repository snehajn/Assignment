package Testsuite;


import org.testng.annotations.Test;

import groovy.util.logging.Log;

import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.*;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import java.util.Iterator;
/*
@author Sneha jain
Date: 17-01-20201
*/
public class Test1 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void verifyResponseBodyandStatusCode()
	{
    	/* Verify the Response Body and status code */
        Response res=
        given()
        .when()
        	.get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ")

        .then()
        
        	.statusCode(200)
        	.log().body()
        	.extract().response();
        	
        	String jsonBody=res.asString();
        	System.out.print(jsonBody);
        	

        	Assert.assertEquals(jsonBody.contains("promotion"), true,"contains promotion Parameter");
        	Assert.assertEquals(jsonBody.contains("promotionId"), true,"contains promotionID Parameter");
        	Assert.assertEquals(jsonBody.contains("orderId"), true,"contains orderId Parameter");
        	Assert.assertEquals(jsonBody.contains("promoArea"), true,"contains promoArea Parameter");
        	Assert.assertEquals(jsonBody.contains("promoType"), true,"contains promoType Parameter");
        	Assert.assertEquals(jsonBody.contains("showPrice"), true,"contains showPrice Parameter");
        	Assert.assertEquals(jsonBody.contains("showText"), true,"contains showText Parameter");
        	Assert.assertEquals(jsonBody.contains("localizedTexts"), true,"contains localizedTexts Parameter");
        	Assert.assertEquals(jsonBody.contains("showText"), true,"contains showText Parameter");
        	
        	
    }
    @Test
    public void verifyResponseBody_Values()
 	{
    	/* Verify the Response Body values*/
    	
    	
    	String[] a ={"EPISODE","MOVIE" ,"SERIES","SEASON" };
    	Response response = 
    			 given().when()
    			 .get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ")
    			 .then()
    			 .assertThat()
    			// .body("promotions.properties.programType", hasItems(hasEntry("episode","movie", "series"))).log()
    			 .log().body()
    			 .extract().response();
	    		 	
    	    			 
    			 JsonPath extractor= response.jsonPath();

    			
    			 ArrayList<String> IDList = extractor.get("promotions.promotionId");
    			 ArrayList <String> ProgramType = extractor.get("promotions.properties.programType");
    			 
    			
    			 
    			 Boolean assertflag= true;
    			 for (String Id : IDList) { 
    				 if (Id == null || Id.trim().isEmpty()) {
    					  assertflag= false;
    				 }
    			 }
    			 
    			 
    			 Assert.assertTrue(assertflag,"Promotion ID is string and non empty");
    			   			 
    			 
    			 assertflag= true;
    			 
    			 for (String Id : IDList) { 
    				 if (Id == null || Id.trim().isEmpty()) {
    					  assertflag= false;
    				 }
    			 }
    			 
    			 Assert.assertTrue(assertflag,"Promotion Type is string contains values EPISODE\",\"MOVIE\" ,\"SERIES\",\"SEASON\" ");
    			 
 	}
	@Test
    public void CheckForErrorCode()
	{
    	/* sending the wrong API key to verify the error response */
    Response response = 
	 given().when()
	 .get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVMzNDdUyZ")
	 .then().log().body()
 	.extract().response();
 	
	 
	 JsonPath extractor= response.jsonPath();
	 
	 String Errormessage = extractor.get("error.message");
	 String Errorcode = extractor.get( "error.code");
	 String requestID = extractor.get( "error.requestId");
	 

	 
	 Assert.assertEquals(Errormessage.contains("invalid api key"), true,"erromessage is displayed as invalid api key");
	 Assert.assertEquals(Errorcode, "8001","erromessage is displayed as invalid api key");
	 Assert.assertEquals(requestID.isEmpty(), false,"RequestID is not null");

        	
        	
        	
        	
        	
        	
        
    }
}
