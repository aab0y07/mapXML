public class CityInfo {
	
	private String fullName;
	private int x;
	private int y;

	public CityInfo(){}
	public CityInfo(String fullName, int x, int y) {
		this.fullName = fullName;
		this.x = x;
		this.y = y;
	}

	public String getFullName() {
		return fullName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return fullName + " (" + x + "/" + y + ")";
	}

}
