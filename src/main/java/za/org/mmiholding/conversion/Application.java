package za.org.mmiholding.conversion;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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

@SpringBootApplication
@RestController
public class Application {

    
    
    @Autowired
	private CategoriesHelper categoriesHelper;

	private HashMap<String, Category> categories;

	@PostConstruct
	public void setup() {
		categories = categoriesHelper.getCategories();
	}
	

    public static void main(String[] args) {
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
			return "Metric - Imperial conversion";
		}
	}
	
/*	public static boolean isSecure (HttpServletRequest request) {
	    String protocol = request.getHeader("x-forwarded-proto");

	    if (protocol == null) {
	        return false;
	    }
	    else if (protocol.equals("https")) {
	        return true;
	    }
	    else {
	        return false;
	    }
	}
	*/
	@CrossOrigin(origins = "*")
   @RequestMapping(value = "/unitconversion",method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String  process(@RequestParam("type") String type, @RequestParam("fromUnit") String fromUnit ,@RequestParam("toUnit") String toUnit,@RequestParam("value") double value) throws Exception {
		Category category = categories.get(type);
			
		String convertedValue =  (String) category.calculate( fromUnit, toUnit, value);
		 JSONObject json = new JSONObject();
         json.put("Status", "200");
         json.put("Response", convertedValue);
         return json.toString();

		}

}
