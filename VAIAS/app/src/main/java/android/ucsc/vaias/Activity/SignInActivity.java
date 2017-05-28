package android.ucsc.vaias.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.ucsc.vaias.Helper.Session;
import android.ucsc.vaias.R;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity  implements View.OnClickListener{

    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        session=new Session(this);

        emailEditText= (EditText) findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        signInButton=(Button)findViewById(R.id.signInbtn);

        signInButton.setOnClickListener(this);
        if (session.loggedIn()){
           startActivity(new Intent(this,HomeActivity.class));
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
        String email= emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()){
           session.setLoggedIn(true);
            Intent intent=new Intent(this,HomeActivity.class);
            startActivity(intent);
        }

    }
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
}
