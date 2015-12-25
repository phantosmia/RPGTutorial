package gameObject;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import engine.Delay;
import engine.Game;
import engine.GameObject;
import engine.Main;
import engine.Time;
import item.Item;
import static org.lwjgl.opengl.GL11.*;
public class Player extends StatObject {
	public static final float SIZE = 32;
	private int attackRange;
	private int facingDirection;
	public static final int FORWARD = 0;
	public static final int BACKWARD = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	private Delay attackDelay;
	private int attackDamage;
	private Inventory inventory;
	private Equipment equipment;
	public Player(float x, float y) {
		init(x, y, 0.1f, 1, 0.25f, SIZE, SIZE, PLAYER_ID);
		stats = new Stats(0, true);
		inventory = new Inventory(20);
		attackRange = 49;
		attackDamage = 1;
		attackDelay = new Delay(500);
		facingDirection = 0;
		moveAmountX = 0;
		moveAmountY = 0;
		attackDelay.terminate();
		equipment = new Equipment(inventory);
	}

	private int moveAmountX;
	private int moveAmountY;
	@Override
	public void render(){
		glTranslatef(Display.getWidth() / 2 - Player.SIZE / 2, Display.getHeight() / 2 - Player.SIZE / 2,0);
		spr.render();
		glTranslatef(-x,-y,0);
	}
	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			move(0, 1);
			System.out.println("receiving");
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			move(0, -1);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			move(-1, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			move(1, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackDelay.isOver())
			attack();
	}

	public void attack() {
		System.out.println("We are attacking");
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		attackDelay.restart();
		if (facingDirection == FORWARD)
			objects = Game.rectangleCollide(x, y, x + SIZE, y + attackRange);
		else if (facingDirection == BACKWARD)
			objects = Game.rectangleCollide(x, y - attackRange + SIZE, x + SIZE, y);
		else if (facingDirection == LEFT)
			objects = Game.rectangleCollide(x - attackRange + SIZE, y, x, y + SIZE);
		else if (facingDirection == RIGHT)
			objects = Game.rectangleCollide(x, y, x + attackRange, y + SIZE);
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for (GameObject go : objects) {
			if (go.getType() == ENEMY_ID) {
				enemies.add((Enemy) go);
			}
		}
		if (enemies.size() > 0) {
			Enemy target = enemies.get(0);
			if (enemies.size() > 1) {
				for (Enemy e : enemies) {
					if (Util.dist(x, y, e.getX(), e.getY()) < Util.dist(x, y, target.getX(), target.getY()))
						target = e;

				}
			}
			target.damage(attackDamage);
			System.out.println(target.getCurrentHealth() + "/" + target.getMaxHealth());
		} else {
			System.out.println("No target");
		}
		attackDelay.restart();
	}

	private void move(float magX, float magY) {
		if (magX == 0 && magY == 1)
			facingDirection = FORWARD;
		if (magX == 0 && magY == -1)
			facingDirection = BACKWARD;
		if (magX == -1 && magY == 0)
			facingDirection = LEFT;
		if (magX == 1 && magY == 0)
			facingDirection = RIGHT;
		// System.out.println(getSpeed());
		
		moveAmountX += getSpeed() * magX * Time.getDelta();
		moveAmountY += getSpeed() * magY * Time.getDelta();
		
	}

	@Override
	public void update() {
		float newX = x + (float) moveAmountX;
		float newY = y + (float) moveAmountY;
		if (stats.getCurrentHealth() <= 0)
			die();
		moveAmountX = 0;
		moveAmountY = 0;
		ArrayList<GameObject> objects = Game.rectangleCollide(newX, newY, newX + SIZE, newY + SIZE);
		ArrayList<GameObject> itens = new ArrayList<GameObject>();
		boolean move = true;
		for (GameObject go : objects) {
			if (go.getType() == GameObject.ITEM_ID) {
				itens.add(go);	
			}
			if(go.getSolid()){
				move = false;
			}
		
		}
		if(!move)
			return;
		x = newX;
		y = newY;
		for(GameObject go : itens){
			 addItem((Item) go);
			 System.out.println("Damn " + ((Item) go).getName());
			 go.remove();
		}
		/*  System.out.println("Stats:"+getLevel());
		 * 
		 * 
		 */
	}

	protected void die() {
		// remove();
	}

	public void addXp(float amt) {
		stats.addXp(amt);
	}

	public void addItem(Item item) {
		inventory.add(item);
	}
}
