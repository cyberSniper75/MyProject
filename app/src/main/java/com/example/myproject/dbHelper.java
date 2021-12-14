package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    public static final String TBL_CLASS = "tblClass";
    public static final String COLUMN_CLASS_NAME = "CLASS_NAME";
    public static final String COLUMN_GRADE = "GRADE";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_ID = "ID";

    public dbHelper(@Nullable Context context) {
        super(context, "class.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TBL_CLASS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CLASS_NAME + " TEXT, " + COLUMN_GRADE + " REAL, " + COLUMN_STATUS + " BOOL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addValue(ClassModel classModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLASS_NAME, classModel.getClassName());
        cv.put(COLUMN_GRADE, classModel.getGrade());
        cv.put(COLUMN_STATUS, classModel.isStatus());

        long insert = db.insert(TBL_CLASS, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public List<ClassModel> getAllValues(){
        List<ClassModel> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + TBL_CLASS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int classID = cursor.getInt(0);
                String className = cursor.getString(1);
                float grade = cursor.getFloat(2);
                boolean status = cursor.getInt(3) == 1 ? true : false;

                ClassModel newClass = new ClassModel(classID, className, grade, status);
                returnList.add(newClass);
            }while (cursor.moveToNext());
        }else{
            //Empty Statement
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteValue(ClassModel classModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TBL_CLASS + " WHERE " + COLUMN_ID + " = " + classModel.getId();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else
            return false;
    }

    public double gradeAvg(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*), SUM(" + COLUMN_GRADE + ") FROM " + TBL_CLASS;

        Cursor cursor = db.rawQuery(countQuery, null);
        int classesCount = 0;
        Float sumGrades = null;

        if(cursor.moveToFirst()){
            classesCount = cursor.getInt(0);
            sumGrades = cursor.getFloat(1);
        }

        if(classesCount > 0){
            return ((double) sumGrades / classesCount);
        }
        else
            return 0.0;
    }
}
