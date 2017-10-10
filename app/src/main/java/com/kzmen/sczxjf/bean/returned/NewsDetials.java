package com.kzmen.sczxjf.bean.returned;

/**
 * 创建者：Administrator
 * 时间：2016/4/26
 * 功能描述：1.1资讯详情信息
 */
public class NewsDetials {

    /**
     * nid : 21
     * content : asfdsdf
     * title : 人民网评：网民来自群众，网意反映民意
     * image : http://192.168.0.163:9012/Uploads/Picture/2016-04-26/571ed1ceacaba.jpg
     * description : 7亿，是活跃在网络世界里的中国力量。近日，在网络安全和信息化工作座谈会上，习近平总书记指出，“这是一个了不起的数字，也是一个了不起的成就”。如此以亿记的数量级成就，背后是强大的网络民意，各级领导干部不仅要认真对待，而且要及时倾听。
     * score : 0
     * source : 享e下
     * datetime : 2016年04月27日
     * opinions : {"A":{"opt_id":"A","opt_title":"网民来自群众","opt_nums":"3","opt_color":"#FF5D5D"},"B":{"opt_id":"B","opt_title":"网意反映民意","opt_nums":"2","opt_color":"#35C8F6"},"C":{"opt_id":"C","opt_title":"支持本人观点","opt_nums":"1","opt_color":"#5E6977"}}
     * opt_id : A
     * opinion : 6
     * relay_url : http://192.168.0.163:9012/api.php/App/findOneNewsContent/nid/21/uid/69/time/1461744122
     * filesname "dsaf"
     * sharepic ""
     */

    private int nid;
    private String content;
    private String title;
    private String image;
    private String description;
    private String score;
    private String source;
    private String datetime;
    private String files;//PDF文件路径
    private String filesname;
    private int hits;
    private String sharepic;

    public String getSharepic() {
        return sharepic;
    }

    public void setSharepic(String sharepic) {
        this.sharepic = sharepic;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getFilesname() {
        return filesname;
    }

    public void setFilesname(String filesname) {
        this.filesname = filesname;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    /**
     * A : {"opt_id":"A","opt_title":"网民来自群众","opt_nums":"3","opt_color":"#FF5D5D"}
     * B : {"opt_id":"B","opt_title":"网意反映民意","opt_nums":"2","opt_color":"#35C8F6"}
     * C : {"opt_id":"C","opt_title":"支持本人观点","opt_nums":"1","opt_color":"#5E6977"}
     */


    private OpinionsEntity opinions;
    private String opt_id;
    private String opinion;
    private String relay_url;

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setOpinions(OpinionsEntity opinions) {
        this.opinions = opinions;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setRelay_url(String relay_url) {
        this.relay_url = relay_url;
    }

    public int getNid() {
        return nid;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getScore() {
        return score;
    }

    public String getSource() {
        return source;
    }

    public String getDatetime() {
        return datetime;
    }

    public OpinionsEntity getOpinions() {
        return opinions;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public String getOpinion() {
        return opinion;
    }

    public String getRelay_url() {
        return relay_url;
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

            @Override
            public String toString() {
                return "AEntity{" +
                        "opt_id='" + opt_id + '\'' +
                        ", opt_title='" + opt_title + '\'' +
                        ", opt_nums=" + opt_nums +
                        ", opt_color='" + opt_color + '\'' +
                        '}';
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

            @Override
            public String toString() {
                return "BEntity{" +
                        "opt_id='" + opt_id + '\'' +
                        ", opt_title='" + opt_title + '\'' +
                        ", opt_nums=" + opt_nums +
                        ", opt_color='" + opt_color + '\'' +
                        '}';
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

            @Override
            public String toString() {
                return "CEntity{" +
                        "opt_id='" + opt_id + '\'' +
                        ", opt_title='" + opt_title + '\'' +
                        ", opt_nums=" + opt_nums +
                        ", opt_color='" + opt_color + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "OpinionsEntity{" +
                    "A=" + A +
                    ", B=" + B +
                    ", C=" + C +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsDetials{" +
                "nid=" + nid +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", score='" + score + '\'' +
                ", source='" + source + '\'' +
                ", datetime='" + datetime + '\'' +
                ", files='" + files + '\'' +
                ", filesname='" + filesname + '\'' +
                ", hits=" + hits +
                ", opinions=" + opinions +
                ", opt_id='" + opt_id + '\'' +
                ", opinion='" + opinion + '\'' +
                ", relay_url='" + relay_url + '\'' +
                '}';
    }
}
