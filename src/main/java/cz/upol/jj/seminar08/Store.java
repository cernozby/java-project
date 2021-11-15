package cz.upol.jj.seminar08;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Store {

    private List<Item> items;

    Store(List<Item> items) {
        this.items = items;
    }

    public boolean hasItem(String name) {
        return this.items.stream().anyMatch(item -> item.getName().equals(name));
    }

    int countItems(String name, Type type) {
        return this.items.stream()
                .filter(item -> item.getName().equals(name) && item.getType().equals(type))
                .mapToInt(Item::getCount).sum();
    }

    double getTotalPrice() {
        return this.items.stream()
                .mapToDouble( item -> item.getPrice() * item.getCount())
                .sum();
    }

    double getTotalPriceOfType(Type type) {

        return this.items.stream()
                .filter(item -> item.getType().equals(type))
                .mapToDouble( item -> item.getPrice() * item.getCount())
                .sum();
    }

    int countTotalItems() {
        return this.items.stream().mapToInt(Item::getCount).sum();
    }

    int countTypes() {
        return this.items.stream().collect(Collectors.groupingBy(Item::getType)).size();
    }

    Stream<Item> cheaperThan(int price) {
        return this.items.stream().filter(item -> item.getPrice() < price);
    }

    Optional<Item> mostExpensive() {
        return this.items.stream().max(Comparator.comparing(Item::getPrice));
    }
}
