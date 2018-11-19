package com.towcent.base.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IdcardDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 姓名 */
    private String name;
    /** 身份证号 */
    private String identityCard;
    /** 性别 */
    private Integer sex;
    /** 住址 */
    private String address;
    /** 出生年月日 */
    private Date birthday;
    
}
