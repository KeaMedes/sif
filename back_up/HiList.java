import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import org.apache.bcel.util.SyntheticRepository;
import org.apache.bcel.util.Repository;

public class HiList{
	public ArrayList<String> mhierarchy_names = new ArrayList<String>();
	public String mclassName;
	public Repository mrepo;
	public void construct_hierarchy(){
		while (!(mhierarchy_names.get(mhierarchy_names.size()-1)).startsWith("android")){
			String className = mhierarchy_names.get(mhierarchy_names.size()-1);
			try{
				String superclassName = mrepo.loadClass(className).getSuperclassName();
				mhierarchy_names.add(superclassName);
			}catch (ClassNotFoundException e){
				e.printStackTrace(System.err);
				break;
			}
		}
	}
	private void show(){
		System.out.print(mclassName + "->");
		for (String name : mhierarchy_names){
			System.out.print(name + "->");
		}
		System.out.println();
	}
	public static void main(String[] args){
		if (args.length == 1 && args[0].endsWith(".class")){
			HiList hilist = new HiList();
			hilist.mrepo = SyntheticRepository.getInstance();
			hilist.mclassName = args[0];
			try{
				JavaClass jclzz = new ClassParser(args[0]).parse();
				JavaClass[] ifs = jclzz.getAllInterfaces();
				for (JavaClass jc : ifs)
					System.out.println(jc.getClassName());
				//String superclassName = "."+jclzz.getSuperclassName();
				//hilist.mhierarchy_names.add(superclassName);
			}catch (IOException e){
				e.printStackTrace(System.err);
			}catch (ClassNotFoundException e){
				e.printStackTrace(System.err);
			}
			//hilist.construct_hierarchy();
			//hilist.show();
		}else {
			System.out.println("Invalid format");
		}
	}
}