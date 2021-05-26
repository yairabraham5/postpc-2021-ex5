package exercise.android.reemh.todo_items;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {


    ImageView deleteView;
    TextView text;
    CheckBox box;

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        deleteView = itemView.findViewById(R.id.imageView);
        text = itemView.findViewById(R.id.textView);
        box = itemView.findViewById(R.id.checkBox);
    }
}
