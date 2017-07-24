package android.ucsc.vaias.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.ucsc.vaias.Helper.DBServerHelper;
import android.ucsc.vaias.Helper.Session;
import android.ucsc.vaias.R;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity  implements View.OnClickListener{

    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        session=new Session(this);

        uidEditText= (EditText) findViewById(R.id.uidEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        signInButton=(Button)findViewById(R.id.signInbtn);

        signInButton.setOnClickListener(this);

        if (session.loggedIn()){
           startActivity(new Intent(this,StartActivity.class));
           finish();
       }


    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInbtn:
                signButtonOncClick();
                break;

        }
    }

    private void signButtonOncClick(){
        String UID= uidEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        if (!UID.isEmpty() && !password.isEmpty()){
            DBServerHelper dbServerHelper=new DBServerHelper(this);
            dbServerHelper.execute("LOGIN", UID, password);
            String res = dbServerHelper.getResult();


            session.setLoggedIn(true);
            Intent intent=new Intent(this,StartActivity.class);
            startActivity(intent);

            if(res.equals("success")){
              //  session.setLoggedIn(true);
                //Intent intent=new Intent(this,StartActivity.class);
               // startActivity(intent);
            }else if(res.equals("notsuccess")){
                Toast.makeText(this,"try again",Toast.LENGTH_LONG).show();
            }else if(res.equals("")){
                Toast.makeText(this,"connection error",Toast.LENGTH_LONG).show();
            }

        }

    }
    private EditText uidEditText;
    private EditText passwordEditText;
    private Button signInButton;
}
