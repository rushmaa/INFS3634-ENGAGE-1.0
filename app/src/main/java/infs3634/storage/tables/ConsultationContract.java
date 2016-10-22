package infs3634.storage.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import infs3634.model.Consultation;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_DATE;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_ID;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_NOTE;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_STATUS;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_TIME;
import static infs3634.storage.tables.ConsultationContract.ConsultationEntry.COLUMN_NAME;


/**
 * Created by SONY on 10/15/2016.
 */

public class ConsultationContract {
    public static final String TABLE_NAME = "consultation";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    public abstract class ConsultationEntry implements BaseColumns {
        public static final String COLUMN_ID = "consultation_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_NOTE = "note";
    }

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

    public ConsultationContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Consultation consultation){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, consultation.getName());
        values.put(COLUMN_DATE, consultation.getDate());
        values.put(COLUMN_TIME, consultation.getTime());
        values.put(COLUMN_STATUS, consultation.getStatus());
        values.put(COLUMN_NOTE, consultation.getNote());

        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public long update(Consultation consultation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, consultation.getName());
        values.put(COLUMN_DATE, consultation.getDate());
        values.put(COLUMN_TIME, consultation.getTime());
        values.put(COLUMN_STATUS, consultation.getStatus());
        values.put(COLUMN_NOTE, consultation.getNote());

        long newRowId;
        newRowId = db.update(TABLE_NAME, values, COLUMN_ID + " = " + consultation.getConsultationID(), null);
        db.close();

        return newRowId;

    }

    public List<Consultation> getAllConsultations(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Consultation> allConsultationList = new ArrayList<>();

        // Grab all information from the database
        Cursor cur = db.query(
                TABLE_NAME,                     // The table to query
                allColumns,                     // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                COLUMN_ID     // The sort order
        );

        while (cur.moveToNext()) {
            Consultation consultation = null;
            consultation = setConsultation(cur, consultation);
            allConsultationList.add(consultation);
        }

        cur.close();
        db.close();
        return allConsultationList;
    }

    public Consultation getConsultation(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cur = db.query(
                TABLE_NAME,                                 // The table to query
                allColumns,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                selectionArgs,                              // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );

        Consultation consultation = null;
        if(cur.moveToNext()){
            consultation = setConsultation(cur, consultation);
        }

        cur.close();
        db.close();
        return consultation;
    }

    public Consultation setConsultation(Cursor cur, Consultation consultation) {
        consultation = new Consultation();

        consultation.setConsultationID(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ID)));
        consultation.setName(cur.getString(cur.getColumnIndexOrThrow(COLUMN_NAME)));
        consultation.setDate(cur.getString(cur.getColumnIndexOrThrow(COLUMN_DATE)));
        consultation.setTime(cur.getString(cur.getColumnIndexOrThrow(COLUMN_TIME)));
        consultation.setStatus(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_STATUS)));
        consultation.setNote(cur.getString(cur.getColumnIndexOrThrow(COLUMN_NOTE)));

        return consultation;
    }
}

