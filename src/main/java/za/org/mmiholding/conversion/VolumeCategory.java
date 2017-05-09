package za.org.mmiholding.conversion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("volume")
public class VolumeCategory implements Category{


	public enum units {
		L("Litre"),
		M3("Cubic Meter");

		private String name;

		units(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	@Override
	public String calculate(String fromUnit, String toUnit, Double volume) {
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
		if( volume < 0){
			throw new RuntimeException("Volume cannot be 0 in "+ fromUnitName +" scale ");
		}

		if("L".equals(fromUnit) ){
			return (volume / 1000 ) + toUnitName;
		}else if("M3".equals(fromUnit) ){
			return ( volume * 1000) + toUnitName;
		}else{
			return null;
		}		

	}
}