package src.list;

import java.util.Iterator;

public class CustomQueue<T> implements Iterable<T>
{
    // CustomQueue attributes
    Node<T> first;
    Node<T> last;

    public CustomQueue()
    {
        first = null;
        last = null;
    }

    // Add element to the end of the queue
    public void enqueue(T data)
    {
        Node<T> newNode = new Node<>(data);

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
            return ;

        Node<T> firstNode = first;
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
        private Node<T> current = first;

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

