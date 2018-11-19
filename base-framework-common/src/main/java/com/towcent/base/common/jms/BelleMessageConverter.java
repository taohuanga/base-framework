package com.towcent.base.common.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

public class BelleMessageConverter implements MessageConverter {

	protected Log log = LogFactory.getLog(getClass());

	public Message toMessage(Object obj, Session session) throws JMSException {
		ObjectMessage objMsg = (ObjectMessage) session.createObjectMessage();
		objMsg.setObject((Serializable) obj);
		return objMsg;
	}

	public Object fromMessage(Message msg) throws JMSException {
		if (msg instanceof ObjectMessage) {
			return ((ObjectMessage) msg).getObject();
		} else {
			throw new JMSException("Msg:[" + msg + "] is not Map");
		}
	}

}
