package mypackage;

public class MyAnimal {
	private static MyAnimal _instance;
	public String type = "";
	
	private MyAnimal() {
	}
	
	public static MyAnimal getInstance() {
		if (_instance == null) {
			_instance = new MyAnimal();
		}
		return _instance;
	}
}
