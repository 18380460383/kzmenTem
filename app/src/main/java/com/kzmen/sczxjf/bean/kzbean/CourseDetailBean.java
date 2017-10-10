package com.kzmen.sczxjf.bean.kzbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pjj18 on 2017/9/3.
 */

public class CourseDetailBean implements Serializable {


    /**
     * cid : 1
     * title : 大咖带你玩转信用卡，提升额度不是梦
     * type : 1
     * banner : http://api.kzmen.cn/Uploads/Picture/2017-08-17/5995b625982b8.png
     * isquestion : 1
     * isxiaojiang : 1
     * zans : 227
     * views : 623
     * share_title : 大咖带你玩转信用卡，提升额度不是梦分享标题
     * share_des : 大咖带你玩转信用卡，提升额度不是梦分享摘要
     * share_image : http://api.kzmen.cn/Uploads/Picture/2017-08-16/59945d59ef2a1.png
     * share_linkurl : https://www.baidu.com
     * tid : 1
     * tid_title : 财经专家
     * name : 易军
     * describe : 易军，财经专家，从上金融行业数年，带领团队潜心研究信用卡领域，做过多年信用卡知识教学，有着非常丰富的行业经验与实操技巧，多年的工作中他发现太多的人对信用卡一无所知，盲目套现，投资失败，成为卡奴，他决心将自己多年所学倾心教给大家！
     * course_time : 1
     * course_stage : 5
     * course_section : 3
     * iscollect : 0
     * iszan : 0
     * questions_money : 2
     * questions_button : 2元向讲师提问
     * questions_desc : 精华提问，还会奖励收听收入的50%分成！
     * stage_list : [{"sid":"1","stage_name":"阶段一","sort":"1","isunlock":1,"kejian_list":[{"chapter_id":"1","chapter_name":"导语","kejian":[{"id":"7","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]},{"chapter_id":"2","chapter_name":"提升额度对我的帮助","kejian":[{"id":"1","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"},{"id":"10","title":"课件课件课件课件课件课件课件课件课件课件","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]}],"xiaojiang_list":[{"id":"2","title":"为什么要办信用卡","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997cceccf4d1.wav","media_time":"1","charge_type":"0","charge_start":"0","charge_end":"0"},{"id":"4","title":"信用卡为什么总是批不下来？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1","charge_type":"2","charge_start":"1504022400","charge_end":"1504800000"}]},{"sid":"2","stage_name":"阶段二","sort":"2","isunlock":1,"kejian_list":[],"xiaojiang_list":[]},{"sid":"3","stage_name":"阶段三","sort":"3","isunlock":1,"kejian_list":[],"xiaojiang_list":[]},{"sid":"4","stage_name":"阶段四","sort":"4","isunlock":1,"kejian_list":[],"xiaojiang_list":[]},{"sid":"5","stage_name":"阶段五","sort":"5","isunlock":1,"kejian_list":[],"xiaojiang_list":[]}]
     */

    private String cid;
    private String title;
    private String type;
    private String banner;
    private String isquestion;
    private String isxiaojiang;
    private String zans;
    private String views;
    private String share_title;
    private String share_des;
    private String share_image;
    private String share_linkurl;
    private String tid;
    private String tid_title;
    private String name;
    private String describe;
    private int course_time;
    private int course_stage;
    private int course_section;
    private int iscollect;
    private int iszan;
    private String questions_money;
    private String questions_button;
    private String questions_desc;
    private String unlock_desc;
    private String unlock_money;
    private String isunlock;
    private List<StageListBean> stage_list;

    public String getIsunlock() {
        return isunlock;
    }

    public void setIsunlock(String isunlock) {
        this.isunlock = isunlock;
    }

    public String getUnlock_desc() {
        return unlock_desc;
    }

    public void setUnlock_desc(String unlock_desc) {
        this.unlock_desc = unlock_desc;
    }

    public String getUnlock_money() {
        return unlock_money;
    }

