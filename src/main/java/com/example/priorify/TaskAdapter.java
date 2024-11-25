package com.example.priorify;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;

    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskDescription.setText(task.getDescription());
        holder.taskTime.setText(task.getTime());

        // Configurar clic corto para editar tarea
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
            intent.putExtra("task_position", position); // Pasar la posición de la tarea
            intent.putExtra("task_title", task.getTitle());
            intent.putExtra("task_description", task.getDescription());
            intent.putExtra("task_time", task.getTime());
            v.getContext().startActivity(intent);
        });

        // Configurar clic largo para eliminar tarea
        holder.itemView.setOnLongClickListener(v -> {
            taskList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(v.getContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDescription, taskTime;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            taskTime = itemView.findViewById(R.id.taskTime);
        }
    }
}
