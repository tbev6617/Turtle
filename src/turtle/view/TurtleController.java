package turtle.view;

import org.newdawn.slick.*;



public class TurtleController extends BasicGame
{
	public int xVelocity = 0;
	public int yVelocity = 0;
	
	public int xAccel = 0;
	public int yAccel = 0;
	
	public int xPos;
	public int yPos;
	public int level;
	
	Animation swim;
	
	Animation turtle;
	Image swim1;
	Image swim2;
	Animation shell;
	
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
		
		if(turtle != shell)
		{
			//Acceleration
			if(keyStroke.isKeyDown(Input.KEY_LEFT))
			{
				xAccel -= 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_RIGHT))
			{
				xAccel += 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_UP))
			{
				yAccel -= 1;
			}
			if(keyStroke.isKeyDown(Input.KEY_DOWN))
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
		
		xPos += xVelocity;
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
		xPos += xVelocity;
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
		yPos += yVelocity;
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
		yPos += yVelocity;
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
