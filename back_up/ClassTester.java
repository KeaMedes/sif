public class ClassTester{
	public static void main(String[] argc){
		ClassContainer ct = new ClassContainer();
		ct.set_root_dir("tv");
		//ct.set_filter("BannerIndicator");
		ct.get_all_classes();
		while(ct.has_next()){
			String class_name = ct.get_next();
			MethodContainer mt = new MethodContainer();
			mt.set_class_name(class_name);
			mt.set_method_filter("onCreate");
			mt.get_all_methods();
			while(mt.has_next()){
				System.out.println(class_name + "->" + mt.get_next());
			}
		}
	}
}