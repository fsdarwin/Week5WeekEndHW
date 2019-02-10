package com.example.week5weekendhw.pojos;

public class ContactEvent {

    Contact contact;

    public ContactEvent() {
    }

    public ContactEvent(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
