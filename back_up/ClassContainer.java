import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import org.apache.bcel.util.SyntheticRepository;
import org.apache.bcel.util.Repository;

class ClassContainer{
	public ArrayList<String> m_classes = new ArrayList<String>();
	private String class_filter;
	private String root_dir;
	private int m_classes_index = 0;
	public void set_filter(String filter){
		this.class_filter = filter;
	}
	public boolean has_next(){
		return m_classes_index < m_classes.size();
	}
	public String get_next(){
		m_classes_index++;
		return m_classes.get(m_classes_index-1);
	}
	public int get_size(){
		return m_classes.size();
	}
	public void set_root_dir(String dir){
		this.root_dir = dir;
	}
	public boolean agree(ClassInfo cl){
		if (this.class_filter == null)
			return true;
		for (String name : cl.mhierarchy_names){
			String classname = name.substring(name.lastIndexOf(".")+1);
			//System.out.println(classname + "<->" + class_filter);
			if (classname.equals(class_filter)){
				//System.out.println(classname + "<->" + class_filter);
				return true;
			}
		}
		return false;
	}
	public void get_all_classes(){
		LinkedList<File> dir_list = new LinkedList<File>();
		File root_file = new File(root_dir);
		if (!root_file.exists()){
			System.out.println("Invalid root_dir");
			return;
		}
		dir_list.add(root_file);
		while(!dir_list.isEmpty()){
			File[] files = dir_list.getFirst().listFiles();
			for (File f : files){
				if (f.isDirectory()){
					dir_list.add(f);
				}else{
					ClassInfo clfo = new ClassInfo(f.getAbsolutePath());
					//clfo.show();
					if (agree(clfo))
						m_classes.add(f.getAbsolutePath());
				}
			}
			dir_list.removeFirst();
		}
	}
	class ClassInfo{
		public ArrayList<String> mhierarchy_names = new ArrayList<String>();
		public ClassInfo(String path){
			try{
				JavaClass jclzz = new ClassParser(path).parse();
				mhierarchy_names.add(jclzz.getClassName());
				Repository mrepo = SyntheticRepository.getInstance();
				//System.out.println(path);
				while (!(mhierarchy_names.get(mhierarchy_names.size()-1)).startsWith("android")
					&& !(mhierarchy_names.get(mhierarchy_names.size()-1)).startsWith("java")
					&& !(mhierarchy_names.get(mhierarchy_names.size()-1)).startsWith("org.apache")){
					String className = mhierarchy_names.get(mhierarchy_names.size()-1);
				//	System.out.println(className);
					try{
						String superclassName = mrepo.loadClass(className).getSuperclassName();
						mhierarchy_names.add(superclassName);
					}catch (ClassNotFoundException e){
						e.printStackTrace(System.err);
						break;
					}
				}
			}catch(IOException e){
				e.printStackTrace(System.err);
			}
		}
		public void show(){
			for (String name : mhierarchy_names){
				System.out.print(name + "->");
			}
			System.out.println();
		}
	}
}