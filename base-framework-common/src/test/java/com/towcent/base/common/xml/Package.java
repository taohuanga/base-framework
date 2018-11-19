package com.towcent.base.common.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="package")
public class Package {
	
	private Head head;

	public Head getHead() {
		return head;
	}

	public void setHead(Head heads) {
		this.head = heads;
	}
	
}
