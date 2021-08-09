package edu.wit.mobileapp.mealprepmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.VeiwHolder>{

    private Context context;
    private List<Meal> list;

    public MealAdapter(Context context, List<Meal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_meal, parent, false);
        return new VeiwHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiwHolder holder, int position) {
        Meal meal = list.get(position);
        holder.textName.setText(meal.getName());
        holder.textCalories.setText(String.valueOf(meal.getCalories()));
        holder.textProtein.setText(String.valueOf(meal.getProtein()));
        holder.textCarbs.setText(String.valueOf(meal.getCarbs()));
        holder.textTotalFat.setText(String.valueOf(meal.getTotalFat()));
        holder.textFiber.setText(String.valueOf(meal.getFiber()));
        holder.textSugar.setText(String.valueOf(meal.getSugar()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VeiwHolder extends RecyclerView.ViewHolder{

        public TextView textName, textCalories, textProtein, textCarbs, textTotalFat, textFiber, textSugar;
        public VeiwHolder(View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.meal_name);
            textCalories = itemView.findViewById(R.id.meal_calories);
            textProtein = itemView.findViewById(R.id.meal_protein);
            textCarbs = itemView.findViewById(R.id.meal_carbs);
            textTotalFat = itemView.findViewById(R.id.meal_totalfat);
            textFiber = itemView.findViewById(R.id.meal_fiber);
            textSugar = itemView.findViewById(R.id.meal_sugar);

        }
    }
}
