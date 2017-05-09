package za.org.mmiholding.conversion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("temperature")
public class TemperatureCategory implements Category{


	public enum units {
		F("Fahrenheit"),
		C("Celcius");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public String calculate(String fromUnit, String toUnit, Double temperature) {
		String fromUnitName = null;
		String toUnitName = null;
		fromUnit = fromUnit.toUpperCase();
		toUnit = toUnit.toUpperCase();

		try{
			fromUnitName = units.valueOf(fromUnit).getName();
			toUnitName = units.valueOf(toUnit).getName();
		}catch(Exception e){
			throw new RuntimeException("Unable to identify the from/to unit symbol");
		}

		if("F".equals(fromUnit) && temperature < -459.67){
			throw new RuntimeException("Temperature cannot be less than -459.67 in "+ fromUnitName +" scale ");
		}

		if("C".equals(fromUnit) && temperature < -273.15){
			throw new RuntimeException("Temperature cannot be less than -73.15 in "+fromUnitName +" scale ");
		}


		if("C".equals(toUnit) ){
			return ((temperature - 32 ) * 5/9 ) + toUnitName;
		}else if("F".equals(toUnit) ){
			return ((9/5) * (temperature + 32)) + toUnitName;
		}else{
			return null;
		}
		

	}
}