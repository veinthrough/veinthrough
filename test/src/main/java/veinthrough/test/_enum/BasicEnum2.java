package veinthrough.test._enum;

import veinthrough.test.AbstractUnitTester;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

/*
 * @author veinthrough
 * <p>
 * Test contains:
 * <p>
 * 1. How to write a effective Enum class.
 * <p>
 * 2. How to use Enum in switch.
 */
public class BasicEnum2 extends AbstractUnitTester {

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
        new BasicEnum2().test();
    }

    public enum SIZE {
        TOO_SMALL(0, 1),
        SMALL(1, 10),
        MEDIUM(10, 20),
        LARGE(20, 50),
        EXTRA_LARGE(50, 100),
        TOO_LARGE(100, Integer.MAX_VALUE)
        ;

        private Range<Integer> scope;
        private static final Map<Range<Integer>,SIZE> VALUE_MAP;

        static {
            final ImmutableMap.Builder<Range<Integer>, SIZE> sizes = ImmutableMap.builder();
            for(SIZE enumItem : SIZE.values()) {
                sizes.put(enumItem.getScope(), enumItem);
            }
            VALUE_MAP = sizes.build();
        }

        private SIZE(Integer min, Integer max) {
            this.scope = Range.closedOpen(min, max);
        }
        public String getScopeString() {
            return this.scope.toString();
            /*
            return "[" + this.scope.lowerEndpoint() + "," +
                         this.scope.upperEndpoint() +
                   ")";
                   */
        }
        @Override
        public String toString() {
            return this.name().toLowerCase() + getScopeString();
        }
        public Range<Integer> getScope() {
            return this.scope;
        }
        public static SIZE forValue(Integer value) {
            return VALUE_MAP.get(
                    VALUE_MAP.keySet().stream()
                        .filter(scope -> scope.contains(value))
                        .findFirst()
                        .get());
        }
    }
}
