package com.example.easyagric;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyagric.helpers.FragmentCommunication;
import com.example.easyagric.models.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements FragmentCommunication {


    RecyclerView transactionListRecyclerView;
    protected TextView totalPrice;
    ArrayList<Transaction> transactionArrayList;
    SlimAdapter slimAdapter;

    int currentLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                AddTransactionFragment addTransactionFragment = new AddTransactionFragment();
                addTransactionFragment.show(fragmentManager, "addTransactionFragment");

            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("transactions", MODE_PRIVATE);
        String data = sharedPreferences.getString("transactions", null);

        if (data != null) {
            Gson gson = new Gson();
            Type itemDetailsType = new TypeToken<ArrayList<Transaction>>() {
            }.getType();
            transactionArrayList = gson.fromJson(data, itemDetailsType);
            currentLength = transactionArrayList.size();
            Log.e("dd",data);
        } else {
            transactionArrayList = new ArrayList<>();
        }


        transactionListRecyclerView = findViewById(R.id.transaction_list_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        transactionListRecyclerView.setLayoutManager(layoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.transaction_item, new SlimInjector<Transaction>() {

                    @Override
                    public void onInject(final Transaction data, IViewInjector injector) {

                        TransactionItemViewHolder views = new TransactionItemViewHolder(injector);
                        views.name.setText(data.getName());
                        views.price.setText(data.getPrice() + "");
                        views.quantity.setText(data.getQuantity() + "");
                        views.date.setText(data.getDate());

                        views.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                ViewTransactionFragment viewTransactionFragment = new ViewTransactionFragment(data);
                                viewTransactionFragment.show(fragmentManager, "viewTransactionFragment");

                            }
                        });
                    }
                })
                .attachTo(transactionListRecyclerView);

        slimAdapter.updateData(transactionArrayList);
        initView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        totalPrice = (TextView) findViewById(R.id.total_price);


    }

    @Override
    public void sendTransAction(Transaction transaction, String action) {
        if(action.equals("add")){
            transaction.setId(currentLength);
            transactionArrayList.add(transaction);
            currentLength++;
        }

        if(action.equals("delete")){
            transaction.setId(currentLength);
            transactionArrayList.remove(transaction);
            currentLength--;
        }



        slimAdapter.updateData(transactionArrayList);

    }


    public class TransactionItemViewHolder {

        protected View itemView;
        protected TextView name;
        protected TextView quantity;
        protected TextView price;
        protected TextView date;

        TransactionItemViewHolder(IViewInjector view) {
            itemView = (View) view.findViewById(R.id.single_item_view);
            name = (TextView) view.findViewById(R.id.name);
            quantity = (TextView) view.findViewById(R.id.quantity);
            price = (TextView) view.findViewById(R.id.price);
            date = (TextView) view.findViewById(R.id.date);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Gson gson = new Gson();
        Type itemDetailsType = new TypeToken<ArrayList<Transaction>>() {
        }.getType();
        String trans = gson.toJson(transactionArrayList, itemDetailsType);

        SharedPreferences sharedPreferences = this.getSharedPreferences("transactions", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("transactions", trans);
        editor.apply();
    }
}
