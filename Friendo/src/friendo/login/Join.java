//회원가입 패널 클래스

package friendo.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import friendo.common.Boilerplate;
import friendo.common.DB;
import friendo.common.Encryption;
import friendo.start.StartFrame;                                                             


public class Join extends JPanel implements ActionListener, FocusListener, KeyListener {
	
	private StartFrame mainFrame = null;		//메인 프레임 객체
	
	private String usersInId;				//사용자에게 입력 받은 아이디
	private String usersInPw;				//사용자에게 입력 받은 비밀번호
	private String usersInName; 			//사용자에게 입력 받은 이름
	private String usersInPhoneNum; 		//사용자에게 입력 받은 휴대폰번호
	private String usersInSchName;			//사용자에게 입력 받은 대학명
	private String usersInSchNum;			//사용자에게 입력 받은 학번
	private String usersInCarNum;			//사용자에게 입력 받은 차량번호
	
	private JTextField tfId;				//아이디 입력 텍스트필드
	private JButton btnOverlapChk;			//중복확인 버튼
	private JPasswordField tfPw;			//비밀번호 입력 텍스트필드
	private JPasswordField tfPw2;			//비밀번호 확인 입력 텍스트필드
	private JTextField tfName;				//이름 입력 텍스트필드

	private JTextField tfPhonNum;			//휴대폰번호 입력 텍스트필드
	private JTextField tfSchNum;			//학번 입력 텍스트필드
	private JTextField tfCarNum;			//차량번호 입력 텍스트필드
	private JTextField tfSchName;			//대학명 입력 텍스트필드
	private JTextField tfCertifiNum;		//인증번호 입력 텍스트필드
	
	private JButton btnSend;				//전송 버튼
	private JButton btnCancel;				//취소 버튼
	private JButton btnOk;					//확인 버튼
	private JLabel lblCount;				//인증시간 카운트 라벨
	private boolean overlapFlag = false;	//중복확인 완료여부 저장
	private static int sendCertifiFlag = 1;	//전송하기 버튼이면 1, 인증하기 버튼이면 2
	private static int certifiNum;			//인증번호
	private boolean certifiFlag = false;	//인증 완료여부 저장
	private CountThread countThread = null;	//인증시간 카운트 Thread
	
	
	private ImageIcon icCancelET;			//취소 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icCancelRo;			//취소 버튼(Rollover) 이미지 아이콘
	private ImageIcon icCancelPr;			//취소 버튼(Pressed) 이미지 아이콘
	private ImageIcon icCertifiedET;		//인증하기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icCertifiedRo;		//인증하기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icCertifiedPr;		//인증하기 버튼(Pressed) 이미지 아이콘
	private ImageIcon icOkET;				//확인 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icOkRo;				//확인 버튼(Rollover) 이미지 아이콘
	private ImageIcon icOkPr;				//확인 버튼(Pressed) 이미지 아이콘
	private ImageIcon icOverlapChkET;		//중복확인 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icOverlapChkRo;		//중복확인 버튼(Rollover) 이미지 아이콘
	private ImageIcon icOverlapChkPr;		//중복확인 버튼(Pressed) 이미지 아이콘
	private ImageIcon icSendET;				//전송하기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icSendRo;				//전송하기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icSendPr;				//전송하기 버튼(Pressed) 이미지 아이콘

	
	//회원가입 화면
	public Join() {
		
        setLayout(new BorderLayout());
        setBackground(new Color(255, 252, 244));
        
        mainFrame = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
        
        mainFrame.setTitle("Join");
        
		makeImageIcon();	//이미지 아이콘 생성
		
        makeTitle();		//타이틀 영역 생성
        makeInput();		//인풋필드 영역 생성
		
	}
	
