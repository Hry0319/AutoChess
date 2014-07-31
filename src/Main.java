import java.awt.MouseInfo;
import java.awt.Point;

import processing.core.PApplet;
import tetsu.Processing.AutoChess.Chessboard.BoardStorage.ChessboardModel;
import tetsu.Processing.AutoChess.Chessboard.BoardWalker.BoardWalker;
import tetsu.Processing.AutoChess.Chessboard.ChessMover.ChessMover;
import tetsu.Processing.AutoChess.Chessboard.ChessThinker.ChessThinker;
import tetsu.Processing.AutoChess.Interface.IBoardWalker;
import tetsu.Processing.AutoChess.Interface.IChessMover;
import tetsu.Processing.AutoChess.util.Stats;
import tetsu.Processing.AutoChess.util.Text;

public class Main extends PApplet implements IBoardWalker , IChessMover
{

	private Text			t;
	private Stats			stats;
	private BoardWalker		walker;
	private ChessMover		mover;
	private ChessThinker	thinker;
	private boolean			bRunning	= false;

	public void setup()
	{
		size(320,240);
		background(0);
		initText();
		initBoardWalker();
		initChessMover();
		initChessThinker();
	}

	private void initText()
	{
		stats = new Stats(this);
		t = new Text(this);
	}

	private void initBoardWalker()
	{
		walker = new BoardWalker(this,this);
	}

	private void initChessMover()
	{
		mover = new ChessMover(this,this);
	}

	private void initChessThinker()
	{
		// set 2(black) is player, 1(white) is computer
		thinker = new ChessThinker(this,2,1);
	}

	// Main loop
	public void draw()
	{
		background(255);

		walker.drawBoard();

		fill(93,119,133);
		drawTitle();
		fill(63,89,103);
		stats.drawFrameRate(0,60);
		drawMousePosition();
	}

	private void drawTitle()
	{
		t.drawText("Chess Robot",0,24);
	}

	private void drawMousePosition()
	{
		Point p = getMouseLocation();
		t.drawText("Pos: " + p.x + "," + p.y,0,84);
	}

	private Point getMouseLocation()
	{
		return MouseInfo.getPointerInfo().getLocation();
	}

	public void keyReleased()
	{
		Point p = getMouseLocation();
		switch (key)
		{
		case 'q': // Set Location of TopLeft
			ChessboardModel.posTopLeft(p);
			println("TopLeft Set: (" + p.x + "," + p.y + ")");
			break;
		case 's': // Set Location of BottomRight
			ChessboardModel.posBottomRight(p);
			println("BottomRight Set: (" + p.x + "," + p.y + ")");
			break;
		case 'r': // Run ChessBoard
			bRunning = true;
			runBoardWalk();
			break;
		case 'x':
			bRunning = false;
			break;
		}
	}

	// Will be called when board0walking done
	@Override
	public void onBoardWalkDone()
	{
		if (bRunning)
		{
			Point pNext = thinker.getNextMove(ChessboardModel.pBoardData());
			if (pNext != null)
			{
				mover.Move(pNext.x,pNext.y);
				println("Move to: (" + pNext.x + "," + pNext.y + ")");
			}
			else
			{
				bRunning = false;
			}
		}
	}

	// Will be called when chess-moving done
	@Override
	public void onChessMoveDone()
	{
		if (bRunning)
		{
			delay(2000); // Wait for 1 second for chess animation
			runBoardWalk(); // Run Walk Again
		}
	}

	private void runBoardWalk()
	{
		if (ChessboardModel.bIsSetLocation())
		{
			walker.startWalk(ChessboardModel.posTopLeft(),
					ChessboardModel.posBottomRight());
		}
	}
}
