package com.kzmen.sczxjf.bean.returned;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ShopDetailsBean {

    /**
     * id : 1
     * type:0
     * title : 全棉四件套纯棉学生四件套单人双人床1.5米1.8米
     * score : 5000
     * money : 0
     * postage : 2900
     * stocks : 98
     * image : http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1eca852555.jpg
     * images : ["http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1eca852555.jpg","http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1ecae13890.jpg","http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1ecae5057b.jpg","http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1ecae8e5d5.jpg","http://192.168.0.111:9012/Uploads/Picture/2016-01-22/56a1ecaec2dae.jpg"]
     * content : <p>
     <strong>全棉12868 &nbsp;全棉活性四件套</strong>
     </p>
     <p>
     面料：100%全棉（活性印染不褪色 不起球）
     </p>
     <p>
     支数：40支 &nbsp; &nbsp; &nbsp; &nbsp;密度：12868
     </p>
     <p>
     <strong>规格：</strong>
     </p>
     <p>
     1.2米床： &nbsp; &nbsp; &nbsp; 被套160*210cm*1 &nbsp;床单160*230cm*1 枕套48*74cm*2
     </p>
     <p>
     1.5-1.8米床：被套200*230cm*1 &nbsp;床单230*250cm*1 枕套48*74cm*2
     </p>
     <p>
     洗涤方式：
     </p>
     <p>
     第一次洗涤记得用清水+盐 （不要加任何漂白剂），
     </p>
     <p>
     把浮色洗掉，而且加盐还可以固色哦！
     </p>
     <p>
     <strong>注意：</strong>
     </p>
     <p>
     亲们，第一次洗颜色深的床品会有一点点浮色哦，这是很正常的现象哦！
     </p>
     <p>
     再好的床品第一次都会有浮色的，不要误以为是褪色哦！
     </p>
     <p>
     另：第一次千万不要放在水里泡，或者用洗衣粉等漂白剂洗！
     </p>
     <p>
     这样更容易导致浮色加重！
     </p>
     * isexchange : 1
     * coupon : []
     */

    private String id;
    private String type;
    private String title;
    private int score;
    private double money;
    private double postage;
    private int stocks;
    private String image;
    private String content;
    private int isexchange;
    private List<String> images;
    private List<?> coupon;
    private String content_url;

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setPostage(double postage) {
        this.postage = postage;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsexchange(int isexchange) {
        this.isexchange = isexchange;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setCoupon(List<?> coupon) {
        this.coupon = coupon;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        df.setRoundingMode(RoundingMode.UP);

        return  df.format(money/100);
    }

    public String getPostage() {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        df.setRoundingMode(RoundingMode.UP);
        return df.format(postage/100);
    }

    public int getStocks() {
        return stocks;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public int getIsexchange() {
        return isexchange;
    }

    public List<String> getImages() {
        return images;
    }

    public List<?> getCoupon() {
        return coupon;
    }
    public String getTotalPrices(){
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        df.setRoundingMode(RoundingMode.UP);

        return  df.format((money+postage)/100);
    }
}
