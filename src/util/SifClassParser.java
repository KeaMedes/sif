package util;

import java.io.File;
import java.io.IOException;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.Repository;
import org.apache.bcel.util.SyntheticRepository;

import data.ClassFile;

public class SifClassParser {
    private static Repository mRepo = SyntheticRepository.getInstance();
    public static ClassFile parse(final File file) throws ClassFormatException, IOException{
        //set file
        final ClassFile classFile = new ClassFile();
        classFile.setFile(file);
        //set java class file
        final JavaClass jclzz = new ClassParser(file.getName()).parse();
        classFile.setJavaClass(jclzz);
        return classFile;
    }
    private static void setInherite(final ClassFile classFile){
        //TODO
    }
}