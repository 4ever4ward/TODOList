package matvienko_apps.ua.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;

import matvienko_apps.ua.todolist.data.AppDBContract;
import matvienko_apps.ua.todolist.data.AppDBHelper;
import matvienko_apps.ua.todolist.data.DataProvider;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexandr on 07/09/2017.
 */


@RunWith(RobolectricGradleTestRunner.class)

@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class TaskDatabaseTest {

    private Context context;
    private SQLiteDatabase db;
    private AppDBHelper appDBHelper;
    private DataProvider dataProvider;

    @Before
    public void init() {
        context = RuntimeEnvironment.application;
        dataProvider = new DataProvider(context);
        appDBHelper = new AppDBHelper(context);
        db = appDBHelper.getReadableDatabase();
    }

    @After
    public void cleanup() {
        db.close();
    }


    @Test
    public void testDBCols() {
        Cursor c = db.query(AppDBContract.TaskEntry.TABLE_NAME, null, null, null, null, null, null);
        assertNotNull( c );

        String[] cols = c.getColumnNames();
        assertThat("Column not implemented: " + AppDBContract.TaskEntry._ID,
                cols, hasItemInArray(AppDBContract.TaskEntry._ID));
        assertThat("Column not implemented: " + AppDBContract.TaskEntry.COL_TASK_TEXT,
                cols, hasItemInArray(AppDBContract.TaskEntry.COL_TASK_TEXT));
        assertThat("Column not implemented: " + AppDBContract.TaskEntry.COL_TASK_READY,
                cols, hasItemInArray(AppDBContract.TaskEntry.COL_TASK_READY));
        assertThat("Column not implemented: " + AppDBContract.TaskEntry.COL_TASK_DATE,
                cols, hasItemInArray(AppDBContract.TaskEntry.COL_TASK_DATE));

        c.close();
    }



    @Test
    public void testDBCreated(){
        AppDBHelper appDBHelper = new AppDBHelper(context);
        SQLiteDatabase db = appDBHelper.getWritableDatabase();
        // Verify is the DB is opening correctly
        assertTrue("DB didn't open", db.isOpen());
        db.close();
    }


    @Test
    public void shouldGetTask() {
        dataProvider = new DataProvider(context);
        dataProvider.addTask(new Task("Task #1", 0, 1L));
        dataProvider.addTask(new Task("Task #2", 0, 1L));

        ArrayList<Task> taskArrayList = dataProvider.getTasks();

        assertThat(taskArrayList.size(), is(2));
        assertThat(taskArrayList.get(1).getText(), is("Task #2"));
    }

    @Test
    public void shouldAddTask() {
        long timeNowInMillis = Calendar.getInstance().getTimeInMillis();
        dataProvider = new DataProvider(context);
        dataProvider.addTask(new Task("New text", 0, timeNowInMillis));

        ArrayList<Task> taskArrayList = dataProvider.getTasks();
        assertThat(taskArrayList.size(), is(1));
        assertThat(taskArrayList.get(0).getDate(), is(timeNowInMillis));
        assertThat(taskArrayList.get(0).getReady(), is(0));
        assertThat(taskArrayList.get(0).getText(), is("New text"));

    }

}
