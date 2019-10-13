package com.example.bloodbankapp.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.notifications_count.NotificationsCount;
import com.example.bloodbankapp.data.model.register_token.RegisterToken;
import com.example.bloodbankapp.helper.HelperMathod;
import com.example.bloodbankapp.ui.fragment.home.nvigationhome.AboutAppFragment;
import com.example.bloodbankapp.ui.fragment.home.donationrequest.AddDonationRequestFragment;
import com.example.bloodbankapp.ui.fragment.home.articalposts.ArticlesFavouriteFragment;
import com.example.bloodbankapp.ui.fragment.home.nvigationhome.CallMeFragment;
import com.example.bloodbankapp.ui.fragment.HomeFragment;
import com.example.bloodbankapp.ui.fragment.NotificationsFragment;
import com.example.bloodbankapp.ui.fragment.home.nvigationhome.SettingNotificationFragment;
import com.example.bloodbankapp.ui.fragment.home.nvigationhome.UpdateDataUserFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.clean;


public class HomeNavgation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    LinearLayout contentHomeReplace;
    private ApiServer apiServer;
    private String getNotificationsCount;
    private TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inti();


    }

    private void inti() {
        apiServer = RetrofitClient.getClient().create(ApiServer.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start fragment Home post Donation
                HelperMathod. getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new AddDonationRequestFragment());
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_draweropen, R.string.navigation_drawerclose);

        drawer.addDrawerListener(toggle);

        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);

        // start fragment Home post Donation
        HelperMathod.getStartFragments(getSupportFragmentManager(), R.id.content_Home_Replace, new HomeFragment());
        contentHomeReplace = findViewById(R.id.content_Home_Replace);

        /// send Register Token
        RegisterToken();
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            contentHomeReplace.setEnabled(true);
            contentHomeReplace.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_setting);
        View view = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = view.findViewById(R.id.cart_badge);
        // get count number notification

        getNotificationsCount();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            HelperMathod.  getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new NotificationsFragment());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_data) {
            // start fragment Home post Donation
            HelperMathod. getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new UpdateDataUserFragment());
            contentHomeReplace.setEnabled(false);
            contentHomeReplace.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_setting_notify) {
            // start fragment Notifications
         HelperMathod. getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new SettingNotificationFragment());
            contentHomeReplace.setEnabled(false);
            contentHomeReplace.setVisibility(View.INVISIBLE);


        } else if (id == R.id.nav_my_favorite) {
            // start fragment Home    my_favorite
            HelperMathod. getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new ArticlesFavouriteFragment());
            contentHomeReplace.setEnabled(false);
            contentHomeReplace.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_my_home) {
            // start fragment Home post Donation
            HelperMathod.getStartFragments(getSupportFragmentManager(), R.id.content_Home_Replace, new HomeFragment());
        } else if (id == R.id.nav_details) {

        } else if (id == R.id.nav_call_me) {
            // start fragment Home about
            HelperMathod.getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new CallMeFragment());
            contentHomeReplace.setEnabled(false);
            contentHomeReplace.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_about) {
            // start fragment Home about
            HelperMathod.getStartFragments(getSupportFragmentManager(), R.id.ReplaceContentAll, new AboutAppFragment());

            contentHomeReplace.setEnabled(false);
            contentHomeReplace.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_exit) {
            Toast.makeText(getApplication(), "6", Toast.LENGTH_LONG).show();
            clean(HomeNavgation.this);
            startActivity(new Intent(HomeNavgation.this, SplashActivity.class));

            RemoveToken();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //send Register Token
    public void RegisterToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences preferences = getSharedPreferences("Blood", 0);
        Log.d("USER_API_TOKEN", LoadData(HomeNavgation.this, USER_API_TOKEN));


        // get  PaginationData governorates
        apiServer.RegisterToken(refreshedToken,
                LoadData(HomeNavgation.this, USER_API_TOKEN), "android").enqueue(new Callback<RegisterToken>() {
            @Override
            public void onResponse(Call<RegisterToken> call, Response<RegisterToken> response) {

                try {
                    if (response.body().getStatus() == 1) {

                   Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                 Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<RegisterToken> call, Throwable t) {

            }
        });

    }


    //send Remove Token
    public void RemoveToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        // get  PaginationData governorates
        apiServer.RemoveToken(refreshedToken,
                LoadData(HomeNavgation.this, USER_API_TOKEN)).enqueue(new Callback<RegisterToken>() {
            @Override
            public void onResponse(Call<RegisterToken> call, Response<RegisterToken> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        //  Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<RegisterToken> call, Throwable t) {

            }
        });

    }


    // get Notifications Count
    public void getNotificationsCount() {
        try {
            ApiServer apiServer = RetrofitClient.getClient().create(ApiServer.class);
            // get  PaginationData  post
            apiServer.getNotificationsCount(LoadData(HomeNavgation.this, USER_API_TOKEN)).enqueue(new Callback<NotificationsCount>() {

                @Override
                public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {

//                    Log.d(" notifications 5", response.body().getMsg());

                    if (response.body().getStatus() == 1) {

                        getNotificationsCount = String.valueOf(response.body().getData().getNotificationsCount());

                        textCartItemCount.setText(getNotificationsCount);


                    } else {
                        Toast.makeText(HomeNavgation.this, "Not PaginationData ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificationsCount> call, Throwable t) {
                    Toast.makeText(HomeNavgation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
