package com.kajol.yourdailytaskmaneger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// MainActivity.java
public class MainActivity extends AppCompatActivity {

    private EditText etTitle, etDesc;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private TaskDatabaseHelper dbHelper;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private Task taskToUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new TaskDatabaseHelper(this);
        taskList = dbHelper.getAllTasks();

        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onUpdate(Task task) {
                etTitle.setText(task.getTitle());
                etDesc.setText(task.getDescription());
                taskToUpdate = task;
                btnAdd.setText("Update Task");
            }

            @Override
            public void onDelete(Task task) {
                dbHelper.deleteTask(task.getId());
                refreshList();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();

            if (!title.isEmpty()) {
                if (taskToUpdate == null) {
                    dbHelper.addTask(new Task(0, title, desc));
                } else {
                    taskToUpdate = new Task(taskToUpdate.getId(), title, desc);
                    dbHelper.updateTask(taskToUpdate);
                    taskToUpdate = null;
                    btnAdd.setText("Add Task");
                }
                etTitle.setText("");
                etDesc.setText("");
                refreshList();
            }
        });
    }

    private void refreshList() {
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }
}
