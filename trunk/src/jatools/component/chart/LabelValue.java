package jatools.component.chart;

public class LabelValue implements Comparable {

	String label;

	int index;

	public LabelValue(String label, int index) {

		this.label = label;
		this.index = index;
	}

	public int compareTo(Object o) {
		LabelValue that = (LabelValue) o;
		if (this.index == that.index)
			return 0;
		else if (this.index > that.index)
			return 1;
		else
			return -1;

	}

	public String toString() {
		return label;

	}

}
