package com.towcent.base.dal.auth;

public class Authorization {

//	// @Value("${dataaccess}")
//	private static Configration configration;
//
//	// @Resource
//	private static AuthorizationProvider authorizationProvider = null;
//	private static DataAccessProvider dataAccessProvider;
//
//	protected static final XLogger logger = XLoggerFactory
//			.getXLogger(Authorization.class);
//
//	private static AuthorizationProvider getProvider() {
//
//		// if (RpcContext.getContext() != null) {
//		// return SpringContext.getBean("dubboAuthorizationProvider");
//		// }
//		// else
//		if (authorizationProvider == null
//				&& SpringContext.containsBean("authorizationProvider"))
//			authorizationProvider = SpringContext
//					.getBean("authorizationProvider");
//		return authorizationProvider;
//	}
//
//	private static Storage storage;
//
//	public static Storage getStorage() {
//		if (storage == null)
//			storage = SpringContext.getBean("securityStorage");
//		return storage;
//	}
//
//	private static RoleAccessProvider roleProvider;
//
//	private static RoleAccessProvider getRoleProvider() {
//		if (roleProvider == null)
//			if (SpringContext.containsBean("roleAccessProvider"))
//				roleProvider = SpringContext.getBean("roleAccessProvider");
//			else
//				roleProvider = new DefaultRoleAccessProvider();
//		return roleProvider;
//	}
//
//	private static Configration getConfig() {
//		if (configration == null)
//			configration = SpringContext.getBean("configration");
//		return configration;
//	}
//
//	private static DataAccessProvider getDataAccessProvider() {
//		if (dataAccessProvider == null) {
//			if (SpringContext.containsBean("dataAccessProvider"))
//				dataAccessProvider = (DataAccessProvider) SpringContext
//						.getBean("dataAccessProvider");
//			else
//				dataAccessProvider = new EmptyDataAccessProvider();
//		}
//		return dataAccessProvider;
//	}
//
//	public static SystemUser getUser(Integer userId) {
//		if( getProvider() == null)
//			return null;
//		return getProvider().getUser(userId);
//	}
//
//	public static SystemUser getUser() {
//		if( getProvider() == null)
//			return null;
//		SystemUser user = getProvider().getUser();
//
//		if (user == null && getConfig().sessionDisable > 0) {
//			logger.info("验证关闭，自动创建用户:" + getConfig().sessionDisable);
//			user = new SystemUser();
//			user.setUserid(getConfig().sessionDisable);
//			user.setUsername("超级管理员");
//			getProvider().setUser(user);
//			clear();
//		}
//
//		return user;
//	}
//
//	public static void setUser(SystemUser val) {
//		if( getProvider()== null )
//			return ;
//		getProvider().setUser(val);
//		clear();
//	}
//
//	public static int getSystemId() {
//		if( getProvider()== null )
//			return 0;
//		return getProvider().getSystemId();
//	}
//
//	public static void setSystemId(int val) {
//		if( getProvider()== null )
//			return ;
//		getProvider().setSystemId(val);
//	}
//
//	public static int getAreasystemId() {
//		if( getProvider()== null )
//			return 0;
//		return getProvider().getAreasystemId();
//	}
//
//	public static void setAreasystemId(int val) {
//		if( getProvider()== null )
//			return ;
//		getProvider().setAreasystemId(val);
//	}
//
//	public static void signout() {
//		if( getProvider()== null )
//			return ;
//		getProvider().signout();
//		clear();
//	}
//
//	private static void clear() {
//		if( getProvider()== null )
//			return ;
//		getProvider().clear();
//		getRoleProvider().clear();
//		getDataAccessProvider().clear();
//	}
//
//	private static boolean valide() {
//		if (getConfig().dataaccess == 0 || getConfig().sessionDisable > 0)
//			return true;
//		if( getProvider()== null )
//			return false;
//		String module = getProvider().getCurrentModule();
//		if (StringUtils.hasLength(module))
//			return false;
//		else
//			return true;
//
//	}
//
//	/**
//	 * 是否拥有指定角色
//	 * 
//	 * @param role
//	 * @return
//	 */
//	public static boolean hasRole(String role) {
//		if( getProvider()== null )
//			return false;
//		return getProvider().getRoles().contains(role);
//	}
//
//	public static boolean hasPermission(int ove) {
//		if( getProvider()== null )
//			return true;
//		if (valide())
//			return true;
//		List<String> opts = Authorization.getOptions(); // tempModuleDto.getOperations();
//		if (opts == null)
//			return false;
//
//		return opts.contains(ove);
//	}
//
//	public static boolean hasPermission(String name, String opt) {
//		if( getProvider()== null )
//			return true;
//		// 当前方法没有设置权限,
//		if (!StringUtils.hasLength(opt)
//				|| opt.equalsIgnoreCase(OperationCodeConstant.IGNORE))
//			return true;
//
//		if (valide())
//			return true;
//
//		List<String> opts = Authorization.getOptions();
//		if (opts == null)
//			return false;
//
//		return opts.contains(opt);
//
//		// 验证公共权限与用户选择权限判断
//		// String name = method.getMethod().getName();
//
//		// if ("add".equals(name) || "post".equals(name) || ove ==
//		// OperationVerifyEnum.ADD) {
//		// return null != opts && opts.contains(OperationVerifyEnum.ADD);
//		// } else if ("moditfy".equals(name) || "put".equals(name) || ove ==
//		// OperationVerifyEnum.MODIFY) {
//		// return null != opts && opts.contains(OperationVerifyEnum.MODIFY);
//		// } else if ("remove".equals(name) || "delete".equals(name) || ove ==
//		// OperationVerifyEnum.REMOVE) {
//		// return null != opts && opts.contains(OperationVerifyEnum.REMOVE);
//		// }
//		// if (ove == OperationVerifyEnum.NONE)
//		// return true;
//
//	}
//
//	/**
//	 * 是否拥有操作权限
//	 * 
//	 * @param option
//	 * @return
//	 */
//	public static boolean hasPermission(String option) {
//		if (valide())
//			return true;
//		if( getProvider()== null )
//			return true;
//		AuthorityUserModuleDto um = getProvider().getUserPermission();
//		if (um == null)
//			return false;
//
//		String userPermission = um.getPermissions() + ",";
//		return userPermission.indexOf(option + ",") >= 0;
//		// int temp = (int) Math.pow(2, option);
//		// int result = userPermission & temp;
//		// if (result == temp) {
//		// flag = true;
//		// }
//		// return flag;
//	}
//
//	/**
//	 * 是否拥有特定的数据权限
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static boolean hasDataPermission(String name) {
//		if( getProvider()== null )
//			return true;
//		return getRoleProvider().hasPermission(name);
//	}
//
//	public static List<String> getOptions() {
//		if( getProvider()== null )
//			return new ArrayList<String>();
//		AuthorityUserModuleDto um = getProvider().getUserPermission();
//		if (um == null)
//			return null;
//		List<String> opts = um.getOperations();
//		if (opts == null) // 模块存在表示可以浏览(用户中心无法设置浏览权限);
//			return new ArrayList<String>();
//		return opts;
//	}
//
//	/**
//	 * 
//	 * @param name
//	 *            ref DataAccessEnum
//	 * @return
//	 */
//	public static List<String> getAccessData(String name) {
//		if( getProvider()== null )
//			return null;
//		return getDataAccessProvider().getAccessData(name);
//	}
//
//	public static String getAccessDataString(String name) {
//		List<String> result = getAccessData(name);
//		if (result == null)
//			return null;
//		return org.apache.commons.lang.StringUtils.join(result, ",");
//	}
}