	//이미지 아이콘 생성
	private void makeImageIcon() {
		
		icCancelET = new ImageIcon("images/join/Btn_Cancel_EnabledTrue.png");			//취소 버튼(EnabledTrue) 이미지 아이콘
		icCancelRo = new ImageIcon("images/join/Btn_Cancel_Rollover.png");				//취소 버튼(Rollover) 이미지 아이콘
		icCancelPr = new ImageIcon("images/join/Btn_Cancel_Pressed.png");				//취소 버튼(Pressed) 이미지 아이콘
		
		icCertifiedET = new ImageIcon("images/join/Btn_Certified_EnabledTrue.png");		//인증하기 버튼(EnabledTrue) 이미지 아이콘
		icCertifiedRo = new ImageIcon("images/join/Btn_Certified_Rollover.png");		//인증하기 버튼(Rollover) 이미지 아이콘
		icCertifiedPr = new ImageIcon("images/join/Btn_Certified_Pressed.png");			//인증하기 버튼(Pressed) 이미지 아이콘
		
		icOkET = new ImageIcon("images/join/Btn_Ok_EnabledTrue.png");					//가입 버튼(EnabledTrue) 이미지 아이콘
		icOkRo = new ImageIcon("images/join/Btn_Ok_Rollover.png");						//가입 버튼(Rollover) 이미지 아이콘
		icOkPr = new ImageIcon("images/join/Btn_Ok_Pressed.png");						//가입 버튼(Pressed) 이미지 아이콘
		
		icOverlapChkET = new ImageIcon("images/join/Btn_OverlapChk_EnabledTrue.png");	//중복확인 버튼(EnabledTrue) 이미지 아이콘
		icOverlapChkRo = new ImageIcon("images/join/Btn_OverlapChk_Rollover.png");		//중복확인 버튼(Rollover) 이미지 아이콘
		icOverlapChkPr = new ImageIcon("images/join/Btn_OverlapChk_Pressed.png");		//중복확인 버튼(Pressed) 이미지 아이콘
		
		icSendET = new ImageIcon("images/join/Btn_Send_EnabledTrue.png");				//전송하기 버튼(EnabledTrue) 이미지 아이콘
		icSendRo = new ImageIcon("images/join/Btn_Send_Rollover.png");					//전송하기 버튼(Rollover) 이미지 아이콘
		icSendPr = new ImageIcon("images/join/Btn_Send_Pressed.png");					//전송하기 버튼(Pressed) 이미지 아이콘
		
	}

	//타이틀 영역 생성
	private void makeTitle() {
		
		JPanel pnTitleBackground = new JPanel();
		pnTitleBackground.setLayout(new BorderLayout());
		pnTitleBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//타이틀 생성
		JPanel pnTitle = new JPanel();
		pnTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnTitle.setBorder(new EmptyBorder(50, 0, 0, 0));	//패널 패딩 설정
		pnTitle.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JLabel lblTitle = new JLabel("Join");
		lblTitle.setFont(new Font("D2Coding", Font.BOLD, 50));	//라벨 폰트 설정
		lblTitle.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 생상 설정
		
		pnTitle.add(lblTitle);
		pnTitleBackground.add(pnTitle, BorderLayout.CENTER);
		
		
		
		
		add(pnTitleBackground, BorderLayout.NORTH);
		
	}

