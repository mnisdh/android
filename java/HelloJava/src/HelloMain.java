
public class HelloMain {
	/**
	 * 접근제한자 리턴타입 함수명(파라미터타입 파라미터) {
	 * public
	 * private
	 * protected
	 * (default)
	 * }
	 * 파라미터 사용 : java -c HelloMain.class a b c 1 22
	 * @param args
	 */
	public static void main(String[] args){
		//System.out.println("Hello Java!!!");
		
		calcTest();
	}
	
	/**
	 * 연산테스트 메소드
	 */
	private static void calcTest(){
		int a = 2 + 3;
		int b = 2 - 3;
		int c = 2 * 3;
		int d = 2 / 3;//소수점이하 반올림(자동형변환)
		int e = 5 % 3;//5를 3으로 나눈 나머지
		int f = 10 % 3;//10을 3으로 나눈 나머지
		double dou = 5.0 % 4.2;
		
		System.out.println("2 + 3 = " + a);
		System.out.println("2 - 3 = " + b);
		System.out.println("2 * 3 = " + c);
		System.out.println("2 / 3 = " + d);
		System.out.println("5 % 3 = " + e);
		System.out.println("10 % 3 = " + f);
		System.out.println("5.0 % 4.2 = " + dou);
	}
}
