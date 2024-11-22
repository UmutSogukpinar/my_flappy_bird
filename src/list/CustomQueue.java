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

    // add element end of the queue
    public void enqueue(T data)
    {
        Node newNode = new Node(data);

        if (last == null)
            first = newNode;
        else
            last.next = newNode;

        last = newNode;
    }

    public void updateQueue()
    {
        if (first == null || first.next == null)
            return;

        Node firstNode = first;
        first = first.next;
        last.next = firstNode;
        last = firstNode;
        firstNode.next = null;
    }
}
