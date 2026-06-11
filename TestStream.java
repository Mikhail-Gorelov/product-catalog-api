import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.Set;

class TestStream {

    private static int numberOfTask = 1;

    static void assertEquals(Object expected, Object actual) throws IllegalArgumentException {
        if (!expected.equals(actual)) {
            throw new IllegalArgumentException(
                    String.format("expected result is %s , but actual %s",
                            expected,
                            actual));
        } else {
            System.out.println(String.format("task %s - done", numberOfTask++));
        }

    }

    public static class Book {
        private String author;
        private String title;
        private double price;
        private List<String> reviews;

        public Book(String author, String title, double price) {
            this.author = author;
            this.title = title;
            this.price = price;
            this.reviews = new ArrayList<>();
        }

        // Геттеры и сеттеры для полей
        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<String> getReviews() {
            return reviews;
        }

        public void addReview(String review) {
            reviews.add(review);
        }
    }


    public static void main(String args[]) {
        // Создание трех объектов Book
        Book book1 = new Book("Автор 1", "Книга 1", 29.99);
        Book book2 = new Book("Автор 2", "Книга 2", 19.99);
        Book book3 = new Book("Автор 3", "Книга 3", 24.99);
        Book book4 = new Book("Автор 2", "Книга 4", 124.99);

        book1.addReview("Отличная книга!");
        book1.addReview("Мне понравилось");
        book2.addReview("Рекомендую всем!");

        // Создание списка и добавление книг в него
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);


        //Тесты и вызов методов с заданием
        //Задание 1
        double sum = task1(books);
        assertEquals(199, (int) sum);

        //Задание 2
        long count = task2(books);
        assertEquals(3L, count);

        //Задание 3
        Map<String, List<String>> bookReviews = task3(books);
        //ожидаемый результат
        Map<String, List<String>> expectedMap = new HashMap<>();
        expectedMap.put("Книга 1", Arrays.asList("Отличная книга!", "Мне понравилось"));
        expectedMap.put("Книга 2", Collections.singletonList("Рекомендую всем!"));
        expectedMap.put("Книга 3", Collections.emptyList());
        expectedMap.put("Книга 4", Collections.emptyList());
        //тест
        assertEquals(expectedMap, bookReviews);

        //Задание 4
        Map<String, List<String>> bookReviews2 = task4(books);
        //ожидаемый результат
        Map<String, List<String>> expectedMap2 = new HashMap<>();
        expectedMap2.put("Книга 1", Arrays.asList("Отличная книга!", "Мне понравилось"));
        expectedMap2.put("Книга 2", Collections.singletonList("Рекомендую всем!"));
        //тест
        assertEquals(expectedMap2, bookReviews2);

        //Задание 5
        List<String> reviews = task5(books);
        assertEquals(Arrays.asList("Отличная книга!", "Мне понравилось", "Рекомендую всем!"), reviews);

        //Задание 6
        double average = task6(books);
        assertEquals(49, (int) average);

        //Задание 7
        boolean authorExists = task7(books);
        assertEquals(true, authorExists);

        //Задание 8
        Set<String> titles = task8(books);
        assertEquals(true, titles.containsAll(Arrays.asList("Книга 1", "Книга 2", "Книга 3")));

        //Задание 9
        List<Book> booksActual = task9(books);
        assertEquals(Collections.singletonList(book2), booksActual);

        //Задание 10
        Map<String, List<Book>> oks = task10(books);
        //ожидаемый результат
        Map<String, List<Book>> expectedMap3 = new HashMap<>();
        expectedMap3.put("Not Ok", Collections.singletonList(book4));
        expectedMap3.put("OK", Arrays.asList(book1, book2, book3));
        //тест
        assertEquals(expectedMap3, oks);

        //Задание 11
        List<Book> recommended = task11(books);
        assertEquals(Collections.singletonList(book2), recommended);

