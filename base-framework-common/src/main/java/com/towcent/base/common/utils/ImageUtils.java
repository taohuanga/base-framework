/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-dist-shop-common
 * @Title : ImageUtils.java
 * @Package : com.towcent.dist.shop.common
 * @date : 2018年7月13日上午11:31:15
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.SysImageConf;
import com.towcent.base.manager.BaseCommonApi;

/**
 * @ClassName: ImageUtils 
 * @Description: 图片工具类型
 *
 * @author huangtao
 * @date 2018年7月13日 上午11:31:15
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ImageUtils {
	
	protected static final String BASE_PATH = "/";  // 正式上线要切换成"/"
	protected static Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	
	/**
	 * 图片的相对目录(Ftp服务器).
	 * @Title buildImageRelativePath
	 * @param BasePath      Ftp服务器根目录["/"  或者  "/huangtao/"]
	 * @param merchantId    商家Id
	 * @param imageType     图片类型
	 * @return
	 */
	public static String buildImageRelativePath(String BasePath, Integer merchantId, Integer imageType) {
		SysImageConf imageConf = getImageConfByType(merchantId, imageType);
		return buildImageRelativePath(BasePath, imageConf);
	}
	
	/**
	 * 图片的相对目录(Ftp服务器).
	 * @Title buildImageRelativePath
	 * @param BasePath     Ftp服务器根目录["/"  或者  "/huangtao/"]
	 * @param imageConf    图片配置对象
	 * @return
	 */
	public static String buildImageRelativePath(String BasePath, SysImageConf imageConf) {
		StringBuffer sb = new StringBuffer(BasePath);
		if (null == imageConf) {
			sb.append("other/");
		} else {
			sb.append(imageConf.getMerchantId()).append("/");
			sb.append(imageConf.getImagePath());
			// 子目录格式 == 日期格式
			if (StringUtils.isNotBlank(imageConf.getSubDirFormat())) {
				sb.append(DateUtils.formatDate(new Date(), imageConf.getSubDirFormat())).append("/");
			}
		}
		return sb.toString();
	}
	
	public static SysImageConf getImageConfByType(Integer merchantId, Integer imageType) {
		BaseCommonApi commonApi = SpringContextHolder.getBean(BaseCommonApi.class); 
		SysImageConf imageConf = null;
		try {
			imageConf = commonApi.getImageConfByType(merchantId, imageType);
		} catch (RpcException e) {
			logger.error("获取图片配置异常.", e);
		}
		return imageConf;
	}
}

	