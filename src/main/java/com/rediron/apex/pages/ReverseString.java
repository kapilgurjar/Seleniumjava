package com.rediron.apex.pages;

public class ReverseString {

	public static void main(String[] args) {
		
		
//		String s="pankaj";
//		String rev="";
//		
//		for(int i=s.length()-1;i>=0;i--) {
//			rev=rev+s.charAt(i);
//			
//		}
//		
//		System.out.println(rev);
//		
//		
//
//		}
		
		int a=15;
		int b=16;
		
		
		a= a+b;// 20+10=30
		b=a-b;//30-10=20
		a=a-b;//30-20;
		
		System.out.println("After swap a value is "+a);
		System.out.println("After swap b value is "+b);
		
		
		int num =567;
		int rev=0;
		
		while(num!=0){
			rev=rev*10+num%10;
			num=num/10;
		}
		System.out.println(rev);
		
				
		
	}

}
