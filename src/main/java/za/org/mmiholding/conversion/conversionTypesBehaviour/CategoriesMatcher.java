package za.org.mmiholding.conversion.conversionTypesBehaviour;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
	public class CategoriesMatcher  {
	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;

	    @Autowired
	    @Qualifier("volume")
	    Category volume;

	    @Autowired
	    @Qualifier("temperature")
	    Category temperature;
	    
	    @Autowired
	    @Qualifier("area")
	    Category area;

		public HashMap<String, Category> getCategories() {
			HashMap<String, Category> categories = new HashMap<String, Category>();
			categories.put("volume", volume);
			categories.put("temperature", temperature);
			categories.put("area", area);
			
			logger.debug("Categories hashmap is intialized.........");
			return categories;
		}
	    

}