package za.org.mmiholding.conversion.Utilities;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MvcResult;

public class Util {

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	public static final String RELATIVE_URL = "/unitconversion";
	
	public static String frameString(String conversionType,String convertFrom, 
			String convertTo, String value){
		return "?conversionType="+conversionType+"&convertFrom="+convertFrom+""
				+ "&convertTo="+convertTo+"&value="+value;
	}


public static String getResponseFromResult(MvcResult result) throws UnsupportedEncodingException {
	String content = result.getResponse().getContentAsString();
	System.out.println(content);
	logger.debug("Test response received for test case "+content);
	return new JSONObject(content).getString("Result");		
}
}
