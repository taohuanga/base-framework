package com.towcent.base.dal.auth;

import java.util.List;

public interface AuthorizationProvider {
	
	// List<String> get
		
	void clear();
	
	List<String> getRoles();
	
}
