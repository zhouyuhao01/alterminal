package aiterminal.android.chdmc.com.aiterminal.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.activity.AddComplainActivity;
import aiterminal.android.chdmc.com.aiterminal.activity.ScoreActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OperationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OperationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int TAKE_PHOTO = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mCamera;
    private View mTousu;
    private View mCall;
    private View other;

    private OnFragmentInteractionListener mListener;

    public OperationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OperationFragment newInstance(String param1, String param2) {
        OperationFragment fragment = new OperationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_operation, container, false);

        mCall = rootView.findViewById(R.id.operation_collect);
        mCall.setOnClickListener(this);

        rootView.findViewById(R.id.operation_complain).setOnClickListener(this);
        rootView.findViewById(R.id.operation_score).setOnClickListener(this);

        return rootView;
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.operation_comment:
                break;
            case R.id.operation_score:
                intent = new Intent(getContext(), ScoreActivity.class);
                startActivity(intent);
                break;
            case R.id.operation_complain:
                intent = new Intent(getContext(), AddComplainActivity.class);
                startActivity(intent);
                break;
            case R.id.operation_collect:
                int hasCall = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);
                if (hasCall == PackageManager.PERMISSION_GRANTED) {
                    //拥有权限，执行操作
                    intent= new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ "13486091957"));
                    startActivity(intent);
                }else{
                    //没有权限，向用户请求权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 0);
                }
                break;

        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bitmap.extractAlpha();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
