
package com.elusivehawk.util.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class TaskURL extends Task
{
	protected final String address;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskURL(String url, ITaskListener tlis)
	{
		super(tlis);
		address = url.replace("\\", "/");
		
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
}
