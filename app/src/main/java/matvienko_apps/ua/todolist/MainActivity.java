package matvienko_apps.ua.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TaskListAdapter mTaskListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        mTaskListAdapter = new TaskListAdapter(this);

        mRecyclerView.setAdapter(mTaskListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddAlertDialog();

            }
        });
    }

    /**
     * Create and show alert dialog for adding tasks
     */
    private void showAddAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.add_dialog_title)
                .setCancelable(false)
                .setView(Utility.buildViewForAlertDialog(MainActivity.this, null))
                .setPositiveButton(R.string.positive_btn_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog alertDialog = (Dialog) dialog;

                        EditText taskEditText = (EditText) alertDialog.findViewById(R.id.task_edit_text);
                        EditText dateEditText = (EditText) alertDialog.findViewById(R.id.date_edit_text);

                        long millis = Utility.fromStringToLong(String.valueOf(dateEditText.getText()));

                        if (String.valueOf(taskEditText.getText()).equals("") || String.valueOf(dateEditText).equals("")) {
                            Toast.makeText(MainActivity.this, R.string.add_err_toast, Toast.LENGTH_SHORT).show();

                        } else {
                            mTaskListAdapter.addItem(new Task(
                                    String.valueOf(taskEditText.getText()),
                                    0,
                                    millis));

                            // scroll to list start
                            mRecyclerView.getLayoutManager().scrollToPosition(0);
                        }

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

}
