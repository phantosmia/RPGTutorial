package gameObject;

import java.util.ArrayList;

import engine.Delay;
import engine.Game;
import engine.GameObject;
import engine.Main;
import engine.Sprite;
import engine.Time;

public class Enemy extends StatObject {
	private float sightRange;
	private float ATTACK_RANGE;
	private StatObject target;
	public static final float DAMPING = 0.5f;
	private int attackDamage;
	private Delay attackDelay;
	public Enemy(int level) {
		stats = new Stats(0, false);
		target = null;
		attackDamage = 1;
		attackDelay = new Delay(500);
		ATTACK_RANGE = 48f;
		attackDelay.terminate();
		sightRange = 128f;
	
	}

	@Override
	public void update() {
		if (target == null)
			look();
		else {
			if (Util.lineOfSight(this, target)
					&& Util.dist(x, y, getTarget().getX(), getTarget().getY()) <= ATTACK_RANGE){
				if(attackDelay.isOver())
					attack();
				}
			else
				chase();

			if (stats.getCurrentHealth() <= 0)
				die();
		}

	}

	protected void attack() {
		getTarget().damage(getAttackDamage());
		System.out.println("Health:"+getTarget().getCurrentHealth() + "/" + getTarget().getMaxHealth());
		attackDelay.restart();
	}
	protected void die() {
		remove();
	}
	protected void look() {
		ArrayList<GameObject> objects=  Game.sphereCollide(x, y, sightRange);
		for(GameObject go : objects){
			if(go.getType() == PLAYER_ID)
			 setTarget((StatObject)go);
		}
	}

	protected void chase() {
		float speedX = (getTarget().getX() - x);
		float speedY = (getTarget().getY() - y);
		float maxSpeed = getStats().getSpeed() * DAMPING;
		if(speedX > maxSpeed)
			speedX = maxSpeed;
		if (speedX < maxSpeed)
			speedX = -maxSpeed;
		if(speedY > maxSpeed)
			speedY = maxSpeed;
		if (speedY < maxSpeed)
			speedY = -maxSpeed;
		x = x + speedX * Time.getDelta();
		y = y + speedY * Time.getDelta();
	}



	public void setTarget(StatObject go) {
		target = go;
	}

	public StatObject getTarget() {
		return this.target;
	}

	public Stats getStats() {
		return stats;
	}
	public int getAttackDamage(){
		return attackDamage;
	}
	public void setAttackRange(int range){
		ATTACK_RANGE = range;
	}
	public void setAttackDelay(int time){
		attackDelay = new Delay(time);
		attackDelay.terminate();
	}
	public void setAttackDamage(int amt){
		this.attackDamage = amt;
	}
	
	public void setSightRange(float dist){
		this.sightRange = dist;
	}
	@Override
	protected void init(float x, float y, float r, float g, float b, float sx, float sy, int type){
		this.x = x;
		this.y = y;
		this.type = ENEMY_ID;
		this.spr = new Sprite(r,g,b,sx,sy);

	}
}
