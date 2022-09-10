package ac.aop.logging.starter.model;

import ac.aop.logging.starter.configuration.DontLog;

public class HelloResponse {


    String a;
    @DontLog
    String b;
    @DontLog
    String c;

    public HelloResponse(String a,String b,String c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
