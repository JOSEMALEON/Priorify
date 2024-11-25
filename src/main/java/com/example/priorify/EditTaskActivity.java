package com.example.priorify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText taskTitleInput, taskDescriptionInput;
    private TimePicker timePicker;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Obtener referencias a los elementos de la interfaz
        taskTitleInput = findViewById(R.id.editTaskTitleInput);
        taskDescriptionInput = findViewById(R.id.editTaskDescriptionInput);
        timePicker = findViewById(R.id.editTaskTimePicker);
        Button saveButton = findViewById(R.id.saveTaskButton);

        // Obtener datos de la tarea seleccionada
        Intent intent = getIntent();
        taskPosition = intent.getIntExtra("task_position", -1);
        String taskTitle = intent.getStringExtra("task_title");
        String taskDescription = intent.getStringExtra("task_description");
        String taskTime = intent.getStringExtra("task_time");

        // Configurar los valores actuales
        taskTitleInput.setText(taskTitle);
        taskDescriptionInput.setText(taskDescription);
        if (taskTime != null) {
            String[] timeParts = taskTime.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }

        // Guardar cambios en la tarea
        saveButton.setOnClickListener(v -> {
            String updatedTitle = taskTitleInput.getText().toString();
            String updatedDescription = taskDescriptionInput.getText().toString();
            int updatedHour = timePicker.getHour();
            int updatedMinute = timePicker.getMinute();
            String updatedTime = String.format("%02d:%02d", updatedHour, updatedMinute);

            if (!updatedTitle.isEmpty()) {
                // Actualizar la tarea en la lista a través del método estático
                Task updatedTask = new Task(updatedTitle, updatedDescription, updatedTime);
                MainActivity.updateTask(taskPosition, updatedTask);

                Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
                finish(); // Volver a la pantalla principal
            } else {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
