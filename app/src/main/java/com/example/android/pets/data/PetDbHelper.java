package com.example.android.pets.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.android.pets.PetRecord;
import com.example.android.pets.data.petContract.petEntry;
import java.util.ArrayList;
import java.util.List;

public class PetDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "shelter.db";
    private static final int DATABASE_VERSION = 3;

    public PetDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG","oncreate");
        String SQL_CREATE_PET_TABLE = "CREATE TABLE" + petEntry.TABLE_NAME + " ("
                + petEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + petEntry.COLUMN_PET_NAME + " TEXT NOT NULL,"
                + petEntry.COLUMN_PET_BREED + "  TEXT,"
                + petEntry.COLUMN_PET_GENDER + "  INTEGER NOT NULL,"
                + petEntry.COLUMN_PET_HEIGHT + " TEXT NOT NULL,"
                + petEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG","new version"+newVersion);
        db.execSQL("DROP TABLE "+petEntry.TABLE_NAME);
        onCreate(db);

    }

    public List<PetRecord> getAllRecords(){

        List<PetRecord> petRecords = new ArrayList<>();
        String SelectQuery = "SELECT * FROM "+ petEntry.TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(SelectQuery,null);

        if(cursor.moveToFirst()){
            do {

                PetRecord petRecord = new PetRecord();
                petRecord.setPet_Name_Database(cursor.getString(1));
                petRecord.setPet_Breed_Database(cursor.getString(2));
                petRecord.setPet_Gender_Database(cursor.getString(3));
                petRecord.setPet_Height_Database(cursor.getString(4));
                petRecord.setPet_Weight_Database(cursor.getString(5));
                petRecord.setID_Databse(cursor.getInt(0));

                petRecords.add(petRecord);
            }while (cursor.moveToNext());
        }cursor.close();
        return petRecords;
    }
}
