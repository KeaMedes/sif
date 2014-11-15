package com.kea.sif.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;
import org.apache.log4j.Logger;


public class ClassManager{
	private static Logger LOG = Logger.getLogger(ClassManager.class);
	private Map<String, String> mInheritMap =  new HashMap<String, String>(10);
	private String[] STOP_CLASS_NAME_PREFIX = {"android."};
	/**
	 * register a new class into the ClassManager
	 * we encourage to do so in the project parse phase
	 * @param jClass
	 * @return
	 */
	public boolean register(JavaClass jClass) {
		String className = jClass.getClassName();
		if (isStopClass(className)){
			LOG.debug("encouter a stoped class");
			return true;
		}
		String superClassName;
		superClassName = jClass.getSuperclassName();
		if (!mInheritMap.containsKey(className)){
			mInheritMap.put(className, superClassName);
			LOG.debug("inserted inherit relationship " + className+ " " + superClassName);
		} else {
			LOG.debug("class " + className + " has already inserted");
		}
		return true;
	}
	
	private boolean isStopClass(String className){
		for (String stopNamePrefix : STOP_CLASS_NAME_PREFIX){
			if (className.startsWith(stopNamePrefix)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check whether the class is in the inherited list of target class
	 * @param srcClassName
	 * @param targetClassName
	 * @return
	 */
	public boolean isChildClass(String srcClassName, String targetClassName){
		if (srcClassName.equals(targetClassName)){
			return true;
		}
		String imClassName = mInheritMap.get(srcClassName);
		while(imClassName != null){
			if (imClassName.equals(targetClassName)){
				return true;
			}
			imClassName = mInheritMap.get(imClassName);
		}
		return false;
	}
}
