package edu.wit.mobileapp.mealprepmobile;

public class Configuration {
    public int NumMeals;
    public int Calories;

    public Configuration() {
        NumMeals = 3;
        Calories = 0;
    }

    public void Meal(int numMeals, int calories) {
        NumMeals = numMeals;
        Calories = calories;
    }

    public int getNumMeals() {
        return NumMeals;
    }

    public void setNumMeals(int numMeals) {
        NumMeals = numMeals;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }
}
