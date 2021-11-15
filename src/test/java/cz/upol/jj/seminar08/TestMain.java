package cz.upol.jj.seminar08;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @Test
    public void testStore()
    {
        Item i1 = new Item("iPhone 13", Type.Phone, 2, 32000.0);
        Item i2 = new Item("Samsung Galaxy S21", Type.Phone, 3, 18000.0);
        Item i3 = new Item("Xiaomi Mi 11", Type.Phone, 2, 20000.0);
        Item i4 = new Item("Xiaomi Mi 11", Type.Phone, 1, 19000.0);
        Item i5 = new Item("Samsung Galaxy Tab A7", Type.Tablet, 2, 5000.0);
        Item i6 = new Item("Lenovo Tab M10", Type.Tablet, 1, 4000.0);

        List<Item> items = Arrays.asList(i1, i2, i3, i4, i5, i6);
        Store store = new Store(items);

        assertTrue(store.hasItem("Samsung Galaxy S21"));
        assertFalse(store.hasItem("Samsung Galaxy S20"));

        assertEquals(3, store.countItems("Xiaomi Mi 11", Type.Phone));
        assertEquals(1, store.countItems("Lenovo Tab M10", Type.Tablet));
        assertEquals(0, store.countItems("Lenovo Tab M10", Type.Phone));

        assertEquals(191000, store.getTotalPrice());

        assertEquals(177000, store.getTotalPriceOfType(Type.Phone));

        assertEquals(11, store.countTotalItems());

        assertEquals(2, store.countTypes());

        List<Item> list = new ArrayList<>();
        list.add(i5);
        list.add(i6);
        assertIterableEquals(list, store.cheaperThan(10000).toList());

        Optional<Item> item = store.mostExpensive();
        if (item.isPresent())
            assertEquals(i1, item.get());
        else
            fail("Optional neni obsazene.");
    }
}