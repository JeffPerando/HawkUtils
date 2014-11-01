
package com.elusivehawk.util;

/**
 * 
 * ...Do I really need to explain it?
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IUpdatable
{
	void update(double delta) throws Throwable;
	
}
