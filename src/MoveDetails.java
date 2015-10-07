
public class MoveDetails implements java.io.Serializable{

	private static final long serialVersionUID = 3L;
	private int x; 
	private int y; 
	
	
	private char z;
	
	public MoveDetails(int x, int y, char z)
	{
		this.x = x;
		this.y = y;
		this.z = z;		
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	public char getState()
	{
		return z;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setState(char z)
	{
		this.z = z;
	}
	
	public boolean equals(Object object) {
		MoveDetails compare = (MoveDetails) object;
	    if(getX() == compare.getX() && getY() == compare.getY() && getState() == compare.getState()) { 
	        return true;
	    }
	    else return false;
	}
}
