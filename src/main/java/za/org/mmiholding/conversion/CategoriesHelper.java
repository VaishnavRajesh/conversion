package za.org.mmiholding.conversion;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
	public class CategoriesHelper  {

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
			return categories;
		}
	    

}