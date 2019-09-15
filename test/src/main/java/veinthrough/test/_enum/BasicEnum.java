/**
 *
 */
package veinthrough.test._enum;

import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import veinthrough.test.AbstractUnitTester;
import com.google.common.collect.Lists;

/**
 * @author veinthrough
 * <p>
 * Test contains:
 * <p>
 * 1. How to write a effective Enum class.
 * <p>
 * 2. How to use Enum in switch.
 *
 */
public class BasicEnum extends AbstractUnitTester {

    /*
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        testForValue(0);
        testForValue(25);
        testForValue(50);
        testForValue(100);

        //Use guava collect to new a ArrayList
        List<SIZE> sizes = Lists.newArrayList(
            SIZE.TOO_SMALL,
            SIZE.SMALL,
            SIZE.MEDIUM,
            SIZE.LARGE,
            SIZE.EXTRA_LARGE,
            SIZE.TOO_LARGE);
        /*
        List<SIZE> sizes = new ArrayList<SIZE>(){{
            add(SIZE.TOO_SMALL);
            add(SIZE.SMALL);
            add(SIZE.MEDIUM);
            add(SIZE.LARGE);
            add(SIZE.EXTRA_LARGE);
            add(SIZE.TOO_LARGE);
        }};
        */
        System.out.println();

        for(SIZE size : sizes) {
            switch(size) {
            case SMALL:
                System.out.println("small" + size.getScopeString());
                break;
            default:
                System.out.println(size.toString());
            }
        }
    }

    private static void testForValue(Integer value) {
        SIZE size = SIZE.forValue(value);
        System.out.println("" + value + " is " + size.name());
    }

    public static void main(String[] args) {
        new BasicEnum().test();
    }

    public static enum SIZE {
        TOO_SMALL(0, 1),
        SMALL(1, 10),
        MEDIUM(10, 20),
        LARGE(20, 50),
        EXTRA_LARGE(50, 100),
        TOO_LARGE(100, Integer.MAX_VALUE)
        ;

        private Pair<Integer,Integer> scope;
        private static final Map<Integer,SIZE> VALUE_MAP;

        static {
            final ImmutableMap.Builder<Integer, SIZE> lefts = ImmutableMap.builder();
            for(SIZE enumItem : SIZE.values()) {
                lefts.put(enumItem.getScope().getLeft(), enumItem);
            }
            VALUE_MAP = lefts.build();
        }

        private SIZE(Integer mininum, Integer maximum) {
            this.scope = ImmutablePair.of(mininum, maximum);
        }

        public String getScopeString() {
            return "[" + this.scope.getLeft() + "," +
                         this.scope.getRight() +
                   ")";
        }

        @Override
        public String toString() {
            return this.name().toLowerCase() + getScopeString();
        }

        public Pair<Integer,Integer> getScope() {
            return this.scope;
        }

        public static SIZE forValue(Integer value) {
            return VALUE_MAP.get(
                    VALUE_MAP.keySet().stream()
                        .filter(num -> num<=value)
                        .max(Integer::compare)
                        .get());
        }
    }

}
