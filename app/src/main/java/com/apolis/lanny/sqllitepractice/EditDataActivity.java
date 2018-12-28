package com.apolis.lanny.sqllitepractice;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditDataActivity extends AppCompatActivity {

    EditText name, phone, email;
    String res;
    Button save;
    MyDBHelper myDBHelper;
    SQLiteDatabase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextEmail);
        save = findViewById(R.id.button);

        Intent receivedIntent = getIntent();

        res = receivedIntent.getStringExtra("ID");

        String[] resPart = res.split("\\s+");

        name.setText( resPart[1]);
        phone.setText(resPart[3]);
        email.setText(resPart[5]);

        myDBHelper = new MyDBHelper(this);
        myDataBase = myDBHelper.getWritableDatabase();

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newPhone = phone.getText().toString();
                String newEmail = email.getText().toString();

                ContentValues values = new ContentValues();
                values.put(myDBHelper.NAME, newName);
                values.put(myDBHelper.PHONE, newPhone);
                values.put(myDBHelper.EMAIL, newEmail);

                myDataBase.insert(myDBHelper.TABLE_NAME, null, values);

            }
        });

    }
}
