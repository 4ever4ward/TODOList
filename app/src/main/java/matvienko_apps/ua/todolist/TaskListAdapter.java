package matvienko_apps.ua.todolist;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import matvienko_apps.ua.todolist.data.DataProvider;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private ArrayList<Task> taskList;
    private DataProvider dataProvider;
    private Context context;

    public TaskListAdapter(Context context) {
        this.context = context;
        dataProvider = new DataProvider(context);
        taskList = dataProvider.getTasks();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, null);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String taskText = taskList.get(position).getText();
        long date = taskList.get(position).getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        holder.taskText.setText(taskText);
        holder.dateText.setText(Utility.fromCalendarToString(calendar));
        holder.checkBox.setChecked(taskList.get(position).getReady() == 1);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskByDialog(holder.getAdapterPosition());
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editBtn.setClickable(false);
                deleteItem(holder.getAdapterPosition());
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    deleteItem(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (taskList.size() == 0)
            return 0;

        return taskList.size();
    }


    /** Add item to list using animation
     * @param task object for insert in db
     **/
    public void addItem(Task task) {
        dataProvider.addTask(task);
        taskList.clear();
        taskList = dataProvider.getTasks();

        // standard value for insert item
        int index = -1;

        // find task index in new list
        for (int i = 0; i < taskList.size(); i++) {
            Task _task = taskList.get(i);
            if (task.getText().equals(_task.getText()))
                index = i;
        }

        notifyItemInserted(index);
    }

    /** Delete item from list using animation
     * @param position position of clicked item
     **/
    public void deleteItem(int position) {
        dataProvider.DeleteTask(taskList.get(position).getTaskID());
        notifyItemRemoved(position);
        taskList.clear();
        taskList = dataProvider.getTasks();
    }

    /** Update item in list using animation
     * @param position position of clicked item
     **/
    public void updateItem(int position) {
        Task task = taskList.get(position);
        taskList.clear();
        taskList = dataProvider.getTasks();

        // standard value for item index
        int newIndex = -1;

        // find task index in new list
        for (int i = 0; i < taskList.size(); i++) {
            Task _task = taskList.get(i);
            if (task.getTaskID() == _task.getTaskID())
                newIndex = i;
        }

        // moving animation item if position changed
        if (position != newIndex)
            notifyItemMoved(position, newIndex);
        notifyItemChanged(newIndex);
    }


    /** Create and show dialog for update item
     *
     * @param position position of clicked item
     **/
    private void updateTaskByDialog(final int position) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.update_title_txt)
                .setCancelable(false)
                .setView(Utility.buildViewForAlertDialog(context, taskList.get(position)))
                .setPositiveButton(R.string.positive_btn_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog alertDialog = (Dialog) dialog;

                        EditText taskEditText = (EditText) alertDialog.findViewById(R.id.task_edit_text);
                        EditText dateEditText = (EditText) alertDialog.findViewById(R.id.date_edit_text);

                        dataProvider.updateTask(
                                new Task(taskList.get(position).getTaskID(),
                                String.valueOf(taskEditText.getText()),
                                0,
                                Utility.fromStringToLong(String.valueOf(dateEditText.getText()))));

                        updateItem(position);
                    }
                })
                .setNegativeButton(R.string.negative_btn_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskText;
        TextView dateText;
        CheckBox checkBox;
        ImageView editBtn;
        ImageView deleteBtn;


        public ViewHolder(View itemView) {
            super(itemView);

            dateText = (TextView) itemView.findViewById(R.id.text_date);
            taskText = (TextView) itemView.findViewById(R.id.task_text);
            checkBox = (CheckBox) itemView.findViewById(R.id.ready_chbx);
            editBtn = (ImageView) itemView.findViewById(R.id.btn_edit);
            deleteBtn = (ImageView) itemView.findViewById(R.id.btn_delete);

        }
    }

}
