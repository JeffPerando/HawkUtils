
package com.elusivehawk.util;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * 
 * Static API for basic specifications regarding the current computer.
 * 
 * @author Elusivehawk
 */
public final class CompInfo
{
	private CompInfo(){}
	
	public static final boolean DEBUG = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("-agentlib:jdwp");
	
	public static final String
			JAVA_VERSION = System.getProperty("java.vm.specification.version"),
			TIME_ZONE = System.getProperty("user.timezone"),
			USERNAME = System.getProperty("user.name"),
			VENDOR = System.getProperty("java.vendor");
	public static final String[] PATH = System.getProperty("java.library.path").split(System.getProperty("path.separator"));
	public static final EnumOS OS = EnumOS.getCurrentOS();
	public static final int CORES = Runtime.getRuntime().availableProcessors();
	public static final File
			JAR_DIR = FileHelper.getRootResDir(),
			USER_DIR = new File(System.getProperty("user.home")),
			TMP_DIR = new File(System.getProperty("java.io.tmpdir"));
	
	public static final boolean BUILT = FileHelper.getExtension(JAR_DIR) != null;
	
}
