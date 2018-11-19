package com.towcent.base.wx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.wx.config.BaseMpInRedisConfigStorage;
import com.towcent.base.wx.config.WxMpConfig;
import com.towcent.base.wx.handler.AbstractHandler;
import com.towcent.base.wx.handler.KfSessionHandler;
import com.towcent.base.wx.handler.LocationHandler;
import com.towcent.base.wx.handler.LogHandler;
import com.towcent.base.wx.handler.MenuHandler;
import com.towcent.base.wx.handler.MsgHandler;
import com.towcent.base.wx.handler.NullHandler;
import com.towcent.base.wx.handler.StoreCheckNotifyHandler;
import com.towcent.base.wx.handler.SubscribeHandler;
import com.towcent.base.wx.handler.UnsubscribeHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;

/**
 *
 * @author Binary Wang
 *
 */
// @Service
public class WeixinService extends WxMpServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// @Autowired
	protected LogHandler logHandler;
	// @Autowired
	protected NullHandler nullHandler;
	// @Autowired
	protected KfSessionHandler kfSessionHandler;
	// @Autowired
	protected StoreCheckNotifyHandler storeCheckNotifyHandler;
	// @Autowired
	private LocationHandler locationHandler;
	// @Autowired
	private MenuHandler menuHandler;
	// @Autowired
	private MsgHandler msgHandler;
	// @Autowired
	private UnsubscribeHandler unsubscribeHandler;
	// @Autowired
	private SubscribeHandler subscribeHandler;
	
	protected WxMpMessageRouter router;
	
	// @Resource
	private RedisTemplateExt<String, Object> redisTemplateExt;
	
	// @PostConstruct
	@SuppressWarnings("unchecked")
	public WeixinService(WxMpConfig wxConfig) {
		this.redisTemplateExt = SpringContextHolder.getBean(RedisTemplateExt.class);
		
		this.logHandler = SpringContextHolder.getBean(LogHandler.class);
		this.nullHandler = SpringContextHolder.getBean(NullHandler.class);
		this.kfSessionHandler = SpringContextHolder.getBean(KfSessionHandler.class);
		this.storeCheckNotifyHandler = SpringContextHolder.getBean(StoreCheckNotifyHandler.class);
		this.locationHandler = SpringContextHolder.getBean(LocationHandler.class);
		this.menuHandler = SpringContextHolder.getBean(MenuHandler.class);
		this.msgHandler = SpringContextHolder.getBean(MsgHandler.class);
		this.unsubscribeHandler = SpringContextHolder.getBean(UnsubscribeHandler.class);
		this.subscribeHandler = SpringContextHolder.getBean(SubscribeHandler.class);
		
		// 基于redis存储公众号配置
		final BaseMpInRedisConfigStorage config = new BaseMpInRedisConfigStorage(null);
		config.setRedisTemplateExt(redisTemplateExt);
		
		config.setAppId(wxConfig.getAppid());// 设置微信公众号的appid
		config.setSecret(wxConfig.getAppsecret());// 设置微信公众号的app corpSecret
		config.setToken(wxConfig.getToken());// 设置微信公众号的token
		config.setAesKey(wxConfig.getAesKey());// 设置消息加解密密钥
		super.setWxMpConfigStorage(config);
		
		
		this.refreshRouter();
	}

	public void refreshRouter() {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);

		// 记录所有事件的日志
		newRouter.rule().handler(this.logHandler).next();

		// 接收客服会话管理事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION).handler(this.kfSessionHandler).end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION).handler(this.kfSessionHandler).end();

		// 门店审核事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.POI_CHECK_NOTIFY)
				.handler(this.storeCheckNotifyHandler).end();

		// 自定义菜单事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.CLICK)
				.handler(this.getMenuHandler()).end();

		// 点击菜单连接事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.VIEW)
				.handler(this.nullHandler).end();

		// 关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
				.handler(this.getSubscribeHandler()).end();

		// 取消关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
				.handler(this.getUnsubscribeHandler()).end();

		// 上报地理位置事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION)
				.handler(this.getLocationHandler()).end();

		// 接收地理位置消息
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.getLocationHandler()).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN)
				.handler(this.getScanHandler()).end();

		// 默认
		newRouter.rule().async(false).handler(this.getMsgHandler()).end();

		this.router = newRouter;
	}

	public WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

	public boolean hasKefuOnline() {
		try {
			WxMpKfOnlineList kfOnlineList = this.getKefuService().kfOnlineList();
			return kfOnlineList != null && kfOnlineList.getKfOnlineList().size() > 0;
		} catch (Exception e) {
			this.logger.error("获取客服在线状态异常: " + e.getMessage(), e);
		}

		return false;
	}

	protected MenuHandler getMenuHandler() {
		return this.menuHandler;
	}

	protected SubscribeHandler getSubscribeHandler() {
		return this.subscribeHandler;
	}

	protected UnsubscribeHandler getUnsubscribeHandler() {
		return this.unsubscribeHandler;
	}

	protected AbstractHandler getLocationHandler() {
		return this.locationHandler;
	}

	protected MsgHandler getMsgHandler() {
		return this.msgHandler;
	}

	protected AbstractHandler getScanHandler() {
		return null;
	}

}
