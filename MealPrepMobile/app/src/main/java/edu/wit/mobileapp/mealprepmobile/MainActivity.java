package edu.wit.mobileapp.mealprepmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String username ="";
    String password = "";

    EditText editTextUsername;
    EditText editTextPassword;
    Button login;
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = (EditText)findViewById(R.id.editTextTextPersonName);
        editTextPassword = (EditText)findViewById(R.id.editTextTextPassword);
        login = (Button) findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="http://3.233.98.252:8080/login?username='" + username + "'&password='" + password + "'";


                // Request a string response from the provided URL.
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                int userId = 0;
                                try {
                                        Intent intent = new Intent(MainActivity.this, main_page.class);
                                        userId=response.getInt("userId");
                                        intent.putExtra("userId", userId);
                                        startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(MainActivity.this, "found user " + userId, Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();

                    }
                });

// Add the request to the RequestQueue.
                queue.add(request);

            }


        });

    }


}



