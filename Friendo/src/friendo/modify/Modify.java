//정보수정하기 패널 클래스

package friendo.modify;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import friendo.common.Boilerplate;
import friendo.common.DB;
import friendo.common.Encryption;
import friendo.login.CountThread;
import friendo.main.Open;
import friendo.start.StartFrame;


public class Modify extends JPanel implements ActionListener, FocusListener, KeyListener {

	private StartFrame mainFrame = null;	//메인 프레임 객체
	
	private JButton btnBack;				//뒤로가기 버튼
	
	private String usersInId;				//사용자에게 입력 받은 아이디
	private String usersInPw;				//사용자에게 입력 받은 비밀번호
	
	private String usersInPhoneNum; 		//사용자에게 입력 받은 휴대폰번호
	private JTextField tfNewName;			//사용자에게 입력 받은 이름
	private JTextField tfNewSchName;		//사용자에게 입력 받은 대학명
	private JTextField tfNewSchNum;			//사용자에게 입력 받은 학번
	private JTextField tfNewCarNum;			//사용자에게 입력 받은 학번
	private JTextField tfPhonNumId;			
	private JTextField tfCertifiNumId;

	
	
	//개인정보 수정하기
	private JTextField tfIdMd;				//아이디 입력 텍스트필드
	private JTextField tfPhonNumMd;			//휴대폰번호 입력 텍스트필드
	private JButton btnSendMd;				//전송 버튼
	private JTextField tfCertifiNumMd;		//인증번호 입력 텍스트필드
	private JLabel lblCountMd;				//인증시간 카운트 라벨
	private JButton btnCtfy;				//비밀번호 찾기 버튼
	private static int sendCertifiFlagPw = 1; //전송하기 버튼이면 1, 인증하기 버튼이면 2
	private JPasswordField tfNewPwMd;		//새 비밀번호 입력 텍스트필드
	private JPasswordField tfNewPw2Md;		//새 비밀번호 확인 입력 텍스트필드
	private JButton btnMdChange;			//수정 완료 버튼
	private boolean certifiFlagPw = false;	//인증 완료여부 저장
	
	//공통
	private static int certifiNum;			//인증번호
	private CountThread countThread = null;	//인증시간 카운트 Thread
	private int idPwFlag = 0;				//선택되기 전이면 0, 아이디 찾기에서의 이벤트면 1, 비밀번호 찾기에서의 이벤트면 2
	
	private ImageIcon icBackET;				//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icBackRo;				//뒤로가기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icBackPr;				//뒤로가기 버튼(Pressed) 이미지 아이콘
	private ImageIcon icChangeET;			//변경 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icChangeRo;			//변경 버튼(Rollover) 이미지 아이콘
	private ImageIcon icChangePr;			//변경 버튼(Pressed) 이미지 아이콘
	private ImageIcon icFindET;				//찾기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icFindRo;				//찾기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icFindPr;				//찾기 버튼(Pressed) 이미지 아이콘
	private ImageIcon icSendET;				//전송하기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icSendRo;				//전송하기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icSendPr;				//전송하기 버튼(Pressed) 이미지 아이콘
	private ImageIcon icCertifiedET;		//인증하기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icCertifiedRo;		//인증하기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icCertifiedPr;		//인증하기 버튼(Pressed) 이미지 아이콘



	

	private JPanel pnNewSchNumCarNum;

	private JPanel pnNewNameSchName;




	
	
	//개인정보 수정 화면
	public Modify() {
		
		setLayout(new BorderLayout());
        setBackground(new Color(255, 252, 244));
        
        mainFrame = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
        
        mainFrame.setTitle("Modify");
        
		
		
		makeImageIcon();	//이미지 아이콘 생성
		
        makeTitle();	//타이틀 영역 생성
       
        makeModify();	//비밀번호 조회 영역 생성

       
	}
	
