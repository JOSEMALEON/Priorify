package com.example.priorify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Task> taskList = new ArrayList<>();
    public static TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button addTaskButton = findViewById(R.id.addTaskButton);

        // Configurar el RecyclerView
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Acción para añadir una nueva tarea
        addTaskButton.setOnClickListener(v -> showAddTaskDialog());
    }

    /**
     * Mostrar el diálogo para añadir una nueva tarea
     */
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir nueva tarea");

        // Usar un diseño personalizado para el diálogo
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(customLayout);

        // Configurar botón de "Agregar"
        builder.setPositiveButton("Agregar", (dialog, which) -> {
            EditText taskTitleInput = customLayout.findViewById(R.id.taskTitleInput);
            EditText taskDescriptionInput = customLayout.findViewById(R.id.taskDescriptionInput);
            TimePicker timePicker = customLayout.findViewById(R.id.taskTimePicker);

            // Usar un método que sea compatible con el TimePicker en SDK > 23
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String taskTime = String.format("%02d:%02d", hour, minute);

            String taskTitle = taskTitleInput.getText().toString();
            String taskDescription = taskDescriptionInput.getText().toString();

            if (!taskTitle.isEmpty()) {
                taskList.add(new Task(taskTitle, taskDescription, taskTime));
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Tarea añadida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar botón de "Cancelar"
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el diálogo
        builder.create().show();
    }

    /**
     * Método estático para actualizar una tarea en la lista
     * @param position Posición de la tarea a actualizar
     * @param updatedTask La tarea actualizada
     */
    public static void updateTask(int position, Task updatedTask) {
        if (position >= 0 && position < taskList.size()) {
            taskList.set(position, updatedTask);
            if (taskAdapter != null) {
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
