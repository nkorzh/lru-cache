class AccessTracker<T> {
    private final Node<T> head;
    private final Node<T> tail;
    private int size;

    AccessTracker() {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    int getSize() {
        assert 0 <= size && size < Integer.MAX_VALUE : "List size is invalid";
        return size;
    }

    void addFirst(Node<T> node) {
        int oldSize = getSize();
        assert 0 <= oldSize && oldSize < Integer.MAX_VALUE;

        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;

        assert getSize() == oldSize + 1;
    }

    Node<T> removeLast() {
        assert tail.prev != head;
        Node<T> lastNode = tail.prev;
        remove(lastNode);
        return lastNode;
    }

    void moveToFirst(Node<T> node) {
        remove(node);
        addFirst(node);
    }

    void remove(Node<T> node) {
        int oldSize = getSize();
        assert 0 <= oldSize && oldSize < Integer.MAX_VALUE;

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;

        assert getSize() == oldSize - 1 && getSize() >= 0;
    }
}
