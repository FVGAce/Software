public class tile {
	private int tileValue = 0;
	private String image = "";
	
	public tile() {}
	
	public tile(int tileValue, String image) {
		this.tileValue = tileValue;
		this.image = image;
	}
	
	public void addTileValue(int tileValue) {
		this.tileValue += tileValue; 
	}
	
	public int getTileValue() {
		return tileValue;
	}
	
	public void setTileValue(int tileValue) {
		this.tileValue = tileValue;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}
