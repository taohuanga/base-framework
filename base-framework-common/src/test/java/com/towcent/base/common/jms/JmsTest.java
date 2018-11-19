package com.towcent.base.common.jms;

import com.towcent.base.BaseTest;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Jms消息测试类
 * 
 * @author huangtao
 * @date 2016年8月23日 下午6:22:32
 * @version 0.1.0 
 */
public class JmsTest extends BaseTest {
	
	@Resource
	private BelleMessageProducer producer;
	
	/*
	#mq setting
	activemq.host=tcp://127.0.0.1:61616
	activemq.username=admin
	activemq.password=admin
	activemq.useCompression=true
	activemq.useAsyncSend=false
	activemq.threadpoolexecutor.corePoolSize=5
	activemq.threadpoolexecutor.maxPoolSize=10
	activemq.threadpoolexecutor.queueCapacity=1000
	activemq.threadpoolexecutor.keepAliveSeconds=300
	activemq.listener.concurrentConsumers=1
	*/
	@Test public void sendJms() {
		producer.setDestination(new ActiveMQQueue("huangtao.test.queue"));
		producer.send(new JmsVo("1"));
	}
	
	// 监听消息使用注解   
	// 写在需要监听的方法上
	// destination : 监听队列的名字
	// containerFactory : 监听容器名称
	// @JmsListener(destination = "huangtao.test.queue", containerFactory = "queueListenerContainer")
	
}
