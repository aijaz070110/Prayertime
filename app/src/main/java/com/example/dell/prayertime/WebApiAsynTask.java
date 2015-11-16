package com.example.dell.prayertime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 9/10/2015.
 */
public class WebApiAsynTask  extends AsyncTask<String, Void, String> {
//
    private final HttpClient httpclient = new DefaultHttpClient();

    final HttpParams params = httpclient.getParams();
    HttpResponse response;

    private boolean error = false;

    private Context mContext;
    WebApiAsynTask   prayersAsyncTask;

    String requestURL ;
    List<NameValuePair> nameValuePairs;
    boolean isGetRequest;

    JSONResponseCallback  responseCallback;
    private ProgressDialog dialog ;

//constractor call
    public WebApiAsynTask(Context context, boolean isGetRequest, String requestURL, List<NameValuePair> nameValuePairs,JSONResponseCallback responseCallback)
    {
        this.mContext = context;
        this.requestURL = requestURL;
        this.nameValuePairs = nameValuePairs;
          this.isGetRequest = isGetRequest;
        this.responseCallback = responseCallback;
        dialog = new ProgressDialog(this.mContext);

    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Prayer Time");
        this.dialog.show();
        dialog.setIndeterminate(true);
        dialog.show();
        dialog.setMax(120);
        super.onPreExecute();

    }
    @Override
    protected String doInBackground(String...urls) {
//        String URL ="http://weather.yahooapis.com/forecastrss";
       String content = null;

        if(isGetRequest)
        {
                content = httpGetAPICall(requestURL);


        }
        else
        {
         content = httpPostAPICall(requestURL);
        }



        return content;
    }


    @Override
    protected void onPostExecute(String s) {
//
//        long currentTime = System.currentTimeMillis();
//        currentTime+= (2*60*60*1000);


        if(s!=null)
        {
            try {
                JSONObject jsonObject = new JSONObject(s);
              // Toast.makeText(mContext,"Response is: "+ s,Toast.LENGTH_SHORT).show();

                responseCallback.onCompleteResponse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
//        super.onPostExecute(s);
    }

    public String httpPostAPICall(String requestURL)
    {
         String content = null;
        try {

            //URL passed to the AsyncTask


            JSONObject json= new JSONObject();
            HttpPost httpPost = new HttpPost(requestURL);

            //Any other parameters you would like to set
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//            nameValuePairs.add(new BasicNameValuePair("data",json.toString()));


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Response from the Http Request
            response = httpclient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            //Check the Http Request for success
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                content = out.toString();
            } else {
                //Closes the connection.
                Log.w("HTTP1:", statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }


        } catch (ClientProtocolException e) {
            Log.w("HTTP2:", e);
            content = e.getMessage();
            error = true;
            cancel(true);
        } catch (IOException e) {
            Log.w("HTTP3:", e);
            content = e.getMessage();
            error = true;
            cancel(true);
        } catch (Exception e) {
            Log.w("HTTP4:", e);
            content = e.getMessage();
            error = true;
            cancel(true);
        }
        return  content;
    }



    public String httpGetAPICall(String requestURL){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(requestURL);


        try{

            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
            } else {
                Log.e(MainActivity.class.toString(),"Failedet JSON object");
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }






}
