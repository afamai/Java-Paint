/* Description: Class that contains all the methods that DynamicStack will use
 * 
 * Name: Alpha Mai
 * 
 * Date: April 20, 2015
 * 
 */
class LinkedList<T> {
    private int numberOfNodes = 0; 
    private ListNode<T> front = null;
    
    // Returns true if the linked list has no nodes, or false otherwise.
    public boolean isEmpty() {
        return (front == null);
    }
    
    // Deletes all of the nodes in the linked list.
    // Note: ListNode objects will be automatically garbage collected by JVM.
    public void makeEmpty() {
        front = null;
        numberOfNodes = 0;
    }
    
    // Returns the number of nodes in the linked list
    public int size() {
        return numberOfNodes;
    }
    
    @SuppressWarnings("unchecked")
    // Returns element at the specified index
    public T find( int index ){
        ListNode<T> currentNode = front;
        //the loop iterates for how much the index is.
        for (int counter = 0; counter < index; counter++)
        {
            currentNode = currentNode.getNext();
        }
        return currentNode.getData();
    }
    
    //adds element to the front of the LinkedList
    public void addFront( T element ) {
        front = new ListNode<T>( element, front );
        numberOfNodes+=1;
    }
    
    //returns the element at the front of the LinkedList
    public T first() {
        if (isEmpty()) 
            return null;
        
        return front.getData();
    }
    
    //remove element at the front of the LinkedList
    @SuppressWarnings("unchecked")
    public T removeFront() {
        T tempData;
        
        if (isEmpty()) 
            return null;
        
        tempData = front.getData();
        front = front.getNext();
        numberOfNodes--;
        return tempData;
    }
    
    // Returns true if the linked list contains a certain element, or false otherwise.
    @SuppressWarnings("unchecked")
    public boolean contains( T key ) {
        ListNode<T> searchNode;
        searchNode = front;
        while ( (searchNode != null) && (!key.equals(searchNode.getData())) ) {
            searchNode = searchNode.getNext();
        }
        return (searchNode != null);
    }
    
    // Insert a node in the list with a given key value
    @SuppressWarnings("unchecked")
    public void insert( Comparable key ) {
        ListNode<T> before = null;
        ListNode<T> after = front;
        ListNode<T> newNode;        
        
        // Traverse the list to find the ListNode before and after our insertion point.
        while ((after != null) && (key.compareTo(after.getData()) > 0)) {
            before = after;
            after = after.getNext();
        }
        
        // Create the new node with link pointing to after
        newNode = new ListNode<T>( (T)key, after);
        
        // Adjust front of the list or set before's link to point to new node, as appropriate
        if (before == null) {
            front = newNode;
        }
        else {
            before.setNext(newNode);
        }
        numberOfNodes++;
    }
    
    // Return String representation of the linked list.
    @SuppressWarnings("unchecked")
    public String toString() {
        ListNode<T> node;
        String linkedList = "FRONT ==> ";
        
        node = front;
        while (node != null) {
            linkedList += "[ " + node.getData() + " ] ==> ";
            node = node.getNext();
        }
        
        return linkedList + "NULL";
    }
}