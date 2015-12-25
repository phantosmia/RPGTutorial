package gameObject;

import java.util.ArrayList;

import engine.GameObject;
import engine.Main;

public class TheCookieMonster extends Enemy {
	public static final int SIZE = 32;
	public TheCookieMonster(float x, float y, int level) {
		super(level);
		init(x, y, 0.2f, 0.2f, 1.0f, SIZE, SIZE, 0);
		setAttackDelay(200);
		// TODO Auto-generated constructor stub
	}
}
