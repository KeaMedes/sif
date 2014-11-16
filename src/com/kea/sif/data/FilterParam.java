package com.kea.sif.data;
/**
 * filter parameter, used to specify the filter
 * @author Kea
 *
 */
public class FilterParam {
	private String mLoops;
	private String mClassName;
	private String mMethodName;
	private String mByteCode;
	private String mPermission;
	
	public String getClassName() {
		return mClassName;
	}
	public void setClassName(String mClassName) {
		this.mClassName = mClassName;
	}
	public String getMethodName() {
		return mMethodName;
	}
	public void setMethodName(String mMethodName) {
		this.mMethodName = mMethodName;
	}
	public String getByteCode() {
		return mByteCode;
	}
	public void setByteCode(String mByteCode) {
		this.mByteCode = mByteCode;
	}
	public String getPermission() {
		return mPermission;
	}
	public void setPermission(String mPermission) {
		this.mPermission = mPermission;
	}
	public String getLoops() {
		return mLoops;
	}
	public void setLoops(String mLoops) {
		this.mLoops = mLoops;
	}
	
	@Override
	public String toString() {
		return "FilterParam [mClassName=" + mClassName + ", mMethodName="
				+ mMethodName + ", mByteCode=" + mByteCode + ", mPermission="
				+ mPermission + ", mLoops=" + mLoops + "]";
	}
	
}
