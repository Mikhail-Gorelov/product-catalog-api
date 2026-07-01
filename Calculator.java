package org.example;

/**
 * This generic class represents how to work calculator
 *
 * @link https://stepik.org/lesson/488096/step/8?unit=479351
 */
class Calculator<T extends Number> {

    /**
     * The value stored inside the calculator
     */
    private final T value;
    /**
     * It represents a calculator in which an error has occurred
     */
    private static final Calculator<?> BROKEN_CALCULATOR = new Calculator<>(true);
    /**
     * It determines if the calculator has an error
     */
    private final boolean hasError;

    private Calculator(T value) {
        this.value = value;
        this.hasError = false;
    }

    private Calculator(boolean hasError) {
        this.value = null;
        this.hasError = hasError;
    }

    /**
     * It returns a broken calculator with an explicit type casting. We recommend you to use this method instead of
     * accessing BROKEN_CALCULATOR directly.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Number> Calculator<T> getBrokenCalculator() {
        return (Calculator<T>) BROKEN_CALCULATOR;
    }

    /**
     * The method creates a new instance of the calculator with a specified initial value.
     */
    public static <T extends Number> Calculator<T> of(T value) {
        value = value;
    }

    /**
     * The method applies a given function to the value stored in the calculator. It never throws ArithmeticException or
     * NullPointerException
     */
    public <U extends Number> Calculator<U> eval(Object o) {

    }

    /**
     * The method passes the stored value to a given consumer only if no errors have occurred in the calculator.
     */
    public Calculator<T> consume(Object o) {
    }

    /**
     * Right answer is: 10 105 15
     */
    public static void main(String[] args) {
        Calculator.of(10) // inits calculator with the default value 10
                .consume(System.out::println)  // shows the current value 10
                .eval(value -> value * 10)     // evaluates a new expression: 100
                .eval(value -> value + 5)      // evaluates a new expression: 105
                .consume(System.out::println)  // shows the current value 105
                .eval(value -> value / 0)      // provokes an error
                .consume(System.out::println); // doesn't print anything

        Calculator.of((Integer) null) // inits calculator with null as the default value
                .eval(value -> value * 10)     // doesn't evaluate anything
                .eval(value -> value + 5)      // doesn't evaluate anything
                .consume(System.out::println); // doesn't print anything

        Calculator.of(10) // init calculator with the default value 10
                .eval(value -> value + 5)      // evaluates a new expression: 15
                .consume(System.out::println)  // shows the current value 15
                .eval(value -> null) // makes the value null
                .consume(System.out::println); // doesn't print anything
    }
}