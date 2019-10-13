package com.example.bloodbankapp.ui.fragment.home.articalposts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.ArticlesAdapterRecycler;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.favoritposts.DatumFavorit;
import com.example.bloodbankapp.data.model.favoritposts.FavoritPosts;
import com.example.bloodbankapp.data.model.getallposts.DatumGetAllPosts;
import com.example.bloodbankapp.data.model.getallposts.GetAllPosts;
import com.example.bloodbankapp.data.model.posts.DataContentPostModel;
import com.example.bloodbankapp.data.model.posts.PostsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;

public class ArticlesFavouriteFragment extends Fragment {

    @BindView(R.id.articlesFavourite_Fragment_ShowPost_RecyclerView)
    RecyclerView articlesFavouriteFragmentShowPostRecyclerView;

    ProgressBar articlesFavouriteFragmentProgBar;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArticlesAdapterRecycler articlesAdapterRecycler;

    private ApiServer apiServer;
    private View view;
    private ArrayList<DatumGetAllPosts> postsArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_articlers_favourite, container, false);

        unbinder = ButterKnife.bind(this, view);


        inti();
        articlesFavouriteFragmentProgBar.setVisibility(View.VISIBLE);

        getPosts();

        return view;

    }

    // initialize tools
    private void inti() {
        articlesFavouriteFragmentProgBar = view.findViewById(R.id.articles_Favourite_Fragment_ProgBar);


        postsArrayList = new ArrayList<>();
        apiServer = RetrofitClient.getClient().create(ApiServer.class);

        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.favorite));

    }


    // get all  post
    private void getPosts() {
        try {
            articlesFavouriteFragmentProgBar.setVisibility(View.VISIBLE);
            // get  data  post
            apiServer.getMyFavourite(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<GetAllPosts>() {
                @Override
                public void onResponse(Call<GetAllPosts> call, Response<GetAllPosts> response) {
                    Log.d("response BloodT ", response.body().getMsg());

                    try {
                        if (response.body().getStatus() == 1) {

                            postsArrayList.addAll(response.body().getData().getData());

                            articlesFavouriteFragmentShowPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            articlesAdapterRecycler = new ArticlesAdapterRecycler(postsArrayList, getActivity());
                            articlesFavouriteFragmentShowPostRecyclerView.setAdapter(articlesAdapterRecycler);

                        } else {

                            articlesFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<GetAllPosts> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
