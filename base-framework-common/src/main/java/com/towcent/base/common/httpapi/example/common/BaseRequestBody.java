/**
 * 
 */
package com.towcent.base.common.httpapi.example.common;

import com.towcent.base.common.httpapi.model.RequestBody;

/**
 * @author shiwei
 *
 */
public class BaseRequestBody extends RequestBody
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String token;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    } 
}
