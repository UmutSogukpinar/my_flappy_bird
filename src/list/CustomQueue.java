package src.list;

public class CustomQueue<T>
{
    // node class initialization
    class Node
    {
        T data;
        Node next;

        public Node(T data)
        {
            this.data = data;
            next = null;
        }
    }

    // CustomQueue attributes
    Node first;
    Node last;

    public CustomQueue()
    {
        first = null;
        last = null;
    }
    
}
