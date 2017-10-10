package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/2.
 */

public class RankListBean {
    private List<All_Rank> all_rank;
    private Me_Rank me_rank;

    public List<All_Rank> getAll_rank() {
        return all_rank;
    }

    public void setAll_rank(List<All_Rank> all_rank) {
        this.all_rank = all_rank;
    }

    public Me_Rank getMe_rank() {
        return me_rank;
    }

    public void setMe_rank(Me_Rank me_rank) {
        this.me_rank = me_rank;
    }
}
