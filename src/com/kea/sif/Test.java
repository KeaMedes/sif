package com.kea.sif;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.log4j.PropertyConfigurator;

import com.kea.sif.instrumenter.InstrumenterMain;
import com.kea.sif.util.ClassManager;
import com.kea.sif.util.FileLister;


/**
 * Outer tester of the whole project
 * @author luo yang
 *
 */
public class Test {
    public static void main(final String[] args) throws IOException{
    	//PropertyConfigurator.configure("config/log4j.properties");
    	InstrumenterMain instrumentor = new InstrumenterMain();
    	instrumentor.parseDirectory("E:\\Data\\sif\\data\\BiliPlayer-dex2jar\\tv");
    	instrumentor.apply(null, null);
    }
}