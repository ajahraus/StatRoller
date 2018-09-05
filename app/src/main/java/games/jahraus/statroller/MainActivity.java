package games.jahraus.statroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Random rand = new Random(System.currentTimeMillis());

    private TextView[] statBlocks = new TextView[6];
    int [] allRolls = new int[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reRollButton = (Button) findViewById(R.id.reRollButton);
        statBlocks[0] = findViewById(R.id.StrScoreTextView);
        statBlocks[1] = findViewById(R.id.DexScoreTextView);
        statBlocks[2] = findViewById(R.id.ConScoreTextView);
        statBlocks[3] = findViewById(R.id.IntScoreTextView);
        statBlocks[4] = findViewById(R.id.WisScoreTextView);
        statBlocks[5] = findViewById(R.id.ChaScoreTextView);

        View.OnClickListener reRollOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numOver15 = 0;
                do {
                    numOver15 = 0;
                    for (int i = 0; i < 6; i++) {
                        allRolls[i] = rollBestXofYdZdice(3, 4, 6);
                        if (allRolls[i] >= 15)
                            numOver15++;
                    }
                    Log.d(TAG, "onClick: " + allRolls[0] + ", " + allRolls[1] + ", " + allRolls[2] + ", " + allRolls[3] + ", " + allRolls[4] + ", " + allRolls[5] );
                    if (numOver15 < 2)
                        Log.d(TAG, "onClick: Rerolling!");

                } while (numOver15 < 2);

                for  (int i = 0; i < 6; i++) {
                    statBlocks[i].setText(Integer.toString(allRolls[i]));
                }
            }
        };

        reRollButton.setOnClickListener(reRollOnClickListener);
    }

    public int rollBestXofYdZdice(int x, int y, int z){

        if (x > y){
            Log.d(TAG, "rollBestXofYdZdice: First argument must be lower than the second");
            return 0;
        }
        String TAG = "Rolling";
        // Seed the random generator with current time

        Integer[] rolls = new Integer[y];

        // roll all the dice
        for (int i = 0; i < y; i++) {
            rolls[i] = rand.nextInt(z) + 1;
        }
        Arrays.sort(rolls, Collections.reverseOrder());

        int total = 0;
        for (int i = 0; i < x; i++){
            total += rolls[i];
        }

        return total;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("allRolls", allRolls);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allRolls = savedInstanceState.getIntArray("allRolls");

        for  (int i = 0; i < 6; i++) {
            statBlocks[i].setText(Integer.toString(allRolls[i]));
        }
    }
}
