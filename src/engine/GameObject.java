package engine;
import static org.lwjgl.opengl.GL11.*;
public abstract class GameObject {
protected float x;
protected Sprite spr;
protected int type;
protected boolean[] flags = new boolean[2];
public float getX() {
	return x;
}
public void setX(float x) {
	this.x = x;
}
public float getY() {
	return y;
}
public void setY(float y) {
	this.y = y;
}
public float getSx() {
	return spr.getSx();
}

public float getSy() {
	return spr.getSy();
}

public Animation getAnim() {
	return anim;
}
public void setAnim(Animation anim) {
	this.anim = anim;
}
protected float y;
protected float sx;
protected float sy;
public static final int ENEMY_ID = 3 ;
public static final int PLAYER_ID = 2;
public static final int ITEM_ID = 1;
public static final int DEFAULT_ID = 0;
protected Animation anim;
public void update(){
	
}
public void render(){
	glPushMatrix();
	{
	glTranslatef(x,y,0);
	spr.render();
	}
	glPopMatrix();
}
public int getType(){
	return type;
}
public boolean getRemove(){
	return flags[0];
}
public void remove(){
	flags[0] = true;
}
public boolean getSolid(){
	return flags[1];
}
public void setSolid(boolean value){
	flags[1] = value;
}
protected void init(float x, float y, float r, float g, float b, float sx, float sy, int type){
	this.x = x;
	this.y = y;
	this.type = type;
	this.spr = new Sprite(r,g,b,sx,sy);

}
}
