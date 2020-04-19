package com.gioppl.fruitmanor.test;

import android.graphics.Point;

public class Test {
    @org.junit.Test
    public void update() {
        String a[]=new String[10];

        try {

            Point point=new Point();
            String path=point.getClass().getName();
            Class<?> classClass= Class.forName("com.gioppl.fruitmanor.test.MyClass");
            MyClass myClass= (MyClass) classClass.newInstance();
//            myClass.aaa(1);
//            myClass.x=100;
//            System.out.println("hello,"+myClass.x);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
