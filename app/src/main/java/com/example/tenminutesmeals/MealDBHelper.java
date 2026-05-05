package com.example.tenminutesmeals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MealDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TenMinMeals.db";
    private static final int DATABASE_VERSION = 1;

    // جداول قاعدة البيانات
    private static final String TABLE_MEALS = "meals";
    private static final String TABLE_USERS = "users";

    public MealDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // إنشاء جدول الوجبات: المعرف، الاسم، وقت التحضير
        db.execSQL("CREATE TABLE " + TABLE_MEALS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MINUTES INTEGER)");
        // إنشاء جدول المستخدمين للتسجيل والدخول[cite: 1]
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // وظيفة الإضافة (Insert)[cite: 1]
    public boolean addMeal(String name, int minutes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("MINUTES", minutes);
        return db.insert(TABLE_MEALS, null, values) != -1;
    }

    // وظيفة عرض الكل (Select All)[cite: 1]
    public Cursor getAllMeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEALS, null);
    }

    // وظيفة البحث (Search)[cite: 1]
    public Cursor searchMeal(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEALS + " WHERE NAME LIKE ?", new String[]{"%" + name + "%"});
    }

    // وظيفة الحذف (Delete)[cite: 1]
    public void deleteMeal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEALS, "ID=?", new String[]{String.valueOf(id)});
    }
}