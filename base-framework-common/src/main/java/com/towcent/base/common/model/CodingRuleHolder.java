package com.towcent.base.common.model;

import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.utils.CodingRuleUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 该类用来扩展CodingRule的计算功能：能用该类生成序列号
 * 如果直接在CodingRule.java中加计算功能，会因为CodingRule如果被mybatis重新生成代码覆盖
 * 
 */
public class CodingRuleHolder {
	/**
	 * 编码规则，对应数据库中的一条记录
	 */
	private CodingRule codingRule;
	
	/**
	 * 是否允许断号（即是否允许中间丢号，内存中缓存号码时，服务器重启可能导致丢号）
	 */
	private boolean isAllowBreakNo;
	
	/**
	 * 当前序号
	 */
	private int currentSerialNo;
	
	/**
	 * 最大序号
	 */
	private int maxSerialNo;
	
	/**
	 *格式化时间 
	 */
	private String formatedDate;
	
	/**
	 * 前缀
	 */
	private String prefix;
	
	/**
	 * 缓存保持时间
	 */
	private long cacheKeepTime = 0L;
	
	/**
	 * 序列号长度
	 */
	private int serialLength = 6;
	
	/**
	 * 回收的编码
	 */
	private LinkedList<String> recycledSerialNoList = new LinkedList<String>();
	
	/**
	 * 初始化的本地时间,数据库中获取到currentSerialNo时的本地时间，用来计算需要重置日期的
	 */
	private long initLocalTime;
	
	/**
	 * 是否使用回收的编码
	 */
	private AtomicBoolean isUseRecycle = new AtomicBoolean(false);

	public AtomicBoolean getIsUseRecycle() {
		return isUseRecycle;
	}

	public CodingRule getCodingRule() {
		return codingRule;
	}

	public void setCodingRule(CodingRule codingRule) {
		this.codingRule = codingRule;
	}
	
	public CodingRuleHolder(CodingRule codingRule, long initLocalTime) {
		this.codingRule = codingRule;
		this.initLocalTime = initLocalTime;
		init();
		
	}
	
	/**
	 * 初始化日期串、cacheKeepTime值等
	 */
	private void init() {
		this.currentSerialNo = codingRule.getCurrentSerialNo();
		if(codingRule.getAllowBreakNo()) {
			maxSerialNo = currentSerialNo + BaseConstant.CACHED_SIZE - 1;
		} else {
			maxSerialNo = currentSerialNo;
		}
		formatedDate = CodingRuleUtil.getFormatedDate(codingRule.getResetMode(),codingRule.getDbTime());
		prefix = codingRule.getPrefix() == null ? "" : codingRule.getPrefix();
		initCacheKeepTime(codingRule.getResetMode());
		serialLength = codingRule.getSerialNoLength();
	}
	
	/**
	 * @param resetMode (0:永不重置 1:按天重置 2:按月重置 3:按年重置)
	 * 测试：当天的最后时刻：2014-06-25 00:00:00
     * 当月的最后时刻：2014-07-01 00:00:00
     * 当年的最后时刻：2015-01-01 00:00:00
	 */
	private void initCacheKeepTime(int resetMode) {
		Date dbTime = codingRule.getDbTime();
		//0永不更新时，cacheKeepTime无穷大
		cacheKeepTime = Long.MAX_VALUE;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dbTime);
		switch(resetMode) {
			case 1:
				//今天的最后时刻
				cacheKeepTime = 1;
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 24, 00, 00);
				break;
			case 2:
				//当月的最后时刻
				cacheKeepTime = 1;
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 24, 00, 00);
				break;
			case 3:
				//当年的最后时刻
				cacheKeepTime = 1;
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 24, 00, 00);
				cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				break;
			default:
				return;//必须return掉，否则cacheKeepTime = 0；

		}
		//缓存持续时间
		cacheKeepTime = cal.getTimeInMillis() - dbTime.getTime();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String nextSerialNo() {
		//需要重置日期了----如果应用服务器在运行中修改了服务器时间，此处计算会出问题（运维上注意）
		if((System.currentTimeMillis() - initLocalTime) >= cacheKeepTime ) {
			return BaseConstant.NEED_RESET;
		}
		
		//从回收队列取序列号
		String no = this.pop();
		if(!no.equals("")) {
			isUseRecycle.getAndSet(true);
			return no;
		}
		
		int nextNo = currentSerialNo++;

		//缓存的号用完了
		if(nextNo > maxSerialNo ) {
			return BaseConstant.NO_CACHED_NO;
		}

//		return  prefix + formatedDate + format(serialLength, nextNo);
		return format(serialLength, nextNo);
			
	}
	
	/**
	 * 对给定的数字intValue，格式化为serialLength长度，长度不够在前面补0
	 * @param serialLength
	 * @param intValue
	 * @return
	 */
	private static String format(int serialLength, int intValue) {
		String str = String.format("%0" + serialLength + "d", intValue);
		return str;
	} 
	
	public boolean isAllowBreakNo() {
		return isAllowBreakNo;
	}
	
	/**
	 * 回收序列号
	 * @param serialNo
	 */
	public void recycleSerialNo(String serialNo) {
		if(isLegalSerialNo(serialNo)) {
			push(serialNo);
		}
	}
	
	/**
	 * 外层已经对requestId加锁了，所以此处不用担心并发问题
	 * @param serialNo
	 */
	private void push(String serialNo) {
		recycledSerialNoList.addLast(serialNo);
	}
	
	/**
	 * 外层已经对requestId加锁了，所以此处不用担心并发问题
	 * @return 如果回收队列为空，返回空串
	 */
	private String pop() {
		if(recycledSerialNoList.size() > 0) {
			String no = recycledSerialNoList.pollFirst();
			return no == null?"":no;
		}
		return "";
	}
	
	/**
	 * 判断序列号是否合法
	 * @param serialNo
	 * @return
	 */
	private boolean isLegalSerialNo(String serialNo) {
		//null或者空串不合法
		if(serialNo== null || serialNo.equals("")) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
//		CodingRule rule = new CodingRule();
//		rule.setCurrentSerialNo(1);
//		rule.setResetMode(1);
//		rule.setCurDate(new Date());
//		rule.setAllowBreakNo(true);
//		CodingRuleHolder holder = new CodingRuleHolder(rule);
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("当天的最后时刻：" + df.format(holder.initResetThreshold(1)));
//		System.out.println("当月的最后时刻：" + df.format(holder.initResetThreshold(2)));
//		System.out.println("当年的最后时刻：" + df.format(holder.initResetThreshold(3)));
//		System.out.println(df.format(System.currentTimeMillis()));
//		int serialLength = 15;
//		String no = format(serialLength, 1234587484);
//		System.out.println(no);
		
		
		Date dbTime = new Date();
		//0永不更新时，cacheKeepTime无穷大
		long cacheKeepTime = Long.MAX_VALUE;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dbTime);
		
		int resetMode = 0;
		switch(resetMode) {
		case 1:
			//今天的最后时刻
			cacheKeepTime = 1;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 24, 00, 00);
			break;
		case 2:
			//当月的最后时刻
			cacheKeepTime = 1;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 24, 00, 00);
			break;
		case 3:
			//当年的最后时刻
			cacheKeepTime = 1;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 24, 00, 00);
			cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			break;
		default:
//			return;//必须return掉，否则cacheKeepTime = 0；

	}
	//缓存持续时间
	cacheKeepTime = cal.getTimeInMillis() - dbTime.getTime();
		System.out.println("cacheKeepTime:" + cacheKeepTime);

	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getFormatedDate() {
		return this.formatedDate;
	}
}
