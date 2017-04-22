package com.example.mhn.trailattendance;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView signInTV;
    TextView signUpTV;
    Context context;
    ContactManeger maneger;

//    EditText logInUserNameET;
//    EditText logInPasswordET;
//    Button okBtn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String USER_NAME="user_name";
    private static final String ADMIN="admin";
    private static final String ADMIN_NAME="admin_name";
    private static final String ADMIN_PASS="admin_pass";
    private static final String ADMIN_NAME_PROVIDED="adminadmin";
    private static final String ADMIN_PASS_PROVIDED="nothingnothing";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences(ADMIN,MODE_PRIVATE);
        editor=getSharedPreferences(ADMIN,MODE_PRIVATE).edit();
        editor.putString(ADMIN_NAME,ADMIN_NAME_PROVIDED);
        editor.putString(ADMIN_PASS,ADMIN_PASS_PROVIDED);
        editor.commit();

        signUpTV=(TextView)findViewById(R.id.sign_in_tv);
        signInTV=(TextView)findViewById(R.id.sign_in_tv);
//        logInUserNameET=(EditText)findViewById(R.id.log_in_user_name_et);
//        logInPasswordET=(EditText)findViewById(R.id.log_in_password_et);
//        okBtn=(Button)findViewById(R.id.log_in_ok_btn);
        maneger=new ContactManeger(this);
    }


    public void clickSignUp(View view) {
        Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void clickSignIn(View view) {
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.log_in_layout);
        dialog.setTitle("log in");
        final EditText userNameET=(EditText)dialog.findViewById(R.id.log_in_user_name_et);
        final EditText passwordET=(EditText)dialog.findViewById(R.id.log_in_password_et);

        Button logInBtn=(Button)dialog.findViewById(R.id.log_in_ok_btn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name="name";
                String pass="pass";
                String userName=userNameET.getText().toString();
                String password=passwordET.getText().toString();

                Cursor cursor=maneger.getInfo();
                int count =0;
                boolean login=false;
                cursor.moveToFirst();
                while(cursor!=null && count<cursor.getCount() && !login)
                {
                    String userNameCheck=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_USER_NAME));
                    String passwordCheck=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PASSWORD));

                    if(userName.equals(userNameCheck) && password.equals(passwordCheck))
                    {
                        /*Toast.makeText(MainActivity.this, "Passed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,UpdateDatabase.class);
                        startActivity(intent);
                        //finish();*/
                        login=true;
                    }
                    else
                    {
                        login=false;
                        cursor.moveToNext();
                        count++;
                        //Toast.makeText(MainActivity.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }

                }
                if(login)
                {

                    Intent intent=new Intent(MainActivity.this,UpdateDatabase.class);
                    //Bundle bundle=new Bundle();
                    //bundle.putString(USER_NAME,userName);
                    intent.putExtra(USER_NAME,userName);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Passed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialog.show();

        /*Intent intent=new Intent(MainActivity.this,UpdateDatabase.class);
        startActivity(intent);*/
    }



    public void clickAdmin(View view) {
        final Dialog adminDialog=new Dialog(MainActivity.this);
        adminDialog.setContentView(R.layout.admin_log_in_layout);
        adminDialog.setTitle("Admin Login");
        final EditText userNameET=(EditText)adminDialog.findViewById(R.id.admin_name_et);
        final EditText passwordET=(EditText)adminDialog.findViewById(R.id.admin_password_et);

        final String adminName=sharedPreferences.getString(ADMIN_NAME,"Not Found");
        final String adminPass=sharedPreferences.getString(ADMIN_PASS,"Not Found");

        userNameET.setText(adminName);
        passwordET.setText(adminPass);

        Button logInBtn=(Button)adminDialog.findViewById(R.id.admin_login_btn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name="name";
                //String pass="pass";
                String userName=userNameET.getText().toString();
                String password=passwordET.getText().toString();
                if(userName.equals(adminName) && password.equals(adminPass))
                {
                    Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                }


            }
        });
        adminDialog.show();

    }
}
