package aletrainsystem.enums;

public enum PointSwitchConnectorEnum {
	
	THROUGH(0, "t", SleeperColor.YELLOW), DIVERT(-180, "d", SleeperColor.GREEN), ENTRY(0, "e", SleeperColor.RED);
	
	private int rotationAngle;
	private String shorthand;
	private SleeperColor color;
	private PointSwitchConnectorEnum(int rotationAngle, String shorthand, SleeperColor color){
		this.rotationAngle = rotationAngle;
		this.shorthand = shorthand;
		this.color = color;
	}
	
	public int angle(){
		return rotationAngle;
	}
	
	public String shorthand(){
		return shorthand;
	}
	
	public SleeperColor color(){
		return color;
	}
	
}
