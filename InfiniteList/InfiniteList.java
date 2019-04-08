package cs2030.mystream;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.BiFunction;

public interface InfiniteList<T> {
    static <T> InfiniteList<T> generate(Supplier<T> supplier) { 
        return new InfiniteListImpl<T>() {
            public Optional<T> get() { 
                return Optional.of(supplier.get());
            }
        };
    }

    static <T> InfiniteList<T> iterate(T seed, Function<T,T> next) {
        return new InfiniteListImpl<T>() {
            T element = seed;
            boolean first = true;
            public Optional<T> get() {
                if (first) {
                    first = false;
                    return Optional.of(seed);
                } else {
                    element = next.apply(element);
                    return Optional.of(element);
                }
            }
        };
    }

    //public Optional<T> get(); 

    public <R> InfiniteList<R> map(Function<T,R> mapper);
    public InfiniteList<T> limit(long maxSize);
    public InfiniteList<T> filter(Predicate<T> predicate); 
    public InfiniteList<T> takeWhile(Predicate<T> predicate);      
    
    //terminal operations
    public void forEach(Consumer<T> consumer);
    public long count();
    public Optional<T> reduce(BiFunction<T,T,T> accumulator);
    public T reduce(T identity, BiFunction<T,T,T> accumulator);
    public Object[] toArray();
    //irrelevant methods
    public InfiniteList<T> nothing();
    public <R> InfiniteList<R> verboseMap(Function<T,R> mapper);
    public InfiniteList<T> optFilter(Predicate<T> predicate);
}

