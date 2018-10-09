package question1;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		DoubleEndedLinkList del = new DoubleEndedLinkList();
		
		del.insertFirst("Sajee", 67.0);
		del.insertFirst("Suresh", 55.5);
		del.insertFirst("Dulani", 88.0);
		
		del.insertLast("Madara", 34.0);
		
		del.displayList();
		
		
	}

}
