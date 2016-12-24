package com.atguigu.shoppingmall.home.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ResultBean extends JSONObject {

    private SeckillInfoBean seckill_info;
    private List<BannerInfoBean> banner_info;
    private List<ChannelInfoBean> channel_info;
    private List<ActInfoBean> act_info;
    private List<HotInfoBean> hot_info;
    private List<RecommendInfoBean> recommend_info;

    public SeckillInfoBean getSeckill_info() {
        return seckill_info;
    }

    public void setSeckill_info(SeckillInfoBean seckill_info) {
        this.seckill_info = seckill_info;
    }

    public List<BannerInfoBean> getBanner_info() {
        return banner_info;
    }

    public void setBanner_info(List<BannerInfoBean> banner_info) {
        this.banner_info = banner_info;
    }

    public List<ChannelInfoBean> getChannel_info() {
        return channel_info;
    }

    public void setChannel_info(List<ChannelInfoBean> channel_info) {
        this.channel_info = channel_info;
    }

    public List<ActInfoBean> getAct_info() {
        return act_info;
    }

    public void setAct_info(List<ActInfoBean> act_info) {
        this.act_info = act_info;
    }

    public List<HotInfoBean> getHot_info() {
        return hot_info;
    }

    public void setHot_info(List<HotInfoBean> hot_info) {
        this.hot_info = hot_info;
    }

    public List<RecommendInfoBean> getRecommend_info() {
        return recommend_info;
    }

    public void setRecommend_info(List<RecommendInfoBean> recommend_info) {
        this.recommend_info = recommend_info;
    }

    public static class SeckillInfoBean {
        private String start_time;
        private String end_time;
        private List<ListBean> list;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "SeckillInfoBean{" +
                    "start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean {
            private String product_id;
            private String name;
            private String cover_price;
            private String origin_price;
            private String figure;

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getOrigin_price() {
                return origin_price;
            }

            public void setOrigin_price(String origin_price) {
                this.origin_price = origin_price;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }
        }
    }

    public static class BannerInfoBean {
        private String image;
        private int option;
        private int type;
        private ValueBean valueBean;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public ValueBean getValueBean() {
            return valueBean;
        }

        public void setValueBean(ValueBean valueBean) {
            this.valueBean = valueBean;
        }

        @Override
        public String toString() {
            return "BannerInfoBean{" +
                    "image='" + image + '\'' +
                    ", option=" + option +
                    ", type=" + type +
                    ", valueBean=" + valueBean +
                    '}';
        }

        public static class ValueBean {
            private String url;
            private String product_id;
            private String brand_id;

            @Override
            public String toString() {
                return "ValueBean{" +
                        "url='" + url + '\'' +
                        ", product_id='" + product_id + '\'' +
                        ", brand_id='" + brand_id + '\'' +
                        '}';
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }
        }
    }

    public static class ChannelInfoBean {
        private int option;
        private int type;
        private String channel_name;
        private String image;

       private ValueBean value;

        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


        public static class ValueBean {
            private String channel_id;

        }

        @Override
        public String toString() {
            return "ChannelInfoBean{" +
                    "option=" + option +
                    ", type=" + type +
                    ", channel_name='" + channel_name + '\'' +
                    ", image='" + image + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    public static class ActInfoBean {
        private String name;
        private String icon_url;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ActInfoBean{" +
                    "name='" + name + '\'' +
                    ", icon_url='" + icon_url + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class HotInfoBean {
        private String product_id;
        private String name;
        private String cover_price;
        private String figure;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover_price() {
            return cover_price;
        }

        public void setCover_price(String cover_price) {
            this.cover_price = cover_price;
        }

        public String getFigure() {
            return figure;
        }

        public void setFigure(String figure) {
            this.figure = figure;
        }

        @Override
        public String toString() {
            return "HotInfoBean{" +
                    "product_id='" + product_id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover_price='" + cover_price + '\'' +
                    ", figure='" + figure + '\'' +
                    '}';
        }
    }

    public static class RecommendInfoBean {
        private String product_id;
        private String name;
        private String cover_price;
        private String figure;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover_price() {
            return cover_price;
        }

        public void setCover_price(String cover_price) {
            this.cover_price = cover_price;
        }

        public String getFigure() {
            return figure;
        }

        public void setFigure(String figure) {
            this.figure = figure;
        }

        @Override
        public String toString() {
            return "RecommendInfoBean{" +
                    "product_id='" + product_id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover_price='" + cover_price + '\'' +
                    ", figure='" + figure + '\'' +
                    '}';
        }
    }

}

