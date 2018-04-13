package com.ping.test.bitmap;

import java.util.BitSet;

public class BitMapTest {
	private static byte[] bits = new byte[100];

	public static void main(String[] args) {
		bits[1] = 72;
//		add(14);
		clear(14);
	}

	public static void add(int num) {
		// num/8得到byte[]的index
		int arrayIndex = num >> 3;
		System.out.println("arrayIndex:" + arrayIndex );
		// num%8得到在byte[index]的位置
		int position = num & 0x07;
		System.out.println("position:" + position );
		// 将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了。
		bits[arrayIndex] |= 1 << position;
		System.out.println("bits[arrayIndex]:" + bits[arrayIndex] );
	}

	
	public static void clear(int num){
        // num/8得到byte[]的index
        int arrayIndex = num >> 3; 
		System.out.println("arrayIndex:" + arrayIndex );
        // num%8得到在byte[index]的位置
        int position = num & 0x07; 
        System.out.println("position:" + position );
        //将1左移position后，那个位置自然就是1，然后对取反，再与当前值做&，即可清除当前的位置了.
        bits[arrayIndex] &= ~(1 << position); 
        System.out.println("bits[arrayIndex]:" + bits[arrayIndex] );
    }

}
