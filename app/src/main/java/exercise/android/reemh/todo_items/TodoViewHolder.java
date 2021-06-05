package exercise.android.reemh.todo_items;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {


    ImageView deleteView;
    TextView description;
    CheckBox box;

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        deleteView = itemView.findViewById(R.id.imageView);
        description = itemView.findViewById(R.id.description);
        box = itemView.findViewById(R.id.checkBox);
    }
}
