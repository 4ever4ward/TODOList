package matvienko_apps.ua.todolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import matvienko_apps.ua.todolist.Task;



public class DataProvider {

    private AppDBHelper dbHelper;

    public DataProvider(Context context) {
        dbHelper = new AppDBHelper(context);
    }

    /** Add task to database
     *
     * @param task obj for adding
     */
    public void addTask(Task task) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppDBContract.TaskEntry.COL_TASK_TEXT, task.getText());
        contentValues.put(AppDBContract.TaskEntry.COL_TASK_READY, task.getReady());
        contentValues.put(AppDBContract.TaskEntry.COL_TASK_DATE, task.getDate());

        db.insertWithOnConflict(AppDBContract.TaskEntry.TABLE_NAME,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

    }

    /** Get task from database
     *
     * @return list of tasks
     */
    public ArrayList<Task> getTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Task> taskList = new ArrayList<>();

        String query = "SELECT * FROM " + AppDBContract.TaskEntry.TABLE_NAME
                + " ORDER BY " + AppDBContract.TaskEntry.COL_TASK_DATE + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getLong(3));

                taskList.add(task);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return taskList;
    }

    /** Update task in database
     *
     * @param task updated obj
     */
    public void updateTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AppDBContract.TaskEntry.COL_TASK_TEXT, task.getText());
        contentValues.put(AppDBContract.TaskEntry.COL_TASK_READY, task.getReady());
        contentValues.put(AppDBContract.TaskEntry.COL_TASK_DATE, task.getDate());

        db.update(AppDBContract.TaskEntry.TABLE_NAME,
                contentValues,
                AppDBContract.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getTaskID())});

    }

    /** Delete task from database
     *
     * @param task_id for find and delete task
     */
    public void DeleteTask(int task_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(AppDBContract.TaskEntry.TABLE_NAME,
                AppDBContract.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task_id)});

    }

}
