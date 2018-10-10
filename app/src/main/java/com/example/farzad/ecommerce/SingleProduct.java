package com.example.farzad.ecommerce;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farzad.ecommerce.home.HomeActivity;
import com.example.farzad.ecommerce.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SingleProduct extends AppCompatActivity {
    Product product;
    TextView titleView,description;
    ImageView image;
    int id;
    JSONObject jsonObj;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Intent intent = getIntent();

        this.id = intent.getIntExtra("id", 0);
//        Log.v("Response",String.valueOf(id));
        new SingleProduct.FetchDataTask().execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        titleView=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.description);
        image=(ImageView) findViewById(R.id.image);



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void show(String title,String des,String imageUrl,int userId){
        product=new Product(title,des,"image",userId);
        titleView.setText(product.getName());
        description.setText(product.getAddress());
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(image);

    }


    public class FetchDataTask extends AsyncTask<Void,Void,Void> {
        private String mZomatoString;
        private ProgressDialog progressDialog = new ProgressDialog(SingleProduct.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Getting data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    SingleProduct.FetchDataTask.this.cancel(true);
                }
            });
        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            Uri buildUri=Uri.parse("https://jsonplaceholder.typicode.com/comments/"+id);

            try
            {
                URL url = new URL(buildUri.toString());
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream=urlConnection.getInputStream();

                StringBuffer buffer=new StringBuffer();
                if (inputStream ==null){
                    return null;
                }
//                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                }
                if (buffer.length()==0){
                    return null;
                }
                result=buffer.toString();
                JSONObject jsonObject=new JSONObject(buffer.toString());
//
//                Log.v("Response",jsonObject.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally {
                if(urlConnection !=null){
                    urlConnection.disconnect();
                }
                if(reader !=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            try {
                JSONObject jsonObject=new JSONObject(result);
                String title = jsonObject.getString("name");
                String body = jsonObject.getString("body");
                int userId = jsonObject.getInt("id");
                show(title,body,"image",userId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.progressDialog.dismiss();

        } // protected void onPostExecute(Void v)
    } //class MyAsyncTask extends AsyncTask<String, String, Void>


}
