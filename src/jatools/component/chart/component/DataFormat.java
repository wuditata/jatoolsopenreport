package jatools.component.chart.component;

import java.text.Format;
import java.text.NumberFormat;

public class DataFormat {
	NumberFormat format = NumberFormat.getNumberInstance();
	int precision;
	int example = 1000000;
	
	public DataFormat(){
		
	}
	
	public DataFormat(NumberFormat format, int precision){
		if(format != null)
			this.format = format;
		this.precision = precision;
	}
	
	public String toString() {
		//format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(precision);
		format.setMaximumFractionDigits(precision);
		return format.format(example);
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(NumberFormat format) {
		this.format = format;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
	
	
	public boolean equals(Object obj) {
		DataFormat d = (DataFormat) obj;
		return (format.equals(d.format) && precision == d.precision);
	}

	public static void main(String[] args){
		DataFormat f = new DataFormat(NumberFormat.getPercentInstance(), 2);
		DataFormat f1 = new DataFormat(NumberFormat.getPercentInstance(), 2);

		System.out.println(f.equals(f1));
	}
}
