package matvienko_apps.ua.todolist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Utility {

    /**
     * Format date to string
     *
     * @param cl date in calendar obj
     * @return
     */
    public static String fromCalendarToString(Calendar cl) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(cl.getTime());
    }

    /**
     * Convert date string in long millis
     *
     * @param dateStr string in format "dd/MM/yy"
     */
    public static long fromStringToLong(String dateStr) {
        Calendar cl = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        try {
            cl.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cl.getTimeInMillis();
    }

    /**
     * Build view for alert dialogs
     * View with editText for text input
     * and editText for date input
     *
     * @param context Application context
     * @param task    Task obj, for update dialog. Set it to null if update not expected
     */
    public static View buildViewForAlertDialog(final Context context, Task task) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText taskEdit = new EditText(context);
        taskEdit.setId(R.id.task_edit_text);
        taskEdit.setHint("Description");
        taskEdit.setGravity(Gravity.CENTER);

        final Calendar currentDate = Calendar.getInstance();

        final EditText taskDate = new EditText(context);
        taskDate.setId(R.id.date_edit_text);
        // keyboard shouldn't open onClick
        taskDate.setFocusable(false);
        taskDate.setGravity(Gravity.CENTER);
        // set standard value - now date as hint
        taskDate.setHint(Utility.fromCalendarToString(currentDate));

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, _year);
                        calendar.set(Calendar.MONTH, _month);
                        calendar.set(Calendar.DAY_OF_MONTH, _dayOfMonth);

                        // onDateSet change value of taskDate to choosing
                        taskDate.setText(Utility.fromCalendarToString(calendar));
                    }
                }, year, month, day);

                datePicker.setTitle(context.getString(R.string.date_dialog_title));
                datePicker.show();
            }
        });

        // if task not null then dialog uses for update
        // fill all field's with task data
        if (task != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(task.getDate());

            taskEdit.setText(task.getText());
            taskDate.setText(Utility.fromCalendarToString(calendar));
        }


        // add view's to layout
        layout.addView(taskEdit);
        layout.addView(taskDate);

        return layout;
    }
}
