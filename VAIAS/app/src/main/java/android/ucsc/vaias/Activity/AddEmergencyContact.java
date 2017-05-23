package android.ucsc.vaias.Activity;

import android.content.Intent;
import android.ucsc.vaias.Adapter.ListViewAdapter;
import android.ucsc.vaias.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;

public class AddEmergencyContact extends AppCompatActivity {

    private ListView contactListView;
    private ListView saveContactListView;
    private HashMap<String, String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);

        /** Getting contact list from Second Fragment */
        Intent intent = getIntent();
        contacts = (HashMap<String, String>) intent.getSerializableExtra("hashmap_contact");

        contactListView = (ListView) findViewById(R.id.listView_contacts);
        final ListViewAdapter adapter  = new ListViewAdapter(contacts);
        contactListView.setAdapter(adapter);
        saveContactListView = contactListView;

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test", "result: " + adapter.getItem(i));

                /**Creating Hashmap to return */
                HashMap<String,String> contactAdded = new HashMap<String, String>();
                contactAdded.put(adapter.getItem(i).getKey(), adapter.getItem(i).getValue());

                /** Sending the emergency contact added to Second Fragment*/
                Intent intent = new Intent();
                intent.putExtra("contact_urgent", contactAdded);
                setResult(RESULT_OK, intent);

                finish();
            }
        });






    }

}
