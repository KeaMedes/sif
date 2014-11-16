import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

public class BCELTiming{
	private static void addWrapper(ClassGen cgen, Method method){
		//set up construction tool
		InstructionFactory ifac = new InstructionFactory(cgen);
		InstructionList ilist = new InstructionList();
		ConstantPoolGen pgen = cgen.getConstantPool();
		String cname = cgen.getClassName();
		MethodGen wrapgen = new MethodGen(method, cname, pgen);
		wrapgen.setInstructionList(ilist);
		
		//rename a copy of original method
		MethodGen methgen = new MethodGen(method, cname, pgen);
		cgen.removeMethod(method);
		String iname = methgen.getName()+"$impl";
		methgen.setName(iname);
		cgen.addMethod(methgen.getMethod());

		/*
		*return value of impl
		*time before invoke_h
		*time before invoke_l
		*int
		*this
		*/
		//compute the size of calling parameters
		Type[] types = methgen.getArgumentTypes();
		int slot = methgen.isStatic() ? 0 : 1;
		for (int i=0; i<types.length; i++){
			slot += types[i].getSize();
		}
		
		//save time prior to invocation
		ilist.append(ifac.createInvoke("java.lang.System", "currentTimeMillis", Type.LONG, Type.NO_ARGS, Constants.INVOKESTATIC));
		ilist.append(InstructionFactory.createStore(Type.LONG, slot));
		
		//call the wrapper method
		int offset = 0;
		short invoke = Constants.INVOKESTATIC;
		if (!methgen.isStatic()){
			ilist.append(InstructionFactory.createLoad(Type.OBJECT, 0));
			offset = 1;
			invoke = Constants.INVOKEVIRTUAL;
		}
		for (Type type : types){
			ilist.append(InstructionFactory.createLoad(type, offset));
			offset += type.getSize();
		}
		Type result = methgen.getReturnType();
		ilist.append(ifac.createInvoke(cname, iname, result, types, invoke));
		
		//store result for return later
		if (result != Type.VOID){
			//ilist.append(InstructionFactory.createStore(result,slot+4));
			ilist.append(InstructionFactory.createStore(Type.STRING,slot+2));
		}
		
		//print time required for method call
		ilist.append(ifac.createFieldAccess("java.lang.System","out" , new ObjectType("java.io.PrintStream"), Constants.GETSTATIC));
		ilist.append(InstructionConstants.DUP);
		ilist.append(InstructionConstants.DUP);
		String text = "Call to method" + methgen.getName() + "took ";
		ilist.append(new PUSH(pgen, text));
		ilist.append(ifac.createInvoke("java.io.PrintStream", "print", Type.VOID, new Type[]{Type.STRING}, Constants.INVOKEVIRTUAL));
		ilist.append(ifac.createInvoke("java.lang.System", "currentTimeMillis", Type.LONG, Type.NO_ARGS, Constants.INVOKESTATIC));
		ilist.append(InstructionFactory.createLoad(Type.LONG, slot));
		ilist.append(InstructionConstants.LSUB);
		ilist.append(ifac.createInvoke("java.io.PrintStream", "print", Type.VOID, new Type[]{Type.LONG}, Constants.INVOKEVIRTUAL));
		ilist.append(new PUSH(pgen, "ms"));
		ilist.append(ifac.createInvoke("java.io.PrintStream", "println", Type.VOID, new Type[]{Type.STRING}, Constants.INVOKEVIRTUAL));
		
		//return result from wrapper method call
		if (result != Type.VOID){
			//ilist.append(InstructionFactory.createLoad(result, slot+2));
			ilist.append(InstructionFactory.createLoad(Type.STRING, slot+2));
		}
		ilist.append(InstructionFactory.createReturn(result));
		
		//finalize the constructed method
		wrapgen.stripAttributes(true);
		wrapgen.setMaxStack();
		wrapgen.setMaxLocals();
		wrapgen.removeCodeAttributes();
		cgen.addMethod(wrapgen.getMethod() );
		ilist.dispose();
	}
	public static void main(String[] argv){
		if (argv.length == 2 && argv[0].endsWith("class")){
			try {
				JavaClass jclas = new ClassParser(argv[0]).parse();
				ClassGen cgen = new ClassGen(jclas);
				Method[] methods = jclas.getMethods();
				for (Method m : methods){
					if (m.getName().equals(argv[1])){
						addWrapper(cgen, m);
						FileOutputStream fos = new FileOutputStream(argv[0]);
						cgen.getJavaClass().dump(fos);
						fos.close();
						break;
					}
				}
			}catch (IOException e){
				e.printStackTrace(System.err);
			}
		} else {
			System.out.println("Invalid Format");
		}
	}
}