	//이미지 아이콘 생성
	private void makeImageIcon() {
		
		icBackET = new ImageIcon("images/modify/Btn_Back_EnabledTrue.png");				//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBackRo = new ImageIcon("images/modify/Btn_Back_Rollover.png");					//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBackPr = new ImageIcon("images/modify/Btn_Back_Pressed.png");					//뒤로가기 버튼(Pressed) 이미지 아이콘
		
		icChangeET = new ImageIcon("images/modify/Btn_Change_EnabledTrue.png");			//변경 버튼(EnabledTrue) 이미지 아이콘
		icChangeRo = new ImageIcon("images/modify/Btn_Change_Rollover.png");				//변경 버튼(Rollover) 이미지 아이콘
		icChangePr = new ImageIcon("images/modify/Btn_Change_Pressed.png");				//변경 버튼(Pressed) 이미지 아이콘
		
		icFindET = new ImageIcon("images/modify/Btn_Modify_EnabledTrue.png");				//수정하기 버튼(EnabledTrue) 이미지 아이콘
		icFindRo = new ImageIcon("images/modify/Btn_Modify_Rollover.png");					//수정하기 버튼(Rollover) 이미지 아이콘
		icFindPr = new ImageIcon("images/modify/Btn_Modify_Pressed.png");					//수정하기 버튼(Pressed) 이미지 아이콘
		
		icSendET = new ImageIcon("images/modify/Btn_Send_EnabledTrue.png");				//전송하기 버튼(EnabledTrue) 이미지 아이콘
		icSendRo = new ImageIcon("images/modify/Btn_Send_Rollover.png");					//전송하기 버튼(Rollover) 이미지 아이콘
		icSendPr = new ImageIcon("images/modify/Btn_Send_Pressed.png");					//전송하기 버튼(Pressed) 이미지 아이콘
		
		icCertifiedET = new ImageIcon("images/modify/Btn_Certified_EnabledTrue.png");		//인증하기 버튼(EnabledTrue) 이미지 아이콘
		icCertifiedRo = new ImageIcon("images/modify/Btn_Certified_Rollover.png");		//인증하기 버튼(Rollover) 이미지 아이콘
		icCertifiedPr = new ImageIcon("images/modify/Btn_Certified_Pressed.png");			//인증하기 버튼(Pressed) 이미지 아이콘
		
	}

