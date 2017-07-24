package android.ucsc.vaias.Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by sajja on 1/12/2017.
 */

public class
DBServerHelper extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    Bitmap image;
    private String result;
    private String res1="";

    public DBServerHelper(Context ctx){
        context=ctx;
        result="";
    }
    public DBServerHelper(Bitmap btm, Context ctx){
        image=btm;
        context=ctx;
        result="";
    }
    public String getResult(){
        System.out.println("===="+res1);
        return res1;
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];


        String SLUrl="http://192.168.8.100/vaias/sendAccident.php";
        String LIUrl="http://192.168.8.100/vaias/logIn.php";

        if (type.equals("SL")){
            try {

                String UID=params[1];
                String LON=params[2];
                String LAT=params[3];
                String VID=params[4];

               /* String UID="565";
                String LAT="2";
                String LON="4";*/

                URL url=new URL(SLUrl);

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data=   URLEncoder.encode("UID","UTF-8")+"="+ URLEncoder.encode(UID,"UTF-8")+"&"+
                                    URLEncoder.encode("LAT","UTF-8")+"="+ URLEncoder.encode(LAT,"UTF-8")+"&"+
                                    URLEncoder.encode("LON","UTF-8")+"="+ URLEncoder.encode(LON,"UTF-8")+"&"+
                                    URLEncoder.encode("VID","UTF-8")+"="+ URLEncoder.encode(VID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                 //result="";
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("LOGIN")){
            try {
                res1="";

                String UID=params[1];
                String PASSWORD=params[2];


                URL url=new URL(LIUrl);

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data=   URLEncoder.encode("UID","UTF-8")+"="+ URLEncoder.encode(UID,"UTF-8")+"&"+
                                    URLEncoder.encode("PASSWORD","UTF-8")+"="+ URLEncoder.encode(PASSWORD,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                //result="";
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                this.res1=result;
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
       alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        this.res1=result;
        alertDialog.setMessage(result);
        alertDialog.show();


    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private HttpParams getHttpRequestParm() {
        //HttpParams httpRequestParams = new BasicHttpParams();
        //HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        //HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return null;
    }
}
