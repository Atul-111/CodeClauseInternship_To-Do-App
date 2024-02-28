package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements DialogCloseListener /*implements DialogCloseListener*/
         {
             private RecyclerView mRecyclerview;
             private ToDoAdapter adapter;
             private FloatingActionButton fab;
             private List<ToDoModel> mList;
             private DatabaseHandler myDB;

             @Override
             protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_main);

                 fab = findViewById(R.id.fab);
                 myDB = new DatabaseHandler(MainActivity.this);
                 mRecyclerview = findViewById(R.id.recyclerview);
                 mList = new ArrayList<>();
                 adapter = new ToDoAdapter(myDB , MainActivity.this);

                 mRecyclerview.setHasFixedSize(true);
                 mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                 mRecyclerview.setAdapter(adapter);
                 ToDoModel task = new ToDoModel();
                 task.setTask("this is a new Task");
                 task.setStatus(0);
                 task.setId(1);

                 mList.add(task);
                 mList.add(task);
                 mList.add(task);
                 mList.add(task);
                 mList.add(task);

                 adapter.setTasks(mList);
            /*    try {
                    mList = myDB.getAllTasks();
                }catch(Exception e){
                    e.printStackTrace();
                }
                 Collections.reverse(mList);
*/

                 fab.setOnClickListener(new View.OnClickListener(){
                     @Override
                     public void onClick(View v) {
                         AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
                     }
                 });

                 ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
                 itemTouchHelper.attachToRecyclerView(mRecyclerview);

             }

             @Override
             public void DialogClose(DialogInterface dialogInterface) {
                 try {
                     mList = myDB.getAllTasks();
                 }catch(NullPointerException e){
                     e.printStackTrace();
                 }
                 Collections.reverse(mList);
                 adapter.setTasks(mList);
                 adapter.notifyDataSetChanged();
             }
}