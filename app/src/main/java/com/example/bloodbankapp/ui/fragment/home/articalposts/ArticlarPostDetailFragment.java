package com.example.bloodbankapp.ui.fragment.home.articalposts;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.local.SharedPreferncesManger;
import com.example.bloodbankapp.data.model.postsdetail.PostsDetail;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlarPostDetailFragment extends Fragment {


    @BindView(R.id.article_image_view)
    ImageView articleImageView;
    @BindView(R.id.title_text_view)
    TextView titleTextView;
    @BindView(R.id.favorite_toggle_button)
    ToggleButton favoriteToggleButton;
    @BindView(R.id.article_text_view)
    TextView articleTextView;
//    ApiServer apiServer;
//    private String id_postdetail;

    public ArticlarPostDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =inflater.inflate(R.layout.fragment_articlar_post_detail, container, false);
//      apiServer = RetrofitClient.getClient().create(ApiServer.class);
//      id_postdetail = SharedPreferncesManger.LoadData(getActivity(),"id_postdetail");



      return  view;
    }



//    public void getPostDetail (){
//        apiServer.getPostDetail(USER_API_TOKEN,id_postdetail,1).enqueue(new Callback<PostsDetail>() {
//            @Override
//            public void onResponse(Call<PostsDetail> call, Response<PostsDetail> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<PostsDetail> call, Throwable t) {
//
//            }
//        });




//    }
}
