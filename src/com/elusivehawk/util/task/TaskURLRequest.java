
package com.elusivehawk.util.task;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import com.elusivehawk.util.io.ByteStreams;
import com.elusivehawk.util.storage.Pair;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskURLRequest extends TaskURL
{
	private final List<Pair<String>> props = Lists.newArrayList();
	
	private int connectTimeout = 15000, readTimeout = 15000;
	private byte[] result = null;
	
	public TaskURLRequest(ITaskListener tlis, URL adr)
	{
		super(tlis, adr);
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		URLConnection con = this.url.openConnection();
		
		con.setConnectTimeout(this.connectTimeout);
		con.setReadTimeout(this.readTimeout);
		
		for (Pair<String> prop : this.props)
		{
			con.setRequestProperty(prop.one, prop.two);
			
		}
		
		con.connect();
		
		if (con.getDoInput())
		{
			ByteStreams s = new ByteStreams(con.getInputStream());
			
			this.result = s.readAll();
			
			s.close();
			
		}
		
		return true;
	}
	
	public byte[] getResult()
	{
		return this.result;
	}
	
	public TaskURLRequest setConnectionTimeout(int ct)
	{
		this.connectTimeout = ct;
		
		return this;
	}
	
	public TaskURLRequest setReadTimeout(int rt)
	{
		this.readTimeout = rt;
		
		return this;
	}
	
	public TaskURLRequest addRequestProp(String name, String value)
	{
		this.props.add(Pair.createPair(name, value));
		
		return this;
	}
	
}
