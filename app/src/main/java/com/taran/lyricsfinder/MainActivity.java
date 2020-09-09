package com.taran.lyricsfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


public class MainActivity extends AppCompatActivity {

    EditText edtArtistName, edtSongName;
    TextView txtLyrics , txtSong;
    Button btnLyrics , btnBack;
    ConstraintLayout constraint , const2;

    //  https://api.lyrics.ovh/v1/Rihanna/Diamonds#
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtArtistName = findViewById(R.id.edtArtistName);
        edtSongName = findViewById(R.id.edtSongName);
        txtLyrics = findViewById(R.id.txtLyrics);
        btnLyrics = findViewById(R.id.btnLyrics);
        constraint = findViewById(R.id.constraint);
        txtSong = findViewById(R.id.txtSong);
        const2 = findViewById(R.id.const2);
        btnBack = findViewById(R.id.btnBack);


        btnLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                constraint.setVisibility(view.INVISIBLE);
                btnLyrics.setVisibility(view.VISIBLE);
                const2.setVisibility(view.VISIBLE);
                btnBack.setVisibility(view.VISIBLE);

                String STR = edtSongName.getText().toString();
                txtSong.setText(STR);

                String url = "https://api.lyrics.ovh/v1/" + edtArtistName.getText().toString() + "/" + edtSongName.getText().toString();
                url = url.replaceAll(" ", "%20");
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            txtLyrics.setText(jsonObject.getString("lyrics"));


                        } catch (JSONException e) {
                            Log.i("hi", "Error" + e.getMessage());

                        }


                    }
                },


                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("My tag", "Error" + error.getMessage());

                            }
                        });

                requestQueue.add(jsonObjectRequest);


            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                constraint.setVisibility(v.VISIBLE);
                btnLyrics.setVisibility(v.VISIBLE);
                const2.setVisibility(v.INVISIBLE);


            }
        });

    }
}