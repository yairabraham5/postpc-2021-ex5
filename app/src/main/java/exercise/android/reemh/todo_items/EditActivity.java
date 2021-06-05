package exercise.android.reemh.todo_items;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        EditText description = findViewById(R.id.description_text);
        TextView lastModified = findViewById(R.id.lastModified_text);
        TextView created = findViewById(R.id.created_time);
        CheckBox doneBox = findViewById(R.id.doneBox);


        // get the item we're working on
        Intent createdActivity = getIntent();
        int position = createdActivity.getIntExtra("Position", -1);
        TodoItem item = ((TodoApp)getApplicationContext()).getItemsHolder().getCurrentItems().get(position);

        // init all values
        description.setText(item.getTodo());
        String createdTime = "Time Created: " + item.getTimeCreated();
        created.setText(createdTime);
        String modified = "Last Modified: " + item.getLastModified();
        lastModified.setText(modified);
        doneBox.setChecked(item.isItemDone());


        doneBox.setOnClickListener(v->{
            // start intent and broadcast to main activity
            Intent intent = new Intent("changeInProgress");
            int newPos = ((TodoApp)getApplicationContext()).getItemsHolder().getCurrentItems().indexOf(item);
            intent.putExtra("Position", newPos);
            sendBroadcast(intent);
        });


        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Intent intentDescription = new Intent("changeDescription");
                intentDescription.putExtra("newDescription", description.getText().toString());
                intentDescription.putExtra("Position",position);
                sendBroadcast(intentDescription);
            }
        });

    }
}