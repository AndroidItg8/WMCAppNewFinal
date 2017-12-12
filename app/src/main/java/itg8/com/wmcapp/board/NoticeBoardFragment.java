package itg8.com.wmcapp.board;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.board.mvp.NBMVP;
import itg8.com.wmcapp.board.mvp.NBPresenterImp;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.complaint.AddComplaintFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticeBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeBoardFragment extends Fragment implements View.OnClickListener, NBMVP.NBView, NoticeBoardAdater.NoticeBoardListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.rl_include)
    RelativeLayout rlInclude;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NBMVP.NBPresenter presenter;
    private Fragment fragment = null;
    private Context mContext;
    private NoticeBoardAdater adapter;
    private LinearLayoutManager layoutManager;
    private FragmentTransaction ft;
     CommonMethod.onSetToolbarTitle listenerTitle;


    public NoticeBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeBoardFragment newInstance(String param1, String param2) {
        NoticeBoardFragment fragment = new NoticeBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.d("CYCLE : onCreate()");
        setRetainInstance(true);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Logs.d("CYCLE : onCreateView()");
        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        rlInclude.setOnClickListener(this);
        presenter = new NBPresenterImp(this);
        init();
        presenter.onLoadMoreItems(getString(R.string.url_notice_board));
        listenerTitle.onSetTitle(getString(R.string.notice_board));
        return view;
    }



    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoticeBoardAdater(getActivity(), this);
        recyclerView.addOnScrollListener(presenter.scrollListener(layoutManager));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Logs.d("CYCLE : Attach()");
         listenerTitle = (CommonMethod.onSetToolbarTitle) mContext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.d("CYCLE : onDestroyView()");
        unbinder.unbind();
        presenter.onDetach();
        MyApplication.getInstance().setNbID(null);

    }

    @Override
    public void onStart() {
        super.onStart();
        Logs.d("CYCLE : onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.d("CYCLE : onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logs.d("CYCLE : onPause()");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_include:
              //  fragment = AddComplaintFragment.newInstance("", "");
                break;

        }
        callFragment(fragment);
    }

    private void callFragment(Fragment fragment) {
        ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void showNoListAvail() {

    }


    private void showSnackbar(String s) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logs.d("CYCLE : Detach()");

        if (mContext != null)
            mContext = null;
    }

    @Override
    public void onNBListAvailable(List<NoticeBoardModel> o) {
        List<NoticeBoardModel> list = new ArrayList<>(o);

        List<Integer> synclistId = MyApplication.getInstance().setNBListToArray();
        for (NoticeBoardModel model : o) {
                if (synclistId.contains(model.getPkid())) {
                    list.remove(model);
                }
            }

        adapter.addItems(list);
    }

    @Override
    public void onNoMoreList() {
//        adapter.removeFooter();
    }

    @Override
    public void onShowPaginationLoading(boolean show) {
        if (adapter == null)
            return;
        if (show) {
            adapter.addFooter();
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.addFooter();
//                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
//                }
//            });
        } else {
            adapter.removeFooter();
            adapter.notifyItemRemoved(adapter.getModelSize());
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.removeFooter();
//                    adapter.notifyItemRemoved(adapter.getModelSize());
////                    adapter.notifyItemRemoved(t);
////                    adapter.notifyItemRangeChanged(t, adapter.getItemCount());
//
//                }
//            });

        }
    }

    @Override
    public void onPaginationError(boolean show) {

    }


    @Override
    public void onNBItemClicked(int position, NoticeBoardModel model) {
        Fragment fragment = NoticeBoardDeatilsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(NoticeBoardDeatilsFragment.class.getSimpleName()).commit();


    }

    @Override
    public void onNBItemDeleteClicked(int position, NoticeBoardModel model) {
        adapter.removeItem(position);

        MyApplication.getInstance().deleteNoticeItemFromServer(getString(R.string.url_delete_notice), model.getPkid());
        MyApplication.getInstance().setNbID(null);
    }


}
