package com.towcent.base.common.jms;

import com.towcent.base.common.vo.BaseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractConsumer implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4424046448013710601L;
	protected Log log = LogFactory.getLog(getClass());

	public abstract void messageListener(BaseDto entity);

}
