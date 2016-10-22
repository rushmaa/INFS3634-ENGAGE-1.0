package infs3634.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import infs3634.model.Consultation;

/**
 * Created by Nick on 17/10/2016.
 */

public class ConsultationDBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lol";

    private SQLiteDatabase sqlDB;
    private ConsultationDBHelper consultationDBHelper;
    private Context context;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    public static final String TABLE_NAME = "consultation";
    public static final String COLUMN_ID = "consultation_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_NOTE = "note";

    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_DATE,
            COLUMN_TIME,
            COLUMN_STATUS,
            COLUMN_NOTE
    };

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_TIME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_STATUS + INT_TYPE + COMMA_SEP +
                    COLUMN_NOTE + TEXT_TYPE + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ConsultationDBAdapter(Context context) {
        this.context = context;
    }

    public long insert(Consultation consultation){
        open();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, consultation.getName());
        values.put(COLUMN_DATE, consultation.getDate());
        values.put(COLUMN_TIME, consultation.getTime());
        values.put(COLUMN_STATUS, consultation.getStatus());
        values.put(COLUMN_NOTE, consultation.getNote());

        long newRowId = sqlDB.insert(TABLE_NAME, null, values);
        close();
        return newRowId;
    }

    public long update(Consultation consultation) {
        open();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, consultation.getName());
        values.put(COLUMN_DATE, consultation.getDate());
        values.put(COLUMN_TIME, consultation.getTime());
        values.put(COLUMN_STATUS, consultation.getStatus());
        values.put(COLUMN_NOTE, consultation.getNote());

        long newRowId = sqlDB.update(TABLE_NAME, values, COLUMN_ID + " = " + consultation.getConsultationID(), null);
        close();
        return newRowId;
    }

    public List<Consultation> getAllConsultations(){
        open();
        List<Consultation> allConsultationList = new ArrayList<>();

        // Grab all information from the database
        Cursor cur = sqlDB.query(
                TABLE_NAME,                     // The table to query
                allColumns,                     // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                COLUMN_ID                       // The sort order
        );

        for(cur.moveToLast(); !cur.isBeforeFirst(); cur.moveToPrevious()) {
            Consultation consultation = cursorToConsultation(cur);
            allConsultationList.add(consultation);
        }

        cur.close();
        close();
        return allConsultationList;
    }

    public Consultation getConsultation(int id){
        open();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cur = sqlDB.query(
                TABLE_NAME,                                 // The table to query
                allColumns,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                selectionArgs,                              // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );

        Consultation consultation = null;
        for(cur.moveToLast(); !cur.isBeforeFirst(); cur.moveToPrevious()) {
            consultation = cursorToConsultation(cur);
        }

        cur.close();
        close();
        return consultation;
    }

    public Consultation cursorToConsultation(Cursor cur) {
        Consultation consultation = new Consultation();

        consultation.setConsultationID(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ID)));
        consultation.setName(cur.getString(cur.getColumnIndexOrThrow(COLUMN_NAME)));
        consultation.setDate(cur.getString(cur.getColumnIndexOrThrow(COLUMN_DATE)));
        consultation.setTime(cur.getString(cur.getColumnIndexOrThrow(COLUMN_TIME)));
        consultation.setStatus(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_STATUS)));
        consultation.setNote(cur.getString(cur.getColumnIndexOrThrow(COLUMN_NOTE)));

        return consultation;
    }

    public ConsultationDBAdapter open() throws android.database.SQLException {
        consultationDBHelper = new ConsultationDBHelper(context);
        sqlDB = consultationDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        consultationDBHelper.close();
    }

    private static class ConsultationDBHelper extends SQLiteOpenHelper {

        public ConsultationDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
            System.out.println("111");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(infs3634.storage.AppDBHelper.class.getName(), "Upgrading database from version " + oldVersion +
                    " to " + newVersion + ", which will destroy all old data");
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
