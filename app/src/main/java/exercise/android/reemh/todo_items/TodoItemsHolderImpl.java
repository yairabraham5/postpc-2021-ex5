package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.List;

// TODO: implement!
public class TodoItemsHolderImpl implements TodoItemsHolder {
  ArrayList<TodoItem> allItems;

  public TodoItemsHolderImpl(){
    allItems = new ArrayList<>();
  }
  @Override
  public List<TodoItem> getCurrentItems() { return allItems; }

  @Override
  public void addNewInProgressItem(String description) {
    TodoItem item = new TodoItem(description);
    allItems.add(0, item);
  }

  @Override
  public void markItemDone(TodoItem item) {
    item.setIsDone();
    allItems.remove(item);
    allItems.add(item);
  }

  @Override
  public void markItemInProgress(TodoItem item) {
    if(item.isItemDone()){
      item.setInProgress();
      item.isDone = false;
      allItems.remove(item);
      allItems.add(0, item);
    }
  }

  @Override
  public void deleteItem(TodoItem item) {

    allItems.remove(item);
  }

  @Override
  public int itemIndex(TodoItem item){
    return allItems.indexOf(item);
  }
}
