import java.util.HashMap;

class MethodTable{
	private HashMap<String, Integer > name_to_id  = new HashMap<String ,Integer>();
	private HashMap<Integer, String> id_to_name = new HashMap<Integer, String>();
	private HashMap<Integer, String> id_to_class = new HashMap<Integer, String>();
	private int inner_id = 0;
	private void get_next_inner_id(){
		inner_id++;
	}
	public boolean has(String method_name){
		return name_to_id.containsKey(method_name);
	}
	private int insert_by_default(String method_name, String class_name){
		name_to_id.put(method_name, inner_id);
		id_to_name.put(inner_id, method_name);
		id_to_class.put(inner_id, class_name);
		int rst = inner_id;
		get_next_inner_id();
		return rst;
	}
	public int insert(String method_name, String class_name){
		return insert_by_default(method_name, class_name);
	}
	public int insert(String method_name, String class_name, int id){
		return insert_by_default(method_name, class_name);
	}
	public int get_id(String method_name){
		return name_to_id.get(method_name);
	}
	public String get_name(int id){
		return id_to_name.get(id);
	}
	public String get_class(int id){
		return id_to_class.get(id);
	}
}