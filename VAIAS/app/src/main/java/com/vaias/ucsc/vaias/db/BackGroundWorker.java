package com.vaias.ucsc.vaias.db;

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



public class BackGroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    Bitmap image;
    private String result;
    private String res1="";

    public BackGroundWorker(Context ctx){
        context=ctx;
        result="";
    }
    public BackGroundWorker(Bitmap btm, Context ctx){
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
       // String loginUrl="http://192.168.1.2/logIn.php";
       // String registerUrl="http://192.168.1.2/register.php";

        String loginUrl="http://192.168.43.35/pocketBird/logIn.php";
        String registerUrl="http://192.168.43.35/pocketBird/register.php";
        String shareLogNoteUrl="http://192.168.43.35 /pocketBird/shareLogNote.php";

        if (type.equals("logIn")){
            try {

                String userEmail=params[1];
                String userPassword=params[2];
                URL url=new URL(loginUrl);

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("user_email","UTF-8")+"="+ URLEncoder.encode(userEmail,"UTF-8")+"&"+
                        URLEncoder.encode("user_password","UTF-8")+"="+ URLEncoder.encode(userPassword,"UTF-8");
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

                //this.res1=result;
               // System.out.println("....."+res1);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("register")){
            try {
                String firstName = params[1];
                String lastName = params[2];
                String userName = params[3];
                String userType = params[4];
                String email=params[5];
                String password=params[6];

                URL url=new URL(registerUrl);

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=   URLEncoder.encode("first_name","UTF-8")+"="+ URLEncoder.encode(firstName,"UTF-8")+"&"+
                                    URLEncoder.encode("last_name","UTF-8")+"="+ URLEncoder.encode(lastName,"UTF-8")+"&"+
                                    URLEncoder.encode("user_name","UTF-8")+"="+ URLEncoder.encode(userName,"UTF-8")+"&"+
                                    URLEncoder.encode("user_type","UTF-8")+"="+ URLEncoder.encode(userType,"UTF-8")+"&"+
                                    URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                                    URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
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

                //this.res1=result;
                //System.out.println("....."+result);

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(type.equals("shareLogNote")){
            try {
               /* String iid="44";

                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                String encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

               ArrayList<NameValuePair> dataToSend=new ArrayList<>();
                dataToSend.add(new BasicNameValuePair("image",encodedImage));
                dataToSend.add(new BasicNameValuePair("iid",iid));

                HttpParams httpRequestParams =getHttpRequestParm();
                HttpClient client=new DefaultHttpClient(httpRequestParams);
                HttpPost post=new HttpPost(shareLogNoteUrl);

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);*/





               /////*
                String iid=params[1];
                String sid = params[2];
                String date = params[3];
                String time = params[4];
                String province = params[5];
                String nearestCity = params[6];
                String village = params[7];
                String exactLocation = params[8];
                String lon = params[9];
                String lat = params[10];
                String elevation = params[11];
                String habitat = params[12];
                String name = params[13];
                String looksLike = params[14];
                String size = params[15];
                String colour = params[16];
                String behaviour =params[17];
                String otherNote = params[18];
                String uid ="10";

                //concat image id***************
                iid=uid+iid;


                URL url=new URL(shareLogNoteUrl);

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=
                        URLEncoder.encode("user_ID","UTF-8")+"="+ URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("image_ID","UTF-8")+"="+ URLEncoder.encode(iid,"UTF-8")+"&"+
                        URLEncoder.encode("shape","UTF-8")+"="+ URLEncoder.encode(sid,"UTF-8")+"&"+
                        URLEncoder.encode("date","UTF-8")+"="+ URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("time","UTF-8")+"="+ URLEncoder.encode(time,"UTF-8")+"&"+
                        URLEncoder.encode("province","UTF-8")+"="+ URLEncoder.encode(province,"UTF-8")+"&"+
                        URLEncoder.encode("village","UTF-8")+"="+ URLEncoder.encode(village,"UTF-8")+"&"+
                        URLEncoder.encode("exact_location","UTF-8")+"="+ URLEncoder.encode(exactLocation,"UTF-8")+"&"+
                        URLEncoder.encode("nearest_city","UTF-8")+"="+ URLEncoder.encode(nearestCity,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+"="+ URLEncoder.encode(lon,"UTF-8")+"&"+
                        URLEncoder.encode("latitude","UTF-8")+"="+ URLEncoder.encode(lat,"UTF-8")+"&"+
                        URLEncoder.encode("habbitat","UTF-8")+"="+ URLEncoder.encode(habitat,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("size","UTF-8")+"="+ URLEncoder.encode(size,"UTF-8")+"&"+
                        URLEncoder.encode("looks_like","UTF-8")+"="+ URLEncoder.encode(looksLike,"UTF-8")+"&"+
                        URLEncoder.encode("colors","UTF-8")+"="+ URLEncoder.encode(colour,"UTF-8")+"&"+
                        URLEncoder.encode("behaviour","UTF-8")+"="+ URLEncoder.encode(behaviour,"UTF-8")+"&"+
                        URLEncoder.encode("Description","UTF-8")+"="+ URLEncoder.encode(otherNote,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
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
        //this.res1=result;
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

   /* private HttpParams getHttpRequestParm() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }*/
}
