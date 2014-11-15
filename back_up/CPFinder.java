import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.InstructionHandle;

class CPFinder{
	private ClassContainer m_ct;
	private String m_root_dir;
	private String m_method_filter;
	private String m_bytecode_filter;
	private MethodTable m_mtable;
	public CPFinder(String root_dir){
		this.m_root_dir = root_dir;
	}
	public void setClass(String class_name){
		m_ct = new ClassContainer();
		m_ct.set_root_dir(this.m_root_dir);
		m_ct.set_filter(class_name);
	}
	public void setMethod(String metho_name){
		this.m_method_filter = metho_name;
	}
	public void setByteCode(String bytecode_filter){
		this.m_bytecode_filter = bytecode_filter;
	}
	public void apply(String user_class_name, String user_method_name, ArgumentType arg_type){
		m_ct.get_all_classes();
		while(m_ct.has_next()){
			String className = m_ct.get_next();
			MethodContainer mt = new MethodContainer();
			mt.set_class_name(className);
			if (this.m_method_filter != null)
				mt.set_method_filter(this.m_method_filter);
			mt.get_all_methods();
			while(mt.has_next()){
				Method method = mt.get_next();
				m_mtable.insert(method.getSignature(), className);
				BytecodeContainer bct = new BytecodeContainer();
				bct.set_class_name(className);
				bct.set_method(method);
				if (this.m_bytecode_filter != null)
					bct.set_bytecode_filter(this.m_bytecode_filter);
				bct.list_all_insts();
				if (!bct.is_empty()){
					UserCodeCaller usercode = new UserCodeCaller(bct.m_cgen, user_class_name, user_method_name, ArgumentType.CP_MID_ARGS, 
						m_mtable.get_id(method.getSignature()));
					while (bct.has_next()){
						InstructionHandle[] match = bct.get_next();
						bct.m_ilist.insert(match[0], usercode.getInstructionList());
					}
				}
			}
		}	
	}
}