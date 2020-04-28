package com.example.easyagric;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.easyagric.helpers.FragmentCommunication;
import com.example.easyagric.models.Transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTransactionFragment extends DialogFragment implements View.OnClickListener {

    protected View rootView;
    protected TextView name;
    protected TextView quantity;
    protected TextView price;
    protected TextView date;
    protected Button delete;
    protected CardView singleItemView;
    protected Button editBtn;
    Transaction transaction;

    FragmentCommunication fragmentCommunication;

    public ViewTransactionFragment(Transaction transaction) {
        // Required empty public constructor
        this.transaction = transaction;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentCommunication = (FragmentCommunication) getActivity();
        // Get field from view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_transaction, container);
        initView(view);
        name.setText(transaction.getName());
        price.setText(transaction.getPrice() + "");
        quantity.setText(transaction.getQuantity() + "");
        date.setText(transaction.getDate());
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete) {
            fragmentCommunication.sendTransAction(transaction, "delete");
            dismiss();

        } else if (view.getId() == R.id.edit_btn) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            EditFragment editFragment = new EditFragment(transaction);
            editFragment.show(fragmentManager, "editTransactionFragment");
            dismiss();
        }
    }

    private void initView(View rootView) {
        name = (TextView) rootView.findViewById(R.id.name);
        quantity = (TextView) rootView.findViewById(R.id.quantity);
        price = (TextView) rootView.findViewById(R.id.price);
        date = (TextView) rootView.findViewById(R.id.date);
        delete = (Button) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(ViewTransactionFragment.this);
        singleItemView = (CardView) rootView.findViewById(R.id.single_item_view);
        editBtn = (Button) rootView.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(ViewTransactionFragment.this);
    }
}
