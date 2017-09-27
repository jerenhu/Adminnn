package com.ecnice.privilege.service.tokenAuthtication;

public interface ITokenAuthtication {
	public String getTokenResult(String Token,String Appkey) throws Exception;	
	public void getResetTokenResult(String Token, String Appkey) throws Exception;
}
