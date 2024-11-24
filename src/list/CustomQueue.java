package src.list;

import java.util.Iterator;

public class CustomQueue<T> implements Iterable<T>
{
    // Node class initialization
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

    public T getNodeData(Node node)
    {
        if (node == null)
            throw new IllegalArgumentException("Node cannot be null");

        return node.data;
    }

    // Add element to the end of the queue
    public void enqueue(T data)
    {
        Node newNode = new Node(data);

        if (last == null)
            first = newNode;
        else
            last.next = newNode;

        last = newNode;
    }

    // Rotate the queue: move the first node to the end
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

    // Implementing Iterable<T>
    @Override
    public Iterator<T> iterator()
    {
        return new CustomQueueIterator();
    }

    // Inner class for the iterator
    private class CustomQueueIterator implements Iterator<T>
    {
        private Node current = first;

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public T next()
        {
            if (!hasNext())
                throw new IllegalStateException("No more elements");

            T data = current.data;
            current = current.next;
            return data;
        }
    }
}

