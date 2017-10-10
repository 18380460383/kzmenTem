package com.handmark.pulltorefresh.library;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建者：Administrator
 * 时间：2016/4/22
 * 功能描述：
 */
public class PullToRefreshStaggeredGridLayout extends PullToRefreshBase<RecyclerView>{

    private int up;
    private float y;
    private BackHUa backHUa;

    public void setBackHUa(BackHUa backHUa) {
        this.backHUa = backHUa;
    }

    public PullToRefreshStaggeredGridLayout(Context context) {
        super(context);
    }

    public PullToRefreshStaggeredGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    private RecyclerView recyclerView;
    private boolean isScrollOnHeader = true;
    private boolean isScrollOnFooter = false;
    @Override
   protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.straggereddGridLayout);
        final StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastVisibleItem;
            int[] fistVisibleItem;
            int y=0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                isScrollOnHeader=false;
                isScrollOnFooter=false;
                if(newState==RecyclerView.SCROLL_STATE_DRAGGING){
                    PullToRefreshStaggeredGridLayout.this.setMode(Mode.BOTH);

                    if (null != fistVisibleItem) {
                        isScrollOnHeader = (0 == fistVisibleItem[0]&&y<0)|1 == fistVisibleItem[0]&&y<0;
                    }else{
                        isScrollOnHeader=false;
                    }

                if (null != lastVisibleItem) {
                    boolean isLast=false;
                    if(lastVisibleItem.length>1){
                        System.out.println(mLayoutManager.getItemCount()+""+lastVisibleItem[0]+""+lastVisibleItem[1]);
                        isLast=((mLayoutManager.getItemCount() - 1 )== lastVisibleItem[1])|(-1==lastVisibleItem[1])|((mLayoutManager.getItemCount() - 1 )== lastVisibleItem[0]);

                    }else{
                        System.out.println(lastVisibleItem[0]);
                        isLast=(mLayoutManager.getItemCount() - 1) == lastVisibleItem[0];
                    }
                    if(isLast&&y>0){
                        isLast=true;
                    }else{
                        isLast=false;
                    }
                    isScrollOnFooter = isLast;
                }else{
                    isScrollOnFooter=false;
                }
                }else{
                    isScrollOnHeader=false;
                    isScrollOnFooter=false;
                    if(newState==RecyclerView.SCROLL_STATE_SETTLING){
                        PullToRefreshStaggeredGridLayout.this.setMode(Mode.DISABLED);
                    }else{
                        PullToRefreshStaggeredGridLayout.this.setMode(Mode.BOTH);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                fistVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPositions(new int[2]);
                lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPositions(new int[2]);
                    y=dy;
            }

        });
        return recyclerView;
    }




    @Override
    protected boolean isReadyForPullStart() {

        return isScrollOnHeader;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isScrollOnFooter;
    }
    public interface  BackHUa{
        int getUp();
    }
}