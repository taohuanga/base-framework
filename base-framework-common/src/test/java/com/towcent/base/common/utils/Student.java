/*
 * All rights Reserved, Designed By www..com
 * @Project : base-framework-common
 * @Title : Student.java
 * @Package : com.towcent.base.common.utils
 * @date : 2017年12月5日上午11:24:18
 * @Copyright: 2017 www..com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市前海金田科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.utils;

import com.towcent.base.common.utils.excel.annotation.ExcelField;

/**
 * @ClassName: Student 
 * @Description: 仅限导出测试用例使用 
 *
 * @author huangtao
 * @date 2017年12月5日 上午11:24:18
 * @version 1.0.0
 * @Copyright: 2017 www..com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市前海金田科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Student {
	
	public Student() {
		
	}
	
	@ExcelField(title="姓名", sort=0)
	private String name;
	
	@ExcelField(title="性别", sort=2, dictType="sex", align=2)
	private String sex;
	
	@ExcelField(title="年龄", sort=1)
	private Integer age;
	
	@ExcelField(title="部门", sort=3)
	private String dept;

	/**
	 * name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * sex.
	 *
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * sex.
	 *
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * age.
	 *
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * age.
	 *
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * dept.
	 *
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * dept.
	 *
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	
}

	