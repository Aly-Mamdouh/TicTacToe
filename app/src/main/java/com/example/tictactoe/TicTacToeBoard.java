package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winingLineColor;
    private int cellSize = getWidth() / 3;
    private final Paint paint = new Paint();
    private final GameLogic game;
    private boolean winingLine = false;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();

        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = arr.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = arr.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = arr.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winingLineColor = arr.getInteger(R.styleable.TicTacToeBoard_winingLineColor, 0);

        } finally {
            arr.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int diem = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = diem / 3;
        setMeasuredDimension(diem, diem);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winingLine){
            paint.setColor(winingLineColor);
            drawWiningLine(canvas);
        }
    }

    private void drawGameBoard(Canvas canvas) {

        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);

        }

    }

    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);

        canvas.drawLine((float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) (col * cellSize + cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);
        canvas.drawLine((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);

    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);
        canvas.drawOval((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col * cellSize + cellSize) - cellSize * 0.2),
                (float) ((row * cellSize + cellSize) - cellSize * 0.2),
                paint);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);

            if (!winingLine) {
                if (game.updateGameBoard(row, col)) {
                    invalidate();

                    // updating the player turn
                    if (game.winnerCheck()) {
                        winingLine = true;
                        invalidate();
                    }

                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }

            }

            invalidate();
            return true;
        }
        return false;
    }

    public void drawHorizontalLine(Canvas canvas, int row, int col) {

        canvas.drawLine(col, row * cellSize + (float)cellSize / 2, cellSize * 3, row * cellSize + (float)cellSize / 2, paint);

    }

    public void drawVerticalLine(Canvas canvas, int row, int col) {

        canvas.drawLine(col * cellSize + (float)cellSize / 2,row , col * cellSize + (float)cellSize / 2, cellSize*3, paint);

    }

    public void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3, cellSize * 3, 0, paint);
    }

    public void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0, 0, cellSize * 3, cellSize * 3, paint);
    }


    public void drawWiningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names) {
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayersName(names);

    }

    public void resetGame() {
        game.resetGame();
        winingLine = false;
    }


}
