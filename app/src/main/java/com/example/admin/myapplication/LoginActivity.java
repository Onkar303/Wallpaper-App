package com.example.admin.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    RelativeLayout relativeLayout;
    Button login_button;
    TextView donthaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    public void init()
    {
      login_button=(Button)findViewById(R.id.login_button);
      login_button.setOnClickListener(this);

      donthaveAccount=(TextView)findViewById(R.id.dont_have_account);
      donthaveAccount.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.login_button:
                i=new Intent(this,MainActivity.class);
                startActivity(i);

                //MyAlertDialog("Test");
                break;


            case R.id.dont_have_account:
                i=new Intent(this,SignIn.class);
                startActivity(i);
                break;
        }
    }


    public void MyAlertDialog(String name)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(name);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

       AlertDialog dialog=builder.create();
       dialog.show();


    }



}
