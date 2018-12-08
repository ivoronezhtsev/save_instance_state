package ru.voronezhtsev.shellgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.Random;

public class MainActivity extends Activity {

    private static final String IS_OPENED = "IsOpened";
    private static final String BALL_POSITION = "BallPosition";

    private boolean mIsOpened;
    private int mBallPosition;
    private View[] mShells;
    View mNewGameButton;
    Runnable openShells = new Runnable() {
        @Override
        public void run() {
            for(int i = 0; i < mShells.length; i++) {
                if(i != mBallPosition) {
                    mShells[i].getBackground().setLevel(2);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShells = new View[] {findViewById(R.id.shell_left), findViewById(R.id.shell_center),
                findViewById(R.id.shell_right)};
        mNewGameButton = findViewById(R.id.new_game_button);
        if(savedInstanceState == null) {
            newGame();
        } else {
            mIsOpened = savedInstanceState.getBoolean(IS_OPENED);
            mBallPosition = savedInstanceState.getInt(BALL_POSITION);
            mNewGameButton.setOnClickListener(v -> newGame());
            mShells[mBallPosition].setOnClickListener(this::openShells);
            if(mIsOpened) {
                openShells(null);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_OPENED, mIsOpened);
        outState.putInt(BALL_POSITION, mBallPosition);
    }

    private void newGame() {
        Random random = new Random(System.currentTimeMillis());
        mBallPosition = random.nextInt(3);
        mIsOpened = false;
        for(View shell: mShells) {
            shell.getBackground().setLevel(0);
            shell.setOnClickListener(this::openShells);
            shell.removeCallbacks(openShells);
        }
        mNewGameButton.setVisibility(View.INVISIBLE);
        mNewGameButton.setOnClickListener(v -> newGame());
    }

    private void openShells(View v) {
        mShells[mBallPosition].getBackground().setLevel(1);
        for(int i = 0; i< mShells.length; i++) {
            if(i != mBallPosition) {
                final View shell = mShells[i];
                shell.postDelayed(openShells,1000);
            }
        }
        mIsOpened = true;
        mNewGameButton.setVisibility(View.VISIBLE);
    }
}
