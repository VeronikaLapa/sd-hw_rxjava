package reactive_mongo_driver;

public enum Currency {
    RUB(1),
    EUR(86),
    USD(75);

    private final double coef;

    Currency(double coef) {
        this.coef = coef;
    }

    public static double convert(Currency from, double value, Currency to) {
        return value * from.coef / to.coef;
    }
}