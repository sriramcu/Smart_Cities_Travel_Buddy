package com.example.sctravelbuddyjavaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity class to allow users to login or click the sign up button
 */
public class LoginActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton ;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    LoginDatabaseHelper loginDatabaseHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";

    /**
     * Method called when activity is launched
     * @param savedInstanceState saved state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogInButton = (Button)findViewById(R.id.buttonLogin);

        RegisterButton = (Button)findViewById(R.id.buttonRegister);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        loginDatabaseHelper = new LoginDatabaseHelper(this);

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();

                // Clears the text fields when the user tries to sign in
                Email.getText().clear();
                Password.getText().clear();

            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


    // Login function starts from here.
    @SuppressLint("Range")
    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = loginDatabaseHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(LoginDatabaseHelper.TABLE_NAME, null, " " + LoginDatabaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(LoginDatabaseHelper.Table_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check whether the password entered is correct
            CheckFinalResult();

        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(LoginActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();

        }

    }


    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText fields and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking whether EditText is empty or not using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }


    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();




            SQLiteDatabase db = loginDatabaseHelper.getReadableDatabase();
            String name_param = "";
            Cursor c = db.rawQuery(String.format("SELECT %s FROM %s where %s='%s';", LoginDatabaseHelper.Table_Column_1_Name, LoginDatabaseHelper.TABLE_NAME, LoginDatabaseHelper.Table_Column_2_Email, EmailHolder), null);
            if (c.moveToFirst()){
                name_param = c.getString(0);
            }
            c.close();
            db.close();

            // if the user is an admin (else condition), he gets to add a new attraction to the database
            if(!name_param.toLowerCase().contains("admin")) {
                Intent intent = new Intent(LoginActivity.this, SelectorActivity.class);
                // Sending Name to Selector Activity using intent.
                intent.putExtra("MY_NAME", name_param);
                startActivity(intent);
            }

            else
            {
                // Admin navigates to the AddAttraction activity class
                Intent intent = new Intent(LoginActivity.this, AddAttraction.class);
                startActivity(intent);
            }


        }
        else {

            Toast.makeText(LoginActivity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND" ;

    }

}