package com.example.week5weekendhw.managers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.week5weekendhw.pojos.Contact;

import java.util.ArrayList;
import java.util.List;

import static com.example.week5weekendhw.adapters.RvAdapter.TAG;

public class ContactsManager {
    private Context context;
    private IContractManager contractManager;

    public ContactsManager(Context context) {
        this.context = context;
        this.contractManager = (IContractManager) context;
    }

    public void getContacts() {

        //define content uri
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        //define columns
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        String HAS_ADDRESS = "has_address";


        //retrieve the contents from contactProvider
        Cursor contactsCursor = context.getContentResolver().query(
                contactUri, null, null, null, null
        );


        List<Contact> contactList = new ArrayList<>();
        while (contactsCursor.moveToNext()) {

            String contactName = contactsCursor.getString(contactsCursor.getColumnIndex(DISPLAY_NAME));

            //Log.d(TAG, "getContacts: " + contactName);
            //retrieve phone numbers from contacts
            int hasNumberColumnIndex = contactsCursor.getColumnIndex(HAS_PHONE_NUMBER);
            int has_phone = contactsCursor.getInt(hasNumberColumnIndex);

            if (has_phone > 0) {

                List<String> numbers = new ArrayList<>();
                String address = "";
                String contactId = contactsCursor.getString(
                        contactsCursor.getColumnIndex(
                                ContactsContract.Contacts._ID));

                Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
                Uri addressUri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
                String ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.STREET;
                String CITY = ContactsContract.CommonDataKinds.StructuredPostal.CITY;
                String ZIP = ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE;

                Cursor phoneCursor = context.getContentResolver().query(
                        phoneUri,
                        new String[]{NUMBER},//projection
                        DISPLAY_NAME + "=?"
                        , new String[]{contactName}
                        , NUMBER + " ASC"
                );

                Cursor addressCursor = context.getContentResolver().query(
                        addressUri,
                        null,//projection
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId
                        , null
                        , null
                );

                //Select PROJECTION from PHONEURI where SELECTION{SELECTION ARG) SORTORDER
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                    numbers.add(phoneNumber);
                }

                while (addressCursor.moveToNext()) {
                    String physicalLocation = addressCursor.getString(addressCursor.getColumnIndex(ADDRESS));
                    String city = addressCursor.getString(addressCursor.getColumnIndex(CITY));
                    String zip = addressCursor.getString(addressCursor.getColumnIndex(ZIP));
                    address = physicalLocation + ", " + city + ", " + zip;
                }

                if (address != null && address != "") {
                    Contact contact = new Contact(contactName, address);
                    contactList.add(contact);
                    Log.d(TAG, "getContacts: " + contactName + ", " + address);
                }
            }
        }
        Log.d(TAG, "getContacts: about to send onContactsReceived " + contactList.get(0).getName());
        contractManager.onContactsReceived(contactList);
    }


    public interface IContractManager {
        void onContactsReceived(List<Contact> contactsList);
    }
}
