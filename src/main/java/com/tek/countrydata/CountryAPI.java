package com.tek.countrydata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tek.utils.Config;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CountryAPI {
	private static Config config;
	private String baseURL;
	static Logger logger = Logger.getLogger(CountryAPI.class);

	static {
		DOMConfigurator.configure("log4j.xml");
		String configPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator
				+ "config.properties";
		try {
			config = new Config(new File(configPath).getCanonicalPath());
		} catch (IOException e) {
			logger.error("Error in loading config file placed at " + configPath, e);
		}
	}
	
	public CountryAPI() {
		baseURL = config.getPropertyValue("BaseURL");
		RestAssured.baseURI = baseURL;
	}
	
	public String getCapitalByCountryCode(String countryCode) {
		String capital = null;
		Response response = null;

		if (StringUtils.isEmpty(countryCode) || countryCode.trim().length() != 3) {
			logger.error("Invalid country code");
			return capital;
		}

		try {
			response = RestAssured.given().header("Content-Type", "application/json").relaxedHTTPSValidation()
					.get("/alpha/" + countryCode);

			if (response.getStatusCode() != HttpStatus.SC_OK) {
				logger.error("Error response : " + response.getStatusCode());
			} else {
				capital = response.jsonPath().get("capital");
			}
		} catch (Exception e) {
			logger.error("Error in hitting endpoint " + baseURL + "/alpha/" + countryCode, e);
		}

		return capital;
	}

	public Map<String, String> getCapitalByCountryName(String countryName) {
		Map<String, String> capitalMap = new HashMap<>();
		Response response = null;

		if (StringUtils.isEmpty(countryName)) {
			logger.error("Invalid country name");
			return capitalMap;
		}

		try {
			response = RestAssured.given().header("Content-Type", "application/json").relaxedHTTPSValidation()
					.get("/name/" + countryName);

			if (response.getStatusCode() != HttpStatus.SC_OK) {
				logger.error("Error response : " + response.getStatusCode());
			} else if(response.asString().contains("Not Found")) {
				logger.error("Invalid country : " + countryName);
			} else {
				JSONArray jArray = new JSONArray(response.asString());
				
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObj = jArray.getJSONObject(i);
					capitalMap.put(jObj.getString("name"), jObj.getString("capital"));
				}
			}
		} catch (Exception e) {
			logger.error("Error in hitting endpoint " + baseURL + "/name/" + countryName, e);
		}

		return capitalMap;
	}
}
