package emerge.project.mr_indoscan_admin.ui.activity.summeries;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsInteractor;
import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsInteractorImpil;
import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsPresenter;
import emerge.project.mr_indoscan_admin.ui.activity.products.assign_products.AssignProductsView;
import emerge.project.mr_indoscan_admin.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_admin.utils.entittes.Navigation;
import emerge.project.mr_indoscan_admin.utils.entittes.Principles;
import emerge.project.mr_indoscan_admin.utils.entittes.Products;
import emerge.project.mr_indoscan_admin.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SummerisPresenterImpli implements SummerisPresenter,
        SummerisInteractor.OnDetailsSummaryFinishedListener,
        SummerisInteractor.OnRepsFinishedListener{


    private SummerisView summerisView;
    SummerisInteractor summerisInteractor;


    public SummerisPresenterImpli(SummerisView view) {
        this.summerisView = view;
        this.summerisInteractor = new SummerisInteractorImpil();

    }



    @Override
    public void getDetailsSummary(Context con,int userid) {
        summerisInteractor.getDetailsSummary(con,userid,this);
    }


    @Override
    public void detailsSummaryList(DetailsSummary list) {
        summerisView.detailsSummaryList(list);
    }

    @Override
    public void detailsSummaryFail(String failMsg) {
        summerisView.detailsSummaryFail( failMsg);
    }

    @Override
    public void detailsSummaryNetworkFail() {
        summerisView.detailsSummaryNetworkFail();
    }




    @Override
    public void getReps(Context context) {
        summerisInteractor.getReps(context,this);
    }


    @Override
    public void repList(ArrayList<Rep> repArrayList, ArrayList<String> repnames) {
        summerisView.repList( repArrayList,  repnames);
    }

    @Override
    public void repsGetingFail(String failMsg) {
        summerisView.repsGetingFail(failMsg);
    }

    @Override
    public void repsGetingNetworkFail() {
        summerisView.repsGetingNetworkFail();
    }
}
