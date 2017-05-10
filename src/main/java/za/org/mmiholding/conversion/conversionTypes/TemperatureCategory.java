package za.org.mmiholding.conversion.conversionTypes;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import za.org.mmiholding.conversion.conversionTypesBehaviour.Category;

@Component
@Qualifier("temperature")
public class TemperatureCategory implements Category{

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	
	public enum units {
		FAHRENHEIT("Fahrenheit"),
		CELCIUS("Celcius");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public Double calculate(String convertFrom, String convertTo, Double temperatureValue) {

		logger.debug("Entered TemperatureCategory calculation block.............");
		logger.debug("The input parameters are convertFrom - "+convertFrom+","
				+ " convertTo - "+convertTo+", volumeValue = "+temperatureValue);
		
		convertFrom = convertFrom.toUpperCase();
		convertTo = convertTo.toUpperCase();

		try{
			units.valueOf(convertFrom);
			units.valueOf(convertTo);
		}catch(Exception e){
			logger.debug("Exception occured - The convertfrom and covertTo units are not defined in the enum");
			e.printStackTrace();
			throw new RuntimeException("Unknown from/to unit");
		}

		if(units.valueOf(convertFrom)==units.FAHRENHEIT && temperatureValue < -459.67){
			logger.debug("Invalid argument - Temperature cannot be less than -459.67 F");
			throw new RuntimeException("Temperature cannot be less than -459.67 in "
					+ ""+ convertFrom +" scale ");
		}

		if(units.valueOf(convertFrom)==units.CELCIUS && temperatureValue < -273.15){
			logger.debug("Invalid argument - Temperature cannot be less than -273.15 C");
			throw new RuntimeException("Temperature cannot be less than -273.15 in "
					+convertFrom +" scale ");
		}


		if(units.valueOf(convertTo)==units.CELCIUS && units.valueOf(convertFrom)==units.FAHRENHEIT){
			return ((temperatureValue - 32 ) * (double)0.5555 );
		}else if(units.valueOf(convertTo)==units.FAHRENHEIT && units.valueOf(convertFrom)==units.CELCIUS ){
			return (1.8 * temperatureValue  + 32);
		}else{
			throw new RuntimeException("Unknown from/to unit");
		}
		

	}
}