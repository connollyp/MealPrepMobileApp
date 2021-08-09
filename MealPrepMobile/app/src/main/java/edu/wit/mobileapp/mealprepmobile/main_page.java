package edu.wit.mobileapp.mealprepmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class main_page extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Meal> mealList;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        int userId = getIntent().getIntExtra("userId", 0);
        mList = findViewById(R.id.meal_list);

        mealList=new ArrayList<>();



        adapter=new MealAdapter(getApplicationContext(), mealList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
        getData(userId);


    }

    private void getData(int userId){

        String url ="http://3.233.98.252:8080/getMeals?userId='" + Integer.toString(userId) + "'";
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading meals");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray = response.getJSONArray("success");
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Meal meal = new Meal();
                        meal.setName(jsonObject.getString("Name"));
                        meal.setCalories(jsonObject.getInt("Calories"));
                        meal.setProtein(jsonObject.getInt("Protein"));
                        meal.setCarbs(jsonObject.getInt("Carbs"));
                        meal.setTotalFat(jsonObject.getInt("Total_Fat"));
                        meal.setFiber(jsonObject.getInt("Fiber"));
                        meal.setSugar(jsonObject.getInt("Sugar"));

                        mealList.add(meal);
                        System.out.println(mealList.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(main_page.this);
        queue.add(jsonObjectRequest);

    }
}