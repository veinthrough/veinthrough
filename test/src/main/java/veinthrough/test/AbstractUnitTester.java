package veinthrough.test;


public abstract class AbstractUnitTester implements UnitTester {
    protected String[] args= new String[0];
    @Override
    public UnitTester setArgs(String[] args) {
        this.args= args;
        return this;
    }
}