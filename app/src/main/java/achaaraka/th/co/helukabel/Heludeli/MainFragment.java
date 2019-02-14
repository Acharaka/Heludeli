package achaaraka.th.co.helukabel.Heludeli;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }   // Constructor

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Register Controller
        TextView textView = getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fregment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();
            }
        });

//        Login Controller
        Button button = getActivity().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEditText = getView().findViewById(R.id.ditUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                MyAlert myAlert = new MyAlert(getActivity());

                if (user.isEmpty() || password.isEmpty()) {
                    myAlert.normalDialog("Have Space", "Please Fill Every Blank");
                } else {

                    try {

                        MyConstant myConstant = new MyConstant();
                        GetUserWhereUserThread getUserWhereUserThread = new GetUserWhereUserThread(getActivity());
                        getUserWhereUserThread.execute(user, myConstant.getUrlGetUser());
                        String json = getUserWhereUserThread.get();
                        Log.wtf("14FebV1", "json" + json);

                        if (json.equals("null")) {
                            myAlert.normalDialog("User False", "No " + user + "in my Database");
                        } else {

                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if (password.equals(jsonObject.getString("Password"))) {
                                Toast.makeText(getActivity(),"Welcome" + jsonObject.getString("Name"),Toast.LENGTH_SHORT).show();
                            } else {
                                myAlert.normalDialog("Password False", "Please Try Again");
                            }

                        } // if

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } // if


            }
        });


    }   // Main Method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }   // Create View

}   // Main Class
