package za.org.mmiholding.conversion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("area")
public class AreaCategory implements Category{


	public enum units {
		M2("Square Meter"),
		A("Acre");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public String calculate(String fromUnit, String toUnit, Double area) {
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

		
		if( area < 0){
			throw new RuntimeException("Area cannot be 0 in "+ fromUnitName +" scale ");
		}

		if("M2".equals(fromUnit) ){
			return (area * 4046.86 ) + toUnitName;
		}else if("A".equals(fromUnit) ){
			return ( area * 4046.86) + toUnitName;
		}else{
			return null;
		}		

	}
}