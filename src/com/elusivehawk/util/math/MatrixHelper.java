
package com.elusivehawk.util.math;

import static com.elusivehawk.util.math.MathConst.*;
import static com.elusivehawk.util.math.MathHelper.*;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class MatrixHelper
{
	private MatrixHelper(){}
	
	public static Matrix identity()
	{
		return new Matrix(4, 4).setIdentity();
	}
	
	public static Matrix homogenous(Vector rot, Vector scl, Vector trans)
	{
		return (Matrix)rotate(rot).mul(scale(scl)).mul(translate(trans));
	}
	
	public static Matrix homogenous(Quaternion rot, Vector scl, Vector trans)
	{
		return (Matrix)rotate(rot).mul(scale(scl)).mul(translate(trans));
	}
	
	public static Matrix projection(float fov, float aspect, float zFar, float zNear)
	{
		return projection(fov, aspect, zFar, zNear, new Matrix(4, 4));
	}
	
	public static Matrix projection(float fov, float aspect, float zFar, float zNear, Matrix dest)
	{
		float yScale = 1 / (float)Math.tan(toRadians(fov / 2f));
		float xScale = yScale / aspect;
		float frustumLength = zFar - zNear;
		
		dest.set(0, xScale);
		dest.set(5, yScale);
		dest.set(10, -((zFar + zNear) / frustumLength));
		dest.set(11, -((2 * zFar * zNear) / frustumLength));
		dest.set(14, -1f);
		
		return dest;
	}
	
	public static Matrix rotate(Vector vec)
	{
		Matrix ret = new Matrix(4, 4);
		
		setEulerZYX(ret, vec);
		
		return ret;
	}
	
	public static Matrix rotate(float x, float y, float z)
	{
		return setEulerZYX(new Matrix(), x, y, z);
	}
	
	public static Matrix setEulerZYX(Matrix mat, Vector euler)
	{
		return setEulerZYX(mat, euler.get(X), euler.get(Y), euler.get(Z));
	}
	
	public static Matrix setEulerZYX(Matrix m, float eulerX, float eulerY, float eulerZ)
	{
		float cx = (float)Math.cos(eulerX);
		float cy = (float)Math.cos(eulerY);
		float cz = (float)Math.cos(eulerZ);
		float sx = (float)Math.sin(eulerX);
		float sy = (float)Math.sin(eulerY);
		float sz = (float)Math.sin(eulerZ);
		
		float cxz = cx * cz;
		float cxsz = cx * sz;
		float sxcz = sx * cz;
		float sxz = sx * sz;
		
		m.setRow(0, cy * cz, sy * sxcz - cxsz, sy * cxz + sxz);
		m.setRow(1, cy * sz, sy * sxz + cxz, sy * cxsz - sxcz);
		m.setRow(2, -sy, cy * sx, cy * cx);
		
		return m;
	}
	
	public static Matrix rotate(Quaternion q)
	{
		return rotate(q, identity());
	}
	
	public static Matrix rotate(Quaternion q, Matrix dest)//TODO Convert into algorithm
	{
		q.normalize();
		
		float s = 2f / MathHelper.length(q);
		
		dest.set(0, 0, 1 - s * (square(q.get(Y)) + square(q.get(Z))), false);
		dest.set(1, 0, s * (q.get(X) * q.get(Y) + q.get(W) * q.get(Z)), false);
		dest.set(2, 0, s * (q.get(X) * q.get(Z) - q.get(W) * q.get(Y)), false);
		
		dest.set(0, 1, s * (q.get(X) * q.get(Y) - q.get(W) * q.get(Z)), false);
		dest.set(1, 1, 1 - s * (square(q.get(X)) + square(q.get(Z))), false);
		dest.set(2, 1, s * (q.get(Y) * q.get(Z) + q.get(W) * q.get(X)), false);
		
		dest.set(0, 2, s * (q.get(X) * q.get(Z) + q.get(W) * q.get(Y)), false);
		dest.set(1, 2, s * (q.get(Y) * q.get(Z) - q.get(W) * q.get(X)), false);
		dest.set(2, 2, 1 - s * (square(q.get(X)) + square(q.get(Y))));
		
		return dest;
	}
	
	public static Matrix scale(Vector vec)
	{
		return scale(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public static Matrix scale(float x, float y, float z)
	{
		Matrix ret = identity();
		
		ret.set(0, x);
		ret.set(5, y);
		ret.set(10, z);
		
		return ret;
	}
	
	public static Matrix translate(Vector vec)
	{
		return translate(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public static Matrix translate(float x, float y, float z)
	{
		Matrix ret = identity();
		
		ret.set(3, x);
		ret.set(7, y);
		ret.set(11, z);
		
		return ret;
	}
	
	public static Matrix lookAt(Vector pos, Vector lookAt)
	{
		return lookAt(pos, lookAt, MathConst.Y_AXIS);
	}
	
	public static Matrix lookAt(Vector pos, Vector target, Vector up)
	{
		assert pos.size() == 3 : "Position size is not 3";
		assert target.size() == 3 : "Size of target vector is not size 3";
		assert up.size() == 3 : "Upwards vector size is not 3";
		
		Vector up0 = up.clone();
		Vector forward = new Vector(4);
		
		for (int c = 0; c < 3; c++)
		{
			forward.set(c, target.get(c) - pos.get(c));
			
		}
		
		forward.normalize();
		
		Vector side = MathHelper.cross(forward, up0);
		side.normalize();
		
		MathHelper.cross(up0, side, forward);
		
		Matrix ret = identity();
		
		ret.setRow(0, side);
		ret.setRow(1, up0);
		ret.setRow(2, forward.negate());
		
		ret.invert();
		
		return ret;
	}
	
	public static Matrix ortho(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix ret = identity();
		
		ret.set(0, 0, 2 * (right - left));
		ret.set(1, 1, 2 * (top - bottom));
		ret.set(2, 2, 2 * (far - near));
		
		ret.set(3, 0, -((right + left) * (right - left)));
		ret.set(3, 1, -((top + bottom) * (top - bottom)));
		ret.set(3, 2, -((far + near) * (far - near)));
		
		ret.setIsDirty(false);
		
		return ret;
	}
	
}
