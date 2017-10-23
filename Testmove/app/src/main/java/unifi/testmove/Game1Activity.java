package unifi.testmove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Game1Activity extends Activity implements View.OnClickListener{

    private int a,b;
    private TextView vns, vd;

    static int REP_DELAY = 100;

    public static final String PREFS_NAME = "MyPrefsFile";

    private Handler repeatUpdateHandler = new Handler();


    class RptUpdater implements Runnable {
        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            } else if( mAutoDecrement ){
                decrement();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            }
        }
    }

    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        a = settings.getInt("binValue", 15);
        b = settings.getInt("difficultValue", 1);

        setContentView(R.layout.activity_game1);

        setupButton();
    }


    public void setupButton(){

        Button pns = (Button)findViewById(R.id.PlusNumberSquare);
        Button mns = (Button)findViewById(R.id.MinusNumberSquare);

        Button pd =(Button)findViewById((R.id.PlusDifficult));
        Button md =(Button)findViewById((R.id.MinusDifficult));

        Button random = (Button)findViewById(R.id.RandomValue);
        Button back = (Button)findViewById(R.id.BackMenu);

        pns.setOnLongClickListener(new View.OnLongClickListener(){
                                       public boolean onLongClick(View arg0) {
                                           mAutoIncrement = true;
                                           repeatUpdateHandler.post( new RptUpdater() );
                                           return false;
                                       }
                                   }
        );

        pns.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        mns.setOnLongClickListener(new View.OnLongClickListener(){
                                       public boolean onLongClick(View arg0) {
                                           mAutoDecrement = true;
                                           repeatUpdateHandler.post( new RptUpdater() );
                                           return false;
                                       }
                                   }
        );

        mns.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement ){
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        random.setOnClickListener(this);
        back.setOnClickListener(this);
        pns.setOnClickListener(this);
        mns.setOnClickListener(this);
        pd.setOnClickListener(this);
        md.setOnClickListener(this);

        vns = (TextView)findViewById(R.id.ValueNumberSquare);
        vd = (TextView)findViewById(R.id.ValueDifficult);

        vns.setText("" + a);
        vd.setText(Difficult(b));
    }
    public void playTheGame(View view) {
        Intent intent = new Intent(this, OpenGLES20Activity.class);
        int[] message = new int[2];
        message[0] = a;
        message[1] = b;
        intent.putExtra("number", message);
        startActivity(intent);
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("binValue", a);
        editor.putInt("difficultValue", b);

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RandomValue) {
            a = 1 + (int) (Math.random() * (50));
            vns.setText(""+a);
        }
        if (v.getId() == R.id.PlusNumberSquare) {
            increment();
        }
        if (v.getId() == R.id.MinusNumberSquare) {
            decrement();
        }
        if (v.getId() == R.id.PlusDifficult) {
            incrementD();
        }
        if (v.getId() == R.id.MinusDifficult) {
            decrementD();
        }
    }

    public void increment() {
        if (a < 50) {
            a++;
            vns.setText("" + a);
        }
    }

    public void decrement() {
        if (a > 2) {
            a--;
            vns.setText("" + a);
        }
    }

    public void incrementD() {
        if (b < 5) {
            b++;
            vd.setText(Difficult(b));
        }
    }

    public void decrementD() {
        if (b > 1) {
            b--;
            vd.setText(Difficult(b));
        }
    }

    public String Difficult(int a) {
        String b="";
        switch (a) {
            case 1:{
                b = "Next Fit";
                break;
            }
            case 2:{
                b = "First Fit";
                break;
            }
            case 3:{
                b = "Best Fit";
                break;
            }
            case 4:{
                b = "First Fit Descreasing";
                break;
            }
            case 5:{
                b = "Best Fit Descreasing";
                break;
            }
            default:break;
        }
        return b;
    }



}

