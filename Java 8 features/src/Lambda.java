
public class Lambda {

	interface HelloWorld {
		String hello();
	}

	public static void main(String[] args) {	      
		 HelloWorld helloWorld = () -> { return "Hello " ; };
		 System.out.println(helloWorld.hello());
	}

}
