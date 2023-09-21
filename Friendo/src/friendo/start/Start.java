//메인함수가 들어있는 스타트 프레임 생성 클래스

package friendo.start;

import friendo.common.Boilerplate;
import friendo.login.Login;


public class Start {
	
	private static StartFrame mf;	//메인 프레임 객체

	public static void main(String[] args) {
		
		mf = new StartFrame("Login");
		
		Boilerplate.redraw(new Login());
		
	}

	public static StartFrame getMf() {
		
		return mf;
		
	}

}
