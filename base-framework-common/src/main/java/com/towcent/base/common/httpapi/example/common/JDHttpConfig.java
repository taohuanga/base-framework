package com.towcent.base.common.httpapi.example.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.towcent.base.common.httpapi.HttpBaseConfig;
import com.towcent.base.common.utils.Md5Utils;

import lombok.Getter;
import lombok.Setter;

/**
 * 京东Vop请求参数
 * @author huangtao
 *
 */
@Component @Setter @Getter
public class JDHttpConfig extends HttpBaseConfig {
	
	@Value("${jdvop.apiUrl}")
	protected String baseUrl;
	
	@Value("${jdvop.client_id}")
    private String clientId = "bNjuvwEloP4VlTFJQhzp";
	
	@Value("${jdvop.client_secret}")
    private String clientSecret = "MXgAKdzO3d36agxFhDXm";

    @Value("${jdvop.username}")
    private String userName = "望家欢VOP";

    @Value("${jdvop.password}")
    private String passWord = "111000";
    
    private String accessToken = "4dc651db-5b8c-4e6e-b887-2ac0b610fe35";

    /** 加密处理 */
	public String getPassWord() {
		return Md5Utils.encryption(passWord);
	}
    
}
