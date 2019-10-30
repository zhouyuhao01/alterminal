package aiterminal.android.chdmc.com.aiterminal.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aiterminal.android.chdmc.com.aiterminal.R;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public final static String[] dayStrs = new String[]{"9.12", "9.13", "9.14", "9.15", "9.16", "9.17", "9.18"};
    public final static float[] dayData = new float[]{100, 200, 400, 500, 700, 300, 100};

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        LineChartView lineChartView = rootView.findViewById(R.id.chart_1);

        int numValues = 7;                      //7个值 注意与定义的X轴数量相同

        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();

        //设置默认值 都为0
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, dayData[i]));
            axisValues.add(new AxisValue(i).setLabel(dayStrs[i]));
        }

        //设置线
        Line line = new Line(values);
        line.setColor(0xFF55FF55).setHasPoints(false).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        //对数据进行一些设置 类似Line Chart
        LineChartData mLineData = new LineChartData(lines);
        mLineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        mLineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
        lineChartView.setLineChartData(mLineData);
        lineChartView.setViewportCalculationEnabled(false);


        //设置到窗口上
        Viewport v = new Viewport(0, 1000, 6, -5);   //防止曲线超过范围 边界保护
        lineChartView.setMaximumViewport(v);
        lineChartView.setCurrentViewport(v);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);

        PieChartView pieChartView = rootView.findViewById(R.id.chart_2);

        List<SliceValue> pieValues = new ArrayList<>();
        String[] textList = new String[] {"易腐垃圾", "可回收垃圾", "其他垃圾", "干垃圾", "湿垃圾"};
        int[] colorList = new int[] {ChartUtils.COLOR_BLUE, ChartUtils.COLOR_GREEN, ChartUtils.COLOR_ORANGE, ChartUtils.COLOR_RED, ChartUtils.COLOR_VIOLET};
        for (int i = 0; i < 5; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, colorList[i]);
            sliceValue.setLabel(textList[i]);
            pieValues.add(sliceValue);
        }

        /*===== 设置相关属性 类似Line Chart =====*/
        PieChartData mPieChartData = new PieChartData(pieValues);
        mPieChartData.setHasLabels(true);
        mPieChartData.setHasLabelsOnlyForSelected(false);
        mPieChartData.setHasLabelsOutside(false);
        mPieChartData.setHasCenterCircle(false);

        //是否分离
        mPieChartData.setSlicesSpacing(2);                 //分离间距为18

        //是否显示单行文本
        mPieChartData.setCenterText1("Hello");             //文本内容

        pieChartView.setPieChartData(mPieChartData);         //设置控件

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
