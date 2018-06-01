package turtle.view;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import java.util.List;
import java.util.ArrayList;



import turtle.model.Obstacle;



public class TurtleController extends BasicGame
{
	private int xVelocity = 0;
	private int yVelocity = 0;
	
	private int xAccel = 0;
	private int yAccel = 0;
	
	private int xPos;
	private int yPos;
	private int level;
	
	private Animation swim;
	
	private Animation turtle;
	private Image swim1;
	private Image swim2;
	private Animation shell;
	
	private Obstacle seaweed;
	private List<Obstacle> obstacleList;
	
	public TurtleController() {
		super("TURTLE");
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer app = new AppGameContainer(new TurtleController());
			app.setDisplayMode(800, 800, false);
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.setVSync(true);
			app.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException 
	{
		g.setColor(new Color(30, 144, 255));
		g.fillRect(0, 0, 800, 800);
		turtle.draw(xPos, yPos);
		for(Obstacle obstacle : obstacleList)
		{
			obstacle.getImage().draw(obstacle.xPos, obstacle.yPos);
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException 
	{
		swim1 = new Image("/src/turtle/view/Turtle1.png");
		swim2 = new Image("/src/turtle/view/Turtle2.png");
		
		Image[] shellArr = {new Image("/src/turtle/view/Shell.png")};
		shell = new Animation(shellArr, 10, false);
		
		Image[] turtleBoi = {swim1, swim2};
		
		swim = new Animation(turtleBoi,300,true);
		
		turtle = swim;
		level = 1;
		
		xPos = 400 - turtle.getWidth() / 2;
		yPos = 400 - turtle.getHeight() / 2;
		
		//Gameplay stuff at beginning
		seaweed = new Obstacle("Seaweed", new Image("/src/turtle/view/Seaweed.png"), 1, 40, 600 - 15, 600 - 50);
		obstacleList = new ArrayList<Obstacle>();
		//obstacleList.add(seaweed);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input keyStroke = container.getInput();
		
		xAccel = 0;
		yAccel = 0;
		//Shell
		if(keyStroke.isKeyPressed(Input.KEY_SPACE))
		{
			if(turtle != shell)
			{
				xPos += 28;
				yPos += 40;
				turtle = shell;
			}
			else
			{
				xPos -= 28;
				yPos -= 40;
				turtle = swim;
			}
		}
		
		//Acceleration
		if(turtle != shell)
		{
			if(keyStroke.isKeyDown(Input.KEY_LEFT) || keyStroke.isKeyDown(Input.KEY_A))
			{
				xAccel -= 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_RIGHT)|| keyStroke.isKeyDown(Input.KEY_D))
			{
				xAccel += 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_UP)|| keyStroke.isKeyDown(Input.KEY_W))
			{
				yAccel -= 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_DOWN)|| keyStroke.isKeyDown(Input.KEY_S))
			{
				yAccel += 1;
			}
			
			yVelocity += yAccel;
			xVelocity += xAccel;
		}
		
		//Rotation
		if(turtle == shell)
		{
			rotateTurtle(rounded(xVelocity), rounded(yVelocity));
		}
		else
		{
			rotateTurtle(xAccel, yAccel);
		}
			
		//Movement
		xPos += xVelocity/3;
		yPos += yVelocity/3;
		
		//Bouncing
		if(xPos < 0)
		{
			bounceRight();
		}
		if(xPos > 800 - turtle.getWidth())
		{
			bounceLeft();
		}
		if(yPos < 0)
		{
			bounceDown();
		}
		if(yPos > 800 - turtle.getHeight())
		{
			bounceUp();
		}
		
		checkCollision();
	}
	
	public void bounceLeft()
	{
		if(turtle != shell)
		{
			xVelocity /= 2;
		}
		
		if(xVelocity > 0)
		{
			xVelocity *= -1;
		}
		
		xPos += xVelocity - 1;
	}
	
	public void bounceRight()
	{
		if(turtle != shell)
		{
			xVelocity /= 2;
		}
		
		if(xVelocity < 0)
		{
			xVelocity *= -1;
		}
		xPos += xVelocity + 1;
	}
	
	public void bounceUp()
	{
		if(turtle != shell)
		{
			yVelocity /= 2;
		}
		
		if(yVelocity > 0)
		{
			yVelocity *= -1;
		}
		yPos += yVelocity - 1;
	}
	
	public void bounceDown()
	{
		if(turtle != shell)
		{
			yVelocity /= 2;
		}
		
		if(yVelocity < 0)
		{
			yVelocity *= -1;
		}
		yPos += yVelocity + 1;
	}
	
	public void rotateTurtle(int xMovement, int yMovement)
	{
		
		double xMov = (double) xMovement;
		double yMov = (double) yMovement;
		
		double degree = 0;
		if(yMov < 0)
		{
			degree = (radToDeg(Math.atan((double) (-1 * xMov/yMov))));
		}
		if(yMov > 0)
		{
			degree = 180 - (radToDeg(Math.atan((double) (xMov/yMov))));
		}
		if(yMov == 0)
		{
			if(xMov > 0)
			{
				degree = 90;
			}
			else if(xMov < 0)
			{
				degree = -90;
			}
			else
			{
				return;
			}
		}
			
		
		for(int i = 0; i < turtle.getFrameCount(); i++) 
		{
			turtle.getImage(i).setRotation((int)(degree));
		}
	}
	
	public void checkCollision()
	{
		for(int i = obstacleList.size() - 1; i >= 0; i--)
		{
			RoundedRectangle turtleRect = new RoundedRectangle(xPos, yPos, turtle.getCurrentFrame().getWidth(), turtle.getCurrentFrame().getHeight(), 100);
			Rectangle obstacleRect = new Rectangle(obstacleList.get(i).xPos, obstacleList.get(i).yPos, obstacleList.get(i).getImage().getWidth(), obstacleList.get(i).getImage().getHeight());
			if(turtleRect.intersects(obstacleRect))
			{
				if(xPos < obstacleList.get(i).xPos)
				{
					bounceLeft();
				}
				else
				{
					bounceRight();
				}
				
				if(yPos < obstacleList.get(i).yPos)
				{
					bounceUp();
				}
				else
				{
					bounceDown();
				}
				
				if(turtle == shell)
				{
					obstacleList.get(i).doDamage(xVelocity);
					obstacleList.get(i).doDamage(yVelocity);
					if(obstacleList.get(i).getHealth() <= 0)
					{
						obstacleList.remove(i);
					}
				}
			}
		}
	}
	
	public double radToDeg(double num)
	{
		return num * 180 / Math.PI;
	}
	
	public int rounded(int num)
	{
		if (num == 1)
		{
			return 0;
		}
		return num;
	}
}
