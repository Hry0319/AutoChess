package tetsu.Processing.AutoChess.Chessboard.ChessMover;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import processing.core.PApplet;
import tetsu.Processing.AutoChess.Chessboard.BoardStorage.ChessboardModel;
import tetsu.Processing.AutoChess.Interface.IChessMover;

public class ChessMover {
	private PApplet parent;
	private IChessMover caller;
	private Robot robot;

	public ChessMover(PApplet p, IChessMover caller) {
		this.parent = p;
		this.caller = caller;
		initRobot();
	}

	private void initRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			parent.println("Robot class not supported");
		}
	}

	public void Move(int x, int y) {
		if (ChessboardModel.bIsBoardDataSet) {
			Point p = ChessboardModel.pBoardLocation()[x][y];
			robot.mouseMove(p.x, p.y);
			// 1-Left Button Clicking; 3-Right Button
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			caller.onChessMoveDone();
		} else {
			parent.println("Chessboard havn't been set!");
		}
	}
}
