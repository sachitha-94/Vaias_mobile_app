package android.ucsc.vaias.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sajja on 5/28/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context){
        this.context=context;
        prefs=context.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor=prefs.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("loggedInmode",loggedIn);
        editor.commit();
    }

    public boolean loggedIn(){
        return prefs.getBoolean("loggedInmode",false);
    }
}
