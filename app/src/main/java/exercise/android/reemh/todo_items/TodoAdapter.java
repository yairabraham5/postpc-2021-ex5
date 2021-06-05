package exercise.android.reemh.todo_items;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


interface  onClickTodoItem{
    void onClickItem(TodoItem item);
}

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {

    TodoItemsHolder allItems = null;
    onClickTodoItem clickTodoItem = null;
    public void setTodoItemsHolder(TodoItemsHolder items){
        allItems = items;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_item, parent, false);
        return new TodoViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem currentItem = allItems.getCurrentItems().get(position);
        holder.description.setText(currentItem.getTodo());
        holder.box.setChecked(currentItem.isItemDone());
        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyDataSetChanged();
                int index = allItems.itemIndex(currentItem);
                allItems.deleteItem(currentItem);
                notifyItemRemoved(index);
            }
        });

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int beforeChangesInd = allItems.itemIndex(currentItem);
                if(holder.box.isChecked()){
                    holder.description.setPaintFlags(holder.description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    allItems.markItemDone(currentItem);
//                    notifyDataSetChanged();
                    notifyItemMoved(beforeChangesInd, allItems.getCurrentItems().size()-1);
                }
                else{
                    holder.description.setPaintFlags(0);
                    allItems.markItemInProgress(currentItem);
//                    notifyDataSetChanged();
                    notifyItemMoved(beforeChangesInd, 0);
                }
            }
        });

        // clicked on current item
        holder.description.setOnClickListener(v->{
             if(clickTodoItem != null){
                 clickTodoItem.onClickItem(currentItem);
             }
        });
    }

    @Override
    public int getItemCount() {
        return allItems.getCurrentItems().size();
    }
}
