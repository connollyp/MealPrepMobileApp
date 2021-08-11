package edu.wit.mobileapp.mealprepmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class create_account extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextEmail;
    String email="";
    String username ="";
    String password = "";


    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation_page);

        editTextUsername = (EditText)findViewById(R.id.editTextTextPersonName);
        editTextPassword = (EditText)findViewById(R.id.editTextTextPassword);
        editTextEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        submit = (Button) findViewById(R.id.button);



        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();



                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(create_account.this);
                String url ="http://3.233.98.252:8080/uploadNewUser?username='" + username + "'&password='" + password + "'&email='" + email + "'";


                // Request a string response from the provided URL.
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                int userId = 0;
                                Intent intent = new Intent(create_account.this, MainActivity.class);
                                startActivity(intent);

                                //Toast.makeText(MainActivity.this, "found user " + userId, Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();

                    }
                });

// Add the request to the RequestQueue.
                queue.add(request);

            }


        });
    }
}