package turtle.model;

import org.newdawn.slick.Image;

public class Obstacle 
{
	private String name;
	private Image icon;
	private HealthBar health;
	private int threshold;
	public int xPos;
	public int yPos;
	
	public Obstacle(String name, Image icon, int health, int threshold, int xPos, int yPos)
	{
		this.name = name;
		this.icon = icon;
		this.threshold = threshold;
		this.health = new HealthBar(health);
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void doDamage(int damage)
	{
		//Damage: is like: how many times did you cross the threshold. Health is estimated at health * threshold but small attacks don't work
		damage = Math.abs(damage);
		while (damage > threshold && health.getHealth() > 0)
		{
			health.subtractHealth(1);
			damage -= threshold;
		}
	}
	
	public Image getImage()
	{
		return icon;
	}
	
	public int getHealth()
	{
		return health.getHealth();
	}
	
	public String getName()
	{
		return name;
	}
	
}
