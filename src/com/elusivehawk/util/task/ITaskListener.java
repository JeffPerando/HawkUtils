
package com.elusivehawk.util.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface ITaskListener
{
	void onTaskComplete(Task task);
	
}
