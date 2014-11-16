package com.kea.sif.data;

import java.io.File;
import java.util.List;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 * add absolutepath for javaclass
 * 
 * @author Kea
 * 
 */
public class ClassFile extends JavaClass {
	private String mAbsolutePath;

	public ClassFile(int class_name_index, int superclass_name_index,
			String file_name, int major, int minor, int access_flags,
			ConstantPool constant_pool, int[] interfaces, Field[] fields,
			Method[] methods, Attribute[] attributes, byte source) {
		super(class_name_index, superclass_name_index, file_name, major, minor,
				access_flags, constant_pool, interfaces, fields, methods,
				attributes, source);
	}

	public ClassFile(int class_name_index, int superclass_name_index,
			String file_name, int major, int minor, int access_flags,
			ConstantPool constant_pool, int[] interfaces, Field[] fields,
			Method[] methods, Attribute[] attributes) {
		super(class_name_index, superclass_name_index, file_name, major, minor,
				access_flags, constant_pool, interfaces, fields, methods,
				attributes);
	}

	public ClassFile(JavaClass jclaa, String path) {
		super(jclaa.getClassNameIndex(), jclaa.getSuperclassNameIndex(), jclaa
				.getFileName(), jclaa.getMajor(), jclaa.getMinor(), jclaa
				.getAccessFlags(), jclaa.getConstantPool(), jclaa
				.getInterfaceIndices(), jclaa.getFields(), jclaa.getMethods(),
				jclaa.getAttributes(), jclaa.getSource());
		this.mAbsolutePath = path;
	}

	public String getAbsolutePath() {
		return mAbsolutePath;
	}

	public void setAbsolutePath(String mAbsolutePath) {
		this.mAbsolutePath = mAbsolutePath;
	}
}
