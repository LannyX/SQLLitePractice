package com.apolis.lanny.sqllitepractice;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nameET, phoneET, emailET;
    Button saveButton, showButton;
    MyDBHelper myDBHelper;
    SQLiteDatabase myDataBase;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> dbList = new ArrayList<>();
    ArrayList<Integer> items_id = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this);
        myDataBase = myDBHelper.getWritableDatabase();

        nameET = findViewById(R.id.editText);
        phoneET = findViewById(R.id.editTextPhone);
        emailET = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.buttonSave);
        showButton = findViewById(R.id.buttonShow);

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(myDBHelper.NAME, nameET.getText().toString());
                values.put(myDBHelper.PHONE, phoneET.getText().toString());
                values.put(myDBHelper.EMAIL, emailET.getText().toString());

                myDataBase.insert(myDBHelper.TABLE_NAME, null, values);

                nameET.setText("");
                phoneET.setText("");
                emailET.setText("");
            }
        });

        showButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDataBase.rawQuery("select * from " + myDBHelper.TABLE_NAME, null);
                cursor.moveToFirst();

                do{
                    String name = cursor.getString(cursor.getColumnIndex(myDBHelper.NAME));
                    String phoneNum = cursor.getString(cursor.getColumnIndex(myDBHelper.PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(myDBHelper.EMAIL));
                    int id = cursor.getInt(cursor.getColumnIndex(myDBHelper.ID));

//                    Toast.makeText(MainActivity.this, " Name: " + name + " Phone: " + phoneNum +
//                            " Email: " + email, Toast.LENGTH_SHORT).show();

                    items_id.add(id);
                    dbList.add("Name: " + name + " Phone: " + phoneNum + " Email: " +email);

                    listView = findViewById(R.id.ListView);


                    registerForContextMenu(listView);

                    listAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1 ,dbList);

                    listView.setAdapter(listAdapter);

                }
                while (cursor.moveToNext());

            }
        });
    }

    /**
     * MENU
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.ListView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent editScreenIntent = new Intent(MainActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("ID", dbList.get((int) info.id));
                startActivity(editScreenIntent);

                int in = (int) info.id;
                deleteData(items_id.get(in));

                dbList = new ArrayList<>();
                items_id = new ArrayList<>();

                return true;
            case R.id.delete:
                int index = (int) info.id;
                deleteData(items_id.get(index));

                dbList = new ArrayList<>();
                items_id = new ArrayList<>();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteData(int indexid) {
        // TODO Auto-generated method stub

//        Toast.makeText(getApplicationContext(), "id " + indexid, Toast.LENGTH_SHORT).show();
        myDataBase = myDBHelper.getWritableDatabase();
        myDataBase.delete(myDBHelper.TABLE_NAME,"ID" + " = " + indexid, null);

    }

}
