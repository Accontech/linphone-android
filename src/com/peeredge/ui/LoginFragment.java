package com.peeredge.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.peeredge.ui.manager.AppManager;

import org.linphone.R;


/**
 * Created by root on 3/25/17.
 */

public class LoginFragment extends Fragment implements  View.OnClickListener{

    private ImageView imgLogin;
    private EditText edUsername;
    private EditText edPassword;
    private AppManager appManager;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment,container,false);

        imgLogin = (ImageView)view.findViewById(R.id.login);
        imgLogin.setOnClickListener(this);
        scrollView = (ScrollView) view.findViewById(R.id.loginscrollview);
        edUsername = (EditText)view.findViewById(R.id.email_address);
        edPassword = (EditText)view.findViewById(R.id.password);

        appManager = AppManager.getInstance(getActivity());

        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        (getActivity()).getActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        attachKeyboardListeners();

        return view;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isEmpty()
    {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();

        return (TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
    }

    private void startActivity()
    {
        Intent intent = new Intent(getActivity(), MainHolderActivity.class);
        startActivity(intent);
        edUsername.setText("");
        edPassword.setText("");
        getActivity().finish();
    }

    private void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = getActivity().findViewById(R.id.top_view);
		/*rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);*/

        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(keyboardListenersAttached)
            detachListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!keyboardListenersAttached && allowAtach)
            attachListener();
    }
//
    void attachListener()
    {
        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    void detachListener()
    {
        keyboardListenersAttached = false;
        if (Build.VERSION.SDK_INT < 16) {
            getActivity().getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        } else {
            getActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }

    private int prev_height = -1;
    private boolean keyboardListenersAttached;
    private boolean allowAtach;
    private View rootLayout;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener =
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    int height = rootLayout.getHeight();
                    if(prev_height>0)
                    {
                        int heightDiff = prev_height - height;
                        if (heightDiff > 200) {
                            scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.scrollTo(0, scrollView.getBottom());
                                }
                            });
                        } else if (heightDiff < -200){
                            //onHideKeyboard();
                        }
                    }
                    prev_height = height;


                }

            };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login:

               if(!isEmpty())
               {
                   String username = edUsername.getText().toString();
                   String password = edPassword.getText().toString();
                   appManager.setPassword(password);
                   appManager.setUsername(username);
                   startActivity();
               }
                else
               {
                   Toast.makeText(getActivity(),"Need to fill all fields", Toast.LENGTH_LONG).show();
               }

                break;
        }
    }
}
