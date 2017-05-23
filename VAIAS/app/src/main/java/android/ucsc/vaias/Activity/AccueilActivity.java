package android.ucsc.vaias.Activity;

import android.content.Intent;
import android.ucsc.vaias.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void btn_lauch_home_activity(View v) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

    }
}
