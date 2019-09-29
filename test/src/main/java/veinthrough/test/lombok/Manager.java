package veinthrough.test.lombok;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Manager extends Employee
{
    @NonNull @Getter @Setter private Double bonus;

    public Manager(String n, double s, int year, int month, int day)
    {
        super(n, s, year, month, day);
        this.bonus = 0D;
    }

    public Manager(String n, double s, Date date, Double bonus)
    {
        super(n, s, date);
        this.bonus = bonus;
    }

    public Manager(String n, double s, Date date, String hobby, Double bonus)
    {
        super(n, s, date, hobby);
        this.bonus = bonus;
    }

    public Double getSalary()
    {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    @Override
    public boolean equals(Object otherObject)
    {
        if (!super.equals(otherObject)) return false;
        Manager other = (Manager) otherObject;
        // super.equals checked that this and other belong to the same class
        return bonus == other.bonus;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() + 17 * new Double(bonus).hashCode();
    }

    @Override
    public String toString()
    {
        return super.toString() + "[bonus=" + bonus + "]";
    }
}

