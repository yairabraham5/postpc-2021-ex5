package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

//  public TodoItemsHolder holder = null;
  private BroadcastReceiver broadcastReceiverForDescription = null;
  private BroadcastReceiver broadcastReceiverForChangeInProgress = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // we don't need holder anymore because we have a class for it
//    if (holder == null) {
//      holder = new TodoItemsHolderImpl();
//    }
    TodoAdapter todoAdapter = new TodoAdapter();

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    String getItemsHolder = sharedPreferences.getString("itemsHolder", null);
    if(getItemsHolder == null){
      if(((TodoApp)getApplicationContext()).getItemsHolder() == null){
        if(savedInstanceState == null) {
          ((TodoApp) getApplicationContext()).setItemsHolder(new TodoItemsHolderImpl());
        }
        else{
          ((TodoApp) getApplicationContext()).setItemsHolder((TodoItemsHolder)savedInstanceState.getSerializable("itemsHolder"));
        }
      }
    }
    else{
      Gson gson = new Gson();
      TodoItemsHolderImpl itemsHolder = gson.fromJson(getItemsHolder, TodoItemsHolderImpl.class);
      ((TodoApp) getApplicationContext()).setItemsHolder(itemsHolder);
    }

    EditText editText = findViewById(R.id.editTextInsertTask);
    FloatingActionButton button = findViewById(R.id.buttonCreateTodoItem);
    RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
    todoAdapter.setTodoItemsHolder(((TodoApp) getApplicationContext()).getItemsHolder());
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    recyclerView.setAdapter(todoAdapter);

    todoAdapter.clickTodoItem = (TodoItem item) ->{
      Intent editItem =  new Intent(MainActivity.this, EditActivity.class);
      int positionItem = ((TodoApp) getApplicationContext()).getItemsHolder().getCurrentItems().indexOf(item);
      editItem.putExtra("Position", positionItem);
      startActivity(editItem);
    };


    button.setOnClickListener(v->{
      String des = editText.getText().toString();
      if (!des.equals("")){
        ((TodoApp) getApplicationContext()).getItemsHolder().addNewInProgressItem(des);
        editText.setText("");
        todoAdapter.notifyDataSetChanged();
      }
    });

    broadcastReceiverForDescription = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if(intent == null || !intent.getAction().equals("changeDescription")){
          return;
        }
        String newDescription = intent.getStringExtra("newDescription");
        int position = intent.getIntExtra("Position", 0);
        ((TodoApp) getApplicationContext()).getItemsHolder().getCurrentItems().get(position).setTodo(newDescription);
        todoAdapter.notifyItemChanged(position);
      }
    };

    broadcastReceiverForChangeInProgress = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if( intent == null || !intent.getAction().equals("changeInProgress")){
          return;
        }
        int position = intent.getIntExtra("Position", 0);
        TodoItem item = ((TodoApp) getApplicationContext()).getItemsHolder().getCurrentItems().get(position);
        if(item.isItemDone()){
          ((TodoApp) getApplicationContext()).getItemsHolder().markItemInProgress(item);
        }
        else{
          ((TodoApp) getApplicationContext()).getItemsHolder().markItemDone(item);
        }
        todoAdapter.notifyDataSetChanged();
      }
    };


    registerReceiver(broadcastReceiverForChangeInProgress, new IntentFilter("changeInProgress"));
    registerReceiver(broadcastReceiverForDescription, new IntentFilter("changeDescription"));

    // TODO: implement the specs as defined below
    //    (find all UI components, hook them up, connect everything you need)


  }

  @Override
  protected void onPause() {
    super.onPause();

    TodoItemsHolderImpl holder = (TodoItemsHolderImpl)  ((TodoApp) getApplicationContext()).getItemsHolder();
    Gson gson = new Gson();
    String s = gson.toJson(holder, TodoItemsHolderImpl.class);
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putString("itemsHolder", s);
    edit.apply();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("itemsHolder", ((TodoApp)getApplicationContext()).getItemsHolder());
  }


  @Override
  protected void onDestroy(){
    super.onDestroy();
    unregisterReceiver(broadcastReceiverForChangeInProgress);
    unregisterReceiver(broadcastReceiverForDescription);
  }




}


/*

SPECS:

- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the descripti
  t
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

Remarks:
- you should use the `holder` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`



(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)

*/
