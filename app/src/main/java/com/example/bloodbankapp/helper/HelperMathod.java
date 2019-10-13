package com.example.bloodbankapp.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.notifications_count.NotificationsCount;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;

public class HelperMathod {

    private static ProgressDialog checkDialog;
    private static String getNotificationsCount;

    // check length password
    public static boolean checkLengthPassword(String newPassword) {
        return newPassword.length() > 6;
    }
    public static void verbose(String message) {
        Log.v(TAG, message);

    }

    public static void error(String message) {
        Log.v(TAG, message);

    }

// check Correspond password  == ConfirmPassword

    public static boolean checkCorrespondPassword(String newPassword, String ConfirmPassword) {
        return newPassword.equals(ConfirmPassword);
    }

    // get start fragment
    public static void getStartFragments(FragmentManager supportFragmentManager, int ReplaceFragment, Fragment fragment) {

        supportFragmentManager.beginTransaction().replace(ReplaceFragment, fragment).addToBackStack(null).commit();

    }

    // lode image

    public static void LodeImage(Context context, String url, ImageView imageView) {

        Glide.with(context).load(url).error(R.drawable.ic_menu_camera).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(imageView);
    }


    public static void LodeImage(Context context, String url, ImageView imageView, final ProgressBar progressBar) {

        Glide.with(context).load(url).error(R.drawable.ic_menu_camera).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(imageView);
    }

    // tool bar
    public static void ToolBar(final FragmentManager supportFragmentManager, final Activity activity, Toolbar toolbar, String string) {


        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.dark));
        toolbar.setTitle(string);
        toolbar.setNavigationIcon(R.drawable.icon_back_menu);
        toolbar.inflateMenu(R.menu.tab_menu);

        MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_notify);

        View actionView = MenuItemCompat.getActionView(menuItem);
        TextView textCartItemCount = actionView.findViewById(R.id.cart_badge);
        if (getNotificationsCount(activity)!=null) {
            if (!getNotificationsCount(activity).equals("null")) {
                textCartItemCount.setText(getNotificationsCount(activity));

            }
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();

            }
        });
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getStartFragments(supportFragmentManager, R.id.ReplaceContentAll, new NotificationsFragment());

            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_notify:
                       // getStartFragments(supportFragmentManager, R.id.ReplaceContentAll, new NotificationsFragment());
                        break;

                }
                return false;
            }
        });
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public static void showProgressDialog(Activity activity, String title) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setMessage(title);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
            checkDialog.show();
        } catch (Exception e) {
        }
    }

    public static void dismissProgressDialog() {
        try {
            if (checkDialog != null && checkDialog.isShowing()) {
                checkDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }


    // get Notifications Count
    public static String getNotificationsCount(final Activity activity) {
        try {
            ApiServer apiServer = RetrofitClient.getClient().create(ApiServer.class);
            // get  PaginationData  post
            apiServer.getNotificationsCount(LoadData(activity, USER_API_TOKEN)).enqueue(new Callback<NotificationsCount>() {

                @Override
                public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {

//                    Log.d(" notifications 5", response.body().getMsg());

                    if (response.body().getStatus() == 1) {
                        getNotificationsCount = String.valueOf(response.body().getData().getNotificationsCount());

                    } else {
                        Toast.makeText(activity, "Not PaginationData ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificationsCount> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
        return getNotificationsCount;
    }
}
