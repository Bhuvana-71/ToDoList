package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listView;
    ArrayList<String> itemList=new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    // here we can directly link the item list to Listview using arrayadapter but we donot use this because when ever we close the application everthing will reset
    // so for this we save it to the file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        listView=findViewById(R.id.list);



        itemList=FileHelper.readData(this);


        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, android.R.id.text1,itemList);
        listView.setAdapter(arrayAdapter);

       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String itemName=item.getText().toString();
               itemList.add(itemName);
               item.setText("");
               FileHelper.writeData(itemList,getApplicationContext());
               arrayAdapter.notifyDataSetChanged();
           }
       });

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                 alert.setTitle("Delete");
                 alert.setMessage("Do you want to delete item from the list?");
                 alert.setCancelable(false);//if the user click somewhere in the screen then this alert box should not be closed
                 alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.cancel();
                     }
                 });
                 alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                           itemList.remove(position);
                           arrayAdapter.notifyDataSetChanged();
                           FileHelper.writeData(itemList,getApplicationContext());
                     }
                 });
                 AlertDialog alertDialog= alert.create();
                 alertDialog.show();
             }
         });


    }
}