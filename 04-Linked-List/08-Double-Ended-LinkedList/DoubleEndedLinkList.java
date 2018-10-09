package question1;

public class DoubleEndedLinkList {
	
	private Link firstRef;
	private Link lastRef;
	
	public DoubleEndedLinkList() {
		
		firstRef = null;
		lastRef = null;
		
	}
	
	
	//isEmpty method
	public boolean isEmpty() {
		
		
		if(firstRef == null) {
			
			return true;
			
		}else
			return false;
	}
	
	
	//display method
	public void displayList(){
		
		
		Link current = firstRef;
		
		while(current != null) {
			
			current.displayDetails();
			current = current.nextRef;
			
		}
		System.out.print(" ");
		
	}
	
	
	public void insertFirst(String name, double average) {
		
		Link newLink = new Link(name, average);
		
		if(isEmpty())
			lastRef = newLink;
		else	
			newLink.nextRef = firstRef;
		
		firstRef = newLink;
		
		
	}
	
	public void insertLast(String name, double average ) {
		
		
		Link newLink = new Link(name, average);
		if(isEmpty())
			firstRef = newLink;
		else
			lastRef.nextRef = newLink;
			lastRef = newLink;
			
		lastRef = newLink;
		
		
	}
	
	
	public Link deleteFirst() {
		
		Link tempVariable = firstRef;
		if(firstRef.nextRef == null)
			lastRef = null;
		
		
		firstRef = firstRef.nextRef;
		return tempVariable;
	
	}
	
	
	

	

}
