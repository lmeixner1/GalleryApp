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
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String TAG = "MainActivity";

    Pokemon[] pokemons = {
            new Pokemon("Duskull", "In the dead of night, these Pokémon wander through towns in search of children, whose vital energy is a Duskull's favorite food."),
            new Pokemon("Garchomp", "It is said that when one runs at high speed, its wings create blades of wind that can fell nearby trees."),
            new Pokemon("Electabuzz", "It loves to feed on strong electricity. It occasionally appears around large power plants and so on.")
    };

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

        //Front images
        imgCard.setVisibility(View.VISIBLE);
        imgCard.setImageResource(imgs[cardNo]);

        tvCard.setText(pokemons[cardNo].getName());

        gestureDetector = new GestureDetector(this, this);
        Log.d(TAG, "onCreate: Complete");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.duskull) {
            cardNo = 0;
        } else if (id == R.id.garchomp) {
            cardNo = 1;
        } else if (id == R.id.electabuzz) {
            cardNo = 2;
        } else {
            return super.onOptionsItemSelected(item);
        }
        updateCard();
        return true;
    }

    public void updateCard(){
        try {
            if (isFront) {
                imgCard.setVisibility(View.VISIBLE);
                imgCard.setImageResource(imgBacks[cardNo]);
                tvCard.setText(pokemons[cardNo].getEntry());

            } else {
                imgCard.setVisibility(View.VISIBLE);
                tvCard.setText(pokemons[cardNo].getName());
                imgCard.setImageResource(imgs[cardNo]);

            }

            isFront = !isFront;
            Log.d(TAG, "onSingleTapUp: ");

        } catch (Exception e) {
            Log.e(TAG, "onSingleTapUp " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
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
            if (isFront) {
                message = "Go to back";
                imgCard.setVisibility(View.VISIBLE);
                imgCard.setImageResource(imgBacks[cardNo]);
                tvCard.setText(pokemons[cardNo].getEntry());

            } else {
                message = "Go to Front";
                imgCard.setVisibility(View.VISIBLE);
                tvCard.setText(pokemons[cardNo].getName());
                imgCard.setImageResource(imgs[cardNo]);

            }

            isFront = !isFront;
            Log.d(TAG, "onSingleTapUp: " + message);

        } catch (Exception e) {
            Log.e(TAG, "onSingleTapUp " + e.getMessage());
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

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}