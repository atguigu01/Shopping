package com.atguigu.shoppingmall.type.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/2.
 */
public class TagBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : [{"tag_id":"1","name":"喵星人"},{"tag_id":"2","name":"尚硅谷"},{"tag_id":"3","name":"星河动漫"},{"tag_id":"4","name":"A3原创洋装店"},{"tag_id":"5","name":"世界线的彼岸"},{"tag_id":"6","name":"一方尘寰"},{"tag_id":"7","name":"彩虹堂正品动漫"},{"tag_id":"8","name":"amovo魔吻"},{"tag_id":"9","name":"SYS艺术说"},{"tag_id":"10","name":"石头人工作室"},{"tag_id":"11","name":"Funko"},{"tag_id":"12","name":"十里丹青"},{"tag_id":"13","name":"创一文化"},{"tag_id":"14","name":"亚蒙兔家"},{"tag_id":"15","name":"宇宙电波"},{"tag_id":"16","name":"禹屋原创"},{"tag_id":"17","name":"符豚甲胄"},{"tag_id":"18","name":"能量天使"},{"tag_id":"19","name":"上海筒菲"},{"tag_id":"20","name":"chairball原创设计"},{"tag_id":"21","name":"砚池工作室"},{"tag_id":"22","name":"云落秋池"},{"tag_id":"23","name":"十一宫"},{"tag_id":"24","name":"长草颜文字"},{"tag_id":"25","name":"网易游戏"},{"tag_id":"26","name":"ISOS"},{"tag_id":"27","name":"绝对萌域"},{"tag_id":"31","name":"雪猫一族"},{"tag_id":"32","name":"天闻角川"},{"tag_id":"33","name":"静悦坊"}]
     */

    private int code;
    private String msg;
    /**
     * tag_id : 1
     * name : 喵星人
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
        private String tag_id;
        private String name;

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
