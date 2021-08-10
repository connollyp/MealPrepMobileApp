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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class meal_plan extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Meal> mealList;
    private int[] userConfiguration;
    private Meal[][] combinations;
    private int maxCombos;
    private int mealComboIndex = 0;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        Button configure = (Button) findViewById(R.id.configure_button);
        int userId = getIntent().getIntExtra("userId", 0);

        mList = findViewById(R.id.meal_list);

        mealList=new ArrayList<>();
        userConfiguration = new int[2];
        maxCombos = 0;

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

        configure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meal_plan.this, configure_page.class);
                intent.putExtra("userId", userId);
                startActivity(intent);


            }
        });
    }

    public int factorial(int num){
        if(num == 0){
            return 1;
        }

        return num * factorial(num - 1);
    }

    public int getMaxCombos(int numMeals, int options){
        return ( (factorial(options)) / ( (factorial(numMeals)) * (factorial((options-numMeals)))));
    }

    private void getData(int userId){

        String url ="http://3.233.98.252:8080/getMeals?userId='" + Integer.toString(userId) + "'";
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading meals");
        progressDialog.show();

        ProgressDialog finalProgressDialog = progressDialog;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONArray jsonArray = response.getJSONArray("success");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Meal meal = new Meal();
                                meal.setMealId(jsonObject.getInt("Meal_Id"));
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
                            finalProgressDialog.dismiss();

                        }
                        adapter.notifyDataSetChanged();
                        finalProgressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(meal_plan.this);
        queue.add(jsonObjectRequest);

        url ="http://3.233.98.252:8080/getConfig?userId='" + Integer.toString(userId) + "'";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading configuration");
        progressDialog.show();
        ProgressDialog finalProgressDialog1 = progressDialog;
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONArray jsonArray = response.getJSONArray("configData");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Configuration config = new Configuration();
                                config.setNumMeals(jsonObject.getInt("Num_Meals"));
                                config.setCalories(jsonObject.getInt("Calories"));

                                userConfiguration[0] = config.getNumMeals();
                                userConfiguration[1] = config.getCalories();

                                System.out.println("numMeals " + config.getNumMeals());
                                System.out.println("numCal " + config.getCalories());

                                maxCombos = getMaxCombos(userConfiguration[0], mealList.size());

                                combinations = new Meal[maxCombos][userConfiguration[0]];

                                Meal[] data = new Meal[maxCombos];

                                generateMealPlan(data, 0, mealList.size(), 0);
                                System.out.println("Loop");
                                for(int j=0; j<data.length; j++) {
                                    System.out.println(data[j]);
                                }
                                int numValidPlans = mealList.size();

                                mealList.clear();

                                for(int k = 0; k < maxCombos; k++){
                                    int calories = 0;
                                    for(int l = 0; l < userConfiguration[0]; l++){
                                        calories += combinations[k][l].Calories;
                                    }
                                    if(userConfiguration[1] < calories){
                                        combinations[k][0] = null;
                                        numValidPlans--;
                                    }
                                }

                                System.out.println("Num Valid Meal Plans " + numValidPlans);

                                int randomNum = ThreadLocalRandom.current().nextInt(0, numValidPlans + 1);

                                int validIndex = 0;

                                for(int m = 0; m < maxCombos; m++){
                                    if(combinations[m][0] != null){
                                        if(validIndex == randomNum){
                                            for(int n = 0; n < userConfiguration[0]; n++){
                                                mealList.add(combinations[m][n]);
                                            }
                                            break;
                                        }

                                        validIndex++;
                                    }
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finalProgressDialog1.dismiss();

                        }
                        adapter.notifyDataSetChanged();
                        finalProgressDialog1.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", error.toString());
            }
        });
        queue.add(jsonObjectRequest2);
    }

    //Based on code from https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
    private void generateMealPlan(Meal[] data, int start, int end, int index){
        if(index == userConfiguration[0]){
            System.out.print("Meal Combo ");
            for(int j =0; j < userConfiguration[0]; j++){
                combinations[mealComboIndex][j] = data[j];
                System.out.print(data[j].Calories+" ");
            }
            System.out.println("");
            mealComboIndex++;
            return;
        }

        for(int i=start; i<end && end-i+1 >= userConfiguration[0]-index; i++){
            data[index] = mealList.get(i);
            generateMealPlan(data, i+1, end, index+1);
        }

    }


}