import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

class MethodContainer{
	private ArrayList<Method> m_methods = new ArrayList<Method>();
	private int m_index = 0;
	private String method_filter;
	private String class_name;
	public void set_method_filter(String filter){
		this.method_filter = filter;
	}
	public void set_class_name(String name){
		this.class_name = name;
	}
	public boolean has_next(){
		return m_index < m_methods.size();
	}
	public Method get_next(){
		m_index++;
		return m_methods.get(m_index-1);
	}
	boolean agree(String method_name){
		if (method_filter == null)
			return true;
		else{
			return method_name.equals(method_filter);
		}
	}
	public void get_all_methods(){
		try{
			JavaClass jclzz = new ClassParser(class_name).parse();
			Method[] mths = jclzz.getMethods();
			for (Method mt : mths){
				String method_name = mt.getName();
				if (agree(method_name))
					m_methods.add(mt);
			}
		}catch (IOException e){
			e.printStackTrace(System.err);
		}
	}
}