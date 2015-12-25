package item;

import engine.Game;
import engine.Physics;
import gameObject.Player;

public class Cube extends Item {
	public static final float SIZE = 32;
	
public Cube(float x, float y){
	init(x,y,1.0f,0.5f,0,SIZE,SIZE,"The Cube");
}
}
