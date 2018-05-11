package turtle.model;

import org.newdawn.slick.Image;

public class HealthBar 
{
	private int health;
	private Image icon;
	
	public HealthBar(int health)
	{
		this.health = health;
	}
	
	public void subtractHealth(int damage)
	{
		health = health - damage;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public Image getIcon()
	{
		return icon;
	}
}
