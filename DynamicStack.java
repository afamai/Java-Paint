class DynamicStack<T> implements StackADT<T> {
    private LinkedList<T> stackArray = new LinkedList<T>();

    // Adds the specified element to the top of the stack, or does nothing if full.
    public void push(T element) {
        stackArray.addFront(element);
    }
     
    // Removes the element at the top of the stack and returns a reference to it, or null (if empty).
    public T pop() {
        return stackArray.removeFront();
    }
     
    // Returns a reference to the element at the top of the stack, or null (if empty).
    public T peek() {
        return stackArray.first();
    }
     
    // Returns true if the stack contains no elements, false otherwise.
    public boolean isEmpty() {
        return stackArray.isEmpty();
    }
    
    public void makeEmpty()
    {
        stackArray.makeEmpty();
    }
     
    // Returns the number of elements in the stack.
    public int size() {
        return stackArray.size();
    }
    
    public T elementAt(int index)
    {
        return stackArray.find(index);
    }
     
    // Returns a String representation of the stack.
    public String toString() {
        String result = "Stack Contains: ";
         
        for (int counter =0; counter < stackArray.size(); counter++){
            result += "["+stackArray.find(counter)+"] ";
        }
         
        return result;
    }
}  // end class StaticStack