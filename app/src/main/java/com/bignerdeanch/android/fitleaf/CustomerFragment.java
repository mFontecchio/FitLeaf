package com.bignerdeanch.android.fitleaf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by MFontecchio on 3/31/2018.
 */
public class CustomerFragment extends Fragment {

    //Static argument to pass
    private static final String ARG_CUSTOMER_ID = "customer_id";

    private Customer mCustomer;
    private EditText mCustomerNameField;
    private Button mNewSessionButton;
    private CheckBox mCompleteSessionCheckBox;

    public static CustomerFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID customerId = (UUID) getArguments().getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerDB.get(getActivity()).getCustomer(customerId);

        setHasOptionsMenu(true);
    }

    //Push updates to customer
    /*@Override
    public void onPause() {
        super.onPause();

        //CustomerDB.get(getActivity()).updateCustomer(mCustomer);
    }*/

    //Inflate layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer, container, false);

        //Set up Customer Name EditField and listeners for on change.
        //On change set the customer objects name to the name input.
        mCustomerNameField = (EditText) v.findViewById(R.id.customer_name_label);
        mCustomerNameField.setText(mCustomer.getName());
        mCustomerNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCustomer.setName(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Set up newSessionButton
        mNewSessionButton = (Button) v.findViewById(R.id.add_session_button);

        //Set up CompleteSessionCheckBox
        mCompleteSessionCheckBox = (CheckBox) v.findViewById(R.id.complete_session_checkbox);
        //mCompleteSessionCheckBox.setChecked(mCustomer.isCompleted()); //place marker for the time being, needs to be paired up with the session history.
        mCompleteSessionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mCustomer.setCompleted(checked);
            }
        });

        return v;
    }

    //Menu Inflater
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_customer, menu);
    }

    //Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_customer:
                //Push Update to DB
                CustomerDB.get(getActivity()).updateCustomer(mCustomer);

                //Pop user back
                backtoMain();
                return true;

            case R.id.delete_customer:
                CustomerDB.get(getActivity()).deleteCustomer(mCustomer);

                backtoMain();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backtoMain() {
        Intent intent = new Intent(getActivity(), CustomerListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}
