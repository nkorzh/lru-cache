class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    Node(T value) {
        this.value = value;
    }

    Node() {
    }
}
