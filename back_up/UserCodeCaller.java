import org.apache.bcel.Constants;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CompoundInstruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

class UserCodeCaller implements CompoundInstruction{
	private InstructionList ilist;
	private void create_ilist(ClassGen cgen, String class_name, String method_name, ArgumentType arg, int method_id,  int pos){
		ilist = new InstructionList();
		InstructionFactory ifac = new InstructionFactory(cgen);
		ilist.append(ifac.createNew(class_name));
		ilist.append(InstructionConstants.DUP);
		ilist.append(InstructionConstants.DUP);
		ilist.append(ifac.createInvoke(class_name, "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
		//ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, Type.NO_ARGS, Constants.INVOKEVIRTUAL));	
		//push arguments to stack
		if (arg == ArgumentType.CP_NO_ARGS){
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		} else if (arg == ArgumentType.CP_MID_ARGS){	//method id
			ilist.append(new PUSH(cgen.getConstantPool(), method_id));
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, new Type[]{Type.INT}, Constants.INVOKEVIRTUAL));
		} else if (arg == ArgumentType.CP_MID_POS_ARGS){
			ilist.append(new PUSH(cgen.getConstantPool(), method_id));
			ilist.append(new PUSH(cgen.getConstantPool(), pos));
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, new Type[]{Type.INT, Type.INT}, Constants.INVOKEVIRTUAL));
		} else if (arg == ArgumentType.CP_MN_ARGS){//method name
			ilist.append(new PUSH(cgen.getConstantPool(), method_name));
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, new Type[]{Type.STRING}, Constants.INVOKEVIRTUAL));
		}else if (arg == ArgumentType.CP_CN_ARGS){//class name
			ilist.append(new PUSH(cgen.getConstantPool(), class_name));
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, new Type[]{Type.STRING}, Constants.INVOKEVIRTUAL));
		}else if (arg == ArgumentType.CP_CN_MN_ARGS){
			ilist.append(new PUSH(cgen.getConstantPool(), class_name));
			ilist.append(new PUSH(cgen.getConstantPool(), method_name));
			ilist.append(ifac.createInvoke(class_name, method_name, Type.VOID, new Type[]{Type.STRING, Type.STRING}, Constants.INVOKEVIRTUAL));
		}else {
			System.out.println("Fuck you!!!");
		}		
	}
	public UserCodeCaller(ClassGen cgen, String class_name, String method_name, ArgumentType arg, int method_id,  int pos){
		create_ilist(cgen, class_name, method_name, arg, method_id, pos);
	}
	public UserCodeCaller(ClassGen cgen, String class_name, String method_name, ArgumentType arg){
		create_ilist(cgen, class_name, method_name, arg, 0, 0);
	}
	public UserCodeCaller(ClassGen cgen, String class_name, String method_name, ArgumentType arg, int method_id){
		create_ilist(cgen, class_name, method_name, arg, method_id, 0);	
	}
	public InstructionList getInstructionList(){
		return ilist;
	}
}