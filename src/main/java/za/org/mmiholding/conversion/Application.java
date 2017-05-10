package za.org.mmiholding.conversion;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import za.org.mmiholding.conversion.conversionTypesBehaviour.CategoriesMatcher;
import za.org.mmiholding.conversion.conversionTypesBehaviour.Category;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private CategoriesMatcher categoriesMatcher;

	private HashMap<String, Category> categories;

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	
	@PostConstruct
	public void setup() {
		categories = categoriesMatcher.getCategories();
		logger.debug("Categories initialized from categoriesMatcher.............");
	}


	public static void main(String[] args) {
		logger.debug("Application execution started .............");
		SpringApplication.run(Application.class, args);
	}	

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}


	@RestController
	public class HomeContoller {
		@RequestMapping("/")
		public String index() {
			logger.debug("In Home Controller block .............");
			return "Metric - Imperial conversion";
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/unitconversion",method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String  process(@RequestParam("conversionType") String conversionType,
			@RequestParam("convertFrom") String convertFrom ,
			@RequestParam("convertTo") String convertTo,@RequestParam("value") double unitValueTobeConverted) throws Exception {
		
		logger.debug("unit conversion rest service execution started .............");
		logger.debug("The input parameters are conversionType - "+conversionType+", convertFrom - "+convertFrom+","
				+ " convertTo - "+convertTo+", unitValueTobeConverted = "+unitValueTobeConverted);
		
		
		Category category = categories.get(conversionType);
		
		String status = null;
		String result = null;
		String units = null;
		if(category != null){
			try{
				if(convertFrom.equalsIgnoreCase(convertTo)){
					result =  String.valueOf(unitValueTobeConverted);
					logger.debug("Convert from and convert to are same unit types.............");
				}else{
					result = String.valueOf(category.calculate( convertFrom, convertTo, unitValueTobeConverted));
					logger.debug("Success response received .............");
					logger.debug("Calculated value is "+result);
				}				
				status = "200";	
				units = convertTo;
			}catch(RuntimeException e){
				result = e.getMessage();
				logger.debug("Exception occured .............");
				logger.debug("Exception message is "+result);
				e.printStackTrace();
				status = "500";
			}
		}else{
			logger.debug("Unknown category received .............");
			result = "Unknown conversion type";
			status = "400";
		}
		
		
		JSONObject json = new JSONObject();
		json.put("Status", status);
		json.put("Result", result);
		json.put("Units", convertTo);
		return json.toString();
	}

}
