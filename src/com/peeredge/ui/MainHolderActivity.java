package com.peeredge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.peeredge.database.DbQuery;
import com.peeredge.ui.manager.AppManager;

import org.linphone.R;


public class MainHolderActivity extends FragmentActivity implements View.OnClickListener{


    private TextView txtLogout;
    private AppManager appManager;
    private DbQuery dbQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogout = (TextView)findViewById(R.id.logout);
        txtLogout.setOnClickListener(this);

        dbQuery = new DbQuery(this);

        appManager = AppManager.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        appManager.clear();
        dbQuery.deleteProviders();
        startActivity(new Intent(MainHolderActivity.this, LoginActivity.class));
        finish();

    }
}
