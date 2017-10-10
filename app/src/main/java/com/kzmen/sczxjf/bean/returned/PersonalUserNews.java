package com.kzmen.sczxjf.bean.returned;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/4/26
 * 功能描述：
 */
public class PersonalUserNews {


    /**
     * pid : 293
     * projectname : 现金推广
     * imageurl : http://192.168.0.111:9012/Uploads/push/hdq_56e8b7b5324cd.jpg
     * projecturl : 123123123?uid=1775
     * type : 0
     * relay : 4
     * reward : 0.5元
     */

    private List<ProjectEntity> project;
    /**
     * nid : 21
     * typename : 美食
     * title : 人民网评：网民来自群众，网意反映民意
     * image : http://192.168.0.163:9012/Uploads/Picture/2016-04-26/571ed1ceacaba.jpg
     * relay : 4
     * opinions : {"A":{"opt_id":"A","opt_title":"网民来自群众","opt_nums":3,"opt_color":"#FF5D5D"},"B":{"opt_id":"B","opt_title":"网意反映民意","opt_nums":"2","opt_color":"#35C8F6"},"C":{"opt_id":"C","opt_title":"支持本人观点","opt_nums":"1","opt_color":"#5E6977"}}
     * collect : 5.8万
     * comment : 8
     * opinion : 6
     */

    private List<NewsEntity> news;

