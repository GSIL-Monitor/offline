package com.ctrip.train.kefu.system.offline;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;




public class MyTest {
    @Test
    public int test(){
        try {
            FileInputStream ss=new FileInputStream("D/111");
            return 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
        return 0;
    }


}
