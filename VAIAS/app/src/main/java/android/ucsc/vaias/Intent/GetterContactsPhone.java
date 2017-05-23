package android.ucsc.vaias.Intent;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;

/**
 * Created by Annick on 04/02/2017.
 */

public class GetterContactsPhone {
    private Context context;
    public GetterContactsPhone(Context context) {
        this.context=context;
    }
    public HashMap<String,String> getAllContact() {
        HashMap<String,String> contactList = new HashMap<>();

        Cursor phones = context.getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.put(name,phoneNumber);
        }
        phones.close();
        return contactList;
    }
}
