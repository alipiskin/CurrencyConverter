package com.afpsoft.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    TextView textView3;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        editText=findViewById(R.id.editText);



    }

    public void getRates(View view) {

        DownloadData downloadData= new DownloadData();

        try {
            String url = "https://api.fixer.io/latest?base=";
            String chosenBase=editText.getText().toString();

            InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            System.out.println("URL = "+url+chosenBase);
            downloadData.execute(url+chosenBase);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject= new JSONObject(s);
                String base= jsonObject.getString("base");
                String date= jsonObject.getString("date");
                String rates=jsonObject.getString("rates");
                System.out.println(base);
                System.out.println(date);
                System.out.println(rates);

                JSONObject jsonObject1= new JSONObject(rates);
                String chf= jsonObject1.getString("CHF");
                String czk=jsonObject1.getString("CZK");
                String trl=jsonObject1.getString("TRY");
                System
                        .out.println(chf);
                System.out.println(czk);
                System.out.println(trl);

                textView.setText("CHF: " + chf);
                textView2.setText("CZK:"+ czk);
                textView3.setText("TRY:"+ trl);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpsURLConnection httpsURLConnection;


            try {

                url= new URL(strings[0]);
                httpsURLConnection= (HttpsURLConnection) url.openConnection();
                InputStream inputStream= httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);

                int data= inputStreamReader.read(); //okunan data sayısı. data bitince sıfır oluyor

                while (data >0) {

                    char character= (char) data;
                    result +=character;

                    data=inputStreamReader.read();

                }


                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
