package unifi.testmove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void gneSilly(View view) {
        Intent intent = new Intent(this, Game1Activity.class);
        startActivity(intent);
    }

    public void gneIstruzioni(View view) {
        Intent intent = new Intent(this, IstruzioniActivity.class);
        startActivity(intent);
    }

    public void gneRip(View view) {
        System.exit(0);
    }
}
