
package com.elusivehawk.util.task;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.storage.Pair;
import com.elusivehawk.util.string.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskURLRequest extends TaskURL
{
	private final List<Pair<String>> props = new ArrayList<Pair<String>>();
	
	private int connectTimeout = 15000, readTimeout = 15000;
	private boolean followRedirect = true;
	
	private byte[] result = null;
	private int response = 0;
	
	public TaskURLRequest(String url, ITaskListener tlis)
	{
		super(url, tlis);
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		URL url = StringHelper.asURL(this.getAddress());
		
		if (url == null)
		{
			this.response = 404;
			
			return true;
		}
		
		URLConnection con = url.openConnection();
		HttpURLConnection http = (con instanceof HttpURLConnection) ? (HttpURLConnection)con : null;
		
		con.setConnectTimeout(this.connectTimeout);
		con.setReadTimeout(this.readTimeout);
		
		for (Pair<String> prop : this.props)
		{
			con.setRequestProperty(prop.one, prop.two);
			
		}
		
		if (http != null)
		{
			http.setInstanceFollowRedirects(this.followRedirect);
			
		}
		
		con.connect();
		
		if (http != null)
		{
			this.response = http.getResponseCode();
			
		}
		
		if (con.getDoInput())
		{
			InputStream in = con.getInputStream();
			
			this.result = new byte[in.available()];
			in.read(this.result);
			
			in.close();
			
		}
		
		return true;
	}
	
	public byte[] getResult()
	{
		return this.result;
	}
	
	public int getResponseCode()
	{
		return this.response;
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
	
	public TaskURLRequest setFollowRedirect(boolean fr)
	{
		this.followRedirect = fr;
		
		return this;
	}
	
}
