package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        relativeLayout = (RelativeLayout)findViewById(R.id.developer_text_layout);
        relativeLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.developer_text_layout:
                Intent intent = new Intent(this,DeveloperActivity.class);
                startActivity(intent);
                break;
        }
    }


}
