
package com.elusivehawk.util.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class MathConst
{
	public static final int X = 0, A = 0;
	public static final int Y = 1, B = 1;
	public static final int Z = 2, C = 2;
	public static final int W = 3, D = 3;
	
	public static final int[] XZY = {X, Z, Y};
	public static final int[] ZXY = {Z, X, Y};
	public static final int[] ZYX = {Z, Y, X};
	public static final int[] YZX = {Y, Z, X};
	public static final int[] YXZ = {Y, X, Z};
	
	public static final int X_BITMASK = 0b0001;
	public static final int Y_BITMASK = 0b0010;
	public static final int Z_BITMASK = 0b0100;
	public static final int W_BITMASK = 0b1000;
	
	public static final int[] BITMASKS = {X_BITMASK, Y_BITMASK, Z_BITMASK, W_BITMASK};
	
	public static final int XY =	X_BITMASK | Y_BITMASK;
	public static final int XZ =	X_BITMASK | Z_BITMASK;
	public static final int XYZ =	X_BITMASK | Y_BITMASK | Z_BITMASK;
	public static final int XYZW =	X_BITMASK | Y_BITMASK | Z_BITMASK | W_BITMASK;
	
	public static final VectorD X_AXIS_D = (VectorD)new VectorD(1, 0, 0).setImmutable();
	public static final VectorF X_AXIS_F = (VectorF)new VectorF(1f, 0f, 0f).setImmutable();
	
	public static final VectorD Y_AXIS_D = (VectorD)new VectorD(0, 1, 0).setImmutable();
	public static final VectorF Y_AXIS_F = (VectorF)new VectorF(0f, 1f, 0f).setImmutable();
	
	public static final VectorD Z_AXIS_D = (VectorD)new VectorD(0, 0, 1).setImmutable();
	public static final VectorF Z_AXIS_F = (VectorF)new VectorF(0f, 0f, 1f).setImmutable();
	
	public static final VectorD[] AXES_D = new VectorD[]{X_AXIS_D, Y_AXIS_D, Z_AXIS_D};
	public static final VectorF[] AXES_F = new VectorF[]{X_AXIS_F, Y_AXIS_F, Z_AXIS_F};
	
	private MathConst(){}
	
}
