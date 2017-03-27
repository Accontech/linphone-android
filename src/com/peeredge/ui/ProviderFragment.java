package com.peeredge.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peeredge.ui.manager.AppManager;

import org.linphone.R;


/**
 * Created by root on 3/25/17.
 */

public class ProviderFragment extends Fragment {

    private ImageView imgLogin;
    private RecyclerView myRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.provider_fragment,container,false);
         initUI(view);

        AppManager appManager = AppManager.getInstance(getActivity());

        if(appManager.hasAccount())
        {
            Intent intent = new Intent(getActivity(), MainHolderActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        return view;
    }


    private void  initUI(View view)
    {

        (getActivity()).getActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);

        imgLogin = (ImageView)view.findViewById(R.id.nextToLogin);
        imgLogin.setOnClickListener(clickListener);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.providersRecycler);
        ProviersAdapter proviersAdapter = new ProviersAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(proviersAdapter);
    }

    private void openLoginScrean()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.replace(R.id.fragment_container,loginFragment);
        fragmentTransaction.commit();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.nextToLogin:

                    openLoginScrean();

                    break;
            }
        }
    };

    private class ProviersAdapter extends  RecyclerView.Adapter<ProviersAdapter.MyViewHolder>
    {


        private final String[] data = new String[]{"Globalgig","Telmex","Brasilfone","BrightLink","TSI Corp"};
        private Context mContext;

        public ProviersAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_item, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            String str = data[position];
            holder.txtProvider.setText(str);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {

            private TextView txtProvider;
            public MyViewHolder(View itemView) {
                super(itemView);

                txtProvider = (TextView)itemView.findViewById(R.id.txtProvider);

                txtProvider.setOnClickListener(clickListener);
            }

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtProvider.setTextColor(mContext.getResources().getColor(R.color.text_selector_provider_screen));
                }
            };
        }

    }

}
