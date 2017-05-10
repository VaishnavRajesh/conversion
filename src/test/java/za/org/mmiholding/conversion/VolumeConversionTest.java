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
public class VolumeConversionTest 
   
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
	public void validDataForLitre() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
		+Util.frameString("volume", "litre", "cubic_meter", "372")))
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
		assertTrue((resultValue - 0.372 < 1) && (resultValue - 0.372 > -1));
	}
	
	@Test
	public void validDataForcubicMeter() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("volume", "cubic_meter", "litre", ".996")))
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
				
		assertTrue((resultValue - 996 < 1) && (resultValue - 996 > -1));
		
	}
	
	@Test
	public void invalidUnitType() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("volume", "litre", "cubic_centimeter", "37.2")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		
		assertTrue("Unknown from/to unit".equals(calculatedResult));
	}
	
	@Test
	public void invalidUnitValue() throws Exception {
		MvcResult result = this.mvc.perform(post(Util.RELATIVE_URL
				+Util.frameString("volume", "litre", "cubic_meter", "-2")))
				.andExpect(status().isOk())
				.andReturn();
		String calculatedResult = Util.getResponseFromResult(result);
		System.out.println(calculatedResult);
		assertTrue(calculatedResult.indexOf("Volume should not be less than 0")>-1);
	}
	


	   
}
