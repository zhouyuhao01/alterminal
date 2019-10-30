package aiterminal.android.chdmc.com.aiterminal.fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aiterminal.android.chdmc.com.aiterminal.MainApplication;
import aiterminal.android.chdmc.com.aiterminal.activity.NewsDetailActivity;
import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.NewsListItemBean;
import aiterminal.android.chdmc.com.aiterminal.database.DbUtil;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<NewsListItemBean> mNewsListBeans;

    private Context mContext = getContext();

    public NewsFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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

        genNewsData();
    }

    private void genNewsData() {

        mNewsListBeans = DbUtil.getNewsList();
        if (mNewsListBeans == null || mNewsListBeans.size() == 0) {
            mNewsListBeans = new ArrayList<>();
            mNewsListBeans.add(new NewsListItemBean("没台词、没脸照样上热搜！没想到，国庆档最强演技竟然被他锁了",
                    "", "https://p1.pstatp.com/list/dfic-imagehandler/53d4046c-0c72-4864-9330-9ad131053b69",
                    "https://www.jianshu.com/p/e68a0b5fd383/"));
            mNewsListBeans.add(new NewsListItemBean("真正的大事：寒冷干旱的中国西北正在变暖变湿",
                    "", "https://p3.pstatp.com/list/190x124/pgc-image/RcPyBUOAkQFGun",
                    "https://static.jingjiribao.cn/static/jjrbrss/3rsshtml/20190918/198684.html?tt_group_id=6737989909144928771"));
            mNewsListBeans.add(new NewsListItemBean("香港艺人严淑明发文证实马蹄露共缝11针：每一针都刺痛爱护中国人的心",
                    "", "https://p1.pstatp.com/list/190x124/pgc-image/ReHISUoATqWsvJ",
                    "https://3w.huanqiu.com/a/c36dc8/7QBn8gl3Bjq?agt=20&tt_group_id=6745277437174612492"));

            mNewsListBeans.add(new NewsListItemBean("香港茶餐厅勇敢撑警 意外收获营业51年来最火爆生意",
                    "", "https://inews.gtimg.com/newsapp_ls/0/10474167186_294195/0",
                    "https://new.qq.com/omn/20191008/20191008V0O5MA00.html"));
            mNewsListBeans.add(new NewsListItemBean("77人涉蒙面被捕，港警：暴徒想要我同事的命",
                    "", "https://inews.gtimg.com/newsapp_ls/0/10473416106_294195/0",
                    "https://new.qq.com/omn/20191008/20191008A0LF2Y00.html"));
            mNewsListBeans.add(new NewsListItemBean("今年的物理学诺奖获得者，改变了我们对宇宙的看法",
                    "", "https://inews.gtimg.com/newsapp_ls/0/10473789352_294195/0",
                    "https://new.qq.com/rain/a/20191008A0LJBA00"));
        }
        DbUtil.insertNewsList(mNewsListBeans);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view;
                RecyclerView.ViewHolder viewHolder = null;
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_1, parent, false);
                viewHolder = new ViewHolderOne(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                NewsListItemBean newsBean = mNewsListBeans.get(position);
                ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
                viewHolderOne.tvTitle.setText(newsBean.newsTitle);
                viewHolderOne.tvCateName.setText(newsBean.newsContent);
                RequestUtil.loadImageByUrl(getContext(), newsBean.newsImage, viewHolderOne.ivImage);

                holder.itemView.setOnClickListener((v) -> {
                    int pos = holder.getLayoutPosition();
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("newsUrl", newsBean.newsUrl);
                    getContext().startActivity(intent);
                });

            }

            @Override
            public int getItemCount() {
                return mNewsListBeans.size();
            }
        });
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        return rootView;
    }

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

    public static class ViewHolderOne extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_cate_name)
        TextView tvCateName;
        @BindView(R.id.iv_main_img)
        ImageView ivImage;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolderOne(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
