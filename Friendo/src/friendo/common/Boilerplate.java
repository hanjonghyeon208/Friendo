//반복적으로 쓰이는 함수 코드 들이 저장된 클래스

package friendo.common;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import friendo.start.Start;


public class Boilerplate {

	//ID 유효성 검사 : 영문, 숫자 조합의 5자리 이상 12자리 이하로 사용 가능하며, 첫 자리에 숫자 사용 불가능
	public static boolean idValidation(String str) {
		
		return Pattern.matches("^[a-zA-Z0-9]{5,12}$", str) && !Pattern.matches("^[0-9]$", str.substring(0, 1));
		
	}
	
	//PW 유효성 검사 : 영문, 숫자, 특수문자 조합의 8자리 이상 15자리 이하로 사용 가능
	public static boolean pwValidation(String str) {
		
		return Pattern.matches("^[a-zA-Z0-9!@#$]{8,15}$", str);
		
	}
	
	//휴대폰번호 유효성 검사 : 숫자 10자리 이상 11자리 이하로 사용 가능
	public static boolean phonNumValidation(String str) {
		
		return Pattern.matches("^[0-9]{10,11}$", str);
		
	}
	
	//인증번호 유효성 검사 : 숫자 6자리
	public static boolean certifiNumValidation(String str) {
		
		return Pattern.matches("^[0-9]{6,6}$", str);
		
	}
	
	//닉네임 유효성 검사 : 한글 2자리 이상 10자리 이하로 사용 가능
	public static boolean nameValidation(String str) {
		
		return Pattern.matches("^[가-힣a-zA-Z0-9]{2,10}$", str);
		
	}
	
	//대학명 유효성 검사 : 한글 3자리 이상 10자리 이하로 사용 가능
		public static boolean SchNameValidation(String str) {
			
			return Pattern.matches("^[가-힣]{3,10}$", str);
			
		}
	
	//학번 유효성 검사 : 숫자 8자리 이상 15자리 이하로 사용 가능
	public static boolean SchNumValidation(String str) {
		
		return Pattern.matches("^[0-9]{8,15}$", str);
		
	}
	
	//차량번호 유효성 검사 : 한글, 숫자, 조합의 8자리 이상 12자리 이하로 사용 가능
	public static boolean CarNumValidation(String str) {
		
		return Pattern.matches("^[가-힣0-9]{8,12}$", str) ;
		
	}

	
	
	//천단위 구분 기호 생성
	public static String setComma(int num) {
		
		DecimalFormat df = new DecimalFormat("###,###");
		String money = df.format(num);
		
		return money;
		
	}
	
	//이미지 버튼 설정
	public static void setImageButton(JButton btn, ImageIcon icRo, ImageIcon icPr, int num1, int num2) {
		
		btn.setRolloverIcon(icRo);
		btn.setPressedIcon(icPr);
		btn.setBorderPainted(false);						//버튼 테두리 설정
		btn.setContentAreaFilled(false);					//버튼 배경 표시 설정
		btn.setFocusPainted(false);							//포커스 테두리 표시 설정
		btn.setOpaque(false);								//투명하게 설정
		btn.setPreferredSize(new Dimension(num1, num2));	//버튼 크기 설정
		btn.setEnabled(false);								//비활성화 상태로 생성
		
	}
	
	//숫자 6자리 인증번호 생성
	public static int certificationNum() {
		
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
        
    }
	
	//주어진 패널을 기반으로 프레임을 다시 그리기. 이전의 패널을 제거하고 새로운 패널을 추가하여 화면을 갱신
	public static void redraw(JPanel panel) {
		if(Start.getMf() != null) Start.getMf().remove(Start.getMf().getCurrentPanel());
		// 메인프레임을 가져와서 메인프레임에 있는 현재 패널을 제거하기
		Start.getMf().setCurrentPanel(panel);
		// 지정한 패널을 새로 열기
		Start.getMf().add(Start.getMf().getCurrentPanel());
		Start.getMf().getContentPane().setVisible(false);
		Start.getMf().getContentPane().setVisible(true);
		

}
	
}
