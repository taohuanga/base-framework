package com.towcent.base.manager;

import com.towcent.base.common.exception.RpcException;

/**
 * 二维码接口
 */
public interface QrcodeApi {

    /**
     * 生成二维码
     * @param content 内容
     * @param size 大小
     * @return
     * @throws RpcException
     */
    public String createQrcode(String content, int size) throws RpcException;

}
