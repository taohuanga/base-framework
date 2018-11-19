package com.towcent.base.common.constants;

/**
 * 基础常量类
 * @author huangtao
 *
 */
public class BaseConstant {
	
	/** 是 */
	public static final String YES = "1";
	/** 否 */
	public static final String NO = "0";
	
	/** 删除标记(0:正常, 1:删除) */
	public static final String DEL_FLAG_0 = "0";
	/** 删除标记(0:正常, 1:删除) */
	public static final String DEL_FLAG_1 = "1";
	
	
	/** 缓存的序列号 */
	public final static int CACHED_SIZE = 10;
	/** 缓存的序列号用完了 */
	public final static  String NO_CACHED_NO = "no cached no";
	/** 根据resetMode (0:永不重置 1:按天重置 2:按月重置 3:按年重置)，需要重置日期了 */
	public final static  String NEED_RESET = "need reset";
	
	/** 手机操作系统(1:ios 2:安卓 3:h5) **/
	public static final String SYS_TYPE_1 = "1";
	/** 手机操作系统(1:ios 2:安卓 3:h5) **/
	public static final String SYS_TYPE_2 = "2";
	/** 手机操作系统(1:ios 2:安卓 3:h5) **/
	public static final String SYS_TYPE_3 = "3";

	/** APP类型(1:一体机 2:医生 3:患者 **/
	public static final byte APP_TYPE_1 = '1';
	/** APP类型(1:一体机 2:医生 3:患者 **/
	public static final byte APP_TYPE_2 = '2';
	/** APP类型(1:一体机 2:医生 3:患者 **/
	public static final byte APP_TYPE_3 = '3';

	
	/** 参数签名 */
	public static final String SIGN = "sign";

	/** appKey */
	public static final String APPKEY = "appKey";
	
	/** 登录标识 */
	public static final String TOKENID = "tokenId";

	/** --------------------------------- */
	/** ------ 返回码定义 -------- */
	/** --------------------------------- */
	
	/** 成功 */
	public static final String E_000 = "000";
	/** 失败 */
	public static final String E_001 = "001";
	/** 参数格式错误 */
	public static final String E_002 = "002";
	/** 缺少参数 */
	public static final String E_003 = "003";
	/** 用户session失效 */
	public static final String E_004 = "004";
	/** 不支持的接口 */
	public static final String E_005 = "005";
	/** 其他消息 */
	public static final String E_006 = "006";
	/** */
	public static final String E_101 = "101";
	/** 账户禁止登录 */
	public static final String E_102 = "102";
	/** 登录失败(用户名不存在) */
	public static final String E_103 = "103";
	/** 登录失败(密码错误) */
	public static final String E_104 = "104";
	/** sign签名不正确 */
	public static final String E_105 = "105";
	/** 系统内部异常 */
	public static final String E_107 = "107";
	/** 账户不允许在App登录 */
	public static final String E_108 = "108";
	
	
	/** 统一后缀 */
	public static final String JPG = ".jpg";
	/** 后台页面展示小缩略图 */
	public static final String SJPG = "_s.jpg";
	
	public static final String XML = "xml";
	public static final String JSON = "json";
	public static final String UTF8 = "utf-8";
	
	/** 排序[desc:降序  asc:升序] */
	public static final String DESC = "desc";
	/** 排序[desc:降序  asc:升序] */
	public static final String ASC = "asc";
}
