package stat;

public class SingletonClient {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Singleton singleton = Singleton.getInstance();
	}
}
