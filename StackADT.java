public interface StackADT<T>
{
    // Adds one element to the top of the stack.
    public void push(T element);
     
    // Removes and returns the top element from this stack.
    public T pop();
     
    // Returns the top element, without removing it from this stack.
    public T peek();
     
    // Returns true if this stack contains no elements, false otherwise.
    public boolean isEmpty();
     
    // Returns the number of elements in this stack.
    public int size();
     
    // Returns a String representation of this stack.
    public String toString();
     
} // End of StackADT Interface class