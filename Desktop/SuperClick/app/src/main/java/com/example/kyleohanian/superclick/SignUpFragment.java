package com.example.kyleohanian.superclick;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


/* The sign up fragment is made so that a user can register for a super click account.
   It asks for info such as name, username, email etc.
 */
public class SignUpFragment extends Fragment {

    View.OnClickListener activity;
    LoginFragment loginFragment;
    static EditText firstName, lastName, email, id, username, password, retype;
    static Firebase reference;

    // Create the new instance of this fragment.
    public static SignUpFragment newInstance(View.OnClickListener activity) {
        SignUpFragment s = new SignUpFragment();
        s.activity = activity;
        return s;
    }

    // Overrides the onCreateView method to get the inflater, container, and state.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }


        /* Overrides the onClick method with the setOnClickListener. We also create a new Firebase
           object along with a new loginFragment so we are able to return to the loginFragment and
           the user will then be able to register.
         */
        View view = inflater.inflate(R.layout.fragment_sign_up,
                container, false);
        firstName = (EditText) view.findViewById(R.id.editText);
        lastName = (EditText) view.findViewById(R.id.editText2);
        id = (EditText) view.findViewById(R.id.editText3);
        email = (EditText) view.findViewById(R.id.editText4);
        username = (EditText) view.findViewById(R.id.editText5);
        password = (EditText) view.findViewById(R.id.editText6);
        retype = (EditText) view.findViewById(R.id.editText7);
        this.loginFragment = LoginFragment.newInstance(activity);
        Firebase ref = new Firebase("https://easyclick.firebaseio.com");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                reference = dataSnapshot.getRef();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Firebase ref = new Firebase("https://easyclick.firebaseio.com");
                    ref.createUser(username.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                        }
                    });
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.signup, loginFragment);
                    ft.commit();
                } catch (Exception e) {
                    Log.d("TAG","Not connected");
                }
            }
        });

        return view;
    }
}