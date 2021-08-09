package edu.wit.mobileapp.mealprepmobile;

public class Meal {
    public String Name;
    public int Calories;
    public int Protein;
    public int Carbs;
    public int TotalFat;
    public int Fiber;
    public int Sugar;

    public Meal() {
        Name = "";
        Calories = 0;
        Protein = 0;
        Carbs = 0;
        TotalFat = 0;
        Fiber = 0;
        Sugar = 0;
    }

    public Meal(String name, int calories, int protein, int carbs, int totalFat, int fiber, int sugar) {
        Name = name;
        Calories = calories;
        Protein = protein;
        Carbs = carbs;
        TotalFat = totalFat;
        Fiber = fiber;
        Sugar = sugar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getProtein() {
        return Protein;
    }

    public void setProtein(int protein) {
        Protein = protein;
    }

    public int getCarbs() {
        return Carbs;
    }

    public void setCarbs(int carbs) {
        Carbs = carbs;
    }

    public int getTotalFat() {
        return TotalFat;
    }

    public void setTotalFat(int totalFat) {
        TotalFat = totalFat;
    }

    public int getFiber() {
        return Fiber;
    }

    public void setFiber(int fiber) {
        Fiber = fiber;
    }

    public int getSugar() {
        return Sugar;
    }

    public void setSugar(int sugar) {
        Sugar = sugar;
    }
}
