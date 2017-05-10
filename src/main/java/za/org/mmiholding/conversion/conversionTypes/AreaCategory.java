package za.org.mmiholding.conversion.conversionTypes;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import za.org.mmiholding.conversion.conversionTypesBehaviour.Category;

@Component
@Qualifier("area")
public class AreaCategory implements Category{

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	
	public enum units {
		SQUARE_METER("Sq.Meter"),
		ACRE("Acre");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public Double calculate(String convertFrom, String convertTo, Double areaValue) {
		
		logger.debug("Entered AreaCategory calculation block.............");
		logger.debug("The input parameters are convertFrom - "+convertFrom+","
				+ " convertTo - "+convertTo+", areaValue = "+areaValue);
		
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
		
		if( areaValue < 0){
			logger.debug("Invalid argument - area cannot be 0");
			throw new RuntimeException("Area should not be less than 0");
		}

		if(units.valueOf(convertFrom)==units.ACRE && units.valueOf(convertTo)==units.SQUARE_METER){
			return (areaValue * 4046.86 );
		}if(units.valueOf(convertFrom)==units.SQUARE_METER && units.valueOf(convertTo)==units.ACRE){
			return ( areaValue / 4046.86);
		}else{
			throw new RuntimeException("Unknown from/to unit");
		}		

	}
}