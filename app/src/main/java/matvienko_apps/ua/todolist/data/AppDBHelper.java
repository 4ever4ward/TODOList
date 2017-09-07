package matvienko_apps.ua.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class AppDBHelper extends SQLiteOpenHelper {


    public AppDBHelper(Context context) {
        super(context, AppDBContract.DB_NAME, null, AppDBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTable = "CREATE TABLE " + AppDBContract.TaskEntry.TABLE_NAME +
                " ( "
                + AppDBContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AppDBContract.TaskEntry.COL_TASK_TEXT + " TEXT NOT NULL, "
                + AppDBContract.TaskEntry.COL_TASK_READY + " INTEGER NOT NULL, "
                + AppDBContract.TaskEntry.COL_TASK_DATE + " INTEGER NOT NULL"
                + ");";

        db.execSQL(createTaskTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppDBContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
