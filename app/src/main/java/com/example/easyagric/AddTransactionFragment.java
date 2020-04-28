package com.example.easyagric;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.easyagric.helpers.FragmentCommunication;
import com.example.easyagric.models.Transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransactionFragment extends DialogFragment implements View.OnClickListener {

    protected View rootView;
    protected AppCompatEditText name;
    protected AppCompatEditText quantity;
    protected AppCompatEditText date;
    protected CardView singleItemView;
    protected Button addTransationBtn;
    protected AppCompatEditText price;


    FragmentCommunication fragmentCommunication;
    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_add_transaction, container);
        initView(rootView);
        return  rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentCommunication = (FragmentCommunication) getActivity();
        // Get field from view;
    }


    private void initView(View rootView) {
        name = (AppCompatEditText) rootView.findViewById(R.id.name);
        quantity = (AppCompatEditText) rootView.findViewById(R.id.quantity);
        date = (AppCompatEditText) rootView.findViewById(R.id.date);
        singleItemView = (CardView) rootView.findViewById(R.id.single_item_view);
        addTransationBtn = (Button) rootView.findViewById(R.id.add_transation_btn);
        addTransationBtn.setOnClickListener(AddTransactionFragment.this);
        price = (AppCompatEditText) rootView.findViewById(R.id.price);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_transation_btn) {
            String nameStr = name.getText().toString().trim();
            String quantityStr = quantity.getText().toString().trim();
            String dateStr = date.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
//            martinkatamba@gmail
            if(!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(quantityStr) && !TextUtils.isEmpty(dateStr) && !TextUtils.isEmpty(priceStr) ){
                Transaction transaction = new Transaction(nameStr,
                        Integer.parseInt(quantityStr),
                        Integer.parseInt(priceStr),dateStr);
                fragmentCommunication.sendTransAction(transaction,"add");
                dismiss();
            }else{
                Toast.makeText(getContext(), "Some Values are missing", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
