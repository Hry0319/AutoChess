package tetsu.Processing.AutoChess.Chessboard.ChessThinker;

import java.awt.Point;

import processing.core.PApplet;

public class ChessThinker
{
	private PApplet	parent;
	private int		playerColor	= 0;
	private int		aiColor		= 0;
	private int		_time		= 2;
	private int		_timeCount	= 0;

	public ChessThinker(PApplet p, int PlayerColor, int AIColor)
	{
		this.parent = p;
		this.playerColor = PlayerColor;
		this.aiColor = AIColor;
	}
// ***AI開始運作進入點***
	public Point getNextMove(int[][] boardData)
	{
		Point _nextMove = new Point();

		// int[][] _myMap = calcScoreMap(boardData);
		_nextMove = singleStep(boardData);
		// _nextMove = wynnsAI(boardData);

		return _nextMove;

	}

	public Point wynnsAI(int[][] boardData)
	{
		Point _nextMove = new Point();
		_nextMove.x = 4;
		_nextMove.y = 4;
		int[][] _myMap = calcScoreMap(boardData);
		int _tmpScore = 0;
		int _tmpLevel = 6;// 1~5
		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				if (_myMap[_x][_y] > 0)
				{
					System.out.println(getLevel(_x,_y));
					if (getLevel(_x,_y) < getLevel(_nextMove.x,_nextMove.y))
					{
						_tmpScore = 0;
						_nextMove.x = _x;
						_nextMove.y = _y;
					}
					else if (getLevel(_x,_y) == getLevel(_nextMove.x,
							_nextMove.y))
					{
						if (_myMap[_x][_y] > _tmpScore)
						{
							_nextMove.x = _x;
							_nextMove.y = _y;
							_tmpScore = _myMap[_x][_y];
						}

					}
				}

			}
		}
		return _nextMove;

	}

	public int getLevel(int _x,int _y)
	{
		int[][] levelMap = new int[8][8];
		// set the level

		// level one
		levelMap[0][0] = 1;
		levelMap[0][7] = 1;
		levelMap[7][0] = 1;
		levelMap[7][7] = 1;

		// level two
		levelMap[2][0] = 2;
		levelMap[5][0] = 2;
		levelMap[2][7] = 2;
		levelMap[5][7] = 2;
		levelMap[0][2] = 2;
		levelMap[0][5] = 2;
		levelMap[7][2] = 2;
		levelMap[7][5] = 2;

		// level three
		levelMap[3][0] = 3;
		levelMap[4][0] = 3;
		levelMap[3][7] = 3;
		levelMap[4][7] = 3;
		levelMap[0][3] = 3;
		levelMap[0][4] = 3;
		levelMap[7][3] = 3;
		levelMap[7][4] = 3;

		// level four
		levelMap[1][0] = 4;
		levelMap[6][0] = 4;
		levelMap[1][7] = 4;
		levelMap[6][7] = 4;
		levelMap[0][1] = 4;
		levelMap[0][6] = 4;
		levelMap[7][1] = 4;
		levelMap[7][6] = 4;
		// level five
		for(int _i = 1 ; _i < 7 ; _i++)
		{
			for(int _j = 1 ; _j < 7 ; _j++)
			{
				levelMap[_i][_j] = 5;
			}
		}
		return levelMap[_x][_y];

	}

	public Point singleStep(int[][] boardData)
	{

		int _maxScore = -100;
		Point _nextMove = new Point();
		// get the highest score of my step;
		int[][] _myMap = calcScoreMap(boardData);

		for(int _x = 0 ; _x < 8 ; _x++)
		{
			for(int _y = 0 ; _y < 8 ; _y++)
			{
				int _tmpMaxScore = 0;
				if (_myMap[_x][_y] > 0 && boardData[_x][_y] == 0)
				{
					int _myScore = _myMap[_x][_y];
					_tmpMaxScore += _myScore;

					int[][] tmpMyMap = boardData;
					tmpMyMap[_x][_y] = 2;
					int[][] _comMap = calcScoreMap(inverseBoard(tmpMyMap));

					int _comScore = 0;
					for(int _i = 0 ; _i < 8 ; _i++)
					{
						for(int _j = 0 ; _j < 8 ; _j++)
						{
							if (_comMap[_i][_j] > _comScore)
								_comScore = _comMap[_i][_j];
							// System.out.print(_comMap[_i][_j]);
						}
					}
					_tmpMaxScore -= _comScore;

					if (_tmpMaxScore > _maxScore)
					{
						_maxScore = _tmpMaxScore;
						_nextMove.x = _x;
						_nextMove.y = _y;

					}
				}
			}
		}

		System.out.println("Score:" + _maxScore + " X:" + _nextMove.x + "_Y:"
				+ _nextMove.y);
		return _nextMove;

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

		// printScoreMap(_sm);
		/*
		 * if(_maxScoreValue==0){ return null; } return _maxScoreLoc;
		 */
		return _sm;
	}
	private int[][] inverseBoard(int[][] boardData)
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
