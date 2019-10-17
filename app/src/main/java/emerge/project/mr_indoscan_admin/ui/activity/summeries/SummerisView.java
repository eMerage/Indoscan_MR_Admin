package emerge.project.mr_indoscan_admin.ui.activity.summeries;


import java.util.ArrayList;

import emerge.project.mr_indoscan_admin.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_admin.utils.entittes.Navigation;
import emerge.project.mr_indoscan_admin.utils.entittes.Principles;
import emerge.project.mr_indoscan_admin.utils.entittes.Products;
import emerge.project.mr_indoscan_admin.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SummerisView {


    void detailsSummaryList(DetailsSummary list);

    void detailsSummaryFail(String failMsg);

    void detailsSummaryNetworkFail();



        void repList(ArrayList<Rep> repArrayList,ArrayList<String> repnames);
        void repsGetingFail(String failMsg);
        void repsGetingNetworkFail();




}
