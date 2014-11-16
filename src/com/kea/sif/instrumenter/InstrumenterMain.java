package com.kea.sif.instrumenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CompoundInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.log4j.Logger;

import com.kea.sif.data.ClassFile;
import com.kea.sif.data.FilterParam;
import com.kea.sif.data.InstrumenterParam;
import com.kea.sif.util.ClassManager;
import com.kea.sif.util.FileLister;
import com.kea.sif.util.FileUtil;
import com.kea.sif.util.SifConfiguration;

public class InstrumenterMain {
	private static final Logger LOG = Logger.getLogger(InstrumenterMain.class);
	private ClassManager mClassManager = ClassManager.getInstance();
	private List<ClassFile> mClassFiles = new ArrayList<ClassFile>();
	private String mNewPathPrefix;
	private String mOldPathPrefix;
	private Integer mThreadCount;

	public InstrumenterMain() {
		SifConfiguration configuration = SifConfiguration.getInstance();
		mNewPathPrefix = configuration.getNewPathPrefix();
		mOldPathPrefix = configuration.getOldPathPrefix();
		mThreadCount = configuration.getThreadCount();
	}

	/**
	 * parse the whole project, build ClassManager mainly
	 * 
	 * @param rootDir
	 * @return
	 */
	public boolean parseDirectory(String rootDir) {
		Path rootPath = Paths.get(rootDir);

		try {
			// list files
			List<File> files = FileLister.listFiles(rootPath);

			// parse file to javaclass
			for (File file : files) {
				if (isClassFile(file)) {
					JavaClass jclaa = new ClassParser(file.getAbsolutePath())
							.parse();
					ClassFile jclzz = new ClassFile(jclaa,
							file.getAbsolutePath());
					LOG.debug("change file : " + file.getAbsoluteFile()
							+ " to : " + jclzz.getClassName());
					mClassFiles.add(jclzz);
				}
			}

			// register into classmanager
			for (JavaClass jclzz : mClassFiles) {
				mClassManager.register(jclzz);
			}
		} catch (IOException e) {
			LOG.error("fail in parsing directory", e);
			return false;
		}
		return true;
	}

	public void apply(FilterParam fParam, InstrumenterParam iParam) {
		for (ClassFile clzzFile : mClassFiles) {
			if (SifFilter.filterByClass(mClassManager, clzzFile, fParam)) {
				LOG.debug("filterByClassName - find a class : "
						+ clzzFile.getClassName());
				ClassGen cgen = getClassGenByMethodName(clzzFile, fParam,
						iParam);
				if (!FileUtil.dump(cgen, clzzFile, mOldPathPrefix,
						mNewPathPrefix)) {
					LOG.error("fail in dump new generated file");
					return;
				}
			} else {
				LOG.debug("filterByClassName - dump original file to new dir");
				if (!FileUtil.dump(clzzFile, mOldPathPrefix, mNewPathPrefix)) {
					LOG.error("fail in copy the old file to new dir");
					return;
				}
			}
		}
		LOG.debug("apply done!");
	}

	private ClassGen getClassGenByMethodName(ClassFile clzzFile,
			FilterParam fParam, InstrumenterParam iParam) {
		ClassGen cgen = new ClassGen(clzzFile);
		for (Method method : clzzFile.getMethods()) {
			if (method != null && SifFilter.filterByMethod(method, fParam)) {
				LOG.debug("filterByMethodName - find a method : "
						+ method.getName());
				// create a method template
				MethodGen methodGen = new MethodGen(method,
						cgen.getClassName(), cgen.getConstantPool());
				// remove old method
				cgen.removeMethod(method);
				// modify method
				modifyMethodByByteCode(cgen, methodGen, fParam, iParam);
				// TODO
				cgen.addMethod(methodGen.getMethod());
			}
		}
		return cgen;
	}

	private void modifyMethodByByteCode(ClassGen cgen, MethodGen methodGen,
			FilterParam fParam, InstrumenterParam iParam) {
		LOG.debug(cgen.getClassName() + "----"
				+ methodGen.getMethod().getName());
		if (methodGen.getInstructionList() != null
				&& methodGen.getInstructionList().getInstructions() != null) {
			//TODO
			//things may be wrong here, for we change the instructionList
			for (Instruction instruction : methodGen.getInstructionList()
					.getInstructions()) {
				LOG.debug(instruction.toString(cgen.getConstantPool()
						.getConstantPool()));
				if (SifFilter.filterByByteCode(cgen.getConstantPool()
						.getConstantPool(), instruction, fParam)) {
					InstructionList instructionList = new UserCodeCaller(iParam)
							.generateCallerCode(cgen).getInstructionList();
					
				}
			}
		}
	}

	private boolean isClassFile(File file) {
		return file.getName().endsWith(".class");
	}
}
