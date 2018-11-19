/**
 * 
 */
package com.towcent.base.common.httpapi.example.common;

import com.towcent.base.common.httpapi.model.Response;

/**
 * @author shiwei
 *
 */
public class BaseResponse extends Response
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean           success;

    private String            resultMessage;

    private String            resultCode;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getResultMessage()
    {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

}
