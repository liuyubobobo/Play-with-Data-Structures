package question1;

public class Link {
	
	public Link nextRef;
	
	public String name;
	public double average;
	
	public Link(String name, double average) {
		
		
		this.name = name;
		this.average = average;
		nextRef = null;
		
		
		
	}
	
	
	public void displayDetails() {
		
		
		System.out.println("Name: " + name + " & " + "Average: " + average);
		
		
	}
	
	
	

}
