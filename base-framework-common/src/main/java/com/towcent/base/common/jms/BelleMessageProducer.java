package com.towcent.base.common.jms;

import com.towcent.base.common.vo.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.io.Serializable;

public class BelleMessageProducer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = LoggerFactory.getLogger(BelleMessageProducer.class);
	
	protected JmsTemplate template;

	protected Destination destination;

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void send(BaseDto dto) {
		try {
			template.convertAndSend(this.destination, dto);
			logger.debug("BelleMessageProducer Send OK ...");
		} catch (Exception e) {
			logger.error("BelleMessageProducer Send ERROR ...", e);
		}
	}

}
