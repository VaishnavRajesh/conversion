package za.org.mmiholding.conversion.conversionTypes;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import za.org.mmiholding.conversion.conversionTypesBehaviour.Category;

@Component
@Qualifier("volume")
public class VolumeCategory implements Category{

	private static final Logger logger =  LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;
	
	public enum units {
		LITRE("Litre"),
		CUBIC_METER("Cubic Meter");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public Double calculate(String convertFrom, String convertTo, Double volumeValue) {
		
		logger.debug("Entered VolumeCategory calculation block.............");
		logger.debug("The input parameters are convertFrom - "+convertFrom+","
				+ " convertTo - "+convertTo+", volumeValue = "+volumeValue);
		
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
		if( volumeValue < 0){
			logger.debug("Invalid argument - volume cannot be 0");
			throw new RuntimeException("Volume should not be less than 0");
		}

		if(units.valueOf(convertTo)==units.LITRE &&  units.valueOf(convertFrom)==units.CUBIC_METER){
			return (volumeValue * 1000 );
		}else if(units.valueOf(convertTo)==units.CUBIC_METER && units.valueOf(convertFrom)==units.LITRE ){
			return ( volumeValue / 1000);
		}else{
			throw new RuntimeException("Unknown from/to unit");
		}		

	}
}