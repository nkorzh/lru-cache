import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class AccessTrackerTest {

    @Test
    void emptyTrackerHasZeroSize() {
        AccessTracker<Integer> list = new AccessTracker<>();

        assertEquals(0, list.getSize());
    }

    @Test
    void returnsSingleElement() {
        AccessTracker<Integer> list = new AccessTracker<>();
        list.addFirst(new Node<>(1));

        Node<Integer> retrievedNode = list.removeLast();

        assertEquals(1, retrievedNode.value);
        assertEquals(0, list.getSize());
    }

    @Test
    void flipsElements() {
        AccessTracker<Integer> list = new AccessTracker<>();
        Node<Integer> firstAdded = new Node<>(1);
        list.addFirst(firstAdded);
        list.addFirst(new Node<>(2));
        list.addFirst(new Node<>(3));

        list.moveToFirst(firstAdded);

        List<Integer> elements = retrieveAll(list);
        assertIterableEquals(List.of(1, 3, 2), elements);
    }


    @Test
    void removesNode() {
        AccessTracker<Integer> list = new AccessTracker<>();
        Node<Integer> middleNode = new Node<>(2);
        list.addFirst(new Node<>(1));
        list.addFirst(middleNode);
        list.addFirst(new Node<>(3));

        list.remove(middleNode);

        List<Integer> elements = retrieveAll(list);
        assertIterableEquals(List.of(3, 1), elements);
    }

    @Test
    void removesNodeAndFlips() {
        AccessTracker<Integer> list = new AccessTracker<>();
        Node<Integer> middleNode = new Node<>(2);
        Node<Integer> firstAddedNode = new Node<>(1);
        list.addFirst(firstAddedNode);
        list.addFirst(middleNode);
        list.addFirst(new Node<>(3));

        list.remove(middleNode);
        list.moveToFirst(firstAddedNode);

        List<Integer> elements = retrieveAll(list);
        assertIterableEquals(List.of(1, 3), elements);
    }

    /**
     * Retrieves elements from {@link AccessTracker}
     *
     * @param list accessTracker
     * @param <T>  element type
     * @return elements in order from most head to tail
     */
    private static <T> List<T> retrieveAll(AccessTracker<T> list) {
        List<T> result = new ArrayList<>(list.getSize());
        while (list.getSize() > 0) {
            result.add(list.removeLast().value);
        }
        Collections.reverse(result);
        return result;
    }
}
