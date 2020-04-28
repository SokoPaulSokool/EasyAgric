package com.example.easyagric.helpers;

import com.example.easyagric.models.Transaction;

public interface FragmentCommunication {

    public void sendTransAction(Transaction transaction, String action);
}
