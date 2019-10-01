package veinthrough.test._class;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import com.google.common.base.Preconditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author veinthrough
 * @see veinthrough.test._class.ClassTest
 * @see veinthrough.test._class.DerivedTest
 *
 */
//@Data包括@RequiredArgsConstructor?
@RequiredArgsConstructor
@AllArgsConstructor()
public class Employee implements Comparable<Employee>, Cloneable
{
    @Getter private static final AtomicLong NEXT_ID;

    //2. final 域，类似于指针常量，没有@Setter
    //id/hirdDay如果在初始化块中初始化，而没有在声明的时候初始化，lombok将会当作required args
    //生成RequiredArgsConstructor时将会包括该参数, @AllArgsConstructor也会包含该参数
    @Getter private final Long id = NEXT_ID.incrementAndGet();
    @Getter private final Date hireDay = new Date(System.currentTimeMillis());

    //1. 私有数据域+公有域访问器+公有域更改器，这里用lombok实现
    @NonNull @Getter @Setter private String name;
    @NonNull @Getter @Setter private Double salary;
    @Getter @Setter private String hobby;

    //static域在加载类，还没有创建对象的时候就会执行
    static {
        NEXT_ID = new AtomicLong(0);
    }

    //初始化块在声明后，构造函数前执行
    /*
    {
        hireDay = new Date(System.currentTimeMillis());
        id = NEXT_ID.incrementAndGet();
    }
    */

    //4. 如果定义了有参构造函数，也应该定义无参构造函数
    //new/未初始化对象都会默认调用无参构造函数
    //5. 子类的构造函数没有显示调用父类的构造函数也会默认调用无参构造函数
    public Employee() {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(salary);
    }

    public void raiseSalary(double byPercent)
    {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    //通过各个name/salary/hireDate的值来比较，那么在子类中可能有更多的域的值需要比较
    //13. 子类拥有自己的相等概念，则对称性需求将强制采用getClass检测而不能用instanceof
    public boolean equals(Object otherObject)
    {
        // a quick test to see if the objects are identical
        if (this == otherObject) return true;

        // must return false if the explicit parameter is null
        if (otherObject == null) return false;

        // if the classes don't match, they can't be equal
        // Using instanceof is a worse choice as
        // as it's not symmetrical when calling equals() in parent and child classes
        if (getClass() != otherObject.getClass()) return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields have identical values
        // Objects.equals can handle null:
        // if only one of them is null, return false;
        // else return a.equals(b)
        return Objects.equals(name, other.name) &&
                salary == other.salary &&
                Objects.equals(hireDay, other.hireDay);
    }

    //另外一种equals的实现，只通过id来比较
    //10. 将方法和类声明为final的主要目的是：确保他们不会再子类中改变语义，
    //因为如果只根据id来比较，就不需要在子类中改变语义
    //14. 因为不需要再子类中改变语义，由父类决定相等概念，则可以用instanceof进行检测，
    //且应该将方法声明为final
    final public boolean equals2(Object otherObject)
    {
        // a quick test to see if the objects are identical
        if (this == otherObject) return true;

        // must return false if the explicit parameter is null
        if (otherObject == null) return false;

        // if the classes don't match, they can't be equal
        // Using instanceof is a worse choice as
        // as it's not symmetrical when calling equals() in parent and child classes
        if (otherObject instanceof Employee) return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields have identical values
        // Objects.equals can handle null:
        // if only one of them is null, return false;
        // else return a.equals(b)
        return id.equals(other.getId());
    }

    //如果重定义了equals方法，就必须重定义hashCode方法并保持一致；
    //即如果a.equals(b), 那么应该a.hashCode()==b.hashCode()
    public int hashCode()
    {
        return Objects.hash(name, salary, hireDay);
    }

    public String toString()
    {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay
            + "]";
    }

    //1. 如果子类之间的比较含义不一样，那父类/子类就属于不同类对象的非法比较；
    //那么父类和子类每个compareTo()都应该在开始进行getClass()==other.getClass()的比较
    //2. 如果在父类和子类中存在通用的比较方法，则应该在父类中提供一个compareTo方法，
    //并声明为final；将方法和类声明为final的主要目的是：确保他们不会再子类中改变语义。
    @Override
    final public int compareTo(Employee other) {
        return Double.compare(getSalary(), other.getSalary());
    }

    public Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.hireDay.setTime(hireDay.getTime());
        return cloned;

    }
}
