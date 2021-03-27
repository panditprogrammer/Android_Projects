package com.panditprogrammer.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    /*player representation
    1 = X
    0 = 0
     */
    int activePlayer = 0;
    //game is active or not
    boolean gameActive;
    /*
    player state
    0 = O
    1 = X
    2 = Null
     */
    int []playerState = {2,2,2,2,2,2,2,2,2};

//    winning positions
    int [][]winPosition = { {0,1,2}, {3,4,5}, {6,7,8},
                            {0,3,6}, {1,4,7}, {2,5,8},
                            {0,4,8}, {2,4,6}  };

    @SuppressLint("SetTextI18n")
    public void onTap(View view)
    {
        ImageView img = (ImageView) view;
        int imageTapped = Integer.parseInt(img.getTag().toString());
//        //restarting game
//        if(!gameActive)
//        {
//            resetGame(view);
//        }

        if(playerState[imageTapped] == 2)
        {
            playerState[imageTapped] = activePlayer;
            img.setTranslationY(-800f);
            if(activePlayer == 0)
            {
                img.setImageResource(R.drawable.oo);
                activePlayer = 1;
                TextView status = findViewById(R.id.textView4);
                status.setText(" X's Turn ");
            }
            else
            {
                img.setImageResource(R.drawable.xx);
                activePlayer = 0;
                TextView status = findViewById(R.id.textView4);
                status.setText(" O's Turn ");
            }
            img.animate().translationYBy(800f).setDuration(300);
        }
        //checking player win
        for (int [] winPosition: winPosition)
        {
            if(playerState[winPosition[0]]==playerState[winPosition[1]] && playerState[winPosition[1]]==playerState[winPosition[2]] && playerState[winPosition[0]] !=2)
            {
                String winner;
                if(playerState[winPosition[0]] == 0) {
                    winner = "O Winner!";
                }
                else{
                    winner = "X Winner";
                }
                //updating the status for winning
                TextView status = findViewById(R.id.textView4);
                status.setText(winner);
            }

        }

    }

    public void resetGame(View view)
    {
        TextView status = findViewById(R.id.textView4);
        status.setText("O's Turn");
        gameActive = true;
        activePlayer = 0;
        //setting default array
        Arrays.fill(playerState, 2);
        //setting blank imageview
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView10)).setImageResource(0);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //remove action bar
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR); getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
