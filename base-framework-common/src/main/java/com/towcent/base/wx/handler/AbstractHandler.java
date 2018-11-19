package com.towcent.base.wx.handler;

import com.google.gson.Gson;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Binary Wang
 *
 */
public abstract class AbstractHandler implements WxMpMessageHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected final Gson gson = new Gson();

}
