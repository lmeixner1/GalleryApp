package edu.fvtc.galleryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String TAG = "MainActivity";

    Pokemon[] pokemons = {
            new Pokemon("Duskull", "In the dead of night, these Pokémon wander through towns in search of children, whose vital energy is a Duskull's favorite food."),
            new Pokemon("Garchomp", "It is said that when one runs at high speed, its wings create blades of wind that can fell nearby trees."),
            new Pokemon("Electabuzz", "It loves to feed on strong electricity. It occasionally appears around large power plants and so on.")
    };

    int[] textFiles = {R.raw.duskull, R.raw.garchomp, R.raw.electabuzz};
    int[] imgs = {R.drawable.duskull, R.drawable.garchomp, R.drawable.electabuzz};
    int[] imgBacks = {R.drawable.duskull2, R.drawable.garchomp2, R.drawable.electabuzz2};
    int cardNo = 0;
    boolean isFront = true;
    ImageView imgCard;
    TextView tvCard;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCard = findViewById(R.id.ivPokemon);
        tvCard = findViewById(R.id.tvInfo);

        updateCard();

        gestureDetector = new GestureDetector(this, this);
        Log.d(TAG, "onCreate: Complete");
    }

    private void updateCard()
    {
        pokemons[cardNo].setEntry(readFile(textFiles[cardNo]));

        isFront = true;
        imgCard.setVisibility(View.VISIBLE);
        imgCard.setImageResource(imgs[cardNo]);
        tvCard.setText(pokemons[cardNo].getName());


    }

    private String readFile(int fileId)
    {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;

        try {
            inputStream = getResources().openRawResource(fileId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();

            String data;

            while((data = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(data).append("\n");
            }

            // Clean up objects
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            Log.d(TAG, "readFile: " + stringBuffer.toString());
            return stringBuffer.toString();
        }
        catch (Exception e)
        {
            Log.d(TAG, "readFile: " + e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    // one of those things that I have to remember!
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        Log.d(TAG, "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {
        Log.d(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: ");

        String message;

        try {
            if(isFront){
                //Go to back
                message = "Got to back";
                imgCard.setVisibility(View.VISIBLE);
                imgCard.setImageResource(imgBacks[cardNo]);
                tvCard.setText(pokemons[cardNo].getEntry());
            }
            else{
                //Go to front
                message = "Got to front";
                imgCard.setVisibility(View.VISIBLE);
                imgCard.setImageResource(imgs[cardNo]);
                tvCard.setText(pokemons[cardNo].getName());
            }

            isFront = !isFront;
            Log.d(TAG, "onSingleTapUp: " + message);
        }
        catch(Exception e)
        {
            Log.e(TAG, "onSingleTapUp " + e.getMessage() );
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {
        Log.d(TAG, "onLongPress: ");

    }

    @Override
    public boolean onFling(@Nullable MotionEvent motionEvent1, @NonNull MotionEvent motionEvent2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: ");

        int numCards = pokemons.length;

        try {
            // Decide which direction I want to fling
            // Only tracking left and right along the X
            int x1= (int) (motionEvent1 != null ? motionEvent1.getX() : 0);
            int x2= (int)motionEvent2.getX();

            if(x1 < x2)
            {
                Animation move = AnimationUtils.loadAnimation(this, R.anim.move_right);
                move.setAnimationListener(new AnimationListener());
                imgCard.startAnimation(move);
                tvCard.startAnimation(move);
                // swipe right
                Log.d(TAG, "onFling: Right");
                cardNo = (cardNo - 1 + numCards) % numCards;
            } else {
                Animation move = AnimationUtils.loadAnimation(this, R.anim.move_left);
                move.setAnimationListener(new AnimationListener());
                imgCard.startAnimation(move);
                tvCard.startAnimation(move);
                // swipe left
                Log.d(TAG, "onFling: Left");
                cardNo = (cardNo + 1) % numCards;
            }
            //updateCard();

        }
        catch (Exception ex)
        {
            Log.e(TAG, "onFling " + ex.getMessage() );
            ex.printStackTrace();
        }

        return true;
    }

    private class AnimationListener implements Animation.AnimationListener
    {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d(TAG, "onAnimationEnd: ");
            updateCard();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}