package com.example.bloodbankapp.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.favoritposts.FavoritPosts;
import com.example.bloodbankapp.data.model.getallposts.DatumGetAllPosts;
import com.example.bloodbankapp.ui.fragment.home.articalposts.ContentArticlesFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.LodeImage;
import static com.example.bloodbankapp.helper.HelperMathod.getStartFragments;

public class ArticlesAdapterRecycler extends RecyclerView.Adapter<ArticlesAdapterRecycler.ViewHolder> {


    private ArrayList<DatumGetAllPosts> postsArrayList;

    private ApiServer apiServer;
    private Activity activity;
    private boolean numFavorite = true;
    private boolean CheckFavorite = true;
    private int postion;

    public ArticlesAdapterRecycler(ArrayList<DatumGetAllPosts> postsArrayList, Activity activity) {
        this.postsArrayList = postsArrayList;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_adapter_articler_recycler_post, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.articlesAdapterTitleTxt.setText(postsArrayList.get(i).getTitle());
        // method lode image view post
        LodeImage(activity, postsArrayList.get(i).getThumbnailFullPath(),
                viewHolder.articlesAdapterShowImg, viewHolder.articlesAdapterLodeProgressBar);

        // set icon favourite
        if (postsArrayList.get(i).getIsFavourite()) {
            viewHolder.articlesAdapterFavoriteImg.setChecked(postsArrayList.get(i).getIsFavourite());
        }
        postion = i;

        // on click icon favourite if choose true or false
        viewHolder.articlesAdapterFavoriteImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(" un Liked", " un Liked");
                    // get data all favourite
                    getFavoriteApi(LoadData(activity, USER_API_TOKEN), postsArrayList.get(i).getId());
                } else {
                    //Show "Removed from favourite" toast
                    Log.d(" Liked", " Liked");
                    // get data all un favourite
                    getFavoriteApi(LoadData(activity, USER_API_TOKEN), postsArrayList.get(i).getId());
                }
            }
        });

        viewHolder.articlesAdapterShowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("getTitle", postsArrayList.get(i).getTitle());
                bundle.putString("getContent", postsArrayList.get(i).getContent());
                bundle.putString("getThumbnailFullPath", postsArrayList.get(i).getThumbnailFullPath());
                bundle.putBoolean("getIsFavourite", postsArrayList.get(i).getIsFavourite());
                bundle.putString("getCategory", postsArrayList.get(i).getCategory().getName());
                Fragment fragment = new ContentArticlesFragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) activity).getSupportFragmentManager(), R.id.ReplaceContentAll
                        , fragment);
            }
        });



    }

    // get data all  favourite
    private void getFavoriteApi(String loadData, final Integer id) {
        apiServer.getFavourite(id, loadData).enqueue(new Callback<FavoritPosts>() {
            @Override
            public void onResponse(Call<FavoritPosts> call, Response<FavoritPosts> response) {

                Log.d("getFavoriteApi", response.message());
                if (response.body().getStatus() == 1) {
                }
            }

            @Override
            public void onFailure(Call<FavoritPosts> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.articles_Adapter_ShowImg)
        ImageView articlesAdapterShowImg;
        @BindView(R.id.articlesAdapterTitleTxt)
        TextView articlesAdapterTitleTxt;
        @BindView(R.id.articles_Adapter_FavoriteImg)
        CheckBox articlesAdapterFavoriteImg;
        @BindView(R.id.articles_AdapterLode_ProgressBar)
        ProgressBar articlesAdapterLodeProgressBar;
        @BindView(R.id.relative_Layout)
        LinearLayout relativeLayout;
        @BindView(R.id.articles_Adapter_Content)
        ConstraintLayout articlesAdapterContent;
        @BindView(R.id.articlar_cardvie)
        CardView articlarCardvie;
        View view;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
