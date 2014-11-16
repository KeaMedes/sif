package com.kea.sif.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.bcel.generic.ClassGen;
import org.apache.log4j.Logger;

import com.kea.sif.data.ClassFile;

public class FileUtil {
	private static final Logger LOG = Logger.getLogger(FileUtil.class);

	/**
	 * copy the old file to new directory
	 * 
	 * @param clzzFile
	 * @return
	 */
	public static boolean dump(ClassFile clzzFile, String oldPathPrefix,
			String newPathPrefix) {
		String oldPath = clzzFile.getAbsolutePath();
		String newPath = oldPath.replace(oldPathPrefix, newPathPrefix);
		try {
			checkAndCreateDir(newPath);
			FileOutputStream fos = new FileOutputStream(newPath);
			clzzFile.dump(fos);
			fos.close();
			return true;
		} catch (IOException e) {
			LOG.error("fail in dump file", e);
			return false;
		}
	}

	/**
	 * dump the new file to new directory
	 * 
	 * @param cgen
	 * @param clzzFile
	 * @return
	 */
	public static boolean dump(ClassGen cgen, ClassFile clzzFile,
			String oldPathPrefix, String newPathPrefix) {
		String oldPath = clzzFile.getAbsolutePath();
		String newPath = oldPath.replace(oldPathPrefix, newPathPrefix);
		try {
			checkAndCreateDir(newPath);
			FileOutputStream fos = new FileOutputStream(newPath);
			cgen.getJavaClass().dump(fos);
			fos.close();
			return true;
		} catch (IOException e) {
			LOG.error("fail in dump file", e);
			return false;
		}
	}
	/**
	 * check whether the directory exists
	 * if not, create it
	 * @param path
	 */
	public static void checkAndCreateDir(String path){
		int index = path.lastIndexOf(File.separator);
		String dirPath = path.substring(0, path.lastIndexOf(File.separator));
		File file = new File(dirPath);
		if (!file.exists()){
			file.mkdirs();
		}
	}
}
