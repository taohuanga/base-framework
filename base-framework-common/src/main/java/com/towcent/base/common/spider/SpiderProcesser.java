/**
 * 
 */
package com.towcent.base.common.spider;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.BasicConfigurator;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import com.towcent.base.common.utils.SpringContextHolder;

/**
 * 爬虫处理类
 * @author shiwei
 *
 */
@Setter @Getter
public class SpiderProcesser implements PageProcessor {

	private Site site = Site.me().setDomain("localhost");
	
	/**
	 * 服务的spring对象id
	 */
	private String serviceId;
	
    @Override
    public void process(Page page) {
    	AbstractSpiderHandle abstractSpiderHandle = (AbstractSpiderHandle)SpringContextHolder.getBean(this.getServiceId());
    	abstractSpiderHandle.process(page);
    }
    
    public void spider(){
    	BasicConfigurator.configure();
    	AbstractSpiderHandle abstractSpiderHandle = (AbstractSpiderHandle)SpringContextHolder.getBean(this.getServiceId());
    	String domain = abstractSpiderHandle.getSpiderUrl().replaceAll("http://|https://", "").split("/")[1];
		site = Site.me().setDomain(domain);
		HttpClientDownloader httpClientDownloader = abstractSpiderHandle.getHttpClientDownloader();
    	if(null == httpClientDownloader) {
			Spider.create(this).addUrl(abstractSpiderHandle.getSpiderUrl()).addPipeline(new ConsolePipeline()).run();
		} else {
			Spider.create(this).addUrl(abstractSpiderHandle.getSpiderUrl()).addPipeline(new ConsolePipeline()).setDownloader(httpClientDownloader).run();
		}
    }
    
    @Override
    public Site getSite() {
        return site;
    }
}
