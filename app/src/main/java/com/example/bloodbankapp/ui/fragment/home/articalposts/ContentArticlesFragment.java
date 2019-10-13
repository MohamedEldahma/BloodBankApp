package com.example.bloodbankapp.ui.fragment.home.articalposts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bloodbankapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.bloodbankapp.helper.HelperMathod.LodeImage;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;


public class ContentArticlesFragment extends Fragment {
    View view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.articles_Content_ShowImg)
    ImageView articlesContentShowImg;
    @BindView(R.id.articles_Content_TitleTxt)
    TextView articlesContentTitleTxt;
    @BindView(R.id.articles_Content_FavoriteImg)
    ImageView articlesContentFavoriteImg;
    Unbinder unbinder;
    @BindView(R.id.articles_ContentTxt)
    TextView articlesContentTxt;
    private boolean myCondition = true;
    private String getTitle, getContent, getThumbnailFullPath, getCategory;
    private boolean getIsFavourite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_content_articlers, container, false);

        unbinder = ButterKnife.bind(this, view);
//        getDataReturnDetails();
        return view;
    }

    private void getDataReturnDetails() {
        // get data Donation Requests Adapter Recycler
        Bundle bundle = getArguments();
        if (bundle != null) {
            getTitle = bundle.getString("getTitle");
            getContent = bundle.getString("getContent");
            getThumbnailFullPath = bundle.getString("getThumbnailFullPath");
            getIsFavourite = bundle.getBoolean("getIsFavourite");
            getCategory = bundle.getString("getCategory");

        }

        // add value tool bar
        ToolBar(getFragmentManager(),getActivity(),toolbar,getResources().getString(R.string.donation_request) + ":   " + getCategory);

        articlesContentTitleTxt.setText(getTitle);
        articlesContentTxt.setText(getContent);


        // if is check once or tow
        if (getIsFavourite) {
            // add icon favourite
            articlesContentFavoriteImg.setImageResource(R.drawable.icon_favorite);
        } else {
            // add icon un favourite
            articlesContentFavoriteImg.setImageResource(R.drawable.icon_un_favorite);

        }

        // method lode image view post
        LodeImage(getContext(), getThumbnailFullPath,
                articlesContentShowImg);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
