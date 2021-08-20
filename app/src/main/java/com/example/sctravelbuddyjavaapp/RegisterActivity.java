package com.example.sctravelbuddyjavaapp;

import android.content.Context;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Activity class for user registration
 */
public class RegisterActivity extends AppCompatActivity {

    EditText Email, Password, Name, ConfirmPassword ;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder, ConfirmPasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    LoginDatabaseHelper loginDatabaseHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    /**
     * Method called when activity is launched
     * @param savedInstanceState saved state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        ConfirmPassword = (EditText)findViewById(R.id.confirmPassword);
        Name = (EditText)findViewById(R.id.editName);

        loginDatabaseHelper = new LoginDatabaseHelper(this);

        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Validate email
                ValidateEmail();

                // Validate password and confirm
                ValidatePassword();

                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


            }
        });

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(LoginDatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + LoginDatabaseHelper.TABLE_NAME + "(" + LoginDatabaseHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + LoginDatabaseHelper.Table_Column_1_Name + " VARCHAR, " + LoginDatabaseHelper.Table_Column_2_Email + " VARCHAR, " + LoginDatabaseHelper.Table_Column_3_Password + " VARCHAR);");

    }

    // Insert data into SQLite database
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ LoginDatabaseHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(RegisterActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(startIntent);
        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegisterActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Clear edittext fields after done inserting
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Email.getText().clear();

        Password.getText().clear();

        ConfirmPassword.getText().clear();

    }

    // Method to check whether EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Checks whether an email is of the correct format
    public void ValidateEmail(){
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(EmailHolder);
        if (!matcher.matches())
        {
            F_Result = "Invalid_Email";
        }

    }

    // Checks whether the passwords entered match and comply with the given conditions
    public void ValidatePassword(){
        PasswordHolder = Password.getText().toString();
        ConfirmPasswordHolder = ConfirmPassword.getText().toString();

        if(!PasswordHolder.equals(ConfirmPasswordHolder))
        {
            F_Result = "pwd_mismatch";
        }
        Pattern pattern = Pattern.compile(
                "(?=.*[A-Z])" +  //At least one upper case character (A-Z)
                        "(?=.*[a-z])" +     //At least one lower case character (a-z)
                        "(?=.*\\d)" +   //At least one digit (0-9)
                        ".*");
        Matcher matcher = pattern.matcher(PasswordHolder);

        if ((!matcher.matches())||(PasswordHolder.length()<8)){
            F_Result = "invalid_pwd";
        }

    }
    // Checking whether email already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = loginDatabaseHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(LoginDatabaseHelper.TABLE_NAME, null, " " + LoginDatabaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email exists then toast msg will display.
            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }

        else if (F_Result.equalsIgnoreCase("Invalid_Email")){
            Toast.makeText(RegisterActivity.this,"Email ID is invalid",Toast.LENGTH_LONG).show();
        }

        else if (F_Result.equalsIgnoreCase("pwd_mismatch")){
            Toast.makeText(RegisterActivity.this,"Passwords do not match",Toast.LENGTH_LONG).show();
        }

        else if (F_Result.equalsIgnoreCase("invalid_pwd")){
            Toast.makeText(RegisterActivity.this,"Password must be of minimum size 8 and must have lowercase, uppercase and numeric characters",Toast.LENGTH_LONG).show();
        }
        else {

            // If there are no validation errors then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}