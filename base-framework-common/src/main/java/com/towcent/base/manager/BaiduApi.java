package com.towcent.base.manager;

import java.util.HashMap;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.BankInfoDo;
import com.towcent.base.common.model.IdcardDto;

/**
 * 百度接口
 */
public interface BaiduApi {
    
	/**
     * 身份证识别
     * @return
     * @throws RpcException
     */
    public IdcardDto getIdcard(String imgPath, Boolean isFront, HashMap<String,String> options) throws RpcException;
    
    /**
     * 银行卡识别
     * @return
     * @throws RpcException
     */
    public BankInfoDo getBank(String imagePath) throws RpcException;
}
