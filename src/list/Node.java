package src.list;

// Node class initialization
public class Node<T>
{
    T data;
    Node<T> next;

    public Node(T data)
    {
        this.data = data;
        next = null;
    }

    public T getNodeData(Node<T> node)
    {
        if (node == null)
            throw new IllegalArgumentException("Node cannot be null");

        return node.data;
    }
}