package picross;

import mygl.*;
import mygl.Graphics;

import java.awt.*;

/**
 * @author onContentStop
 */
public class GameWindow extends Graphics {
	private final int maxFPS = 144;
	private final int TOP_BAR_HEIGHT = 30;
	private Background background;
	private ButtonElement b;

	public GameWindow(KeyListener kl) {
		super("Picross"); //Sets things up. If you want details, look in Graphics, it's too much to explain here.
		frame.setKeyHandler(kl); //TODO a really good solution for key handling that makes everything look pretty
		frame.setExtendedState(Frame.MAXIMIZED_BOTH); //Maximizes the frame on screen.
		Timer bgTimer = new Timer();
		new Thread(bgTimer).start();
		bgTimer.start();
		background = new Background(100, bgTimer, 10000); //This background will choose random colors and shift between them smoothly every 10 seconds.
		initButtons();
	}

	private void initButtons() {
		//b is a perfectly centered ButtonElement. No matter what, B will be at the center of the screen.
		b = new ButtonElement(width / 2, height / 2, 200, 100, this); //Initializes the button with a position, size and graphics context.
		b.setText("Start Gayme"); //The text to display on the button goes here. The size of this text will be determined automatically by a process unknown to humankind.
		b.setColor(Color.GREEN); //This is the color that will be used on the background of the button, behind the text and inside the borders.
		b.setClickListener(() -> {
			System.out.println("You clicked the button. You Win!"); //This code will be executed whenever the button registers a click.
			b.setVisible(false);                                    //A click occurs when the left mouse button is released on top of the visible button element.
		});
		b.setOnUpdateAction(() -> {
			b.setX(width / 2); //This code is executed on every frame that the button is visible.
			b.setY(height / 2); //So is this.
		});
		b.setVisible(true); //Now the button will be drawn and updated on screen!
	}

	@Override
	public void runActions() {
		updateSize(); //TODO move this to Graphics, it's such a necessity that I should have done it yesterday.
		background.update(); //This allows the background color to change continuously. (Discretely, but with small enough steps it looks continuous.)
		draw(); //I'm not explaining this. Just no. There is no way the function is not clear. It draws stuff.
		try {
			//This should cause the framerate to max out at maxFPS, though in reality it probably won't reach that value because my code bad.
			Thread.sleep((long) (1000d / (double) maxFPS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void drawActions() {
		//draw background - this should ALWAYS be first in the draw! I mean duh, it goes in the back...
		//if you don't have a background, things will look weird when the window is resized because of how that's done.
		graphics2D.setColor(background.getCurrentColor());
		graphics2D.fillRect(0, 0, width, height);
		setFont(new Font("Arial", Font.BOLD, 50));
		graphics2D.setColor(Color.black);
		DrawingTools.drawCenteredText(f, "PICROSS", width / 2, TOP_BAR_HEIGHT + 60, graphics2D);
		//There are some debug tools here. Use them, or don't. I don't really care.
		//region debug mouse position
		/*setFont(new Font("Arial", Font.PLAIN, 20));
		DrawingTools.drawCenteredText(f, "" + frame.mouseX + ", " + frame.mouseY, width / 2, height / 2, art);*/
		//endregion
		//b.draw();
		//^ Hey look, you don't have to do this anymore!
	}
}
