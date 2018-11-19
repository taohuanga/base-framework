package com.towcent.base.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageJmsVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 保存状态 (0:保存、1:发布)
	 */
	private String saveFlag;
	
	/**
	 * 商户Id
	 */
	private Integer merchantId;
	
	/**
	 * 商品编码
	 */
	private String goodsNo;
	
	/**
	 * 待处理图片集合<br/>
	 * key = 0:窗口图、1:详情图 <br/>
	 * value = 待处理图url集合 (key = 序号, value = url)
	 */
	private Map<String, List<ImageUrlVo>> imageMap = new HashMap<String, List<ImageUrlVo>>();
	
	public ImageJmsVo() {  }
	
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public ImageJmsVo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Map<String, List<ImageUrlVo>> getImageMap() {
		return imageMap;
	}

	public void setImageMap(Map<String, List<ImageUrlVo>> imageMap) {
		this.imageMap = imageMap;
	}
	
	public void putImageMap(String key, List<ImageUrlVo> value) {
		imageMap.put(key, value);
	}

	@Override
	public String toString() {
		return "ImageJmsVo [saveFlag=" + saveFlag + ", goodsNo=" + goodsNo
				+ ", imageMap=" + imageMap + "]";
	}
	
	
}
