import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.InstructionFinder;
import org.apache.bcel.util.InstructionFinder.CodeConstraint;

public class BytecodeContainer{
	private String bytecode_filter;
	private String class_name;
	private Method m_method;
	private ArrayList<InstructionHandle[]> m_insts_iter = new ArrayList<InstructionHandle[]>();
	private CodeConstraint m_constraint;
	public InstructionList m_ilist;
	public ClassGen m_cgen;
	public MethodGen m_mgen;
	public JavaClass m_jclz;
	public ConstantPoolGen m_cpgen;
	private int m_index = 0;
	public void set_class_name(String class_name){
		this.class_name = class_name;
	}
	public void set_method(Method method){
		this.m_method = method;
	}
	public void set_bytecode_filter(String filter){
		this.bytecode_filter = filter;
	}
	public boolean has_next(){
		return m_index < m_insts_iter.size();
	}
	public InstructionHandle[] get_next(){
		m_index++;
		return m_insts_iter.get(m_index -1);
	}
	public void set_constraint(CodeConstraint cc){
		m_constraint = cc;
	}
	private void get_ilist(){
		try{
			m_jclz = new ClassParser(class_name).parse();
			m_cgen = new ClassGen(m_jclz);
			m_cpgen = m_cgen.getConstantPool();
			m_mgen = new MethodGen(m_method, class_name, m_cpgen);
			m_ilist = m_mgen.getInstructionList();
		}catch(IOException e){
			e.printStackTrace(System.err);
		}
	}
	public boolean is_empty(){
		return m_insts_iter.isEmpty();
	}
	public void list_all_insts(){
		get_ilist();
		if (bytecode_filter == null)
			return;
		InstructionFinder f = new InstructionFinder(m_ilist);
		if (m_constraint == null){
			for (Iterator e = f.search(bytecode_filter); e.hasNext();){
				m_insts_iter.add((InstructionHandle[])e.next());
			}
		}
		else{
			for (Iterator e = f.search(bytecode_filter, m_constraint); e.hasNext();){
				m_insts_iter.add((InstructionHandle[])e.next());
			}
		}
	}
}