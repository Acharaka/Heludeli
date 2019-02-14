package achaaraka.th.co.helukabel.Heludeli;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolar
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);




    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {

//            Get Value From EditText
            EditText nameEditText = getView().findViewById(R.id.edtName);
            EditText userEditText = getView().findViewById(R.id.edtUser);
            EditText passwordEditText = getView().findViewById(R.id.edtPassword);

//            Change Type Data To String
            String name = nameEditText.getText().toString().trim();
            String user = userEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

//            Check Space
            if (name.isEmpty() || user.isEmpty() || password.isEmpty()) {
//            have space
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.normalDialog("Have Space", "Please Fill All Blank");
            } else {
//                No Space

                try {

                    MyConstant myConstant = new MyConstant();
                    AddUserThread addUserThread = new AddUserThread(getActivity());
                    addUserThread.execute(name, user, password, myConstant.getUrlAddUser());
                    String result = addUserThread.get();
                    Log.d("14FedV1", "result = " + result);

                    if (Boolean.parseBoolean(result)) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        MyAlert myAlert = new MyAlert(getActivity());
                        myAlert.normalDialog("Cannot Register", "Please try again");
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }




        }  // if


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_register, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}
