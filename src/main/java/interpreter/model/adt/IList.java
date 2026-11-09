package interpreter.model.adt;

import java.util.List;
import interpreter.exceptions.ADTException;

public interface IList<T> {
    void add(T v);
    T get(int index) throws ADTException;
    int size();
    List<T> toList(); // in insertion order
}