package data;

public class Column {
	private boolean in = false;
	private int up;
	private int d;
	private int x;
	public Column(int up,int d,int px){
		this.up = up;
		this.d = d;
		x = px;
	}
	public int getUp(){
		return up;
	}
	public int getD(){
		return d;
	}
	public int getX(){
		return x;
	};
	public void move(){
		x--;
	}
	public void setIn(boolean in){
		this.in = in;
	}
	public boolean getIn(){
		return in;
	}
}
