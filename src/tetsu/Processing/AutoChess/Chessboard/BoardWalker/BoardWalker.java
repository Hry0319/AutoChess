package tetsu.Processing.AutoChess.Chessboard.BoardWalker;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import processing.core.PApplet;
import tetsu.Processing.AutoChess.Chessboard.BoardStorage.ChessboardModel;
import tetsu.Processing.AutoChess.Interface.IBoardWalker;

public class BoardWalker
{
	private PApplet			parent;
	private Robot			robot;
	private Timer			timer;
	private IBoardWalker	caller;
	public ActionListener	taskPerformer	= new ActionListener()
											{
												public void actionPerformed(
														ActionEvent e)
												{

												}
											};

	private Point			pTopLeft;
	private Point			pBottomRight;
	private int				nBoardWidth		= 0;
	private int				nBoardHeight	= 0;
	private float			fWidthOffset	= 0.0f;
	private float			fHeightOffset	= 0.0f;
	private int				nPaddingX		= 20;
	private int				nPaddingY		= 20;
	private int				nCounter		= 0;
	private Point			pPointer;
	private Point[][]		pBoardLocation	= new Point[8][8];
	private int[][]			pBoardData		= new int[8][8];
	private boolean			bIsDataSet		= false;

	public BoardWalker(PApplet p, IBoardWalker caller)
	{
		this.caller = caller;
		this.parent = p;
		initTimer();
		initRobot();
	}

	private void initTimer()
	{
		timer = new Timer(1000,taskPerformer);
	}

	private void initRobot()
	{
		try
		{
			robot = new Robot();
		} catch (AWTException e)
		{
			parent.println("Robot class not supported");
		}
	}

	public void startWalk(Point topLeft,Point bottomRight)
	{
		pTopLeft = topLeft;
		pBottomRight = bottomRight;
		nBoardWidth = Math.abs(pBottomRight.x - pTopLeft.x);
		nBoardHeight = Math.abs(pBottomRight.y - pTopLeft.y);
		fWidthOffset = nBoardWidth / 8;
		fHeightOffset = nBoardHeight / 8;
		pPointer = pTopLeft; // Initial Position of Pointer;
		nCounter = 0;

		WalkMainLoop();

		doSaveToBoardModel();

		bIsDataSet = true;
		// parent.println("Board-Walking Done!");

		caller.onBoardWalkDone();
	}

	private void WalkMainLoop()
	{
		for(int w = 0 ; w < 8 ; w++)
		{
			for(int h = 0 ; h < 8 ; h++)
			{
				doBuildBoard(w,h);
				doBoardWalk(w,h);
			}
		}
	}

	private void doBuildBoard(int w,int h)
	{
		pBoardLocation[w][h] = new Point(pTopLeft.x + nPaddingX
				+ (int)( fWidthOffset * w ),pTopLeft.y + nPaddingY
				+ (int)( fHeightOffset * h ));
	}

	private void doBoardWalk(int w,int h)
	{
		Point p = pBoardLocation[w][h];
		Color c = robot.getPixelColor(p.x,p.y);
		int _color = ( c.getRed() + c.getGreen() + c.getBlue() ) / 3;
		if (_color >= c.getRed() - 3 && _color <= c.getRed() + 3)
		{
			if (c.getRed() < 100)
			{
				pBoardData[w][h] = 2; // 2 for black
			}
			else
			{
				pBoardData[w][h] = 1; // 1 for white
			}
			// pBoardData[w][h] = c.getRGB();
		}
		else
		{
			pBoardData[w][h] = 0; // 0 for empty
		}
	}

	private void doSaveToBoardModel()
	{
		ChessboardModel.pBoardData(pBoardData);
		ChessboardModel.pBoardLocation(pBoardLocation);
		ChessboardModel.bIsBoardDataSet = true;
		// parent.println("Chessboard Data Saved!");
	}

	public void drawBoard()
	{
		if (bIsDataSet)
		{
			int n = 0;
			for(int w = 0 ; w < 8 ; w++)
			{
				for(int h = 0 ; h < 8 ; h++)
				{
					n = pBoardData[w][h];
					if (n == 0)
					{
						parent.fill(parent.color(255,0));
					}
					else if (n == 1)
					{
						parent.fill(parent.color(255));
					}
					else if (n == 2)
					{
						parent.fill(parent.color(0));
					}
					parent.ellipse(50 + w * 15,50 + h * 15,10,10);
				}
			}
		}
	}
}