    public void setUnlock_money(String unlock_money) {
        this.unlock_money = unlock_money;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIsquestion() {
        return isquestion;
    }

    public void setIsquestion(String isquestion) {
        this.isquestion = isquestion;
    }

    public String getIsxiaojiang() {
        return isxiaojiang;
    }

    public void setIsxiaojiang(String isxiaojiang) {
        this.isxiaojiang = isxiaojiang;
    }

    public String getZans() {
        return zans;
    }

    public void setZans(String zans) {
        this.zans = zans;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_des() {
        return share_des;
    }

    public void setShare_des(String share_des) {
        this.share_des = share_des;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getShare_linkurl() {
        return share_linkurl;
    }

    public void setShare_linkurl(String share_linkurl) {
        this.share_linkurl = share_linkurl;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTid_title() {
        return tid_title;
    }

    public void setTid_title(String tid_title) {
        this.tid_title = tid_title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCourse_time() {
        return course_time;
    }

    public void setCourse_time(int course_time) {
        this.course_time = course_time;
    }

    public int getCourse_stage() {
        return course_stage;
    }

    public void setCourse_stage(int course_stage) {
        this.course_stage = course_stage;
    }

    public int getCourse_section() {
        return course_section;
    }

    public void setCourse_section(int course_section) {
        this.course_section = course_section;
    }

    public int getIscollect() {
        return iscollect;
    }

    public void setIscollect(int iscollect) {
        this.iscollect = iscollect;
    }

    public int getIszan() {
        return iszan;
    }

    public void setIszan(int iszan) {
        this.iszan = iszan;
    }

    public String getQuestions_money() {
        return questions_money;
    }

    public void setQuestions_money(String questions_money) {
        this.questions_money = questions_money;
    }

    public String getQuestions_button() {
        return questions_button;
    }

    public void setQuestions_button(String questions_button) {
        this.questions_button = questions_button;
    }

    public String getQuestions_desc() {
        return questions_desc;
    }

    public void setQuestions_desc(String questions_desc) {
        this.questions_desc = questions_desc;
    }

    public List<StageListBean> getStage_list() {
        return stage_list;
    }

    public void setStage_list(List<StageListBean> stage_list) {
        this.stage_list = stage_list;
    }

    public static class StageListBean implements Serializable {
        /**
         * sid : 1
         * stage_name : 阶段一
         * sort : 1
         * isunlock : 1
         * kejian_list : [{"chapter_id":"1","chapter_name":"导语","kejian":[{"id":"7","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]},{"chapter_id":"2","chapter_name":"提升额度对我的帮助","kejian":[{"id":"1","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"},{"id":"10","title":"课件课件课件课件课件课件课件课件课件课件","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]}]
         * xiaojiang_list : [{"id":"2","title":"为什么要办信用卡","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997cceccf4d1.wav","media_time":"1","charge_type":"0","charge_start":"0","charge_end":"0"},{"id":"4","title":"信用卡为什么总是批不下来？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1","charge_type":"2","charge_start":"1504022400","charge_end":"1504800000"}]
         */
        private String sid;
        private String stage_name;
        private String sort;
        private String unlock_desc;
        private String unlock_money;
        private String unlock_day;
        private int isunlock;
        private List<KejianListBean> kejian_list;
        private List<XiaojiangListBean> xiaojiang_list;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getStage_name() {
            return stage_name;
        }

        public String getUnlock_desc() {
            return unlock_desc;
        }

        public void setUnlock_desc(String unlock_desc) {
            this.unlock_desc = unlock_desc;
        }

        public String getUnlock_money() {
            return unlock_money;
        }

        public void setUnlock_money(String unlock_money) {
            this.unlock_money = unlock_money;
        }

        public String getUnlock_day() {
            return unlock_day;
        }

        public void setUnlock_day(String unlock_day) {
            this.unlock_day = unlock_day;
        }

        public void setStage_name(String stage_name) {
            this.stage_name = stage_name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getIsunlock() {
            return isunlock;
        }

        public void setIsunlock(int isunlock) {
            this.isunlock = isunlock;
        }

        public List<KejianListBean> getKejian_list() {
            return kejian_list;
        }

        public void setKejian_list(List<KejianListBean> kejian_list) {
            this.kejian_list = kejian_list;
        }

        public List<XiaojiangListBean> getXiaojiang_list() {
            return xiaojiang_list;
        }

        public void setXiaojiang_list(List<XiaojiangListBean> xiaojiang_list) {
            this.xiaojiang_list = xiaojiang_list;
        }

        public static class KejianListBean implements Serializable {
            /**
             * chapter_id : 1
             * chapter_name : 导语
             * kejian : [{"id":"7","title":"为何要做信用卡的额度提升？","media":"http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3","media_time":"1"}]
             */

            private String chapter_id;
            private String chapter_name;
            private List<KejianBean> kejian;

            public String getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(String chapter_id) {
                this.chapter_id = chapter_id;
            }

            public String getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String chapter_name) {
                this.chapter_name = chapter_name;
            }

            public List<KejianBean> getKejian() {
                return kejian;
            }

            public void setKejian(List<KejianBean> kejian) {
                this.kejian = kejian;
            }

            public static class KejianBean implements Serializable {
                /**
                 * id : 7
                 * title : 为何要做信用卡的额度提升？
                 * media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3
                 * media_time : 1
                 */

                private String id;
                private String title;
                private String media;
                private String media_time;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMedia() {
                    return media;
                }

                public void setMedia(String media) {
                    this.media = media;
                }

                public String getMedia_time() {
                    return media_time;
                }

                public void setMedia_time(String media_time) {
                    this.media_time = media_time;
                }
            }
        }

        public static class XiaojiangListBean implements Serializable {
            /**
             * id : 2
             * title : 为什么要办信用卡
             * media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997cceccf4d1.wav
             * media_time : 1
             * charge_type : 0
             * charge_start : 0
             * charge_end : 0
             */

            private String id;
            private String title;
            private String media;
            private String media_time;
            private String charge_type;
            private String charge_start;
            private String charge_end;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMedia() {
                return media;
            }

            public void setMedia(String media) {
                this.media = media;
            }

            public String getMedia_time() {
                return media_time;
            }

            public void setMedia_time(String media_time) {
                this.media_time = media_time;
            }

            public String getCharge_type() {
                return charge_type;
            }

            public void setCharge_type(String charge_type) {
                this.charge_type = charge_type;
            }

            public String getCharge_start() {
                return charge_start;
            }

            public void setCharge_start(String charge_start) {
                this.charge_start = charge_start;
            }

            public String getCharge_end() {
                return charge_end;
            }

            public void setCharge_end(String charge_end) {
                this.charge_end = charge_end;
            }

            @Override
            public String toString() {
                return "XiaojiangListBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", media='" + media + '\'' +
                        ", media_time='" + media_time + '\'' +
                        ", charge_type='" + charge_type + '\'' +
                        ", charge_start='" + charge_start + '\'' +
                        ", charge_end='" + charge_end + '\'' +
                        '}';
            }
        }
    }
}
