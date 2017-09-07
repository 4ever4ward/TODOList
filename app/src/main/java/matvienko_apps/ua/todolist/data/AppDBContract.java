package matvienko_apps.ua.todolist.data;

import android.provider.BaseColumns;



public  class AppDBContract {

    public static final String DB_NAME = "TODOList.db";
    public static int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "tasks";

        public static final String COL_TASK_TEXT = "task_text";
        public static final String COL_TASK_READY = "task_ready";
        public static final String COL_TASK_DATE = "task_date";

    }

}
