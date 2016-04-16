import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Esmery Corniel
 *
 */
public class SortableDataOrganizer extends DataOrganizer {

	//The sortButton
	Abutton sortButton;
	
	/**
	 * The constructor
	 */
	public SortableDataOrganizer(){
		super();
	}
	
	/**
	 * The constructor which has a parameter
	 * @param someApplet
	 */
	public SortableDataOrganizer(Applet someApplet){
		
		//sending the apple to the parent class
		super(someApplet);
		
		//This creates and places our Sort  button
		x -= 1.5*Abutton.BUTTON_WIDTH;
		y -= 1*Abutton.BUTTON_HEIGHT;

		y += 1.5*Abutton.BUTTON_HEIGHT;
		sortButton = new Abutton("Sort", Color.magenta , x , y);

		x += 1.5*Abutton.BUTTON_WIDTH;
		y += 1*Abutton.BUTTON_HEIGHT;
	}

	/**
	 * This handles the operation of sorting
	 */
	public void sort(){
		
		
		collection  = mergeSort(collection);
		//when the collection is sort this helps unHilght the last item
		collection.reset(null);
		
	}

	/**
	 * This is our recursive method which splits
	 * the collection  
	 * @param c
	 * @return
	 */
	public DataCollection mergeSort(DataCollection c){				
		
		//here we make sure we reset the list
		//this makes sure that we are at the beginning 
		c.reset();
		int size = getSize(c);// it keeps track of the amount of objects in our collection
		int half = size / 2;// half of the collection
		
		//If we dont have a collection or our collection only has one object
		if (c == null || size <= 1)	
		{		
			return c;
		}
		else{
			//these two object will take each half of the collection
			//as we call the method recursively 
			DataCollection head1 = new ArrayDataCollection();//the left list has the same head as the large list
			DataCollection head2 = new ArrayDataCollection();

			//split into the fist collection
			for (int i = 0; i < half; i ++){ // loop through the list until you get to the middle
				head1.add((Item) c.next());
			}
			
			//split into the second collection
			for(int i = half; i<size; i++ ){
				head2.add((Item) c.next());
			}
			
			
			head1 = mergeSort (head1);// recursively call this method on head1
			head2 = mergeSort (head2);	// recursively call this method on head2

			return merge(head1, head2);//returns the list sorted
		}

	}

	/**
	 * This method sorts the collection and then merges them together
	 * @param h1
	 * @param h2
	 * @return
	 */
	private DataCollection merge (DataCollection h1, DataCollection h2)	
	{
		//we reset them both so that we are at the beginning 
		h1.reset();
		h2.reset();
		
		//we will at to this object as we sort
		DataCollection result = new ArrayDataCollection(x,y);
		
		//this objects will be used to hold a reference to the items
		Object item1 = h1.next(), item2=h2.next();
		
		//while both of our items are not null
		//we will check which one is greater and 
		//then add the lesser one to the result
		while(item1 != null && item2 != null){
			if(((Item)item1).getValue()>=((Item)item2).getValue()){
				result.add((Item)item2);
				item2 = h2.next();
			}
			else if(((Item)item2).getValue()>((Item)item1).getValue()){
				result.add((Item)item1);
				item1 = h1.next();
			}
		}
		
		//when our item2 is null
		if(item2 == null){
			while(item1 != null){
				result.add((Item)item1);
				item1 = h1.next();
			}
		}
		else{
			//else when our item is not null
			while(item2 != null){
				result.add((Item)item2);
				item2 = h2.next();
			}
		}

		return result;// returns result to mergeSort
	}
	
	/**
	 * Gets the size of our collection
	 * @param sample
	 * @return
	 */
	public int getSize(DataCollection sample){
		
		//keeps the size of our collection
		int s = 0;
		//runs through the collection adding a +1 to s everytime
		while(sample.hasNext()){
			sample.next();
			s++;
		}
		//we reset to go back to beginning 
		sample.reset();
		return s;
	}

	/**
	 * checks to see if we are inside the sortButton 
	 * if we are then we call our sort method
	 */
	public void check(){
		super.check();
		if(sortButton.isInside(lastX, lastY)){
			sort();
		}
	}

	/**
	 * This gives the button the 3-D illusion of it being clicked
	 */
	public void flipWhenInside(){
		super.flipWhenInside();
		if(sortButton.isInside(lastX, lastY)){
			sortButton.flip();
			theApplet.repaint();
		}

	}
	
	/**
	 * Paint the sortButton
	 */
	public void paint(Graphics pane){
		super.paint(pane);
		if(sortButton != null){
			sortButton.paint(pane);
		}
	}

}