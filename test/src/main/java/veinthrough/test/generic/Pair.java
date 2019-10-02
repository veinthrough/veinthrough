/**
 *
 */
package veinthrough.test.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author veinthrough
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pair<T> {
    @NonNull @Getter @Setter private T first;
    @NonNull @Getter @Setter private T second;
    public void reverse() {
        T t = first;
        first = second;
        second = t;
    }
}
