package unifi.testmove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RiepilogoActivity extends AppCompatActivity {

    private String s;
    private int timer;
    private TextView tex1;
    private TextView tex2;
    private TextView tex3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riepilogo);

        Bundle i = getIntent().getExtras();
        s = i.getString("Tempo");
        timer = i.getInt("Timer");

        tex1 = (TextView)findViewById(R.id.textView4);
        tex2 = (TextView)findViewById(R.id.textView2);
        tex3 = (TextView)findViewById(R.id.textView3);

        tex3.setText("Hai completato il gioco in "+timer+" secondi.");
        if(s.equals("gameover")) {
            tex2.setText("GIOCO FINITO - HAI PERSO");
            tex1.setText("Hai perso al gioco per aver messo male un elemento");
        }
        if(s.equals("win")) {
            tex2.setText("GIOCO FINITO - HAI COMPLETATO LA PARTITA");
            tex1.setText("Complimenti per aver finito il gioco inserendo gli elementi correttamente");
        }
    }


}
