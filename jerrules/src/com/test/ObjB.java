package com.test;

public class ObjB 
{
	private String someTragetProperty;
	public String getSomeTragetProperty() 
	{
		return someTragetProperty;
	}
	public void setSomeTargetProperty(String someTragetProperty)
	{
		this.someTragetProperty = someTragetProperty;
	}
	
	private int someOtherTargetProperty;
	public int getSomeOtherTargetProperty() 
	{
		return someOtherTargetProperty;
	}
	public void setSomeOtherTargetProperty(int someOtherTargetProperty)
	{
		this.someOtherTargetProperty = someOtherTargetProperty;
	}
	
	private ObjC objC;
	public ObjC getObjC() 
	{
		return objC;
	}
	public void setSomeObject(ObjC objC)
	{
		this.objC = objC;
	}
}
