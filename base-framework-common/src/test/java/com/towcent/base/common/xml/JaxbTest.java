package com.towcent.base.common.xml;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.towcent.base.common.mapper.JaxbMapper;

public class JaxbTest {
	
	@Test
	public void toXml() {
		Package v = new Package();
		
		Head head = new Head();
		v.setHead(head);
		
		List<Field> heads = Lists.newArrayList();
		Field f = new Field();
		f.setId("1");
		f.setValue("sdfsdg");
		heads.add(f);
		
		f = new Field();
		f.setId("2");
		f.setValue("sdfsdg2");
		heads.add(f);
		head.setField(heads);
		
		String xml = JaxbMapper.toXml(v, "utf-8");
		System.out.println(xml);
	}
	
}
