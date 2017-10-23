package unifi.testmove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IstruzioniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istruzioni);
    }

    public void ahRekt (View view) {
        finish();
    }
}
