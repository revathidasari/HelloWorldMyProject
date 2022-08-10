package com.example.helloworldgrpc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koin2.RetrofitAccess;
import com.example.nativelibc.NativeLib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

public class RESTApiAccess extends AppCompatActivity {

    String myUrl = "https://api.github.com/";//"https://127.0.0.1:50005/session";// /*"https://httpbin.org/post";*/"https://api.mocki.io/v1/a44b26bb";
    TextView resultsTextView;
    ProgressDialog progressDialog;
    Button displayData, restApiButton;

    /* https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
    * https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751
    *  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restapi_access);
        String[] names = {"login","seq","password"};
        String[] values = { "admin", "2811", "admin" };


        NativeLib nativeLibc =new NativeLib();
        Toast.makeText(this, "lib "+nativeLibc.stringFromJNI()+nativeLibc.getText(), Toast.LENGTH_SHORT).show();
        WebView myWebView = (WebView) findViewById(R.id.webview);
       // myWebView.loadUrl("http://www.example.com");
                //"file:///home/ee211905/AndroidStudioProjects/HelloWorldGrpc/app/src/main/java/com.example.helloworldgrpc/");

        //https://www.myawesomesite.com/turtles/types?type=1&sort=relevance#section-name
        //https://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt
/*        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("jsonparsing.parseapp.com")
                .appendPath("jsonData")
                .fragment("moviesDemoItem.txt");
        String newUrl = builder.build().toString();*/


//String newUrl = new URL("https://www.example.com");
        //String url = "https://localhost:8844/login";
/*
        try {
            httpPost(myUrl, names, values);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
/*        try {
            URL httpbinEndpoint = new URL(myUrl);
            HttpsURLConnection myConnection
                    = (HttpsURLConnection) httpbinEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            // Create the data
            String myData = "message=Hello";
            myConnection.setDoOutput(true);
         //   myConnection.getOutputStream().write(myData.getBytes());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        resultsTextView = (TextView) findViewById(R.id.results);
        displayData = (Button) findViewById(R.id.displayData);
        restApiButton = (Button) findViewById(R.id.restApiButton);
        // implement setOnClickListener event on displayData button
        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create object of MyAsyncTasks class and execute it
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
            }
        });
        restApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RESTApiAccess.this, RetrofitAccess.class));
             //   new JSONTask().execute("https://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");
            }
        });
    }

    public static String httpPost(String urlStr, String[] paramName, String[] paramVal) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStream out = conn.getOutputStream();
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        for (int i = 0; i < paramName.length; i++) {
            writer.write(paramName[i]);
            writer.write("=");
            writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
            writer.write("&");
        }
        System.out.println("WRITER: " + writer);
        writer.close();
        out.close();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(RESTApiAccess.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            // Fetch data from the API in the background.
            String result = "";
            InetAddress addr = null;
            URL u = null;
            try {
                    addr = InetAddress.getByName("172.20.128.94");
                    u = new URL("https://"+addr.getHostAddress()+":50005");
                Log.d("revathi","addr "+addr+" u "+u);
u.openConnection();
                InputStream is = u.openStream();
                Log.d("revathi","addr ");

                    InputStreamReader isReader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isReader);
                    String line;
                    while((line = reader.readLine()) != null){
                        System.out.println(line);
                        Log.d("revathi","line "+line);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("revathi","addr "+addr+" u "+u);
/*                try {
                    URL httpbinEndpoint = new URL(myUrl);
                    HttpsURLConnection myConnection
                            = (HttpsURLConnection) httpbinEndpoint.openConnection();
                    myConnection.setRequestMethod("POST");
                    // Create the data
                    String myData = "message=Hello";
                    myConnection.setDoOutput(true);
                    //   myConnection.getOutputStream().write(myData.getBytes());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
               /* URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(myUrl);
                    //open a URL coonnection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    InputStream in;
                    if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        in = urlConnection.getErrorStream();
                    } else {
                        in = urlConnection.getInputStream();
                    }
*//*                    InputStreamReader isw = new InputStreamReader(in);
                    int data = isw.read();
                    while (data != -1) {
                        result += (char) data;
                        data = isw.read();
                    }*//*

                    StringBuffer buffer = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line).append("\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }

                    result = buffer.toString();
                  //  result = urlConnection.getInputStream().toString();
                    // return the data to onPostExecute method
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }*/
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {
                Log.d("revathi","onpost execute : "+s);
/*                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray1 = jsonObject.getJSONArray("user_url");
                JSONObject jsonObject1 =jsonArray1.getJSONObject(1);
                String id = jsonObject1.getString("message");*/
                //String my_users = "User Data: "+id+"\n"+"Name: ";

                //Show the Textview after fetching data
                resultsTextView.setVisibility(View.VISIBLE);

                //Display data with the Textview
                resultsTextView.setText(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class JSONTask extends AsyncTask<String,String,String>{
        HttpsURLConnection connection = null;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url=new URL(params[0]);
                connection= (HttpsURLConnection)url.openConnection();
                connection.connect();

                InputStream stream= connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                StringBuffer buffer= new StringBuffer();
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null) {
                    connection.disconnect();
                }
                try {
                    if(reader!=null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultsTextView.setText(result);

        }
    }
}