	//타이틀 영역 생성
	private void makeTitle() {

		JPanel pnTitleBackground = new JPanel();
		pnTitleBackground.setLayout(new BorderLayout());
		pnTitleBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//타이틀 생성
		JPanel pnTitle = new JPanel();
		pnTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnTitle.setBorder(new EmptyBorder(50, 0, 0, 120));	//패널 패딩 설정
		pnTitle.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JLabel lblTitle = new JLabel("개인정보수정");
		lblTitle.setFont(new Font("D2Coding", Font.BOLD, 50));	//라벨 폰트 설정
		lblTitle.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 생상 설정
		
		pnTitle.add(lblTitle);
		pnTitleBackground.add(pnTitle, BorderLayout.CENTER);
		
		//뒤로가기 버튼 생성
		JPanel pnBack = new JPanel();
		pnBack.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnBack.setPreferredSize(new Dimension(120, 120));	//패널 크기 설정
		pnBack.setBorder(new EmptyBorder(30, 0, 0, 0));		//패널 패딩 설정
		pnBack.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
				
		btnBack = new JButton(icBackET);
		Boilerplate.setImageButton(btnBack, icBackRo, icBackPr, 100, 50);	//이미지 버튼 세팅
		btnBack.setEnabled(true);	
		btnBack.addActionListener(this);
		
				
		pnBack.add(btnBack);
		pnTitleBackground.add(pnBack, BorderLayout.WEST);
				
		add(pnTitleBackground, BorderLayout.NORTH);
				
	}
		
	
	//비밀번호 조회 영역 생성
	private void makeModify() {
		
		JPanel pnModifyBackground = new JPanel();
		pnModifyBackground .setLayout(new BorderLayout());
		pnModifyBackground .setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//안내 메시지 생성
		JLabel lblInfo = new JLabel("아래의 인증을 하고 정보를 변경하세요", JLabel.CENTER);
		lblInfo.setFont(new Font("D2Coding", Font.BOLD, 18));		//라벨 폰트 설정
		lblInfo.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 색상 설정
		lblInfo.setBorder(new EmptyBorder(50, 0, 0, 0));
		
		JPanel pnModifyLeft = new JPanel();
		pnModifyLeft.setLayout(new GridLayout(8, 1, 5, 5));
		pnModifyLeft.setBorder(new EmptyBorder(100, 80, 0, 0));		//패널 패딩 설정
		pnModifyLeft.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		
		//아이디 텍스트필드 생성
		tfIdMd = new JTextField("  아이디");
		tfIdMd.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfIdMd.setFont(new Font("D2Coding", Font.PLAIN, tfIdMd.getFont().getSize()));	//텍스트필드 폰트 설정
		tfIdMd.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfIdMd.addFocusListener(this);
		tfIdMd.addKeyListener(this);
		
		
		//휴대폰번호 필드
		JPanel pnPhonNumInput = new JPanel();
		pnPhonNumInput.setLayout(new BorderLayout());
		pnPhonNumInput.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//휴대폰번호 텍스트필드 생성
		tfPhonNumMd = new JTextField("  휴대폰번호('-'제외)");
		tfPhonNumMd.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfPhonNumMd.setFont(new Font("D2Coding", Font.PLAIN, tfPhonNumMd.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPhonNumMd.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfPhonNumMd.addFocusListener(this);
		tfPhonNumMd.addKeyListener(this);
		
		//전송하기 버튼 생성
		btnSendMd = new JButton(icSendET);
		Boilerplate.setImageButton(btnSendMd, icSendRo, icSendPr, 90, 48);	//이미지 버튼 세팅
		btnSendMd.addActionListener(this);
		
		
		//인증번호 필드
		JPanel pnCertif = new JPanel();
		pnCertif.setLayout(new BorderLayout());
		pnCertif.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//인증번호 텍스트필드 생성
		tfCertifiNumMd = new JTextField("  인증번호");
		tfCertifiNumMd.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfCertifiNumMd.setFont(new Font("D2Coding", Font.PLAIN, tfCertifiNumMd.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCertifiNumMd.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfCertifiNumMd.addFocusListener(this);
		tfCertifiNumMd.addKeyListener(this);
		
		//인증시간 카운트 라벨 생성
		lblCountMd = new JLabel();
		lblCountMd.setPreferredSize(new Dimension(90, 48));	//라벨 크기 설정
		lblCountMd.setFont(new Font("D2Coding", Font.PLAIN, lblCountMd.getFont().getSize()));	//라벨 폰트 설정
		lblCountMd.setForeground(Color.RED);	//라벨 폰트 색상 설정
		lblCountMd.setHorizontalAlignment(JLabel.CENTER);	//라벨 가운데 정렬
		lblCountMd.setOpaque(true);	//라벨 투명도 설정
		lblCountMd.setBackground(Color.WHITE);	//라벨 색상 하얀색으로 설정
		
		
		//인증완료 버튼
		JPanel pnCtfy = new JPanel();
		pnCtfy.setLayout(new FlowLayout());
		pnCtfy.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		
		//인증완료 버튼 생성
		btnCtfy = new JButton(icFindET);
		Boilerplate.setImageButton(btnCtfy, icFindRo, icFindPr, 183, 50);	//이미지 버튼 세팅
		btnCtfy.addActionListener(this);
		
		
		JPanel pnModifyRight = new JPanel();
		pnModifyRight.setLayout(new GridLayout(8, 1, 5, 5));
		pnModifyRight.setBorder(new EmptyBorder(100, 0, 0, 80));		//패널 패딩 설정
		pnModifyRight.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		//이름, 대학명 필드
		pnNewNameSchName = new JPanel();
		pnNewNameSchName.setLayout(new BorderLayout());
		pnNewNameSchName.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		pnNewNameSchName.setVisible(false);	//미노출 상태로 생성
		
		//이름 텍스트필드 생성
		tfNewName = new JTextField("  닉네임");
		tfNewName.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfNewName.setFont(new Font("D2Coding", Font.PLAIN, tfNewName.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewName.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewName.addFocusListener(this);
		tfNewName.addKeyListener(this);
				
		//대학명 텍스트필드 생성
		tfNewSchName = new JTextField("  대학명");
		tfNewSchName.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfNewSchName.setFont(new Font("D2Coding", Font.PLAIN, tfNewSchName.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewSchName.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewSchName.addFocusListener(this);
		tfNewSchName.addKeyListener(this);
				
		//학번, 차량번호 필드
		pnNewSchNumCarNum = new JPanel();
		pnNewSchNumCarNum.setLayout(new BorderLayout());
		pnNewSchNumCarNum.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		pnNewSchNumCarNum.setVisible(false);	//미노출 상태로 생성		
		
		//학번 텍스트필드 생성
		tfNewSchNum = new JTextField("  학번");
		tfNewSchNum.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfNewSchNum.setFont(new Font("D2Coding", Font.PLAIN, tfNewSchNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewSchNum.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewSchNum.addFocusListener(this);
		tfNewSchNum.addKeyListener(this);

		//차량번호
		tfNewCarNum = new JTextField("  차량번호");
		tfNewCarNum.setPreferredSize(new Dimension(207, 48));	//텍스트필드 크기 설정
		tfNewCarNum.setFont(new Font("D2Coding", Font.PLAIN, tfNewCarNum.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewCarNum.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewCarNum.addFocusListener(this);
		tfNewCarNum.addKeyListener(this);
		
		//새 비밀번호 텍스트필드 생성
		tfNewPwMd = new JPasswordField("  새 비밀번호");
		tfNewPwMd.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfNewPwMd.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfNewPwMd.setFont(new Font("D2Coding", Font.PLAIN, tfNewPwMd.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewPwMd.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewPwMd.setVisible(false);	//미노출 상태로 생성
		tfNewPwMd.addFocusListener(this);
		tfNewPwMd.addKeyListener(this);
		
		
		//새 비밀번호 확인 텍스트필드 생성
		tfNewPw2Md = new JPasswordField("  새 비밀번호 확인");
		tfNewPw2Md.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfNewPw2Md.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfNewPw2Md.setFont(new Font("D2Coding", Font.PLAIN, tfNewPw2Md.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewPw2Md.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewPw2Md.setVisible(false);	//미노출 상태로 생성
		tfNewPw2Md.addFocusListener(this);
		tfNewPw2Md.addKeyListener(this);
		
		
		//변경 버튼
		JPanel pnChangeBtn = new JPanel();
		pnChangeBtn.setLayout(new FlowLayout());
		pnChangeBtn.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		// 변경 버튼 생성
		btnMdChange = new JButton(icChangeET);
		Boilerplate.setImageButton(btnMdChange, icChangeRo, icChangePr, 183, 50);	//이미지 버튼 세팅
		btnMdChange.setVisible(false);	//미노출 상태로 생성
		btnMdChange.addActionListener(this);
		
		
		pnPhonNumInput.add(tfPhonNumMd, BorderLayout.CENTER);
		pnPhonNumInput.add(btnSendMd, BorderLayout.EAST);
		
		pnCertif.add(tfCertifiNumMd, BorderLayout.CENTER);
		pnCertif.add(lblCountMd, BorderLayout.EAST);
		
		
		pnCtfy.add(btnCtfy);
		
		pnChangeBtn.add(btnMdChange);
		
		pnModifyBackground.add(lblInfo, BorderLayout.NORTH);
		pnModifyLeft.add(tfIdMd);
		pnModifyLeft.add(pnPhonNumInput);
		pnModifyLeft.add(pnCertif);
		pnModifyLeft.add(pnCtfy);
		
		pnNewNameSchName.add(tfNewName, BorderLayout.CENTER);
		pnNewNameSchName.add(tfNewSchName, BorderLayout.EAST);

		pnNewSchNumCarNum.add(tfNewSchNum, BorderLayout.CENTER);
		pnNewSchNumCarNum.add(tfNewCarNum, BorderLayout.EAST);
		
		pnModifyRight.add(pnNewNameSchName);
		pnModifyRight.add(pnNewSchNumCarNum);
		
		pnModifyRight.add(tfNewPwMd);
		pnModifyRight.add(tfNewPw2Md);
		pnModifyRight.add(pnChangeBtn);
		
		pnModifyBackground.add(pnModifyLeft, BorderLayout.WEST);
		pnModifyBackground.add(pnModifyRight, BorderLayout.EAST);
		
		add(pnModifyBackground, BorderLayout.CENTER);
		
	}
	
	//JTextField PlaceHolder 초기화 함수
	private void clearPH(JTextField tf) {
		
		tf.setText("");
		tf.setForeground(Color.BLACK);
		
	}
	
	//JTextField PlaceHolder 세팅 함수
	private void setPH(JTextField tf) {

		String str = "";
		
		if(tf == tfNewName) str = "  닉네임";
		else if(tf == tfNewSchName) str = "  대학명";
		else if(tf == tfNewSchNum) str = "  학번";
		else if(tf == tfNewCarNum) str = "  차량번호";
		else if(tf == tfPhonNumId || tf == tfPhonNumMd) str = "  휴대폰번호('-'제외)";
		else if(tf == tfCertifiNumId || tf == tfCertifiNumMd) str = "  인증번호";
		else if(tf == tfIdMd) str = "  아이디";
		
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
		
		if(tf == tfNewPwMd) str = "  새 비밀번호";
		else if(tf == tfNewPw2Md) str = "  새 비밀번호 확인";
		
		tf.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tf.setText(str);
		tf.setForeground(Color.GRAY);
		
	}
	

	
	//비밀번호 찾기 버튼 활성화 조건 검사
	private void pwFindBtnChk() {
		
		if(!tfIdMd.getText().equals("  아이디") && !tfPhonNumMd.getText().equals("  휴대폰번호('-'제외)")&& !tfCertifiNumMd.getText().equals("  인증번호")) {
			if(Boilerplate.idValidation(tfIdMd.getText()) && Boilerplate.phonNumValidation(tfPhonNumMd.getText()) && certifiFlagPw) {
				btnCtfy.setEnabled(true);	//비밀번호 찾기 버튼 활성화
			} else {
				btnCtfy.setEnabled(false);	//비밀번호 찾기 버튼 비활성화
			}
		}
		
	}
	
	//에러 핸들링
	private void errorHandling() {
		
		
		 if(idPwFlag == 2) {	//비밀번호 찾기 에러 핸들링
			setPH(tfIdMd);
			setPH(tfPhonNumMd);
			setPH(tfCertifiNumMd);
			
			chageSend();	//전송하기 버튼으로 변경
			
			btnCtfy.setEnabled(false);	//비밀번호 찾기 버튼 비활성화
			
			certifiFlagPw = false;	//인증 완료 상태 변경
			
			JOptionPane.showMessageDialog(mainFrame, "입력하신 정보와 일치하는 회원이 없습니다.", "비밀번호 찾기 실패", JOptionPane.ERROR_MESSAGE);
			tfIdMd.requestFocus();
			
			System.out.println("(PW Find) 비밀번호 찾기 실패");
		}
		
	}

	//전송하기 버튼으로 변경
	private void chageSend() {
		
		
		if(idPwFlag == 2) {	//비밀번호 찾기
			sendCertifiFlagPw = 1;
			btnSendMd.setIcon(icSendET);
			btnSendMd.setRolloverIcon(icSendRo);
			btnSendMd.setPressedIcon(icSendPr);
			btnSendMd.setEnabled(false);	//전송하기 버튼 비활성화
		}
		
	}
	
	//인증하기 버튼으로 변경
	private void changeCertifi() {
		
		
		if(idPwFlag == 2) {	//비밀번호 찾기
			sendCertifiFlagPw = 2;
			btnSendMd.setIcon(icCertifiedET);
			btnSendMd.setRolloverIcon(icCertifiedRo);
			btnSendMd.setPressedIcon(icCertifiedPr);
			btnSendMd.setEnabled(false);	//인증하기 버튼 비활성화
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
		
		sendCertifiFlagPw = 1;
		certifiFlagPw = false;
		idPwFlag = 0;
		
	    Boilerplate.redraw(new Open());	//로그인 화면으로 전환/////////////////////////////////////////
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == btnBack) {
			
			changeLogin();	//로그인 화면으로 전환
			
		
			}
		if(obj == btnSendMd && sendCertifiFlagPw == 1 && Boilerplate.phonNumValidation(tfPhonNumMd.getText())) {
			tfPhonNumMd.setEnabled(false);	//휴대폰번호 텍스트필드 비활성화
			
			System.out.println("(PW Find) 인증번호 발송");
			
			certifiNum = Boilerplate.certificationNum();	//6자리 숫자 난수 생성
			JOptionPane.showMessageDialog(mainFrame, "인증번호는 [" + certifiNum + "]입니다.", tfPhonNumMd.getText() + " 문자", JOptionPane.PLAIN_MESSAGE);
			
			changeCertifi();	//인증하기 버튼으로 변경
			
			//인증시간 카운트
			countThread = new CountThread(3, lblCountMd, btnSendMd, tfPhonNumMd, tfCertifiNumMd);	//카운트 스레드 생성
			countThread.start();	//카운트 스레드 시작
			System.out.println("(PW Find) Count Thread 실행");
		} else if(obj == btnSendMd && sendCertifiFlagPw == 2 && Boilerplate.certifiNumValidation(tfCertifiNumMd.getText())) {
			System.out.println("(PW Find) 인증번호 일치여부 검사");
			
			if(certifiNum == Integer.parseInt(tfCertifiNumMd.getText())) {
				System.out.println("(PW Find) 인증완료");
				
				JOptionPane.showMessageDialog(mainFrame, "인증이 완료되었습니다.", "인증 완료", JOptionPane.PLAIN_MESSAGE);
				
				certifiFlagPw = true;	//인증 완료 저장
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				tfCertifiNumMd.setEnabled(false);	//인증번호 텍스트필드 비활성화
				btnSendMd.setEnabled(false);		//인증하기 버튼 비활성화
				
				certifiFlagPw  = true;
				
				pwFindBtnChk();	//비밀번호 찾기 버튼 활성화 조건 검사
			} else {
				JOptionPane.showMessageDialog(mainFrame, "인증번호가 일치하지 않습니다.\n다시 시도해주세요.", "인증 실패", JOptionPane.ERROR_MESSAGE);
				
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				setPH(tfPhonNumMd);		//아이디 찾기 휴대폰번호 텍스트필드 Placeholder 세팅
				setPH(tfCertifiNumMd);	//아이디 찾기 인증번호 텍스트필드 Placeholder 세팅
				
				chageSend();	//전송하기 버튼으로 변경
			}
		} else if(obj == btnCtfy) {
			usersInId = tfIdMd.getText();				//입력받은 아이디 저장
			usersInPhoneNum = tfPhonNumMd.getText();	//입력받은 휴대폰번호 저장
			
			if(Boilerplate.idValidation(usersInId) && Boilerplate.phonNumValidation(usersInPhoneNum)) {
				
				ResultSet rs = DB.getResult("select * from USERS WHERE USERSID = '" + usersInId + "' and USERSPHONENUM = '" + usersInPhoneNum + "'");
				
				try {
					if(rs.next()) {
						System.out.println("(PW Find) 회원 조회(PW Find) 성공");
						
						tfIdMd.setEnabled(false);		//아이디 텍스트필드 비활성화
						
						pnNewNameSchName.setVisible(true);
						pnNewSchNumCarNum.setVisible(true);
						tfNewPwMd.setVisible(true);		//새 비밀번호 텍스트필드 노출
						tfNewPw2Md.setVisible(true);	//새 비밀번호 확인 텍스트필드 노출
						btnMdChange.setVisible(true);	//비밀번호 변경 버튼 노출
						
					} else {
						errorHandling();
					}
				} catch (SQLException e1) {
					System.out.println("(PW Find) 예외발생 : 회원 조회에 실패했습니다.");
					errorHandling();
					e1.printStackTrace();
				} finally {
					try {
						rs.close();
						System.out.println("(PW Find) ResultSet 객체 종료");
					} catch (SQLException e1) {
						System.out.println("(PW Find) 예외발생 : ResultSet 객체 종료에 실패했습니다.");
						e1.printStackTrace();
					}
				}
			} else {
				errorHandling();
			}
		} else if(obj == btnMdChange) {
			
		
			String pw1 = getPassword(tfNewPwMd);
			
			String pw2 = getPassword(tfNewPw2Md);
			
			if(pw1.equals(pw2) ){	//새 비밀번호와 새 비밀번호 확인 일치여부 검사
				usersInPw = pw1;
				
				if( Boilerplate.nameValidation(tfNewName.getText()) && Boilerplate.SchNameValidation(tfNewSchName.getText()) && Boilerplate.SchNumValidation(tfNewSchNum.getText())  && Boilerplate.CarNumValidation(tfNewCarNum.getText())  && Boilerplate.pwValidation(usersInPw)) {	//유효성 검사
					String pwSalt = Encryption.Salt();	//SHA512 암호화에 사용할 난수 생성
					usersInPw = Encryption.SHA512(usersInPw, pwSalt);	//입력받은 비밀번호를 Salt 값으로 SHA512 암호화
					//회원정보 Update문 생성
					String sqlUpdate = "UPDATE USERS SET USERSNAME = '" + tfNewName.getText() + "', USERSSCHNAME = '" + tfNewSchName.getText() + "', USERSSCHNUM = '" + tfNewSchNum.getText() + "', USERSCARNUM = '" + tfNewCarNum.getText() + "', USERSPW = '" + usersInPw + "', USERSPWSALT = '" + pwSalt + "' WHERE USERSID = '" + usersInId + "'";
					DB.executeSQL(sqlUpdate); 	//DB로 Update문 전송 
					
					JOptionPane.showMessageDialog(mainFrame, "개인정보 수정이 완료되었습니다.", "개인정보 수정 완료", JOptionPane.PLAIN_MESSAGE);
					
				
					certifiFlagPw = false;	//비밀번호 찾기 인증완료 초기화
				
					sendCertifiFlagPw = 1;	//비밀번호 찾기 전송하기로 변경
					
					changeLogin();	//로그인 화면으로 전환
				} else {
					JOptionPane.showMessageDialog(mainFrame, "정확하게 입력되지 않은 항목이 있습니다.\n다시 시도해주세요.", "개인정보 수정 실패", JOptionPane.ERROR_MESSAGE);
					
					setPH(tfNewName);	
					setPH(tfNewSchName);	
					setPH(tfNewSchNum);	
					setPH(tfNewCarNum);	
					setPH(tfNewPwMd);	//새 비밀번호 텍스트필드 Placeholder 세팅
					setPH(tfNewPw2Md);	//새 비밀번호 확인 텍스트필드 Placeholder 세팅
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.\n다시 시도해주세요.", "비밀번호 변경 실패", JOptionPane.ERROR_MESSAGE);
				
				setPH(tfNewPwMd);	//새 비밀번호 텍스트필드 Placeholder 세팅
				setPH(tfNewPw2Md);	//새 비밀번호 확인 텍스트필드 Placeholder 세팅
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfNewName) {
			//아이디 찾기 이름 텍스트필드 PlaceHolder
			if(tfNewName.getText().equals("  닉네임")) {
				clearPH(tfNewName);	//PlaceHolder 초기화
			}
		} else if(obj == tfNewSchName) {
			//아이디 찾기 대학명 텍스트필드 PlaceHolder
			if(tfNewSchName.getText().equals("  대학명")) {
				clearPH(tfNewSchName);	//PlaceHolder 초기화
			}
		} else if(obj == tfNewSchNum) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfNewSchNum.getText().equals("  학번")) {
				clearPH(tfNewSchNum);	//PlaceHolder 초기화
			}
		} else if(obj == tfNewCarNum) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfNewCarNum.getText().equals("  차량번호")) {
				clearPH(tfNewCarNum);	//PlaceHolder 초기화
			}
	
		} else if(obj == tfIdMd) {
			//비밀번호 찾기 아이디 텍스트필드 PlaceHolder
			if(tfIdMd.getText().equals("  아이디")) {
				clearPH(tfIdMd);	//PlaceHolder 초기화
			}
		} else if(obj == tfPhonNumMd) {
			//비밀번호 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumMd.getText().equals("  휴대폰번호('-'제외)")) {
				clearPH(tfPhonNumMd);	//PlaceHolder 초기화
				idPwFlag = 2;
			}
		} else if(obj == tfCertifiNumMd) {
			//비밀번호 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumMd.getText().equals("  인증번호")) {
				clearPH(tfCertifiNumMd);	//PlaceHolder 초기화
				idPwFlag = 2;
			}
		} else if(obj == tfNewPwMd) {
			
			String pw1 = getPassword(tfNewPwMd);
			
			//비밀번호 찾기 새 비밀번호 텍스트필드 PlaceHolder
			if(pw1.equals("  새 비밀번호")) {
				clearPwPH(tfNewPwMd);	//PlaceHolder 초기화
			}
		} else if(obj == tfNewPw2Md) {
			
			String pw2 = getPassword(tfNewPw2Md);
			
			//비밀번호 찾기 새 비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.equals("  새 비밀번호 확인")) {
				clearPwPH(tfNewPw2Md);	//PlaceHolder 초기화
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfNewName) {
			//아이디 찾기 이름 텍스트필드 PlaceHolder
			if(tfNewName.getText().isEmpty()) {
				setPH(tfNewName);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewSchName) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfNewSchName.getText().isEmpty()) {
				setPH(tfNewSchName);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewSchNum) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfNewSchNum.getText().isEmpty()) {
				setPH(tfNewSchNum);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewCarNum) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfNewCarNum.getText().isEmpty()) {
				setPH(tfNewCarNum);	//PlaceHolder 세팅
			}
		} else if(obj == tfPhonNumId) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumId.getText().isEmpty()) {
				setPH(tfPhonNumId);	//PlaceHolder 세팅
			}
		} else if(obj == tfCertifiNumId) {
			//아이디 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumId.getText().isEmpty()) {
				setPH(tfCertifiNumId);	//PlaceHolder 세팅
			}
		} else if(obj == tfIdMd) {
			//비밀번호 찾기 아이디 텍스트필드 PlaceHolder
			if(tfIdMd.getText().isEmpty()) {
				setPH(tfIdMd);	//PlaceHolder 세팅
			}
		} else if(obj == tfPhonNumMd) {
			//비밀번호 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumMd.getText().isEmpty()) {
				setPH(tfPhonNumMd);	//PlaceHolder 세팅
			}
		} else if(obj == tfCertifiNumMd) {
			//비밀번호 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumMd.getText().isEmpty()) {
				setPH(tfCertifiNumMd);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewPwMd) {
			
			String pw1 = getPassword(tfNewPwMd);
			
			//비밀번호 찾기 새 비밀번호 텍스트필드 PlaceHolder
			if(pw1.isEmpty()) {
				setPwPH(tfNewPwMd);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewPw2Md) {
			
			String pw2 = getPassword(tfNewPw2Md);
			
			//비밀번호 찾기 새 비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.isEmpty()) {
				setPwPH(tfNewPw2Md);	//PlaceHolder 세팅
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
		
	
		
		if(!tfPhonNumMd.getText().equals("  휴대폰번호('-'제외)") && sendCertifiFlagPw == 1 && !certifiFlagPw && idPwFlag == 2) {
			if(Boilerplate.phonNumValidation(tfPhonNumMd.getText())) {	//비밀번호 찾기 전송하기 버튼 활성화 조건 검사
				btnSendMd.setEnabled(true);	//비밀번호 찾기 전송하기 버튼 활성화
			} else {
				btnSendMd.setEnabled(false);	//비밀번호 찾기 전송하기 버튼 비활성화
			}
		}
		
		if(!tfPhonNumMd.getText().equals("  인증번호") && sendCertifiFlagPw == 2 && !certifiFlagPw && idPwFlag == 2) {
			if(Boilerplate.certifiNumValidation(tfCertifiNumMd.getText())) {	//비밀번호 찾기 인증하기 버튼 활성화 조건 검사
				btnSendMd.setEnabled(true);	//비밀번호 찾기 인증하기 버튼 활성화
			} else {
				btnSendMd.setEnabled(false);	//비밀번호 찾기 인증하기 버튼 비활성화
			}
		}
		
		String pw1 = getPassword(tfNewPwMd);
		
		String pw2 = getPassword(tfNewPw2Md);
		
		if(!pw1.equals("  새 비밀번호") && !pw2.equals("  새 비밀번호 확인") && idPwFlag == 2) {
			if(Boilerplate.pwValidation(pw1) && Boilerplate.pwValidation(pw2)) {
				btnMdChange.setEnabled(true);
			} else {
				btnMdChange.setEnabled(false);				
			}
		}
		
		
		
		pwFindBtnChk();	//비밀번호 찾기 버튼 활성화 조건 검사
		
	}

	

	

	public static void setSendCertifiFlagPw(int sendCertifiFlagPwIn) {
		
		sendCertifiFlagPw = sendCertifiFlagPwIn;
		
	}

	public static void setCertifiNum(int certifiNumIn) {
		
		certifiNum = certifiNumIn;
		
	}


	
}
