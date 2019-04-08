package cs2030.mystream;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.ArrayList;

public abstract class InfiniteListImpl<T> implements InfiniteList<T> {

    abstract Optional<T> get();
    public <R> InfiniteList<R> map(Function<T,R> mapper) { 
        return new InfiniteListImpl<R>() { 
            public Optional<R> get() {
                return InfiniteListImpl.this.get().map(mapper);
            } 
        };
    }

    public InfiniteList<T> limit(long maxSize) {
        return new InfiniteListImpl<T>() {   
            long remaining = maxSize; 
            public Optional<T> get() {
                if (maxSize < 0) {
                    throw new IllegalArgumentException("" + maxSize);
                } else {
                    if (remaining != 0) {
                        Optional<T> element = InfiniteListImpl.this.get();
                        remaining--;
                        return element; 
                    } else {
                        return Optional.empty();
                    }
                }
            }
        };
    }
    public InfiniteList<T> optFilter(Predicate<T> predicate) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() { 
                Optional<T> element = InfiniteListImpl.this.get();
                Optional<T> result = element.filter(predicate);
                if (result.isPresent()) {
                    return result;
                } else {
                    return get();
                }
            }
        };
    }
    public InfiniteList<T> filter(Predicate<T> predicate) { 
        return new InfiniteListImpl<T>() { 
            public Optional<T> get() {
                Optional<T> element = InfiniteListImpl.this.get();
                if (element.isPresent()) {
                    if (predicate.test(element.get())) {
                        return element;
                    } else {
                        return get();
                    }
                } else {
                    return Optional.empty(); 
                }
            }
        };
    }
    
    public InfiniteList<T> takeWhile(Predicate<T> predicate) { 
        return new InfiniteListImpl<T>() {
            boolean check = true;
            public Optional<T> get() {
                Optional<T> element = InfiniteListImpl.this.get();
                if (element.isPresent() && check) { 
                    if (predicate.test(element.get())) {
                        return element; 
                    } else {
                        check = false; 
                        return Optional.empty();
                    }
                } else {
                    return Optional.empty();
                }
            }
        };
    }
   
    //terminal operations 
    
    public void forEach(Consumer<T> action) {
        Optional<T> element;
        while ((element = InfiniteListImpl.this.get()).isPresent()) {
            action.accept(element.get());
        }
    }
    
    public long count() { 
        long counter = 0;
        Optional<T> element;
        while ((element = InfiniteListImpl.this.get()).isPresent()) {
            counter++;
        }
        return counter;
    }
    
    public Optional<T> reduce(BiFunction<T,T,T> accumulator) { 
        Optional<T> element;        
        T result = null;
        boolean foundAny = false;
        while ((element = InfiniteListImpl.this.get()).isPresent()) {
            if (!foundAny) {
                result = element.get();
                foundAny = true; 
            } else {
                result = accumulator.apply(result, element.get());
            }
        }
        if (foundAny) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }
    
    public T reduce(T identity, BiFunction<T,T,T> accumulator) {
        Optional<T> element;
        T result = identity;
        while ((element = InfiniteListImpl.this.get()).isPresent()) { 
            result = accumulator.apply(result, element.get());
        } 
        return result;
    }
    public Object[] toArray() {
        ArrayList<Object> myList = new ArrayList<>(); 
        Optional<T> element;
        while ((element = InfiniteListImpl.this.get()).isPresent()) {
            myList.add(element.get());
        }
        return myList.toArray();
    }
            
    //irrelevant methods
    public InfiniteList<T> nothing() {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                return InfiniteListImpl.this.get();
            }
        };
    }
    public <R> InfiniteList<R> verboseMap(Function<T,R> mapper) { 
        return new InfiniteListImpl<R>() { 
            public Optional<R> get() {
                Optional<T> element = InfiniteListImpl.this.get();
                if (element.isPresent()) {
                    return Optional.of(mapper.apply(element.get()));
                } else {
                    return Optional.empty();
                }
            } 
        };
    }    
}




