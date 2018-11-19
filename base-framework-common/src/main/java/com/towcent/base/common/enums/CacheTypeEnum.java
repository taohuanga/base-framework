package com.towcent.base.common.enums;

/**
 * 缓存类型枚举类
 * 设置缓存时间
 * @author 
 *
 */
public enum CacheTypeEnum {
	/**
	 * 品牌权限查询
	 */
	AUTHORITY_BRAND_QUERY(CacheStateEnum.STATIC,CacheExpireEnum.THIRTY_MINUTE),
	/**
	 * 机构权限查询
	 */
	AUTHORITY_ORGANIZATION_QUERY(CacheStateEnum.STATIC,CacheExpireEnum.THIRTY_MINUTE),
	/**
	 * 菜单缓存一天
	 */
	AUTHORITY_MENU_TREE_QUERY(CacheStateEnum.LRU,CacheExpireEnum.TODAY),
	/**
	 * 模块缓存一天
	 */
	AUTHORITY_MODULE_QUERY(CacheStateEnum.STATIC,CacheExpireEnum.THIRTY_MINUTE);
	
	private CacheExpireEnum expire;
	private CacheStateEnum state;
	
	CacheTypeEnum(CacheStateEnum state,CacheExpireEnum expire){
		this.state = state;
		this.expire = expire;
	}
	
	public CacheExpireEnum getExpire(){
		return this.expire;
	}
	
	public CacheStateEnum getState(){
		return this.state;
	}
}
