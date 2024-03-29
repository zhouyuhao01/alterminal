package aiterminal.android.chdmc.com.aiterminal.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.activity.LoginActivity;
import aiterminal.android.chdmc.com.aiterminal.activity.MyComplainListActivity;
import aiterminal.android.chdmc.com.aiterminal.manager.LoginManager;
import aiterminal.android.chdmc.com.aiterminal.manager.SPManager;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;
import aiterminal.android.chdmc.com.aiterminal.utils.Utils;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        View rootView = getView();
        if (rootView != null) {
            initView();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_about:
                Utils.showDialogAndDoNothing(getActivity(), "当前版本0.1");
                break;
            case R.id.button_logout:
                LoginManager.getInstance().logout();
                initView();

                break;
            case R.id.button_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.button_my_complain:
                intent = new Intent(getActivity(), MyComplainListActivity.class);
                startActivity(intent);
                break;
            case R.id.button_my_order:
                break;
            case R.id.button_my_score:
                break;

        }

    }


    private void initView() {
        View rootView = getView();
        if (rootView == null) {
            return;
        }
        boolean hasLogin = LoginManager.getInstance().hasLogin();

        if (hasLogin) {
            rootView.findViewById(R.id.button_login).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.layout_personal_info).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.button_logout).setOnClickListener(this);

            TextView loginName = rootView.findViewById(R.id.login_name);
            loginName.setText(SPManager.getString(SPManager.KEY_USERNAME));

            ImageView iconImage = rootView.findViewById(R.id.login_icon);
            RequestUtil.loadImageByUrl(getContext(), SPManager.getString(SPManager.KEY_USERICON_URL) ,iconImage);

            rootView.findViewById(R.id.button_about).setOnClickListener(this);
            rootView.findViewById(R.id.button_my_complain).setOnClickListener(this);
            rootView.findViewById(R.id.button_my_order).setOnClickListener(this);
            rootView.findViewById(R.id.button_my_score).setOnClickListener(this);


        } else {

            rootView.findViewById(R.id.button_login).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.layout_personal_info).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.button_login).setOnClickListener(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
