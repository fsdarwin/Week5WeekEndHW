package com.example.week5weekendhw.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.week5weekendhw.R;
import com.example.week5weekendhw.pojos.Contact;
import com.example.week5weekendhw.pojos.ContactEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    //DECLARATIONS
    ArrayList<Contact> contactsArrayList;
    public static final String TAG = "FRANK: ";

    public RvAdapter(ArrayList<Contact> contactsArrayList) {
        this.contactsArrayList = contactsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Contact contact = contactsArrayList.get(position);
        if (contact != null) {
            viewHolder.setItemContact(contact);

            String contactName = contact.getName();
            String contactAddress = contact.getAddress();

            viewHolder.tvVContactName.setText(contactName);
            viewHolder.tvVContactAddress.setText(contactAddress);
        }
    }

    @Override
    public int getItemCount() {
        return contactsArrayList != null ? contactsArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvVContactName;
        TextView tvVContactAddress;
        Contact itemContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVContactName = itemView.findViewById(R.id.tvContactName);
            tvVContactAddress = itemView.findViewById(R.id.tvContactAddress);

            //ONCLICK listener for item selection
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new ContactEvent(itemContact));

                }
            });
        }

        public void setItemContact(Contact itemContact) {
            this.itemContact = itemContact;
        }
    }
}
