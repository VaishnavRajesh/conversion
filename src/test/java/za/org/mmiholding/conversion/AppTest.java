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
/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest 
   
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
	public void testHome() throws Exception {

		MvcResult result = this.mvc.perform(post("/unitconversion?type=temperature&fromUnit=C&toUnit=F&value=32")).andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		logger.error(content);
		assertTrue(content.indexOf("Fahrenheit") == -1);
		
				
}
    
    
}
