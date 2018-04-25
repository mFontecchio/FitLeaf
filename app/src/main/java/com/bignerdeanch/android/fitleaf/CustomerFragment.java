package com.bignerdeanch.android.fitleaf;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by MFontecchio on 3/31/2018.
 */
public class CustomerFragment extends Fragment {

    //Static argument to pass
    private static final String ARG_CUSTOMER_ID = "customer_id";
    private static final int REQUEST_PHOTO = 2;

    private Customer mCustomer;
    private EditText mCustomerNameField;
    private Button mNewSessionButton;
    private CheckBox mCompleteSessionCheckBox;
    private ImageView mPhotoView;
    private File mPhotoFile;

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
        mPhotoFile = CustomerDB.get(getActivity()).getPhotoFile(mCustomer);

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
        mNewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BillingActivity.class);
                startActivity(intent);
            }
        });

        //Set up CompleteSessionCheckBox
        mCompleteSessionCheckBox = (CheckBox) v.findViewById(R.id.complete_session_checkbox);
        //mCompleteSessionCheckBox.setChecked(mCustomer.isCompleted()); //place marker for the time being, needs to be paired up with the session history.
        mCompleteSessionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mCustomer.setCompleted(checked);
            }
        });

        //PackageManager
        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoView = (ImageView) v.findViewById(R.id.customer_image_details);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager)
                != null;
        mPhotoView.setEnabled(canTakePhoto);

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(), "com.bignerdranch.android.fitleaf.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });



        //ImageView
        //mPhotoView = (ImageView) v.findViewById(R.id.customer_image_details);
        updateCustomerPhoto();

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

    //Activity Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(), "com.bignerdranch.android.fitleaf.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updateCustomerPhoto();
        }
    }

    private void updateCustomerPhoto() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_client));
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
