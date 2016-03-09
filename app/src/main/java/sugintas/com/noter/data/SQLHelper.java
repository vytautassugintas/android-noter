package sugintas.com.noter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NOTES_DB";
    private static final String TABLE_NOTES = "NOTES";
    private static final String NOTE_ID = "ID";
    private static final String NOTE_TITLE = "TITLE";
    private static final String NOTE_BODY = "BODY";
    private static final String NOTE_DATE = "DATE";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE "+TABLE_NOTES+" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "body TEXT, " +
                "date TEXT)";

        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            this.onCreate(db);
        }
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_BODY, note.getBody());
        values.put(NOTE_DATE, note.getDate());
        db.insert(TABLE_NOTES, null, values);
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, NOTE_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    public List<Note> getAllNotes() {
        ArrayList<Note> tempList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Note note;
        if (cursor.moveToFirst()) {
            do {
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setBody(cursor.getString(2));
                note.setDate(cursor.getString(3));
                tempList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tempList;
    }

    public Note getNoteById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + NOTE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        Note note = null;
        if (cursor.moveToFirst()) {
            note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setBody(cursor.getString(2));
            note.setDate(cursor.getString(3));
        }
        cursor.close();
        return note;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_BODY, note.getBody());
        values.put(NOTE_DATE, note.getDate());
        db.update(TABLE_NOTES, values, NOTE_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

}

