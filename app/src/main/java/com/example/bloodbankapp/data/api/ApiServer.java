package com.example.bloodbankapp.data.api;
import com.example.bloodbankapp.data.model.bloodtyps.BloodTyps;
import com.example.bloodbankapp.data.model.categories.Categories;
import com.example.bloodbankapp.data.model.cities.Cities;
import com.example.bloodbankapp.data.model.contact.Contact;
import com.example.bloodbankapp.data.model.donationRequestNotifications.DonationRequestNotifications;
import com.example.bloodbankapp.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbankapp.data.model.favoritposts.FavoritPosts;
import com.example.bloodbankapp.data.model.favourite.FavouriteModel;
import com.example.bloodbankapp.data.model.generate.GovernoratesAndBloodTypesModel;
import com.example.bloodbankapp.data.model.getallposts.GetAllPosts;
import com.example.bloodbankapp.data.model.getdataprofil.GetDataProfil;
import com.example.bloodbankapp.data.model.governorates.Governorates;
import com.example.bloodbankapp.data.model.login.Login;
import com.example.bloodbankapp.data.model.new_password.NewPasswordModel;
import com.example.bloodbankapp.data.model.notifications.Notifications;
import com.example.bloodbankapp.data.model.notificationsSettings.NotificationsSettings;
import com.example.bloodbankapp.data.model.notifications_count.NotificationsCount;
import com.example.bloodbankapp.data.model.posts.PostsModel;
import com.example.bloodbankapp.data.model.postsdetail.PostsDetail;
import com.example.bloodbankapp.data.model.postsfilter.PostsFilter;
import com.example.bloodbankapp.data.model.register.Register;
import com.example.bloodbankapp.data.model.register_token.RegisterToken;
import com.example.bloodbankapp.data.model.reset.ResetModel;
import com.example.bloodbankapp.data.model.updateprofil.UpdateProfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {

    @POST("login")
    @FormUrlEncoded
    Call<Login> onLogin(@Field("phone") String phone, @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<Register> onRegister(@Field("name") String name, @Field("email") String email
            , @Field("birth_date") String birth_date, @Field("city_id") int city_id
            , @Field("phone") String phone, @Field("donation_last_date") String donation_last_date
            , @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                              @Field("blood_type_id") long blood_type_id);

    @POST("profile")
    @FormUrlEncoded
    Call<UpdateProfil> onUpdate(@Field("name") String name, @Field("email") String email
            , @Field("birth_date") String birth_date, @Field("city_id") int city_id
            , @Field("phone") String phone, @Field("donation_last_date") String donation_last_date
            , @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                                @Field("blood_type_id") long blood_type_id, @Field("api_token") String api_token);

    @POST("profile")
    @FormUrlEncoded
    Call<GetDataProfil> getProfile(@Field("api_token") String api_token);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetModel> resetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPasswordModel> newPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation
            , @Field("pin_code") String pin_code, @Field("phone") String phone);

    @GET("governorates")
    Call<Governorates> getGovernorate();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") int governorate_id);


    @GET("governorates")
    Call<GovernoratesAndBloodTypesModel> getGovernorate1();

    @GET("cities")
    Call<GovernoratesAndBloodTypesModel> getCities1(@Query("governorate_id") int governorate_id);
    @GET("blood-types")
    Call<GovernoratesAndBloodTypesModel> getBlood_types1();



    @GET("blood-types")
    Call<BloodTyps> getBlood_types();

    @GET("posts")
    Call<GetAllPosts> getPosts(@Query("api_token") String api_token, @Query("page") int page);
    @GET("post")
    Call<PostsDetail> getPostDetail(@Query("api_token") String api_token, @Query("post_id") String post_id, @Query("page") int page);

    @GET("posts")
    Call<PostsFilter> getPostsFilter(@Query("api_token") String api_token, @Query("page") int page
            , @Query("keyword") String keyword
            , @Query("category_id") Integer category_id);

    @GET("categories")
    Call<Categories> getCategories();

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<FavoritPosts> getFavourite(@Field("post_id") int post_id, @Field("api_token") String api_token);


    @GET("my-favourites")
    Call<GetAllPosts> getMyFavourite(@Query("api_token") String api_token);


    @GET("donation-requests")
    Call<DonationRequestsModel> getDonationRequests(@Query("api_token") String api_token, @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequestsModel> getDonationRequestsFilter(@Query("api_token") String api_token
            , @Query("blood_type_id") String blood_type_id, @Query("governorate_id") long governorate_id, @Query("page") int page);

    @GET("donation-request")
    Call<DonationRequestNotifications> getAllDonationDisplay(@Query("api_token") String api_token, @Query("donation_id") int donation_id);




    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestsModel> CreateDonationRequests(@Field("api_token") String api_token, @Field("patient_name") String patient_name
            , @Field("patient_age") String patient_age, @Field("blood_type_id") long blood_type_id, @Field("bags_num") int bags_num,
                                                       @Field("hospital_name") String hospital_name, @Field("hospital_address") String hospital_address, @Field("city_id") int city_id
            , @Field("phone") String phone, @Field("notes") String notes, @Field("latitude") double latitude, @Field("longitude") double longitude);


    @GET("notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> getNotificationsSettings(@Field("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> ChangeNotificationsSettings(@Field("api_token") String api_token,
                                                            @Field("governorates[]") List<Integer> governorates,
                                                            @Field("blood_types[]") List<Integer> blood_types);

    @POST("contact")
    @FormUrlEncoded
    Call<Contact> SendContact(@Field("api_token") String api_token
            , @Field("title") String title, @Field("message") String message);

    @GET("notifications-count")
    Call<NotificationsCount> getNotificationsCount(@Query("api_token") String api_token);



    @POST("register-token")
    @FormUrlEncoded
    Call<RegisterToken>RegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

    @POST("remove-token")
    @FormUrlEncoded
    Call<RegisterToken>RemoveToken(@Field("token") String token
            , @Field("api_token") String api_token);

}
