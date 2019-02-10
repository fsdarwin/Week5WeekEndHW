package com.example.week5weekendhw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.week5weekendhw.adapters.RvAdapter;
import com.example.week5weekendhw.managers.ContactsManager;
import com.example.week5weekendhw.managers.PermissionsManager;
import com.example.week5weekendhw.pojos.Contact;
import com.example.week5weekendhw.pojos.ContactEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements PermissionsManager.IPermissionManager, ContactsManager.IContractManager {

    PermissionsManager permissionsManager;
    ContactsManager contactsManager;
    ArrayList<Contact> listContacts;
    String email;
    public static final String TAG = "FRANK: ";
    RecyclerView recyclerView;
    RvAdapter rvAdapter;
    TextView tvVSelectedAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvVSelectedAddress = findViewById(R.id.tvSelectedAddress);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView = findViewById(R.id.rvAct1);
        recyclerView.setLayoutManager(layoutManager);

        permissionsManager = new PermissionsManager(this, this);
        contactsManager = new ContactsManager(this);

        permissionsManager.checkPermission();
        contactsManager.getContacts();
    }

    @Override
    public void onPermissionResult(boolean isGranted) {

    }

    @Override
    public void onContactsReceived(List<Contact> contactsList) {
        Log.d(TAG, "onContactsReceived:xfidglidgd " + contactsList.get(1).getAddress());
        listContacts = new ArrayList<>(contactsList);
        Log.d(TAG, "onContactsReceived: after cast" + listContacts.get(1).getAddress());
        rvAdapter = new RvAdapter(listContacts);
        recyclerView.setAdapter(rvAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (ContactEvent event) {
        tvVSelectedAddress.setText(event.getContact().getAddress());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Log.d(TAG, "onClick: HERE" + tvVSelectedAddress.getText().toString());
        intent.putExtra("address", tvVSelectedAddress.getText().toString());
        startActivity(intent);
    }
}
