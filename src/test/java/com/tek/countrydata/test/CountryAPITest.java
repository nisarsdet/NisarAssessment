package com.tek.countrydata.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tek.countrydata.CountryAPI;

public class CountryAPITest {
	@Test(enabled = true)
	public void testCapitalByCode() {
		CountryAPI obj = new CountryAPI();
		Assert.assertEquals(obj.getCapitalByCountryCode("ind"), "New Delhi");
		Assert.assertEquals(obj.getCapitalByCountryCode("USA"), "Washington, D.C.");
	}
	
	@Test(enabled = true)
	public void testCapital_InvalidCode() {
		CountryAPI obj = new CountryAPI();
		Assert.assertNull(obj.getCapitalByCountryCode("uk"));
		Assert.assertNull(obj.getCapitalByCountryCode("123"));
		Assert.assertNull(obj.getCapitalByCountryCode("UAE"));
	}
	
	@Test(enabled = true)
	public void testCapitalByName() {
		CountryAPI obj = new CountryAPI();
		Map<String, String> capitalMap = obj.getCapitalByCountryName("United");
		Assert.assertTrue(capitalMap.size()>0);
	}
	
	@Test(enabled = true)
	public void testCapital_InvalidName() {
		CountryAPI obj = new CountryAPI();
		Map<String, String> capitalMap = obj.getCapitalByCountryName("xyz");
		Assert.assertTrue(capitalMap.size()==0);
	}
	
	
}
