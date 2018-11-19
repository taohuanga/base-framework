package com.towcent.base.common.vo;

import java.io.Serializable;

public class ImageUrlVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片序号 1对应第一张图片
	 */
	private Integer seq;
	
	/**
	 * 图片url
	 */
	private String url;
	
	/**
	 * 商户Id
	 */
	private Integer merchantId;
	
	/**
	 * 商品编码
	 */
	private String goodsNo;
	
	public ImageUrlVo() {  }
	
	public ImageUrlVo(Integer seq, String url) {
		this.seq = seq;
		this.url = url;
	}
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Override
	public String toString() {
		return "ImageUrlVo [seq=" + seq + ", url=" + url + "]";
	}
	
}
