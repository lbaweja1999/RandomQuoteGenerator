package com.example.computersciencequotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button button_showquote;
TextView textView_quote;
List<String> list_quotes;
Random random_index;
int index;
String quote;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_showquote=findViewById(R.id.btn_showquote);
        textView_quote=findViewById(R.id.textView_quote);
        list_quotes=new ArrayList<>();
        random_index=new Random();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Generating Quote....");
button_showquote.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
getAllquotes();
    }
});
    }
    public void getAllquotes(){
        String url="https://programming-quotes-api.herokuapp.com/quotes/lang/en";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONArray jsonArray_data = new JSONArray(response);
                    for(int i=0;i<jsonArray_data.length();i++){
                        JSONObject jsonObject_data=jsonArray_data.getJSONObject(i);
                        String quotes=jsonObject_data.getString("en");
                        list_quotes.addAll(Collections.singleton(quotes));
                    }

                    index=random_index.nextInt(list_quotes.size());
                    quote=list_quotes.get(index);
                    textView_quote.setText(quote);
                    textView_quote.setVisibility(View.VISIBLE);
                    button_showquote.setText("SHOW ANOTHER QUOTE");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                 super.getHeaders();
                 Map<String, String> header = new HashMap<>();
                 header.put("Content-Type","application/json");
                 return header;
            }
        };
        requestQueue.add(stringRequest);
    }

}
