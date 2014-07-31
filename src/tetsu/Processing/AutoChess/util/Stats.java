package tetsu.Processing.AutoChess.util;

import processing.core.PApplet;
import processing.core.PFont;

public class Stats extends SubClass {
	PFont font;

	public Stats(PApplet p) {
		super(p);
		font = parent.loadFont("MyriadWebPro-24.vlw");
		parent.textFont(font);
	}

	public void drawFrameRate(int x, int y) {
		parent.text("Fps: " + PApplet.floor(parent.frameRate), x, y);
	}
}
