package turtle.view;

import org.newdawn.slick.*;



public class TurtleController extends BasicGame
{
	public int xVelocity = 0;
	public int yVelocity = 0;
	
	public int xAccel = 0;
	public int yAccel = 0;
	
	public int xPos = 350;
	public int yPos = 350;
	public int level;
	
	Image turtle;
	Image swim1;
	Image swim2;
	Image shell;
	
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
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(new Color(50, 80, 120));
		g.fillRect(0, 0, 800, 800);
		turtle.draw(xPos, yPos);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		swim1 = new Image("/src/turtle/view/Turtle1.png");
		swim2 = new Image("/src/turtle/view/Turtle2.png");
		shell = new Image("/src/turtle/view/Shell.png");
		
		turtle = swim1;
		level = 1;
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
				turtle = swim1;
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
		//Movement
		xPos += xVelocity/3;
		yPos += yVelocity/3;
		
		//Bouncing
		if(xPos < 0)
		{
			xPos = 0;
			if(turtle != shell)
			{
				xVelocity /= 1.5;
			}
			xVelocity *= -1;
		}
		if(xPos > 800 - turtle.getWidth())
		{
			xPos = 800 - turtle.getWidth();
			if(turtle != shell)
			{
				xVelocity /= 1.5;
			}
			
			xVelocity *= -1;
		}
		if(yPos < 0)
		{
			yPos = 0;
			if(turtle != shell)
			{
				yVelocity /= 1.5;
			}
			
			yVelocity *= -1;
		}
		if(yPos > 800 - turtle.getHeight())
		{
			yPos = 800 - turtle.getHeight();
			if(turtle != shell)
			{
				yVelocity /= 1.5;
			}
			
			yVelocity *= -1;
		}
		
		//TODO Swim animation
		
		turtle.draw(xPos, yPos);
	}
}
