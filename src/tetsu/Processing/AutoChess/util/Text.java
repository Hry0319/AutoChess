package tetsu.Processing.AutoChess.util;
import processing.core.PApplet;
import processing.core.PFont;

public class Text extends SubClass {
	PFont font;

	public Text(PApplet p) {
		super(p);
		font = parent.loadFont("MyriadWebPro-24.vlw");
		parent.textFont(font);
	}

	public void drawText(String str, int x, int y) {
		parent.text(str, x, y);
	}

}