	//인풋필드 영역 생성
	private void makeInput() {
		JPanel pnInputBackground = new JPanel();
		pnInputBackground.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnInputBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JPanel pnInput = new JPanel();
		pnInput.setLayout(new GridLayout(8, 1, 5, 5));
		pnInput.setBorder(new EmptyBorder(50, 0, 0, 20));	//패널 패딩 설정
		pnInput.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//아이디 필드
		JPanel pnIdInput = new JPanel();
		pnIdInput.setLayout(new BorderLayout());
		pnIdInput.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		//아이디 텍스트필드 생성
		tfId = new JTextField("  아이디");
		tfId.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfId.setFont(new Font("D2Coding", Font.PLAIN, tfId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfId.addFocusListener(this);
		tfId.addKeyListener(this);
		
		//중복확인 버튼 생성
		btnOverlapChk = new JButton(icOverlapChkET);
		Boilerplate.setImageButton(btnOverlapChk, icOverlapChkRo, icOverlapChkPr, 90, 48);	//이미지 버튼 세팅
		btnOverlapChk.addActionListener(this);
		
		
		//비밀번호 텍스트필드 생성
		tfPw = new JPasswordField("  비밀번호");
		tfPw.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfPw.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfPw.setFont(new Font("D2Coding", Font.PLAIN, tfPw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPw.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfPw.addFocusListener(this);
		tfPw.addKeyListener(this);
		
		
		//비밀번호 확인 텍스트필드 생성
		tfPw2 = new JPasswordField("  비밀번호 확인");
		tfPw2.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfPw2.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfPw2.setFont(new Font("D2Coding", Font.PLAIN, tfPw2.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPw2.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfPw2.addFocusListener(this);
		tfPw2.addKeyListener(this);
		
		
		
		//이름, 대학명 필드
		JPanel pnNameSchName = new JPanel();
		pnNameSchName.setLayout(new BorderLayout());
		pnNameSchName.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//이름 텍스트필드 생성
		tfName = new JTextField("  닉네임");
		tfName.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfName.setFont(new Font("D2Coding", Font.PLAIN, tfName.getFont().getSize()));	//텍스트필드 폰트 설정
		tfName.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfName.addFocusListener(this);
		tfName.addKeyListener(this);
		
		//대학명 텍스트필드 생성
		tfSchName = new JTextField("  대학명");
		tfSchName.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfSchName.setFont(new Font("D2Coding", Font.PLAIN, tfSchName.getFont().getSize()));	//텍스트필드 폰트 설정
		tfSchName.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfSchName.addFocusListener(this);
		tfSchName.addKeyListener(this);
		
		//학번, 차량번호 필드
		JPanel pnSchNumCarNum = new JPanel();
		pnSchNumCarNum.setLayout(new BorderLayout());
		pnSchNumCarNum.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//학번 텍스트필드 생성
		tfSchNum = new JTextField("  학번");
		tfSchNum.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfSchNum.setFont(new Font("D2Coding", Font.PLAIN, tfSchNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfSchNum.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfSchNum.addFocusListener(this);
		tfSchNum.addKeyListener(this);

		//차량번호
		tfCarNum = new JTextField("  차량번호");
		tfCarNum.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfCarNum.setFont(new Font("D2Coding", Font.PLAIN, tfCarNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCarNum.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfCarNum.addFocusListener(this);
		tfCarNum.addKeyListener(this);
		
		
		
		//휴대폰번호 필드
		JPanel pnPhonNumInput = new JPanel();
		pnPhonNumInput.setLayout(new BorderLayout());
		pnPhonNumInput.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//휴대폰번호 텍스트필드 생성
		tfPhonNum = new JTextField("  휴대폰번호('-'제외)");
		tfPhonNum.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfPhonNum.setFont(new Font("D2Coding", Font.PLAIN, tfPhonNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPhonNum.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfPhonNum.addFocusListener(this);
		tfPhonNum.addKeyListener(this);
		
		//전송하기 버튼 생성
		btnSend = new JButton(icSendET);
		Boilerplate.setImageButton(btnSend, icSendRo, icSendPr, 90, 48);	//이미지 버튼 세팅
		btnSend.addActionListener(this);
		
		
		//인증번호 필드
		JPanel pnCertif = new JPanel();
		pnCertif.setLayout(new BorderLayout());
		pnCertif.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//인증번호 텍스트필드 생성
		tfCertifiNum = new JTextField("  인증번호");
		tfCertifiNum.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfCertifiNum.setFont(new Font("D2Coding", Font.PLAIN, tfCertifiNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCertifiNum.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfCertifiNum.addFocusListener(this);
		tfCertifiNum.addKeyListener(this);
		
		//인증시간 카운트 라벨 생성
		lblCount = new JLabel();
		lblCount.setPreferredSize(new Dimension(90, 48));	//라벨 크기 설정
		lblCount.setFont(new Font("D2Coding", Font.PLAIN, lblCount.getFont().getSize()));	//라벨 폰트 설정
		lblCount.setForeground(Color.RED);	//라벨 폰트 생상 설정
		lblCount.setHorizontalAlignment(JLabel.CENTER);	//라벨 가운데 정렬
		lblCount.setOpaque(true);	//라벨 투명도 설정
		lblCount.setBackground(Color.WHITE);	//라벨 색상 하얀색으로 설정
		
		
		//취소, 확인 버튼
		JPanel pnBtn = new JPanel();
		pnBtn.setLayout(new BorderLayout());
		pnBtn.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//취소 버튼 생성
		btnCancel = new JButton(icCancelET);
		Boilerplate.setImageButton(btnCancel, icCancelRo, icCancelPr, 183, 50);	//이미지 버튼 세팅
		btnCancel.setEnabled(true);	//활성화 상태로 생성
		btnCancel.addActionListener(this);
		
		//확인 버튼 생성
		btnOk = new JButton(icOkET);
		Boilerplate.setImageButton(btnOk, icOkRo, icOkPr, 183, 50);	//이미지 버튼 세팅
		btnOk.addActionListener(this);
		
		
		pnIdInput.add(tfId, BorderLayout.CENTER);
		pnIdInput.add(btnOverlapChk, BorderLayout.EAST);
		
		pnNameSchName.add(tfName, BorderLayout.CENTER);
		pnNameSchName.add(tfSchName, BorderLayout.EAST);

		pnSchNumCarNum.add(tfSchNum, BorderLayout.CENTER);
		pnSchNumCarNum.add(tfCarNum, BorderLayout.EAST);
		
		pnPhonNumInput.add(tfPhonNum, BorderLayout.CENTER);
		pnPhonNumInput.add(btnSend, BorderLayout.EAST);
		
		pnCertif.add(tfCertifiNum, BorderLayout.CENTER);
		pnCertif.add(lblCount, BorderLayout.EAST);
		
		pnBtn.add(btnCancel, BorderLayout.WEST);
		pnBtn.add(btnOk, BorderLayout.EAST);
		
		pnInput.add(pnIdInput);
		pnInput.add(tfPw);
		pnInput.add(tfPw2);
		pnInput.add(pnNameSchName);
		pnInput.add(pnSchNumCarNum);
		
		
		pnInput.add(pnPhonNumInput);
		pnInput.add(pnCertif);
		pnInput.add(pnBtn);
		
		pnInputBackground.add(pnInput);
		
		add(pnInputBackground, BorderLayout.CENTER);
		
	}
	
	//JTextField PlaceHolder 초기화 함수
	private void clearPH(JTextField tf) {
		
		tf.setText("");
		tf.setForeground(Color.BLACK);
		
	}
	
	//JTextField PlaceHolder 세팅 함수
	private void setPH(JTextField tf) {

		String str = "";
		
		if(tf == tfId) str = "  아이디";
		else if(tf == tfPw) str = "  비밀번호";
		else if(tf == tfPw2) str = "  비밀번호 확인";
		else if(tf == tfName) str = "  닉네임";
		else if(tf == tfSchName) str = "  대학명";
		else if(tf == tfSchNum) str = "  학번";
		else if(tf == tfCarNum) str = "  차량번호";
		else if(tf == tfPhonNum) str = "  휴대폰번호('-'제외)";
		else if(tf == tfCertifiNum) str = "  인증번호";
		
		tf.setEnabled(true);
		tf.setText(str);
		tf.setForeground(Color.GRAY);
		
	}
	
	//JPasswordField PlaceHolder 초기화 함수
	private void clearPwPH(JPasswordField tf) {
		
		tf.setEchoChar('●');	//작성되는 문자 '●'로 변환 설정
		tf.setText("");
		tf.setForeground(Color.BLACK);
		
	}
	
	//JPasswordField PlaceHolder 세팅 함수
	private void setPwPH(JPasswordField tf) {
		
		String str = "";
		
		if(tf == tfPw) str = "  비밀번호";
		else if(tf == tfPw2) str = "  비밀번호 확인";
		
		tf.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tf.setText(str);
		tf.setForeground(Color.GRAY);
		
	}
	
	//확인 버튼 활성화 조건 검사 입력받은 텍스트가 Boilerplate의 유효성검사에 부합하는지 확인하고 버튼 활성화
	private void okBtnChk() {
		
		String pw1 = getPassword(tfPw);
		
		String pw2 = getPassword(tfPw2);
		
		if(!tfId.getText().equals("  아이디") && !pw1.equals("  비밀번호") && !pw2.equals("  비밀번호 확인") &&  !tfName.getText().equals("  닉네임") &&  !tfSchName.getText().equals("  대학명")
				&&  !tfSchNum.getText().equals("  학번") &&  !tfCarNum.getText().equals("  차량번호") && !tfPhonNum.getText().equals("  휴대폰번호('-'제외)")&& !tfCertifiNum.getText().equals("  인증번호")) {
			if(overlapFlag && Boilerplate.pwValidation(pw1) && Boilerplate.pwValidation(pw2) && Boilerplate.nameValidation(tfName.getText())  && Boilerplate.SchNameValidation(tfSchName.getText())
					&& Boilerplate.SchNumValidation(tfSchNum.getText())  && Boilerplate.CarNumValidation(tfCarNum.getText()) && Boilerplate.phonNumValidation(tfPhonNum.getText()) && certifiFlag) {
				btnOk.setEnabled(true);	//확인 버튼 활성화
			} else {
				btnOk.setEnabled(false);	//확인 버튼 비활성화
			}
		}
		
	}
	
	//비밀번호 문자열로 변환
	private String getPassword(JPasswordField pw) {
		
		String pwStr = "";
		
		char[] secret_pw = pw.getPassword();
		
		for(char cha : secret_pw){
		     Character.toString(cha);
		     pwStr += cha;
		}
		
		return pwStr;
		
	}
	
	//로그인 화면으로 전환
	private void changeLogin() {
		
		//판단 변수 초기화
		overlapFlag = false;  
		certifiFlag = false;  
		sendCertifiFlag = 1;
		
		Boilerplate.redraw(new Login());	//로그인 화면으로 전환
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == btnOverlapChk) {
			//아이디 유효성 검사
			if(Boilerplate.idValidation(tfId.getText())) {
				System.out.println("(Join) 아이디 중복여부 검사");
				
				usersInId = tfId.getText();	//사용자가 아이디 텍스트필드에 입력한 데이터 저장
				
				ResultSet rs = DB.getResult("select * from USERS WHERE USERSID like '" + usersInId + "'");	//USERS 테이블에서 일치하는 사용자 존재 유무 조회
				
				try {
					if(rs.next()) {
						System.out.println("(Join) 아이디 중복");
						JOptionPane.showMessageDialog(mainFrame, "이미 사용중인 아이디입니다.\n다시 시도해주세요.", "아이디 중복", JOptionPane.ERROR_MESSAGE);
						
						btnOverlapChk.setEnabled(false);	//중복확인 버튼 비활성화
						setPH(tfId);	//PlaceHolder 세팅
					} else {
						System.out.println("(Join) 아이디 사용가능");
						
						int result = JOptionPane.showConfirmDialog(mainFrame, "사용 가능한 아이디입니다.\n" + usersInId + "로 사용하시겠습니까?", "아이디 사용 가능", JOptionPane.YES_NO_OPTION);
						
						if(result == JOptionPane.YES_OPTION) {
							tfId.setEnabled(false);	//아이디 텍스트필드 비활성화
							btnOverlapChk.setEnabled(false);	//중복확인 버튼 비활성화
							overlapFlag = true;
							okBtnChk();
						} else {
							btnOverlapChk.setEnabled(false);	//중복확인 버튼 비활성화
							setPH(tfId);	//PlaceHolder 세팅
						}
					}
				} catch (SQLException e1) {
					System.out.println("(Join) 예외발생 : 중복 아이디 조회에 실패했습니다.");
					e1.printStackTrace();
				} finally {
					try {
						rs.close();
						System.out.println("(Join) ResultSet 객체 종료");
					} catch (SQLException e1) {
						System.out.println("(Join) 예외발생 : ResultSet 객체 종료에 실패했습니다.");
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "사용할 수 없는 아이디입니다.\n아이디는 영문, 숫자 조합의 5자리 이상 12자리 이하로 사용 가능하며,\n첫 자리에 숫자를 사용할 수 없습니다.\n다시 시도해주세요.", "규칙 위배", JOptionPane.ERROR_MESSAGE);
				
				btnOverlapChk.setEnabled(false);	//중복확인 버튼 비활성화
				setPH(tfId);	//PlaceHolder 세팅
			}
		} else if(obj == btnSend && sendCertifiFlag == 1 && Boilerplate.phonNumValidation(tfPhonNum.getText())) {
			tfPhonNum.setEnabled(false);	//휴대폰번호 텍스트필드 비활성화
			
			System.out.println("(Join) 인증번호 발송");
			
			certifiNum = Boilerplate.certificationNum();	// 인증번호 6자리 숫자 난수 생성
			JOptionPane.showMessageDialog(mainFrame, "인증번호는 [" + certifiNum + "]입니다.", tfPhonNum.getText() + " 문자", JOptionPane.PLAIN_MESSAGE);
			
			//인증하기 버튼으로 변경
			sendCertifiFlag = 2;
			btnSend.setIcon(icCertifiedET);
			btnSend.setRolloverIcon(icCertifiedRo);
			btnSend.setPressedIcon(icCertifiedPr);
			btnSend.setEnabled(false);	//인증하기 버튼 비활성화
			
			//인증시간 카운트
			countThread = new CountThread(1, lblCount, btnSend, tfPhonNum, tfCertifiNum);	//카운트 스레드 생성
			//(카운트다운 작업을 시작할 숫자, 카운트다운 숫자를 표시할 라벨, 사용자가 클릭할 버튼,
			//전화번호를 입력받는 텍스트 필드, 인증 번호를 입력받는 텍스트 필드)
			countThread.start();	//카운트 스레드 시작
			System.out.println("(Join) Count Thread 실행");
		} else if(obj == btnSend && sendCertifiFlag == 2 && Boilerplate.certifiNumValidation(tfCertifiNum.getText())) {
			System.out.println("(Join) 인증번호 일치여부 검사");
			
			if(certifiNum == Integer.parseInt(tfCertifiNum.getText())) {
				System.out.println("(Join) 인증완료");
				
				JOptionPane.showMessageDialog(mainFrame, "인증이 완료되었습니다.", "인증 완료", JOptionPane.PLAIN_MESSAGE);
				
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				tfCertifiNum.setEnabled(false);	//인증번호 텍스트필드 비활성화
				btnSend.setEnabled(false);		//인증하기 버튼 비활성화
				
				certifiFlag  = true;
				
				okBtnChk();
			} else {
				System.out.println("(Join) 인증실패");
				
				JOptionPane.showMessageDialog(mainFrame, "인증번호가 일치하지 않습니다.\n다시 시도해주세요.", "인증 실패", JOptionPane.ERROR_MESSAGE);
				
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				setPH(tfPhonNum);		//PlaceHolder 세팅
				setPH(tfCertifiNum);	//PlaceHolder 세팅
				
				certifiNum = 0;	//인증번호 만료 처리
				
				//전송하기 버튼으로 변경
				sendCertifiFlag = 1;
				btnSend.setIcon(icSendET);
				btnSend.setRolloverIcon(icSendRo);
				btnSend.setPressedIcon(icSendPr);
				btnSend.setEnabled(false);	//전송하기 버튼 비활성화
			}
		} else if(obj == btnCancel) {
			
			changeLogin();	//로그인 화면으로 전환
			
		} else if(obj == btnOk) {
			
			usersInId = tfId.getText();					//입력받은 아이디 저장
			usersInPw = getPassword(tfPw);				//입력받은 비밀번호 저장
			
			usersInName = tfName.getText();				//입력받은 이름 저장
			usersInSchName = tfSchName.getText();		//입력받은 대학명 저장
			
			
			usersInSchNum = tfSchNum.getText();		//입력받은 학번 저장
			usersInCarNum = tfCarNum.getText();		//입력받은 차량번호 저장
			
			usersInPhoneNum = tfPhonNum.getText();		//입력받은 휴대폰번호 저장
			
			//입력받은 데이터 유효성 검사
			if(Boilerplate.idValidation(usersInId) && Boilerplate.nameValidation(usersInName) && Boilerplate.SchNameValidation(usersInSchName) && Boilerplate.SchNumValidation(usersInSchNum)  && Boilerplate.CarNumValidation(usersInCarNum) && Boilerplate.phonNumValidation(usersInPhoneNum)) {
				
				String pw2 = getPassword(tfPw2);
				
				if(usersInPw.equals(pw2)) {	//비밀번호와 비밀번호 확인 일치여부 검사
					if(Boilerplate.pwValidation(usersInPw)) {
						
						
						
						
						try {
							
							
							String pwSalt = Encryption.Salt();	//SHA512 암호화에 사용할 난수 생성
							usersInPw = Encryption.SHA512(usersInPw, pwSalt);	//입력받은 비밀번호를 Salt 값으로 SHA512 암호화
							
							String sqlInsert = "INSERT INTO USERS (USERSID, USERSPW, USERSNAME, USERSSCHNAME, USERSSCHNUM, USERSCARNUM, USERSPHONENUM, USERSPWSALT)"
									+ " VALUES('" + usersInId + "', '" + usersInPw + "', '" + usersInName + "','" + usersInSchName + "', '" + usersInSchNum + "', '"+ usersInCarNum +"',  '" + usersInPhoneNum + "', '" + pwSalt + "')";	//회원정보 Insert문 생성
							DB.executeSQL(sqlInsert);	//DB로 Insert문 전송
							
							
							
														JOptionPane.showMessageDialog(mainFrame, "회원가입이 완료되었습니다.", "회원가입 완료", JOptionPane.PLAIN_MESSAGE);
							
							changeLogin();	//로그인 화면으로 전환
					
							System.out.println("(Join) ResultSet 객체 종료");
							}finally {
								
						}
				}
			
						
					
						
					else {
						JOptionPane.showMessageDialog(mainFrame, "사용할 수 없는 비밀번호입니다.\n비밀번호는 영문, 숫자, 특수문자 조합으로\n최소 8자리 이상, 최대 15자리 이하로 사용 가능합니다.\n다시 시도해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
						
						setPwPH(tfPw2);	//비밀번호 확인 텍스트필드 초기화
						btnOk.setEnabled(false);	//확인 버튼 비활성화
					}
				} else {
					JOptionPane.showMessageDialog(mainFrame, "비밀번호와 비밀번호 확인이 일치하지 않습니다.\n다시 시도해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
					
					setPwPH(tfPw2);	//비밀번호 확인 텍스트필드 초기화
					btnOk.setEnabled(false);	//확인 버튼 비활성화
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "정확하게 입력되지 않은 항목이 있습니다.\n다시 시도해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfId) {
			//아이디 텍스트필드 PlaceHolder
			if(tfId.getText().equals("  아이디")) {
				clearPH(tfId);	//PlaceHolder 초기화
			}
		} else if(obj == tfPw) {
			
			String pw1 = getPassword(tfPw);
			
			//비밀번호 텍스트필드 PlaceHolder
			if(pw1.equals("  비밀번호")) {
				clearPwPH(tfPw);	//PlaceHolder 초기화
			}
		} else if(obj == tfPw2) {
			
			String pw2 = getPassword(tfPw2);
			
			//비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.equals("  비밀번호 확인")) {
				clearPwPH(tfPw2);	//PlaceHolder 초기화
			}
		} else if(obj == tfName) {
			//이름 텍스트필드 PlaceHolder
			if(tfName.getText().equals("  닉네임")) {
				clearPH(tfName);	//PlaceHolder 초기화
			}
		} else if(obj == tfSchName) {
			//대학명 텍스트필드 PlaceHolder
			if(tfSchName.getText().equals("  대학명")) {
				clearPH(tfSchName);	//PlaceHolder 초기화
			}
		} else if(obj == tfSchNum) {
			//학번 텍스트필드 PlaceHolder
			if(tfSchNum.getText().equals("  학번")) {
				clearPH(tfSchNum);	//PlaceHolder 초기화
			}
		} else if(obj == tfCarNum) {
			//차량번호 텍스트필드 PlaceHolder
			if(tfCarNum.getText().equals("  차량번호")) {
				clearPH(tfCarNum);	//PlaceHolder 초기화
			}
		} else if(obj == tfPhonNum) {
			//휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNum.getText().equals("  휴대폰번호('-'제외)")) {
				clearPH(tfPhonNum);	//PlaceHolder 초기화
			}
		} else if(obj == tfCertifiNum) {
			//인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNum.getText().equals("  인증번호")) {
				clearPH(tfCertifiNum);	//PlaceHolder 초기화
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfId) {
			//아이디 텍스트필드 PlaceHolder
			if(tfId.getText().isEmpty()) {
				setPH(tfId);	//PlaceHolder 세팅
			}
		} else if(obj == tfPw) {
			
			String pw1 = getPassword(tfPw);
			
			//비밀번호 텍스트필드 PlaceHolder
			if(pw1.isEmpty()) {
				setPwPH(tfPw);	//PlaceHolder 세팅
			}
		} else if(obj == tfPw2) {
			
			String pw2 = getPassword(tfPw2);
			
			//비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.isEmpty()) {
				setPwPH(tfPw2);	//PlaceHolder 세팅
			}
		} else if(obj == tfName) {
			//이름 텍스트필드 PlaceHolder
			if(tfName.getText().isEmpty()) {
				setPH(tfName);	//PlaceHolder 세팅
			}
		} else if(obj == tfSchName) {
			//대학명 텍스트필드 PlaceHolder
			if(tfSchName.getText().isEmpty()) {
				setPH(tfSchName);	//PlaceHolder 세팅
			}
		} else if(obj == tfSchNum) {
			//학번 텍스트필드 PlaceHolder
			if(tfSchNum.getText().isEmpty()) {
				setPH(tfSchNum);	//PlaceHolder 세팅
			}
		} else if(obj == tfCarNum) {
			//차량번호 텍스트필드 PlaceHolder
			if(tfCarNum.getText().isEmpty()) {
				setPH(tfCarNum);	//PlaceHolder 세팅
			}
		} else if(obj == tfPhonNum) {
			//휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNum.getText().isEmpty()) {
				setPH(tfPhonNum);	//PlaceHolder 세팅
			}
		} else if(obj == tfCertifiNum) {
			//인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNum.getText().isEmpty()) {
				setPH(tfCertifiNum);	//PlaceHolder 세팅
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(!tfId.getText().equals("  아이디")) {
			if(tfId.getText().length() >= 5 && tfId.getText().length() <= 12 && !overlapFlag) {	//중복확인 버튼 활성화 조건 검사
				btnOverlapChk.setEnabled(true);	//중복확인 버튼 활성화
			} else {
				btnOverlapChk.setEnabled(false);	//중복확인 버튼 비활성화
			}
		}
		
		if(!tfPhonNum.getText().equals("  휴대폰번호('-'제외)") && sendCertifiFlag == 1 && !certifiFlag) {
			if(Boilerplate.phonNumValidation(tfPhonNum.getText())) {	//전송하기 버튼 활성화 조건 검사
				btnSend.setEnabled(true);	//전송하기 버튼 활성화
			} else {
				btnSend.setEnabled(false);	//전송하기 버튼 비활성화
			}
		}
		
		if(!tfCertifiNum.getText().equals("  인증번호") && sendCertifiFlag == 2 && !certifiFlag) {
			if(Boilerplate.certifiNumValidation(tfCertifiNum.getText())) {	//인증하기 버튼 활성화 조건 검사
				btnSend.setEnabled(true);	//인증하기 버튼 활성화
			} else {
				btnSend.setEnabled(false);	//인증하기 버튼 비활성화
			}
		}
		
		okBtnChk();	//확인 버튼 활성화 조건 검사
		
	}

	
	
	public static void setSendCertifiFlag(int sendCertifiFlagIn) {
		
		sendCertifiFlag = sendCertifiFlagIn;
		
	}

	public static void setCertifiNum(int certifiNumIn) {
		
		certifiNum = certifiNumIn;
		
	}

	

}
