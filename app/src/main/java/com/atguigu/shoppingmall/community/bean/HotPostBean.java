package com.atguigu.shoppingmall.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/6.
 */
public class HotPostBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : [{"post_id":"2313","user_id":"90437","figure":"http://img01.cycangcdn.com/ugc/post/img/201609/14746252609717297.png","saying":"惊喜不断，新爆款、爆款、爆款，限时预定哦！","add_time":"1474625262","likes":"13","comments":"6","is_hot":"1","is_top":"1","is_essence":"1","username":"尚硅谷首席惊喜官","avatar":"http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png","is_like":"0","comment_list":["啦啦","啦啦","吃土快乐，祝我快乐","猝不及防一口土\u2026\u2026","吃土愉快","啊啊啊啊啊啊啊疯掉了！！！！！猝不及防啊啊啊"]},{"post_id":"2199","user_id":"90437","figure":"http://img01.cycangcdn.com/ugc/post/img/201609/14736747792594015.png","saying":"谷的新品-流烟昔泠新款套装【轻梦泽】今晚八点准时在尚硅谷首发，买买买2333\u2026","add_time":"1473674779","likes":"15","comments":"2","is_hot":"1","is_top":"1","is_essence":"1","username":"尚硅谷首席惊喜官","avatar":"http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png","is_like":"0","comment_list":["第二！","我第一～～哈哈哈～～沙发～"]},{"post_id":"2345","user_id":"90437","figure":"http://img01.cycangcdn.com/ugc/post/img/201609/14751401203006663.png","saying":"特大惊喜：#谷の盛典# #小谷陪你过国庆# 国庆攻略大曝光！小谷教您买买买！0元备战黄金周~30日前海量优惠券免！费！领！假期福利满天飞~全场满减搭配优惠券！爆款直降白菜价！","add_time":"1475140120","likes":"3","comments":"10","is_hot":"1","is_top":"1","is_essence":"1","username":"尚硅谷首席惊喜官","avatar":"http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png","is_like":"0","comment_list":["回复尚硅谷首席铲屎官：wom","回复尚硅谷首席铲屎官：wom","ilcd","回复 ＠夕夕夕凄：亲 通过首页的\u201c小谷陪你过国庆\u201d的banner进去就可以参加大转盘抽奖了*^_^*","哎找不到大转盘哎","回复 ＠小鸟～萌萌哒：大转盘9月30日18点上线哦","大转盘在哪里找呀？～～～","回复二次元哒小神棍：满减是可以配合优惠券一起叠加使用的","回复二次元哒小神棍：国庆我们有满减，这个才是重头啦，优惠券这次国庆不是主角！","优惠券的力度减小了，有些商品除了小谷自营的商品不划算。就算有免单的机会也不一定能抽到啊！"]},{"post_id":"2278","user_id":"90437","figure":"http://img01.cycangcdn.com/ugc/post/img/201609/14742827198764639.png","saying":"#谷の闪购# 机智菌爷的判断从来是非常准确的~\r\n食欲之秋已经到来！宝宝们绝对需要今天的大安利！【麦大叔】美味零食~开启超爽8折大狂欢！肉肉肉什么的一波带走！开心果大把大把地塞！一起来放纵吃吃吃吧！","add_time":"1474282720","likes":"2","comments":"5","is_hot":"1","is_top":"1","is_essence":"1","username":"尚硅谷首席惊喜官","avatar":"http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png","is_like":"0","comment_list":["恶魔","啊啊啊啊","啊啊啊啊"]},{"post_id":"2350","user_id":"90437","figure":"http://img01.cycangcdn.com/ugc/post/img/201609/14752273661625367.png","saying":"#谷の爆款# 告别了炎夏~小谷内心依然惦记着海边的美好画面！【全职高手】4款新品PU小包包！带你寻味夏季！激萌指数直线飙升！浅蓝色配色充满海洋的气息！趁着国庆优惠把他们一同带回家过节吧~","add_time":"1475227367","likes":"7","comments":"0","is_hot":"1","is_top":"1","is_essence":"1","username":"尚硅谷首席惊喜官","avatar":"http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png","is_like":"0","comment_list":[]}]
     */

    private int code;
    private String msg;
    /**
     * post_id : 2313
     * user_id : 90437
     * figure : http://img01.cycangcdn.com/ugc/post/img/201609/14746252609717297.png
     * saying : 惊喜不断，新爆款、爆款、爆款，限时预定哦！
     * add_time : 1474625262
     * likes : 13
     * comments : 6
     * is_hot : 1
     * is_top : 1
     * is_essence : 1
     * username : 尚硅谷首席惊喜官
     * avatar : http://img01.cycangcdn.com/ugc/user/avatar/14651924470798522.png
     * is_like : 0
     * comment_list : ["啦啦","啦啦","吃土快乐，祝我快乐","猝不及防一口土\u2026\u2026","吃土愉快","啊啊啊啊啊啊啊疯掉了！！！！！猝不及防啊啊啊"]
     */

    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String post_id;
        private String user_id;
        private String figure;
        private String saying;
        private String add_time;
        private String likes;
        private String comments;
        private String is_hot;
        private String is_top;
        private String is_essence;
        private String username;
        private String avatar;
        private String is_like;
        private List<String> comment_list;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFigure() {
            return figure;
        }

        public void setFigure(String figure) {
            this.figure = figure;
        }

        public String getSaying() {
            return saying;
        }

        public void setSaying(String saying) {
            this.saying = saying;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public String getIs_essence() {
            return is_essence;
        }

        public void setIs_essence(String is_essence) {
            this.is_essence = is_essence;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }

        public List<String> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<String> comment_list) {
            this.comment_list = comment_list;
        }
    }
}
