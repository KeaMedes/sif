package com.kea.sif;

import java.io.IOException;

import com.kea.sif.instrumenter.InstrumenterMain;



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