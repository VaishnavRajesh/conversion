package za.org.mmiholding.conversion;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.invoke.MethodHandles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import za.org.mmiholding.conversion.Utilities.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemperatureConversionTest 
   
{
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	
	@Test
	public void validDataForCelcius() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
		+Util.frameString("temperature", "celcius", "fahrenheit", "37.2")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		Double resultValue = null;
		try{
			resultValue = Double.parseDouble(calculatedResult);
		}catch (Exception e) {
			logger.debug("Incorrect result - Expected double ");
			assertTrue(false);
		}				
		assertTrue((resultValue - 98.96 < 1) && (resultValue - 98.96 > -1));
	}
	
	@Test
	public void validDataForFahrenheit() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("temperature", "fahrenheit", "celcius", "98.96")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		Double resultValue = null;
		try{
			resultValue = Double.parseDouble(calculatedResult);
		}catch (Exception e) {
			logger.debug("Incorrect result - Expected double ");
			assertTrue(false);
		}
				
		assertTrue((resultValue - 37.2 < 1) && (resultValue - 37.2 > -1));
		
	}
	
	@Test
	public void invalidUnitType() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("temperature", "celcius", "kelvin", "37.2")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		
		assertTrue("Unknown from/to unit".equals(calculatedResult));
	}
	
	@Test
	public void invalidUnitValue() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("temperature", "celcius", "fahrenheit", "-500")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		
		assertTrue(calculatedResult.indexOf("Temperature cannot be less than -273.15")>-1);
	}

	   
}
