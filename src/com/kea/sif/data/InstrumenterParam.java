package com.kea.sif.data;

import java.util.Arrays;

public class InstrumenterParam {
	private String mClassName;
	private String mMethodName;
	private String mPosition;
	private String[] mArguments;
	private String[] mArgumentTypes;
	private String mReturnType;
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
	public String getPosition() {
		return mPosition;
	}
	public void setPosition(String mPosition) {
		this.mPosition = mPosition;
	}
	public String[] getArguments() {
		return mArguments;
	}
	public void setArguments(String[] mArguments) {
		this.mArguments = mArguments;
	}
	public String[] getArgumentTypes() {
		return mArgumentTypes;
	}
	public void setArgumentTypes(String[] mArgumentTypes) {
		this.mArgumentTypes = mArgumentTypes;
	}
	public String getReturnType() {
		return mReturnType;
	}
	public void setReturnType(String mReturnType) {
		this.mReturnType = mReturnType;
	}
	@Override
	public String toString() {
		return "InstrumenterParam [mClassName=" + mClassName + ", mMethodName="
				+ mMethodName + ", mPosition=" + mPosition + ", mArguments="
				+ Arrays.toString(mArguments) + ", mArgumentTypes="
				+ Arrays.toString(mArgumentTypes) + ", mReturnType="
				+ mReturnType + "]";
	}
}
