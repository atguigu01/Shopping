package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.activity.GoodsListActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.ResultBean;
import com.atguigu.shoppingmall.home.uitls.AlphaPageTransformer;
import com.atguigu.shoppingmall.home.uitls.ScaleInTransformer;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String GOODS_BEAN = "goods_bean";
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据Bean对象
     */
    private ResultBean resultBean;
    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;



    public HomeRecycleAdapter(Context mContext, ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 根据位置得到类型-系统调用
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 返回总条数，共六种类型
     * @return
     */
    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, resultBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        } else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), mContext);
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), mContext);
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null), mContext);
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null), mContext);
        }
        return null;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.HotInfoBean> data) {
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, data);
            gv_hot.setAdapter(adapter);

            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = data.get(position).getCover_price();
                    String name = data.get(position).getName();
                    String figure = data.get(position).getFigure();
                    String product_id = data.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private Context mContext;

        public RecommendViewHolder(View itemView, Context mContext) {
            super(itemView);
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.RecommendInfoBean> data) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, data);
            gv_recommend.setAdapter(adapter);

            //点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = data.get(position).getCover_price();
                    String name = data.get(position).getName();
                    String figure = data.get(position).getFigure();
                    String product_id = data.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private boolean isFirst = true;
    private TextView tvTime;
    private int dt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt == 0) {
                    handler.removeMessages(0);
                }
            }

        }
    };

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        private RecyclerView recyclerView;
        public Context mContext;

        public SeckillViewHolder(View itemView, Context mContext) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            this.mContext = mContext;
        }


        public void setData(final ResultBean.SeckillInfoBean data) {
            //设置时间
            if (isFirst) {
//                dt = (int) (Integer.parseInt(data.getEnd_time()) - System.currentTimeMillis());
                dt = (int) (Integer.parseInt(data.getEnd_time()) - (Integer.parseInt(data.getStart_time())));
                isFirst = false;
            }

            //设置RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, data);
            recyclerView.setAdapter(adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onClick(int position) {
                    ResultBean.SeckillInfoBean.ListBean listBean = data.getList().get(position);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);

                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        public ViewPager actViewPager;
        public Context mContext;

        public ActViewHolder(View itemView, Context mContext) {
            super(itemView);
            actViewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.ActInfoBean> data) {
            actViewPager.setPageMargin(20);
            actViewPager.setOffscreenPageLimit(3);
            actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            actViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return data.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView view = new ImageView(mContext);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    //绑定数据
                    Glide.with(mContext)
                            .load(Constants.BASE_URl_IMAGE + data.get(position).getIcon_url())
                            .into(view);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            //点击事件
            actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;

        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            gvChannel = (GridView) itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.ChannelInfoBean> channel_info) {
            gvChannel.setAdapter(new ChannelAdapter(mContext, channel_info));

            //点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 8) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    } else {

                    }
                }
            });
        }

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public ResultBean resultBean;

        public BannerViewHolder(View itemView, Context context, ResultBean resultBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
            this.mContext = context;
            this.resultBean = resultBean;
        }

        public void setData(final List<ResultBean.BannerInfoBean> banner_info) {

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < resultBean.getBanner_info().size(); i++) {
                imageUris.add(resultBean.getBanner_info().get(i).getImage());
            }
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    /**
                     * 这里你可以根据框架灵活设置
                     */
                    Glide.with(mContext)
                            .load(Constants.BASE_URl_IMAGE + url)
                            .into(view);
                }
            });
            //设置点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    if(position - 1 < banner_info.size()){
                        int option = banner_info.get(position - 1).getOption();
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (position - 1 == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (position - 1 == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                        } else {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》 剑网3同人本";
                        }
                        String image = banner_info.get(position - 1).getImage();
                        GoodsBean goodsBean = new GoodsBean(name, cover_price, image, product_id);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra("goods_bean", goodsBean);
                        mContext.startActivity(intent);
                    }

                }
            });

        }
    }

}
