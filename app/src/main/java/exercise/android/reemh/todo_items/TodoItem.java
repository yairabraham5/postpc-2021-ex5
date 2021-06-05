package exercise.android.reemh.todo_items;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem implements Serializable {
    String todo;
    boolean isDone;
    boolean inProgress;
    Date dateItemCreated;
    Date dateItemLastModified;

  // TODO: edit this class as you want
    public TodoItem(){
        todo = "";
        isDone = false;
        inProgress = false;
        dateItemCreated = new Date();
        dateItemLastModified = new Date();
    }
    public TodoItem(String string){
        todo = string;
        isDone = false;
        inProgress = false;
        dateItemCreated = new Date();
        dateItemLastModified = new Date();
    }
    public void setIsDone(){
        isDone = true;
        dateItemLastModified = new Date();
    }

    public String getTodo(){
        return todo;
    }

    public boolean isItemDone(){
        return isDone;
    }

    public void setInProgress(){
        inProgress = false;
        dateItemLastModified = new Date();
    }

    public String getTimeCreated(){
        return dateItemCreated.toString().substring(0, 20);
    }

    public String getLastModified(){
        Date currentDate = new Date();
        long currentTime = currentDate.getTime();
        long timePassedSeconds = (currentTime - dateItemLastModified.getTime()) / 1000L;
        if(timePassedSeconds <= (60*60)){
            return timePassedSeconds/60 + " minutes ago";
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(simpleDateFormat.format(dateItemLastModified).equals(simpleDateFormat.format(currentDate))){
            return "Today at " + dateItemLastModified.toString().substring(11, 18);
        }
        return dateItemLastModified.toString().substring(0, 10)  + " at "  +  dateItemLastModified.toString().substring(11, 18);
    }

    public void setTodo(String todo){
        this.todo = todo;
        dateItemLastModified = new Date();
    }



}
