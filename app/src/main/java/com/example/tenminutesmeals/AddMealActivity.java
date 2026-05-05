package com.example.tenminutesmeals;

import android.content.Intent; // استيراد كلاس الانتقال
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMealActivity extends AppCompatActivity {


    MealDBHelper myDb;
    EditText editName, editTime;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meal);


        myDb = new MealDBHelper(this);

        editName = findViewById(R.id.etMealName);
        editTime = findViewById(R.id.etMinutes);
        btnAddData = findViewById(R.id.btnSaveMeal);


        AddData();
    }


    public void AddData() {
        btnAddData.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String timeStr = editTime.getText().toString().trim();


            if (!name.isEmpty() && !timeStr.isEmpty()) {
                try {

                    int minutes = Integer.parseInt(timeStr);


                    boolean isInserted = myDb.addMeal(name, minutes);

                    if (isInserted) {
                        Toast.makeText(AddMealActivity.this, "Meal Saved Successfully!", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(AddMealActivity.this, MainActivity.class);
                        startActivity(intent);


                        finish();
                    } else {
                        Toast.makeText(AddMealActivity.this, "Error: Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Please enter a valid number for minutes", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}