package CPUscheduling;

class Link<E>{
	public E data;
	public Link<E> next;
	
	public Link(E data){
		this.data = data;
	}
	
	public void display(){
		System.out.println(this);
	}
}


class FirstLastLinkedList<E>{
	private Link<E> first;
	private Link<E> last;
	
	public FirstLastLinkedList(){
		first = null;
		last = null;
	}
	
	public boolean isEmpty(){return first == null;}
	
	public void insertLast(E newObj){
	    Link<E> newLink = new Link<E>(newObj);   
	    if(isEmpty())                
	       first = newLink;            
	    else
	       last.next = newLink;        
	    last = newLink;                
	}
	
	public E deleteFirst(){
		if(first == null)
			return null;
		else{
		    E temp = first.data;
		    if(first.next == null)         
		       last = null;                
		    first = first.next;           
		    return temp;
		}
    }
	
	public E peek(){
		return first.data;
	}

	public void displayList(){
	    Link current = first;          
	    while(current != null)         
	       {
	       current.display();          // print data
	       current = current.next;     // move to next link
	       }
	    System.out.println("");
	}
}



// Based on FirstLastLinkedList implementation
public class Queue<E> {

	public Link<E> first;
	public Link<E> last;
	private FirstLastLinkedList<E> flll;
	
	public Queue(){
		flll = new FirstLastLinkedList<E>();
	}
	
	// insert object at end of the queue
	public void insert(E elem){
		flll.insertLast(elem);
	}
	
	// remove first element at beginning of the queue
	public E remove(){
		return flll.deleteFirst();
	}
	
	public E peek(){
		return flll.peek();
	}
	
	
}
