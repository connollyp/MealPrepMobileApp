package edu.wit.mobileapp.mealprepmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class meal_plan extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Meal> mealList;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        Button configure = (Button) findViewById(R.id.configure_button);
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
        getData(0);

        configure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meal_plan.this, configure_page.class);
                intent.putExtra("userId", userId);
                startActivity(intent);


            }
        });

    }

    private void getData(int mealPlanId){

        String url ="http://3.233.98.252:8080/getMealsInMealPlan?mealPlanId='" + Integer.toString(mealPlanId) + "'";

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
                                String mealUrl = "http://3.233.98.252:8080/getMeal?mealId='" + Integer.toString(jsonObject.getInt("Meal_Id")) + "'";
                                System.out.println(mealUrl);
                                JsonObjectRequest mealRequest = new JsonObjectRequest(Request.Method.GET, mealUrl, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject r) {
                                        try {
                                            JSONArray mealArray = r.getJSONArray("success");
                                            JSONObject mealObject = mealArray.getJSONObject(0);
                                            Meal meal = new Meal();
                                            meal.setName(mealObject.getString("Name"));
                                            meal.setCalories(mealObject.getInt("Calories"));
                                            meal.setProtein(mealObject.getInt("Protein"));
                                            meal.setCarbs(mealObject.getInt("Carbs"));
                                            meal.setTotalFat(mealObject.getInt("Total_Fat"));
                                            meal.setFiber(mealObject.getInt("Fiber"));
                                            meal.setSugar(mealObject.getInt("Sugar"));

                                            mealList.add(meal);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("volley", error.toString());
                                    }
                                }

                                );


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

        RequestQueue queue = Volley.newRequestQueue(meal_plan.this);
        queue.add(jsonObjectRequest);

    }

}