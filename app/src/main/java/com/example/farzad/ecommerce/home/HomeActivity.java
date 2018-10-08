package com.example.farzad.ecommerce.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.farzad.ecommerce.R;
import com.example.farzad.ecommerce.RecylerViewAdapter;
import com.example.farzad.ecommerce.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecylerViewAdapter mViewAdapter;
    private ArrayList<Product> mArrayCollection;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        new FetchDataTask().execute();
    }

    public void init(){
        mRecyclerView=(RecyclerView)findViewById(R.id.product_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mArrayCollection=new ArrayList<>();
        mViewAdapter=new RecylerViewAdapter(mArrayCollection,this);
        mRecyclerView.setAdapter(mViewAdapter);


    }

    public class FetchDataTask extends AsyncTask<Void,Void,Void>{
        private String mZomatoString;
        private ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Getting data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    FetchDataTask.this.cancel(true);
                }
            });
        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            Uri buildUri=Uri.parse("https://jsonplaceholder.typicode.com/posts");

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
//                JSONObject jsonObject=new JSONObject(buffer.toString());
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
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                Log.v("Response",jArray.toString());
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String title = jObject.getString("title");
                    String body = jObject.getString("body");
                    int userId = jObject.getInt("userId");

                    Product product=new Product(title,body,"image");
                    mArrayCollection.add(product);

                } // End Loop
                mViewAdapter.notifyDataSetChanged();
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)

        } // protected void onPostExecute(Void v)
    } //class MyAsyncTask extends AsyncTask<String, String, Void>

}
