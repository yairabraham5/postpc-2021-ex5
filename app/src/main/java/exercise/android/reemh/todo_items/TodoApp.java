package exercise.android.reemh.todo_items;

import android.app.Application;

public class TodoApp extends Application {

    TodoItemsHolder itemsHolder;

    void setItemsHolder(TodoItemsHolder todoItemsHolder){
        itemsHolder = todoItemsHolder;
    }

    TodoItemsHolder getItemsHolder(){
        return this.itemsHolder;
    }


}
