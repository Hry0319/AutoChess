package tetsu.Processing.AutoChess.Chessboard.BoardStorage;

import java.awt.Point;

public class ChessboardModel {

	// *****************Properties for Calibrating*****************
	static private boolean[] _bIsSetLocation = new boolean[2];

	static public boolean bIsSetLocation() {
		if (_bIsSetLocation[0] && _bIsSetLocation[1]) {
			return true;
		}
		return false;
	}

	static private Point _posTopLeft = new Point();

	static public void posTopLeft(Point p) {
		_posTopLeft = p;
		_bIsSetLocation[0] = true;
	}

	static public Point posTopLeft() {
		return _posTopLeft;
	}

	static private Point _posBottomRight = new Point();

	static public void posBottomRight(Point p) {
		_posBottomRight = p;
		_bIsSetLocation[1] = true;
	}

	static public Point posBottomRight() {
		return _posBottomRight;
	}

	// *****************Properties for Calibrating*****************

	// *****************Properties for ChessBoard*****************
	static public boolean bIsBoardDataSet = false;
	
	static private int[][] _pBoardData = new int[8][8];

	static public int[][] pBoardData() {
		return _pBoardData;
	}

	static public void pBoardData(int[][] value) {
		_pBoardData = value;
	}

	static private Point[][] _pBoardLocation = new Point[8][8];

	static public Point[][] pBoardLocation() {
		return _pBoardLocation;
	}

	static public void pBoardLocation(Point[][] value) {
		_pBoardLocation = value;
	}
	// *****************Properties for ChessBoard*****************
}
