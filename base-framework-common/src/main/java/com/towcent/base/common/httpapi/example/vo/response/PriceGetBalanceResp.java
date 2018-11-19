/**
 * 
 */
package com.towcent.base.common.httpapi.example.vo.response;

import com.towcent.base.common.httpapi.example.common.BaseResponse;

/**
 * @author shiwei
 *
 */
public class PriceGetBalanceResp extends BaseResponse
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String result;

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
