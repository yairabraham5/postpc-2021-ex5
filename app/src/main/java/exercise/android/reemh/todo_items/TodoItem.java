package exercise.android.reemh.todo_items;

import java.io.Serializable;

public class TodoItem implements Serializable {
    String todo;
    boolean isDone;
    boolean inProgress;
  // TODO: edit this class as you want
    public TodoItem(){
        todo = "";
        isDone = false;
        inProgress = false;
    }
    public TodoItem(String string){
        todo = string;
        isDone = false;
        inProgress = false;
    }
    public void setIsDone(){
        isDone = true;
    }

    public String getTodo(){
        return todo;
    }

    public boolean isItemDone(){
        return isDone;
    }

    public void setInProgress(){
        inProgress = false;
    }


}
