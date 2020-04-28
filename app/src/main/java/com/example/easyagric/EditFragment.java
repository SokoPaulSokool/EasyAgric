package com.example.easyagric;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
public class EditFragment extends DialogFragment implements View.OnClickListener {

    protected View rootView;
    protected AppCompatEditText name;
    protected AppCompatEditText quantity;
    protected AppCompatEditText price;
    protected AppCompatEditText date;
    protected Button editBtn;
    protected CardView singleItemView;
    Transaction transaction;

    FragmentCommunication fragmentCommunication;

    public EditFragment(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit, container);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentCommunication = (FragmentCommunication) getActivity();
        name.setText(transaction.getName());
        price.setText(transaction.getPrice()+"");
        quantity.setText(transaction.getQuantity()+"");
        date.setText(transaction.getDate());

        // Get field from view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edit_btn) {
            String nameStr = name.getText().toString().trim();
            String quantityStr = quantity.getText().toString().trim();
            String dateStr = date.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
//            martinkatamba@gmail
            if(!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(quantityStr) && !TextUtils.isEmpty(dateStr) && !TextUtils.isEmpty(priceStr) ){
                Transaction newtransaction = new Transaction(nameStr,
                        Integer.parseInt(quantityStr),
                        Integer.parseInt(priceStr),dateStr);
                newtransaction.setId(transaction.getId());

                fragmentCommunication.sendTransAction(newtransaction, "edit");
                dismiss();
            }else{
                Toast.makeText(getContext(), "Some Values are missing", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void initView(View rootView) {
        name = (AppCompatEditText) rootView.findViewById(R.id.name);
        quantity = (AppCompatEditText) rootView.findViewById(R.id.quantity);
        price = (AppCompatEditText) rootView.findViewById(R.id.price);
        date = (AppCompatEditText) rootView.findViewById(R.id.date);
        editBtn = (Button) rootView.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(EditFragment.this);
        singleItemView = (CardView) rootView.findViewById(R.id.single_item_view);
    }
}
