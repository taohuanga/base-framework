/**
 * 
 */
package com.towcent.base.common.spider;

import java.util.List;

import lombok.Getter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * 爬虫抽象的解析类
 * 
 * @author shiwei
 *
 */
@Getter
public abstract class AbstractSpiderHandle {
	
	/**
	 * 爬虫主访问地址
	 */
	private String spiderUrl = "";
	
	/**
	 * 爬虫详细爬地址
	 */
	private String spiderHelpUrl = "";		
	
	private HttpClientDownloader httpClientDownloader = null;
	
	/**
	 * 处理爬出来的页面信息
	 * @param page
	 */
	public void process(Page page){
		 List<String> links = page.getHtml().links().regex(this.getSpiderHelpUrl()).all();
	     page.addTargetRequests(links);
	     this.handle(page);
	}

	/**
	 * 处理爬出来的页面信息详情页面
	 * @param page
	 */
	public abstract void handle(Page page);
}
