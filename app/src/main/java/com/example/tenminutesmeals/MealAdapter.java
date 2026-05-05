package com.example.tenminutesmeals;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> mealList;

    // constructor يأخذ قائمة الوجبات
    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {

        Meal currentMeal = mealList.get(position);


        holder.txtName.setText(currentMeal.getName());
        holder.txtTime.setText(currentMeal.getMinutes() + " mins");
        // إضافة مستمع عند الضغط المطول للحذف (لتحقيق شرط DialogFragment)
        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("حذف الوجبة");
            builder.setMessage("هل أنتِ متأكدة من حذف هذه الوجبة؟");

            builder.setPositiveButton("نعم", (dialog, which) -> {
                // استدعاء دالة الحذف من قاعدة البيانات باستخدام معرف الوجبة[cite: 1]
                MealDBHelper dbHelper = new MealDBHelper(v.getContext());
                dbHelper.deleteMeal(currentMeal.getId());

                // تحديث القائمة في الواجهة
                mealList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mealList.size());

                Toast.makeText(v.getContext(), "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("إلغاء", null);
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }


    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTime;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_meal_name);
            txtTime = itemView.findViewById(R.id.txt_meal_time);
        }
    }
}