        //Задание 12
        Book cheap = task12(books);
        assertEquals(book2, cheap);
    }

    /**
     * общая сумма по всем книгам.
     *
     * @param books - список книг
     * @return сумма по всем книгам
     */
    private static double task1(List<Book> books) {
        return books.stream().mapToDouble(b -> b.getPrice()).reduce(0, (start, x) -> start + x);
    }

    /**
     * количество уникальных авторов среди всех книг.
     *
     * @param books - список книг
     * @return количество уникальных авторов
     */
    private static long task2(List<Book> books) {
        return books.stream().map(b -> b.getAuthor()).distinct().count();
    }

    /**
     * Map в ключе название книги, в значении все отзывы по этой книге.
     *
     * @param books - список книг
     * @return ожидаемый мап
     */
    private static Map<String, List<String>> task3(List<Book> books) {
        //todo написать реализацию
        Map<String, List<String>> result = new HashMap<>();
        books.stream().forEach(book -> {
            result.put(book.getTitle(), book.getReviews());
        });
        return result;
    }

    /**
     * Map в ключе название книги, в значении все отзывы по этой книге.
     * Дополнительное условие: хранить ключи только тех, у кого есть отзывы
     *
     * @param books - список книг
     * @return ожидаемый мап
     */
    private static Map<String, List<String>> task4(List<Book> books) {
        //todo написать реализацию
        Map<String, List<String>> result = new HashMap<>();
        books.stream().filter(b -> b.getReviews().size() > 0).forEach(book -> {
            result.put(book.getTitle(), book.getReviews());
        });
        return result;
    }

    /**
     * Список всех отзывов по всем книгам
     *
     * @param books - список книг
     * @return список отзывов
     */
    private static List<String> task5(List<Book> books) {
        //todo написать реализацию
        return books.stream().map(b -> b.getReviews()).flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Определить среднюю стоимость книги (не считая сумму книг)
     *
     * @param books - список книг
     * @return среднюю стоимость книги
     */
    private static double task6(List<Book> books) {

        //todo написать реализацию
        return books.stream().mapToDouble(b -> b.getPrice()).average().orElse(0);
    }

    /**
     * У всех книг в поле Автор есть слово "Автор"
     *
     * @param books - список книг
     * @return результат
     */
    private static boolean task7(List<Book> books) {
        //todo написать реализацию
        return books.stream().allMatch(b -> b.getAuthor().contains("Автор"));
    }

    /**
     * Преобразовать List в Set. где Set это все названия книг. достаточно 3
     *
     * @param books - список книг
     * @return не больше 3 названий книг
     */
    private static Set<String> task8(List<Book> books) {
        //todo написать реализацию
        Set<String> result = new HashSet<>();
        books.stream().limit(3).forEach(book -> {
            result.add(book.getTitle());
        });
        return result;
    }

    /**
     * Найти книги, у которых в названии четная цифра, но цена меньше 100
     *
     * @param books - список книг
     * @return
     */
    private static List<Book> task9(List<Book> books) {
        //todo написать реализацию
        return books.stream().filter(b -> b.getTitle().matches(".*[02468].*") && b.getPrice() < 100).collect(Collectors.toList());
    }

    /**
     * поместить книга в Map по двум ключам: "OK" и "Not Ok". где второе когда цена у книги больше 50
     *
     * @param books - список книг
     * @return Map с двумя ключами
     */
    private static Map<String, List<Book>> task10(List<Book> books) {
        //todo написать реализацию
        return books.stream().collect(Collectors.groupingBy(b -> b.getPrice() > 50 ? "Not Ok" : "OK"));
    }

    /**
     * Получить список книг, у которых хотя бы один отзыв содержит слово "рекомендую".
     *
     * @param books - список книг
     * @return список книг с интересными отзывами
     */
    private static List<Book> task11(List<Book> books) {
        //todo написать реализацию
        return books.stream()
                .filter(b -> b.getReviews().stream()
                        .anyMatch(r -> r.toLowerCase().contains("рекомендую")))
                .collect(Collectors.toList());
    }

    /**
     * Найти самую дешевую книгу.
     *
     * @param books - список книг
     * @return самая дешевая книга
     */
    private static Book task12(List<Book> books) throws IllegalArgumentException {
        //todo написать реализацию
        return books.stream()
                .min(Comparator.comparing(Book::getPrice))
                .orElseThrow(() -> new IllegalArgumentException("No books provided"));
    }

}

