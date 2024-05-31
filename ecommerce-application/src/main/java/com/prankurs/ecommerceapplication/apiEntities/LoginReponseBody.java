package com.prankurs.ecommerceapplication.apiEntities;

public class LoginReponseBody
{

	private String jwt;

	private boolean success = false;

	private String failureReason;

	public LoginReponseBody(String jwt)
	{
		super();
		this.jwt = jwt;
	}

	public LoginReponseBody()
	{
		super();
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getFailureReason()
	{
		return failureReason;
	}

	public void setFailureReason(String failureReason)
	{
		this.failureReason = failureReason;
	}

	public String getJwt()
	{
		return jwt;
	}

	public void setJwt(String jwt)
	{
		this.jwt = jwt;
	}

}
