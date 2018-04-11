package com.bignerdeanch.android.fitleaf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MFontecchio on 4/5/2018.
 */
public class CustomerListFragment extends Fragment{

    private RecyclerView mCustomerRecyclerView;
    private CustomerAdapter mAdapter;

    private ImageView mPlaceholder;

    private int mCustomerModifiedPosition;

    //Menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mCustomerRecyclerView = (RecyclerView) view.findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    //Reload the RecyclerList onResume
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //Menu Inflater
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_customer_list, menu);
    }

    //Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logg_off:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.log_off_dialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override

                    //Log off onClick handler to send user back to login screen.
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Update recyclerView
    private void updateUI() {
        CustomerDB customerDB = CustomerDB.get(getActivity());
        List<Customer> customers = customerDB.getCustomerList();

        if (mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCustomerRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCustomers(customers);
            mAdapter.notifyItemChanged(mCustomerModifiedPosition);
            mCustomerModifiedPosition = RecyclerView.NO_POSITION;
        }

        //Blank RecyclerView placeholder
        mPlaceholder = (ImageView) getActivity().findViewById(R.id.blank_placeholder);
        if (customers.size() != 0) {
            mPlaceholder.setVisibility(View.GONE);
        } else {
            mPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    //ViewHolder
    private class CustomerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declare widgets for use in the recycler list details view
        private ImageView mCustomerImageView;
        private TextView mCustomerNameTextView;

        //Customer item methods on create
        private Customer mCustomer;


        public CustomerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_customer, parent, false));
            itemView.setOnClickListener(this);

            //Bind customer items to widgets in use.
            mCustomerImageView = (ImageView) itemView.findViewById(R.id.customer_photo);
            mCustomerNameTextView = (TextView) itemView.findViewById(R.id.customer_name);
        }

        public void bind(Customer customer) {
            mCustomer = customer;
            //reserved to set mCustomerImageView src.
            mCustomerNameTextView.setText(mCustomer.getName());
        }

        //OnClick event handler for list items.
        @Override
        public void onClick(View view){
            mCustomerModifiedPosition = getAdapterPosition();
            Intent intent = PagerActivity.newIntent(getActivity(), mCustomer.getId());
            startActivity(intent);
        }
    }

    //Adapter
    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {
        private List<Customer> mCustomers;

        public CustomerAdapter(List<Customer> customers) {
            mCustomers = customers;
        }
        //Adapter override methods
        @Override
        public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CustomerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CustomerHolder holder, int position) {
            Customer customer = mCustomers.get(position);
            holder.bind(customer);
        }

        @Override
        public int getItemCount() {
            return mCustomers.size();
        }

        //Update customers to the recycler adapter
        public void setCustomers(List<Customer> customers) {
            mCustomers = customers;
        }
    }
}
