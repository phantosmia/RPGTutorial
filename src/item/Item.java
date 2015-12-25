package item;

import engine.GameObject;
import engine.Physics;
import engine.Sprite;
import gameObject.Player;

public class Item extends GameObject{
protected String name;

public String getName(){
	return name;
}

protected void init(float x, float y, float r, float g, float b, float sx, float sy, String name){
	this.x = x;
	this.y = y;
	this.type = ITEM_ID;
	this.spr = new Sprite(r,g,b,sx,sy);
	this.name = name;
}
}
