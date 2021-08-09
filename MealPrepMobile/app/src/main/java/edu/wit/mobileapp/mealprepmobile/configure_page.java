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

import org.json.JSONObject;

public class configure_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_page);

        Button submit = findViewById(R.id.submitButton);
        Button back = findViewById(R.id.backButton);
        int userId = getIntent().getIntExtra("userId", 0);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all data
                EditText meals = findViewById(R.id.meals);
                EditText calories = findViewById(R.id.calories);
                EditText protein = findViewById(R.id.protein);
                EditText carbs = findViewById(R.id.carbs);
                EditText fat = findViewById(R.id.fat);
                EditText fiber = findViewById(R.id.fiber);
                EditText sugar = findViewById(R.id.sugar);

                EditText fn = findViewById(R.id.firstName);
                EditText ln = findViewById(R.id.lastName);
                EditText a = findViewById(R.id.age);
                EditText wt = findViewById(R.id.weight);
                EditText ht = findViewById(R.id.height);

                String calCount = calories.getText().toString().trim();
                String proteinCount = protein.getText().toString().trim();
                String carbCount = carbs.getText().toString().trim();
                String fatCount = fat.getText().toString().trim();
                String sugarCount = sugar.getText().toString().trim();
                String fiberCount = fiber.getText().toString().trim();
                String mealCount = meals.getText().toString().trim();

                String firstName = fn.getText().toString().trim();
                String lastName = ln.getText().toString().trim();
                String age = a.getText().toString().trim();
                String weight = wt.getText().toString().trim();
                String height = ht.getText().toString().trim();

                RequestQueue queue = Volley.newRequestQueue(configure_page.this);
                String url = "http://3.233.98.252:8080/uploadUserConfig?userId='" +
                        userId +
                        "'&firstname='" + firstName +
                        "'&lastname='" + lastName +
                        "'&age='" + age +
                        "'&weight='" + weight +
                        "'&height='" + height +
                        "'&calories='" + calCount +
                        "'&protein='" + proteinCount +
                        "'&carbs='" + carbCount +
                        "'&fat='" + fatCount +
                        "'&sugar='" + sugarCount +
                        "'&fiber='" + fiberCount +
                        "'&numMeals='" + mealCount;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(configure_page.this, "Configuration updated.", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(configure_page.this, "Please fill out the fields properly.", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(configure_page.this, main_page.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
}