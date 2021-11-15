#KMI/JJ1 Seventh homework 2021/2022

1. Vytvořte třídu Item, která reprezentuje předmět v obchodě.
+ Konstruktor Item(String name, Type type, int count, double price)
+ Zaveďte gettry pro privátní atributy
+ Překryjte toString() metodu

2. Vytvořte třídu Store, která reprezentuje obchod s předměty.
+ Konstruktor Store(List\<Item\> items)
+ Následující metody naprogramujte s využitím Stream API:
+ boolean hasItem(String name) - vrátí true, pokud obchod vlastní předmět
+ int countItems(String name, Type type) - vrátí počet předmětů s názvem a určitého typu
+ double getTotalPrice() - vrátí celkovou cenu předmětů v obchodě
+ double getTotalPriceOfType(Type type) - vrátí celkovou cenu předmětů určitého typu
+ int countTotalItems() - vrátí celkový počet předmětů
+ int countTypes() - vrátí počet druhů předmětů, které se v obchodě prodávají
+ Stream\<Item\> cheaperThan(int price) - vrátí stream předmětů levnějších než price
+ Optional\<Item\> mostExpensive() - vrátí nejdražší předmět v obchodě