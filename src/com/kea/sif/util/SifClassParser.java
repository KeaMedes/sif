package com.kea.sif.util;

import java.io.File;
import java.io.IOException;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.Repository;
import org.apache.bcel.util.SyntheticRepository;

public class SifClassParser {
    private static Repository mRepo = SyntheticRepository.getInstance();
    public static JavaClass parse(final File file) throws ClassFormatException, IOException{
        JavaClass jClass = new ClassParser(file.getName()).parse();
        return jClass;
    }
}