package com.kea.sif;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import com.kea.sif.util.ClassManager;
import com.kea.sif.util.FileLister;


/**
 * Outer tester of the whole project
 * @author luo yang
 *
 */
public class Test {
    public static void main(final String[] args) throws IOException{
    	ClassManager cm = new ClassManager();
    	final Path path = Paths.get("E:\\Data\\sif\\data\\simpleCase");
        final List<File> files = FileLister.listFiles(path);
        final JavaClass jclzz = new ClassParser(files.get(0).getAbsolutePath()).parse();
        cm.register(jclzz);
    }
}