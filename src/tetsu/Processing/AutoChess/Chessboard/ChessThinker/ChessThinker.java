package tetsu.Processing.AutoChess.Chessboard.ChessThinker;

import java.awt.Color;
import java.awt.Point;

import processing.core.PApplet;

public class ChessThinker
{
	private PApplet		parent;
	private int			playerColor			= 0;
	private int			aiColor				= 0;
	private static int	_MAXCAULATESPACE	= 64;
	private int			MaxCaulateSpace		= _MAXCAULATESPACE;
	Point				nextPoint;

	public ChessThinker(PApplet p, int PlayerColor, int AIColor)
	{
		nextPoint = new Point();
		this.parent = p;
		this.playerColor = PlayerColor;
		this.aiColor = AIColor;
	}

	public Point getNextMove(int[][] boardData)
	{
		MaxCaulateSpace--;

		int MaxScore = 0;
		int tmpScore = 0;
		int[][] ScoreMap = calcScoreMap(boardData);

		int[][] tmpBoardData = boardData;
		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				if (ScoreMap[_x][_y] > 0)
				{
					tmpBoardData[_x][_y] = 2;

					MaxScore = getLevel(_x,_y) + ScoreMap[_x][_y];

					MaxScore += searchAnimeTree(tmpBoardData,MaxCaulateSpace);

					if (MaxScore > tmpScore)
					{
						tmpScore = MaxScore;
						nextPoint.x = _x;
						nextPoint.y = _y;
					}
				}
			}
		}
		MaxCaulateSpace = _MAXCAULATESPACE;
		return nextPoint;

	}

	public int searchMyTree(int[][] boardData,int CaulateCount)
	{
		int MaxScore = 0;
		int[][] ScoreMap = calcScoreMap(boardData);

		int[][] tmpBoardData = boardData;
		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				if (ScoreMap[_x][_y] > 0 )
				{
					tmpBoardData[_x][_y] = 2;

					MaxScore = getLevel(_x,_y) + ScoreMap[_x][_y];

					return (MaxScore+ searchAnimeTree(tmpBoardData,CaulateCount) );

				}
			}
		}
		return 0;

	}

	public int searchAnimeTree(int[][] boardData,int CaulateCount)
	{
		int[][] tmpBoardData = boardData;
		tmpBoardData = reverseBoard(tmpBoardData);

		int AnimeMaxScore = 0;
		int[][] ScoreMap = calcScoreMap(tmpBoardData);
		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				if (ScoreMap[_x][_y] > 0)
				{
					tmpBoardData[_x][_y] = 2;
					AnimeMaxScore = -1 * ( getLevel(_x,_y) + ScoreMap[_x][_y] );
					tmpBoardData = reverseBoard(tmpBoardData);
					if (CaulateCount > 0)
					{
						return ( AnimeMaxScore + searchMyTree(tmpBoardData,
								CaulateCount - 1) );
					}
					else
					{
						return AnimeMaxScore;
					}
				}
			}
		}
		return 0;
	}

	public int[][] reverseBoard(int[][] boardData)
	{
		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				if (boardData[_x][_y] == 1)
					boardData[_x][_y] = 2;
				else if (boardData[_x][_y] == 2)
					boardData[_x][_y] = 1;
			}
		}
		return boardData;
	}

	public int getLevel(int px,int py)
	{
		int level = 0;
		if (px == 0 && py == 0 || px == 7 && py == 7 || px == 7 && py == 0
				|| px == 0 && py == 7)
		{
			level = 30;
		}
		if (px == 1 || px == 6)
		{
			if (py == 0 || py == 1 || py == 6 || py == 7)
				level = -18;
		}
		if (px == 0 || px == 7)
		{
			if (py == 1 || py == 6)
				level = -18;
		}
		// int px = nextMove.x;
		// int py = nextMove.y;
/*
		if (px == 0 || px == 7)
			level = 3;
		if (py == 0 || py == 7)
			level = 3;

		
*/
		return level;
	}

	public int[][] calcScoreMap(int[][] boardData)
	{
		int[][] _om = boardData;
		int[][] _sm = new int[8][8];

		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				// If the location is emtpy
				if (_om[_x][_y] == 0)
				{
					// Searching for 8 directions
					for(int _d = 0 ; _d < 8 ; _d++)
					{
						int _sx = _x;
						int _sy = _y;
						int _v = 0; // Value
						int _fx = 0;
						int _fy = 0;

						switch (_d)
						{
						case 0:
							_fx = _fy = -1;
							break;
						case 1:
							_fy = -1;
							break;
						case 2:
							_fx = 1;
							_fy = -1;
							break;
						case 3:
							_fx = -1;
							break;
						case 4:
							_fx = 1;
							break;
						case 5:
							_fx = -1;
							_fy = 1;
							break;
						case 6:
							_fy = 1;
							break;
						case 7:
							_fx = _fy = 1;
							break;
						}

						while (true)
						{
							_sx += _fx;
							_sy += _fy;
							if (_sx >= 0 && _sy >= 0 && _sx < 8 && _sy < 8)
							{
								if (_om[_sx][_sy] == aiColor)
								{
									_v++;
								}
								else if (_om[_sx][_sy] == playerColor)
								{
									_sm[_x][_y] += _v; // Give it a score
									break;
								}
								else if (_om[_sx][_sy] == 0)
								{
									break;
								}
							}
							else
							{
								break;
							}
						}
					}

				}
			}
		}
		return _sm;
	}

	private void printScoreMap(int[][] boardData)
	{
		for(int _y = 0 ; _y < 8 ; _y++)
		{
			for(int _x = 0 ; _x < 8 ; _x++)
			{
				parent.print(boardData[_x][_y]);
				parent.print(" ");
			}
			parent.print("\n");
		}
	}
}