    public void setProject(List<ProjectEntity> project) {
        this.project = project;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public List<ProjectEntity> getProject() {
        return project;
    }

    public List<NewsEntity> getNews() {
        return news;
    }

    public static class ProjectEntity {
        private int pid;
        private String projectname;
        private String imageurl;
        private String projecturl;
        private String type;
        private int relay;
        private String reward;

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public void setProjecturl(String projecturl) {
            this.projecturl = projecturl;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRelay(int relay) {
            this.relay = relay;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public int getPid() {
            return pid;
        }

        public String getProjectname() {
            return projectname;
        }

        public String getImageurl() {
            return imageurl;
        }

        public String getProjecturl() {
            return projecturl;
        }

        public String getType() {
            return type;
        }

        public int getRelay() {
            return relay;
        }

        public String getReward() {
            return reward;
        }

        @Override
        public String toString() {
            return "ProjectEntity{" +
                    "pid=" + pid +
                    ", projectname='" + projectname + '\'' +
                    ", imageurl='" + imageurl + '\'' +
                    ", projecturl='" + projecturl + '\'' +
                    ", type='" + type + '\'' +
                    ", relay=" + relay +
                    ", reward='" + reward + '\'' +
                    '}';
        }
    }

    public static class NewsEntity {
        private int nid;
        private String typename;
        private String title;
        private String image;
        private int relay;//转发
        private String type_color;
        private int hits;//浏览
        private String datetime;
        private int jumptype;
        private String jumpurl;
        private String is_collect;
        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public int getJumpType() {
            return jumptype;
        }

        public void setJumpType(int jumptype) {
            this.jumptype = jumptype;
        }

        public String getJumpUrl() {
            return jumpurl;
        }

        public void setJumpUrl(String jumpurl) {
            this.jumpurl = jumpurl;
        }


        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public String getType_color() {

                return type_color;

        }

        public void setType_color(String type_color) {
            this.type_color = type_color;
        }

        @Override
        public String toString() {
            return "NewsEntity{" +
                    "nid=" + nid +
                    ", typename='" + typename + '\'' +
                    ", title='" + title + '\'' +
                    ", image='" + image + '\'' +
                    ", relay=" + relay +
                    ", type_color='" + type_color + '\'' +
                    ", hits='" + hits + '\'' +
                    ", opinions=" + opinions +
                    ", collect=" + collect +
                    ", comment=" + comment +
                    ", opinion=" + opinion +
                    '}';
        }

        /**
         * A : {"opt_id":"A","opt_title":"网民来自群众","opt_nums":3,"opt_color":"#FF5D5D"}
         * B : {"opt_id":"B","opt_title":"网意反映民意","opt_nums":"2","opt_color":"#35C8F6"}
         * C : {"opt_id":"C","opt_title":"支持本人观点","opt_nums":"1","opt_color":"#5E6977"}
         */
        private OpinionsEntity opinions;
        private int collect;
        private int comment;
        private int opinion;

        public void setNid(int nid) {
            this.nid = nid;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setRelay(int relay) {
            this.relay = relay;
        }

        public void setOpinions(OpinionsEntity opinions) {
            this.opinions = opinions;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public void setOpinion(int opinion) {
            this.opinion = opinion;
        }

        public int getNid() {
            return nid;
        }

        public String getTypename() {
            return typename;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public int getRelay() {
            return relay;
        }

        public OpinionsEntity getOpinions() {
            return opinions;
        }

        public int getCollect() {
            return collect;
        }

        public int getComment() {
            return comment;
        }

        public int getOpinion() {
            return opinion;
        }

        public static class OpinionsEntity {
            /**
             * opt_id : A
             * opt_title : 网民来自群众
             * opt_nums : 3
             * opt_color : #FF5D5D
             */
            private AEntity A;
            /**
             * opt_id : B
             * opt_title : 网意反映民意
             * opt_nums : 2
             * opt_color : #35C8F6
             */

            private BEntity B;
            /**
             * opt_id : C
             * opt_title : 支持本人观点
             * opt_nums : 1
             * opt_color : #5E6977
             */

            private CEntity C;

            public void setA(AEntity A) {
                this.A = A;
            }

            public void setB(BEntity B) {
                this.B = B;
            }

            public void setC(CEntity C) {
                this.C = C;
            }

            public AEntity getA() {
                return A;
            }

            public BEntity getB() {
                return B;
            }

            public CEntity getC() {
                return C;
            }

            public static class AEntity {
                private String opt_id;
                private String opt_title;
                private int opt_nums;
                private String opt_color;

                public void setOpt_id(String opt_id) {
                    this.opt_id = opt_id;
                }

                public void setOpt_title(String opt_title) {
                    this.opt_title = opt_title;
                }

                public void setOpt_nums(int opt_nums) {
                    this.opt_nums = opt_nums;
                }

                public void setOpt_color(String opt_color) {
                    this.opt_color = opt_color;
                }

                public String getOpt_id() {
                    return opt_id;
                }

                public String getOpt_title() {
                    return opt_title;
                }

                public int getOpt_nums() {
                    return opt_nums;
                }

                public String getOpt_color() {
                    return opt_color;
                }
            }

            public static class BEntity {
                private String opt_id;
                private String opt_title;
                private int opt_nums;
                private String opt_color;

                public void setOpt_id(String opt_id) {
                    this.opt_id = opt_id;
                }

                public void setOpt_title(String opt_title) {
                    this.opt_title = opt_title;
                }

                public void setOpt_nums(int opt_nums) {
                    this.opt_nums = opt_nums;
                }

                public void setOpt_color(String opt_color) {
                    this.opt_color = opt_color;
                }

                public String getOpt_id() {
                    return opt_id;
                }

                public String getOpt_title() {
                    return opt_title;
                }

                public int getOpt_nums() {
                    return opt_nums;
                }

                public String getOpt_color() {
                    return opt_color;
                }
            }

            public static class CEntity {
                private String opt_id;
                private String opt_title;
                private int opt_nums;
                private String opt_color;

                public void setOpt_id(String opt_id) {
                    this.opt_id = opt_id;
                }

                public void setOpt_title(String opt_title) {
                    this.opt_title = opt_title;
                }

                public void setOpt_nums(int opt_nums) {
                    this.opt_nums = opt_nums;
                }

                public void setOpt_color(String opt_color) {
                    this.opt_color = opt_color;
                }

                public String getOpt_id() {
                    return opt_id;
                }

                public String getOpt_title() {
                    return opt_title;
                }

                public int getOpt_nums() {
                    return opt_nums;
                }

                public String getOpt_color() {
                    return opt_color;
                }
            }
        }
    }

}
