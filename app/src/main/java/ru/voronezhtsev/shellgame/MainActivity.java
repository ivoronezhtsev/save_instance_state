package ru.voronezhtsev.shellgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends Activity {

    private static final String IS_OPENED = "IsOpened";
    private static final String BALL_POSITION = "BallPosition";
    private boolean mIsOpened;
    private int mBallPosition;
    private View[] mShells;
    View mNewGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShells = new View[] {findViewById(R.id.shell_left), findViewById(R.id.shell_center),
                findViewById(R.id.shell_right)};
        mNewGameButton = findViewById(R.id.new_game_button);
        initGame();
        if(savedInstanceState != null) {
            mIsOpened = savedInstanceState.getBoolean(IS_OPENED);
            mBallPosition = savedInstanceState.getInt(BALL_POSITION);
            if(mIsOpened) {
                restoreGame();
            }
        }
    }

    private void initGame() {
        Random random = new Random(System.currentTimeMillis());
        if (!mIsOpened) {
            mBallPosition = random.nextInt(3);
        } else {
            mIsOpened = false;
            mShells[mBallPosition].getBackground().setLevel(0);
            mShells[mBallPosition].setOnClickListener(null);
            mBallPosition = random.nextInt(3);
        }

        mShells[mBallPosition].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getBackground().setLevel(1);
                if (!mIsOpened) {
                    mIsOpened = true;
                    mNewGameButton.setVisibility(View.VISIBLE);
                }
            }
        });
        if(mNewGameButton.getVisibility() == View.VISIBLE) {
            mNewGameButton.setVisibility(View.INVISIBLE);
            mNewGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initGame();
                }
            });
        }
    }

    private void restoreGame() {
        mShells[mBallPosition].getBackground().setLevel(1);
        mNewGameButton.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_OPENED, mIsOpened);
        outState.putInt(BALL_POSITION, mBallPosition);
    }
}
