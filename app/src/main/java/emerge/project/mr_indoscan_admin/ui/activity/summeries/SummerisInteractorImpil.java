package emerge.project.mr_indoscan_admin.ui.activity.summeries;


import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mr_indoscan_admin.BuildConfig;
import emerge.project.mr_indoscan_admin.R;
import emerge.project.mr_indoscan_admin.services.api.ApiClient;
import emerge.project.mr_indoscan_admin.services.api.ApiInterface;
import emerge.project.mr_indoscan_admin.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsInteractor;
import emerge.project.mr_indoscan_admin.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_admin.utils.entittes.Navigation;
import emerge.project.mr_indoscan_admin.utils.entittes.Principles;
import emerge.project.mr_indoscan_admin.utils.entittes.Products;
import emerge.project.mr_indoscan_admin.utils.entittes.Rep;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SummerisInteractorImpil implements SummerisInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Products> addedproductsList = new ArrayList<Products>();

    ArrayList<Rep> repList;



    @Override
    public void getReps(Context context, final OnRepsFinishedListener onRepsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onRepsFinishedListener.repsGetingNetworkFail();
        } else {
            try {
                final ArrayList<String> repsNameList = new ArrayList<String>();
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                repList.add(new Rep(0,"All",true));

                apiService.getMRByAdmin(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Rep>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Rep> rep) {
                                repList = rep;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onRepsFinishedListener.repsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {

                                for (Rep rep : repList) {
                                    repsNameList.add(rep.getName());
                                }
                                onRepsFinishedListener.repList(repList,repsNameList);


                            }
                        });
            } catch (Exception ex) {
                onRepsFinishedListener.repsGetingFail("Something went wrong, Please try again");
            }

        }


    }

    @Override
    public void getDetailsSummary(Context context,int userid,OnDetailsSummaryFinishedListener onDetailsSummaryFinishedListener) {

        DetailsSummary detailsSummary = new DetailsSummary();
        onDetailsSummaryFinishedListener.detailsSummaryList(detailsSummary);


    }
}


