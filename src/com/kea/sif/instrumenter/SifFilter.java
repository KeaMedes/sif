package com.kea.sif.instrumenter;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Instruction;
import org.apache.log4j.Logger;

import com.kea.sif.data.FilterParam;
import com.kea.sif.util.ClassManager;

public class SifFilter {
	private static final Logger LOG = Logger.getLogger(SifFilter.class);

	/**
	 * filter classes with respect to setClass full name of className is
	 * required here
	 * 
	 * @param classManager
	 * @param jclzz
	 * @param fParam
	 * @return
	 */
	public static boolean filterByClass(ClassManager classManager,
			JavaClass jclzz, FilterParam fParam) {
		LOG.debug("source class name : " + jclzz.getClassName()
				+ " target class name : " + fParam);
		if (fParam == null || fParam.getClassName() == null
				|| fParam.getClassName().equals("*")
				|| fParam.getClassName().isEmpty()) {
			return true;
		}
		if (classManager.isChildClass(jclzz.getClassName(),
				fParam.getClassName())) {
			return true;
		}
		return false;
	}

	/**
	 * filter method with respect to setMethod
	 * 
	 * @param method
	 * @param fParam
	 * @return
	 */
	public static boolean filterByMethod(Method method, FilterParam fParam) {
		LOG.debug("source method name : " + method.getName()
				+ " target method name : " + fParam);
		if (fParam == null || fParam.getMethodName() == null
				|| fParam.getMethodName().equals("*")
				|| fParam.getMethodName().isEmpty()) {
			return true;
		}
		if (method.getName().equals(fParam.getMethodName())) {
			return true;
		}
		return false;
	}

	/**
	 * filter bytecode with respect to setByteCode we get the bycode of
	 * instruction by toString(constpool)
	 * 
	 * @param constPool
	 * @param instruction
	 * @param filterByteCode
	 * @return
	 */
	public static boolean filterByByteCode(ConstantPool constPool,
			Instruction instruction, String filterByteCode) {
		LOG.debug("source instruction bytecode : "
				+ instruction.toString(constPool) + " target filterByteCode : "
				+ filterByteCode);
		if (filterByteCode == null || filterByteCode.equals("*")
				|| filterByteCode.isEmpty()) {
			return true;
		}
		if (instruction.toString(constPool).matches(filterByteCode)) {
			return true;
		}
		return false;
	}
}
