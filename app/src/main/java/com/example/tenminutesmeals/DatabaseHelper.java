package com.example.tenminutesmeals;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class manages the SQLite database (create, update, insert, delete, search)
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "MealsDB";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_NAME = "meals";

    // Column names
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_TIME = "time";
    private static final String COL_IMAGE = "image";
    private static final String COL_FAV = "isFavorite";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table when database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_TIME + " TEXT,"
                + COL_IMAGE + " TEXT,"
                + COL_FAV + " INTEGER DEFAULT 0"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrade database if version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new meal into database
    public boolean insertMeal(String name, String time, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);      // meal name
        values.put(COL_TIME, time);      // preparation time
        values.put(COL_IMAGE, image);    // image link or name
        values.put(COL_FAV, 0);          // default not favorite

        long result = db.insert(TABLE_NAME, null, values);

        // return true if insert successful
        return result != -1;
    }

    // Delete a meal by ID
    public void deleteMeal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Search meals by name keyword
    public Cursor searchMeal(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NAME + " LIKE ?",
                new String[]{"%" + keyword + "%"}
        );
    }

    // Update favorite status (0 = not favorite, 1 = favorite)
    public void updateFavorite(int id, int isFav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_FAV, isFav);

        db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}