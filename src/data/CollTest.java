package data;

import java.awt.Point;

public class CollTest {
	public boolean Test(Point xy1,Point xy2,Point wh1,Point wh2){
		int x1 = xy1.x;
		int y1 = xy1.y;
		int x2 = xy2.x;
		int y2 = xy2.y;
		int w1 = wh1.x;
		int h1 = wh1.y;
		int w2 = wh2.x;
		int h2 = wh2.y;
		if(x1>=x2&&x1<=x2+w2&&y1>=y2&&y1<=y2+h2){//前者入后者
			return true;
		}else if(x1+w1>=x2&&x1+w1<=x2+w2&&y1+h1>=y2&&y1+h1<=y2+h2){
			return true;
		}else if(x1+w1>=x2&&x1+w1<=x2+w2&&y1>=y2&&y1<=y2+h2){
			return true;
		}else if(x1>=x2&&x1<=x2+w2&&y1+h1>=y2&&y1+h1<=y2+h2){
			return true;
		}else if(x2>=x1&&x2<=x1+w1&&y2>=y1&&y2<=y1+h1){//后者入前者
			return true;
		}else if(x2+w2>=x1&&x2+w2<=x1+w1&&y2+h2>=y1&&y2+h2<=y1+h1){
			return true;
		}else if(x2+w2>=x1&&x2+w2<=x1+w1&&y2>=y1&&y2<=y1+h1){
			return true;
		}else if(x2>=x1&&x2<=x1+w1&&y2+h2>=y1&&y2+h2<=y1+h1){
			return true;
		}else{
			return false;
		}
	}
}
