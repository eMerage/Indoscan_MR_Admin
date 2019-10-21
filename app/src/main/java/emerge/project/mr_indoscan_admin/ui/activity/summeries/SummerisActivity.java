package emerge.project.mr_indoscan_admin.ui.activity.summeries;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_admin.BuildConfig;
import emerge.project.mr_indoscan_admin.R;
import emerge.project.mr_indoscan_admin.services.api.ApiClient;
import emerge.project.mr_indoscan_admin.services.api.ApiInterface;
import emerge.project.mr_indoscan_admin.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_admin.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_admin.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsActivity;
import emerge.project.mr_indoscan_admin.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_admin.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_admin.ui.adapters.summeries.RepsAdapter;
import emerge.project.mr_indoscan_admin.ui.adapters.summeries.TownsCoverdAdapter;
import emerge.project.mr_indoscan_admin.ui.adapters.summeries.VisitsDocsAdapter;
import emerge.project.mr_indoscan_admin.ui.adapters.summeries.VisitsPharmacyAdapter;
import emerge.project.mr_indoscan_admin.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_admin.utils.entittes.Doctor;
import emerge.project.mr_indoscan_admin.utils.entittes.Navigation;
import emerge.project.mr_indoscan_admin.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_admin.utils.entittes.Products;
import emerge.project.mr_indoscan_admin.utils.entittes.Rep;
import emerge.project.mr_indoscan_admin.utils.entittes.Towns;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SummerisActivity extends Activity implements SummerisView{


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;


    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    @BindView(R.id.recyclerview_rep)
    RecyclerView recyclerviewRep;

    @BindView(R.id.scrollView)
    ScrollView scrollView;


    @BindView(R.id.autoCompleteTextView_mr)
    AutoCompleteTextView autoCompleteTextViewMr;



    @BindView(R.id.progressBar2)
    ProgressBar includeProgres;


    NavigationAdapter navigationAdapter;



    public static String tokenID;
    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";

    ApiInterface apiService;

    ArrayList<Rep> allRepArray = new ArrayList<Rep>();


    SummerisPresenter summerisPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summeris);
        ButterKnife.bind(this);


        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        tokenID = BuildConfig.API_TOKEN_ID;
        apiService = ApiClient.getClient().create(ApiInterface.class);

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Location Assing", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Daily Summaries", R.drawable.ic_product_defult_small));

        navigationAdapter = new NavigationAdapter(this, navigationItems);
        listViewNavigation.setAdapter(navigationAdapter);

        summerisPresenter =  new SummerisPresenterImpli(this);



        ImageView ImageView1 = findViewById(R.id.ImageView1);
        final RelativeLayout relativeLayout2 = findViewById(R.id.relativeLayout2);
        RecyclerView recyclerviewTowns = findViewById(R.id.recyclerview_towns);


        ImageView ImageView2 = findViewById(R.id.ImageView2);
        final RelativeLayout relativeLayout3 = findViewById(R.id.relativeLayout3);
        RecyclerView recyclerviewDocs =findViewById(R.id.recyclerview_docs);



        ImageView ImageView3 = findViewById(R.id.ImageView3);
        final RelativeLayout relativeLayout4 = findViewById(R.id.relativeLayout4);
        RecyclerView recyclerviewPharmacy = findViewById(R.id.recyclerview_pharmacy);







        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout2.getVisibility() == View.VISIBLE) {
                    relativeLayout2.setVisibility(View.GONE);
                }else {
                    relativeLayout2.setVisibility(View.VISIBLE);
                }
            }
        });


        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout3.getVisibility() == View.VISIBLE) {
                    relativeLayout3.setVisibility(View.GONE);
                }else {
                    relativeLayout3.setVisibility(View.VISIBLE);
                }
            }
        });



        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout4.getVisibility() == View.VISIBLE) {
                    relativeLayout4.setVisibility(View.GONE);
                }else {
                    relativeLayout4.setVisibility(View.VISIBLE);
                }
            }
        });




        ArrayList<Towns> townsItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Colombo "+a;
            townsItems.add(new Towns(a,tw));
        }
        TownsCoverdAdapter townsCoverdAdapter =  new TownsCoverdAdapter(this,townsItems);
        recyclerviewTowns.setAdapter(townsCoverdAdapter);


        ArrayList<Doctor> doctorItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Doctor "+a;
            doctorItems.add(new Doctor(a,tw));
        }
        VisitsDocsAdapter visitsDocsAdapter =  new VisitsDocsAdapter(this,doctorItems);
        recyclerviewDocs.setAdapter(visitsDocsAdapter);



        ArrayList<Pharmacy> pharmacyItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Pharmacy "+a;
            pharmacyItems.add(new Pharmacy(a,tw));
        }
        VisitsPharmacyAdapter visitsPharmacyAdapter =  new VisitsPharmacyAdapter(this,pharmacyItems);
        recyclerviewPharmacy.setAdapter(visitsPharmacyAdapter);




    }


    @Override
    protected void onStart() {
        super.onStart();

       // includeProgres.setVisibility(View.VISIBLE);
      //  summerisPresenter.getReps(this);


        Toast.makeText(this, "This feature will be available soon", Toast.LENGTH_LONG).show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intentVisits = new Intent(SummerisActivity.this, VisitsActivity.class);
                    Bundle bndlanimationVisits = ActivityOptions.makeCustomAnimation(SummerisActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentVisits, bndlanimationVisits);
                    finish();

                    return true;
                case R.id.navigation_location:
                    Intent intentLocation = new Intent(SummerisActivity.this, LocationActivity.class);
                    Bundle bndlanimationLocation = ActivityOptions.makeCustomAnimation(SummerisActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentLocation, bndlanimationLocation);
                    finish();
                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoctors = new Intent(SummerisActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoctors = ActivityOptions.makeCustomAnimation(SummerisActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoctors, bndlanimationDoctors);
                    finish();
                    return true;
                case R.id.navigation_products:
                    Intent intentPro = new Intent(SummerisActivity.this, AssignProductsActivity.class);
                    Bundle bndlanimationPro = ActivityOptions.makeCustomAnimation(SummerisActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentPro, bndlanimationPro);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();


    }




    @Override
    public void detailsSummaryList(DetailsSummary list) {

    }

    @Override
    public void detailsSummaryFail(String failMsg) {

    }

    @Override
    public void detailsSummaryNetworkFail() {

    }

    @Override
    public void repList(ArrayList<Rep> repArrayList, ArrayList<String> repnames) {

        includeProgres.setVisibility(View.GONE);

        allRepArray = repArrayList;

        RepsAdapter assigendRepsAdapter = new RepsAdapter(this, repArrayList);
        recyclerviewRep.setAdapter(assigendRepsAdapter);
        scrollView.setVisibility(View.VISIBLE);


        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, repnames);
        autoCompleteTextViewMr.setAdapter(adapterList);

        autoCompleteTextViewMr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedItem = parent.getItemAtPosition(pos).toString();




            }
        });



    }

    @Override
    public void repsGetingFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                             summerisPresenter.getReps(SummerisActivity.this);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void repsGetingNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();

    }
}
