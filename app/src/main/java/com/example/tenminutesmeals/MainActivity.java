package com.example.tenminutesmeals;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // استيراد الـ FAB
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private List<Meal> mealList;
    private MealDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ضبط المسافات للحواف (Edge-to-Edge)
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // إعداد الـ RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new MealDBHelper(this);
        mealList = new ArrayList<>();


        adapter = new MealAdapter(mealList);
        recyclerView.setAdapter(adapter);
        loadMealsFromDatabase();

        // --- إضافة كود الزر  (FAB) هنا ---
        FloatingActionButton fab = findViewById(R.id.fab_add_meal);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // الانتقال إلى صفحة إضافة الوجبة
                Intent intent = new Intent(MainActivity.this, AddMealActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadMealsFromDatabase();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void loadMealsFromDatabase() {
        mealList.clear();
        Cursor cursor = dbHelper.getAllMeals();
        if (cursor != null && cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int minutes = cursor.getInt(2);

                mealList.add(new Meal(id, name, minutes));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}