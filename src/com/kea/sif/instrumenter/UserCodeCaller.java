package com.kea.sif.instrumenter;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CompoundInstruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.Type;
import org.apache.log4j.Logger;

import com.kea.sif.data.InstrumenterParam;


/**
 * generate user code caller
 * specified items:
 * 	Source class, method, position
 * 	Target class, method, instruction
 * We do not support argument in this version
 * @author Kea
 *
 */
public class UserCodeCaller implements CompoundInstruction{
	private static final Logger LOG = Logger.getLogger(UserCodeCaller.class);
	private String mSourceClassName;
	private String mSourceMethodName;
	private InstructionList mInstructionList;
	public UserCodeCaller(String sourceClassName, String sourceMethodName){
		this.mSourceClassName = sourceClassName;
		this.mSourceMethodName = sourceMethodName;
	}
	public UserCodeCaller(InstrumenterParam iParam) {
		this.mSourceClassName = iParam.getClassName();
		this.mSourceMethodName = iParam.getMethodName();
	}
	/**
	 * generate the instruments for calling code
	 * besides, it will change the const pool of cgen,
	 * which is the template of new class
	 * @param cgen
	 * @return
	 */
	public UserCodeCaller generateCallerCode(ClassGen cgen){
		mInstructionList = new InstructionList();
		//while cgen is passed as construction-args of ifac
		//every time you call functions of ifac, cgen may be
		//modified, which is of course disgusting
		InstructionFactory ifac = new InstructionFactory(cgen);
		//new an target object
		mInstructionList.append(ifac.createNew(mSourceClassName));
		//dup another two object for stack
		mInstructionList.append(InstructionConstants.DUP);
		mInstructionList.append(InstructionConstants.DUP);
		//invoke init function of new object
		mInstructionList.append(ifac.createInvoke(mSourceClassName, "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
		//invoke target method of object
		mInstructionList.append(ifac.createInvoke(mSourceClassName, mSourceMethodName, Type.VOID, Type.NO_ARGS, Constants.INVOKEINTERFACE));
		
		return this;
	}
	@Override
	public InstructionList getInstructionList() {
		return mInstructionList;
	}
}
