package emerge.project.mr_indoscan_admin.ui.activity.summeries;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_admin.utils.entittes.Principles;
import emerge.project.mr_indoscan_admin.utils.entittes.Products;
import emerge.project.mr_indoscan_admin.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SummerisPresenter {


    void getReps(Context context);


    void getDetailsSummary(Context context,int userid);



}
