//아이디와 비밀번호 찾기 패널 클래스

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
import java.util.Vector;

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
import friendo.start.StartFrame;


public class Find extends JPanel implements ActionListener, FocusListener, KeyListener {

	private StartFrame mainFrame = null;	//메인 프레임 객체
	
	private JButton btnBack;				//뒤로가기 버튼
	private JTabbedPane tabPane;			//아이디/비밀번호 탭 팬
	
	private String usersInId;				//사용자에게 입력 받은 아이디
	private String usersInPw;				//사용자에게 입력 받은 비밀번호
	private String usersInName; 			//사용자에게 입력 받은 이름
	private String usersInSchNum;			//사용자에게 입력 받은 학번
	private String usersInCarNum;			//사용자에게 입력 받은 차량번호
	private String usersInPhoneNum; 		//사용자에게 입력 받은 휴대폰번호
	
	//아이디 찾기
	private JTextField tfNameId;			//이름 입력 텍스트필드
	private JTextField tfSchNumId;			//학번 입력 텍스트필드
	private JTextField tfCarNumId;			//차량번호 입력 텍스트필드
	private JTextField tfPhonNumId;			//휴대폰번호 입력 텍스트필드
	private JButton btnSendId;				//전송 버튼
	private JTextField tfCertifiNumId;		//인증번호 입력 텍스트필드
	private JLabel lblCountId;				//인증시간 카운트 라벨
	private JButton btnIdFind;				//아이디 찾기 버튼
	private static int sendCertifiFlagId = 1; //전송하기 버튼이면 1, 인증하기 버튼이면 2
	
	private boolean certifiFlagId = false;	//인증 완료여부 저장
	
	//비밀번호 찾기
	private JTextField tfIdPw;				//아이디 입력 텍스트필드
	private JTextField tfPhonNumPw;			//휴대폰번호 입력 텍스트필드
	private JButton btnSendPw;				//전송 버튼
	private JTextField tfCertifiNumPw;		//인증번호 입력 텍스트필드
	private JLabel lblCountPw;				//인증시간 카운트 라벨
	private JButton btnPwFind;				//비밀번호 찾기 버튼
	private static int sendCertifiFlagPw = 1; //전송하기 버튼이면 1, 인증하기 버튼이면 2
	private JPasswordField tfNewPwPw;		//새 비밀번호 입력 텍스트필드
	private JPasswordField tfNewPw2Pw;		//새 비밀번호 확인 입력 텍스트필드
	private JButton btnPwChange;			//비밀번호 변경 버튼
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


	

	
	
	//아이디/비밀번호 찾기 화면
	public Find() {
		
		setLayout(new BorderLayout());
        setBackground(new Color(255, 252, 244));
        
        mainFrame = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
        
        mainFrame.setTitle("Find");
        
		tabPane = new JTabbedPane(JTabbedPane.TOP);	//탭 팬 생성
		
		makeImageIcon();	//이미지 아이콘 생성
		
        makeTitle();	//타이틀 영역 생성
        
        makeIdFind();	//아이디 조회 영역 생성
        
        makePwFind();	//비밀번호 조회 영역 생성

        add(tabPane, BorderLayout.CENTER);
		
	}
	
	//이미지 아이콘 생성
	private void makeImageIcon() {
		
		icBackET = new ImageIcon("images/find/Btn_Back_EnabledTrue.png");				//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBackRo = new ImageIcon("images/find/Btn_Back_Rollover.png");					//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBackPr = new ImageIcon("images/find/Btn_Back_Pressed.png");					//뒤로가기 버튼(Pressed) 이미지 아이콘
		
		icChangeET = new ImageIcon("images/find/Btn_Change_EnabledTrue.png");			//변경 버튼(EnabledTrue) 이미지 아이콘
		icChangeRo = new ImageIcon("images/find/Btn_Change_Rollover.png");				//변경 버튼(Rollover) 이미지 아이콘
		icChangePr = new ImageIcon("images/find/Btn_Change_Pressed.png");				//변경 버튼(Pressed) 이미지 아이콘
		
		icFindET = new ImageIcon("images/find/Btn_Find_EnabledTrue.png");				//찾기 버튼(EnabledTrue) 이미지 아이콘
		icFindRo = new ImageIcon("images/find/Btn_Find_Rollover.png");					//찾기 버튼(Rollover) 이미지 아이콘
		icFindPr = new ImageIcon("images/find/Btn_Find_Pressed.png");					//찾기 버튼(Pressed) 이미지 아이콘
		
		icSendET = new ImageIcon("images/find/Btn_Send_EnabledTrue.png");				//전송하기 버튼(EnabledTrue) 이미지 아이콘
		icSendRo = new ImageIcon("images/find/Btn_Send_Rollover.png");					//전송하기 버튼(Rollover) 이미지 아이콘
		icSendPr = new ImageIcon("images/find/Btn_Send_Pressed.png");					//전송하기 버튼(Pressed) 이미지 아이콘
		
		icCertifiedET = new ImageIcon("images/find/Btn_Certified_EnabledTrue.png");		//인증하기 버튼(EnabledTrue) 이미지 아이콘
		icCertifiedRo = new ImageIcon("images/find/Btn_Certified_Rollover.png");		//인증하기 버튼(Rollover) 이미지 아이콘
		icCertifiedPr = new ImageIcon("images/find/Btn_Certified_Pressed.png");			//인증하기 버튼(Pressed) 이미지 아이콘
		
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
		
		JLabel lblTitle = new JLabel("Find ID/PW");
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
		btnBack.setRolloverIcon(icBackRo);
		btnBack.setPressedIcon(icBackPr);
		btnBack.setBorderPainted(false);					//버튼 테두리 설정
		btnBack.setContentAreaFilled(false);				//버튼 배경 표시 설정
		btnBack.setFocusPainted(false);						//포커스 테두리 표시 설정
		btnBack.setOpaque(false);							//투명하게 설정
		btnBack.setPreferredSize(new Dimension(75, 73));	//버튼 크기 설정
		btnBack.addActionListener(this);
				
		pnBack.add(btnBack);
		pnTitleBackground.add(pnBack, BorderLayout.WEST);
				
		add(pnTitleBackground, BorderLayout.NORTH);
				
	}
		
		
	//아이디 조회 영역 생성
	private void makeIdFind() {
		
		JPanel pnIdFindBg = new JPanel();
		pnIdFindBg.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnIdFindBg.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JPanel pnIdFindInput = new JPanel();
		pnIdFindInput.setLayout(new GridLayout(8, 1, 5, 5));
		pnIdFindInput.setBorder(new EmptyBorder(30, 0, 0, 0));	//패널 패딩 설정
		pnIdFindInput.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//안내 메시지 생성
		JLabel lblInfo = new JLabel("아이디를 찾기 위해 아래의 정보를 입력해주세요", JLabel.CENTER);
		lblInfo.setFont(new Font("D2Coding", Font.BOLD, 18));		//라벨 폰트 설정
		lblInfo.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 생상 설정
		
		
		//이름 텍스트필드 생성
		tfNameId = new JTextField("  닉네임");
		tfNameId.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfNameId.setFont(new Font("D2Coding", Font.PLAIN, tfNameId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNameId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfNameId.addFocusListener(this);
		tfNameId.addKeyListener(this);
		
		
		tfSchNumId = new JTextField("  학번");
		tfSchNumId.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfSchNumId.setFont(new Font("D2Coding", Font.PLAIN, tfNameId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfSchNumId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfSchNumId.addFocusListener(this);
		tfSchNumId.addKeyListener(this);
		
		tfCarNumId = new JTextField("  차량번호");
		tfCarNumId.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfCarNumId.setFont(new Font("D2Coding", Font.PLAIN, tfNameId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCarNumId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfCarNumId.addFocusListener(this);
		tfCarNumId.addKeyListener(this);
		
		//휴대폰번호 필드
		JPanel pnPhonNumInput = new JPanel();
		pnPhonNumInput.setLayout(new BorderLayout());
		pnPhonNumInput.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//휴대폰번호 텍스트필드 생성
		tfPhonNumId = new JTextField("  휴대폰번호('-'제외)");
		tfPhonNumId.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfPhonNumId.setFont(new Font("D2Coding", Font.PLAIN, tfPhonNumId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPhonNumId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfPhonNumId.addFocusListener(this);
		tfPhonNumId.addKeyListener(this);
		
		
		//전송하기 버튼 생성
		btnSendId = new JButton(icSendET);
		Boilerplate.setImageButton(btnSendId, icSendRo, icSendPr, 90, 48);	//이미지 버튼 세팅
		btnSendId.addActionListener(this);
		
		
		//인증번호 필드
		JPanel pnCertif = new JPanel();
		pnCertif.setLayout(new BorderLayout());
		pnCertif.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//인증번호 텍스트필드 생성
		tfCertifiNumId = new JTextField("  인증번호");
		tfCertifiNumId.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfCertifiNumId.setFont(new Font("D2Coding", Font.PLAIN, tfNameId.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCertifiNumId.setForeground(Color.GRAY);	//텍스트필드 폰트 생상 설정
		tfCertifiNumId.addFocusListener(this);
		tfCertifiNumId.addKeyListener(this);
		
		//인증시간 카운트 라벨 생성
		lblCountId = new JLabel();
		lblCountId.setPreferredSize(new Dimension(90, 48));	//라벨 크기 설정
		lblCountId.setFont(new Font("D2Coding", Font.PLAIN, lblCountId.getFont().getSize()));	//라벨 폰트 설정
		lblCountId.setForeground(Color.RED);	//라벨 폰트 생상 설정
		lblCountId.setHorizontalAlignment(JLabel.CENTER);	//라벨 가운데 정렬
		lblCountId.setOpaque(true);	//라벨 투명도 설정
		lblCountId.setBackground(Color.WHITE);	//라벨 색상 하얀색으로 설정
		
		
		//아이디 찾기 버튼 영역
		JPanel pnFindBtn = new JPanel();
		pnFindBtn.setLayout(new FlowLayout());
		pnFindBtn.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//아이디 찾기 버튼 생성
		btnIdFind = new JButton(icFindET);
		Boilerplate.setImageButton(btnIdFind, icFindRo, icFindPr, 183, 50);	//이미지 버튼 세팅
		btnIdFind.addActionListener(this);
		
		
		pnPhonNumInput.add(tfPhonNumId, BorderLayout.CENTER);
		pnPhonNumInput.add(btnSendId, BorderLayout.EAST);
		
		pnCertif.add(tfCertifiNumId, BorderLayout.CENTER);
		pnCertif.add(lblCountId, BorderLayout.EAST);
		
		pnFindBtn.add(btnIdFind);
		
		pnIdFindInput.add(lblInfo);
		pnIdFindInput.add(tfNameId);
		pnIdFindInput.add(tfSchNumId);
		pnIdFindInput.add(tfCarNumId);
		pnIdFindInput.add(pnPhonNumInput);
		pnIdFindInput.add(pnCertif);
		pnIdFindInput.add(pnFindBtn);
		
		pnIdFindBg.add(pnIdFindInput);
		
		tabPane.addTab(" 아이디찾기", pnIdFindBg);
		
		
	}
	
	//비밀번호 조회 영역 생성
	private void makePwFind() {
		
		JPanel pnPwFindBg = new JPanel();
		pnPwFindBg.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnPwFindBg.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JPanel pnPwFindInput = new JPanel();
		pnPwFindInput.setLayout(new GridLayout(8, 1, 5, 5));
		pnPwFindInput.setBorder(new EmptyBorder(30, 0, 0, 0));		//패널 패딩 설정
		pnPwFindInput.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//안내 메시지 생성
		JLabel lblInfo = new JLabel("비밀번호를 찾기 위해 아래의 정보를 입력해주세요", JLabel.CENTER);
		lblInfo.setFont(new Font("D2Coding", Font.BOLD, 18));		//라벨 폰트 설정
		lblInfo.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 색상 설정
		
		
		//아이디 텍스트필드 생성
		tfIdPw = new JTextField("  아이디");
		tfIdPw.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfIdPw.setFont(new Font("D2Coding", Font.PLAIN, tfIdPw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfIdPw.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfIdPw.addFocusListener(this);
		tfIdPw.addKeyListener(this);
		
		
		//휴대폰번호 필드
		JPanel pnPhonNumInput = new JPanel();
		pnPhonNumInput.setLayout(new BorderLayout());
		pnPhonNumInput.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//휴대폰번호 텍스트필드 생성
		tfPhonNumPw = new JTextField("  휴대폰번호('-'제외)");
		tfPhonNumPw.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfPhonNumPw.setFont(new Font("D2Coding", Font.PLAIN, tfPhonNumPw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfPhonNumPw.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfPhonNumPw.addFocusListener(this);
		tfPhonNumPw.addKeyListener(this);
		
		//전송하기 버튼 생성
		btnSendPw = new JButton(icSendET);
		Boilerplate.setImageButton(btnSendPw, icSendRo, icSendPr, 90, 48);	//이미지 버튼 세팅
		btnSendPw.addActionListener(this);
		
		
		//인증번호 필드
		JPanel pnCertif = new JPanel();
		pnCertif.setLayout(new BorderLayout());
		pnCertif.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//인증번호 텍스트필드 생성
		tfCertifiNumPw = new JTextField("  인증번호");
		tfCertifiNumPw.setPreferredSize(new Dimension(324, 48));	//텍스트필드 크기 설정
		tfCertifiNumPw.setFont(new Font("D2Coding", Font.PLAIN, tfCertifiNumPw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfCertifiNumPw.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfCertifiNumPw.addFocusListener(this);
		tfCertifiNumPw.addKeyListener(this);
		
		//인증시간 카운트 라벨 생성
		lblCountPw = new JLabel();
		lblCountPw.setPreferredSize(new Dimension(90, 48));	//라벨 크기 설정
		lblCountPw.setFont(new Font("D2Coding", Font.PLAIN, lblCountPw.getFont().getSize()));	//라벨 폰트 설정
		lblCountPw.setForeground(Color.RED);	//라벨 폰트 색상 설정
		lblCountPw.setHorizontalAlignment(JLabel.CENTER);	//라벨 가운데 정렬
		lblCountPw.setOpaque(true);	//라벨 투명도 설정
		lblCountPw.setBackground(Color.WHITE);	//라벨 색상 하얀색으로 설정
		
		
		//비밀번호 찾기 버튼
		JPanel pnFindBtn = new JPanel();
		pnFindBtn.setLayout(new FlowLayout());
		pnFindBtn.setBackground(new Color(255, 252, 244));	//패널 색상 배경생과 동일하게 설정
		
		
		//비밀번호 찾기 버튼 생성
		btnPwFind = new JButton(icFindET);
		Boilerplate.setImageButton(btnPwFind, icFindRo, icFindPr, 183, 50);	//이미지 버튼 세팅
		btnPwFind.addActionListener(this);
		
		
		//새 비밀번호 텍스트필드 생성
		tfNewPwPw = new JPasswordField("  새 비밀번호");
		tfNewPwPw.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfNewPwPw.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfNewPwPw.setFont(new Font("D2Coding", Font.PLAIN, tfNewPwPw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewPwPw.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewPwPw.setVisible(false);	//미노출 상태로 생성
		tfNewPwPw.addFocusListener(this);
		tfNewPwPw.addKeyListener(this);
		
		
		//새 비밀번호 확인 텍스트필드 생성
		tfNewPw2Pw = new JPasswordField("  새 비밀번호 확인");
		tfNewPw2Pw.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tfNewPw2Pw.setPreferredSize(new Dimension(414, 48));	//텍스트필드 크기 설정
		tfNewPw2Pw.setFont(new Font("D2Coding", Font.PLAIN, tfNewPw2Pw.getFont().getSize()));	//텍스트필드 폰트 설정
		tfNewPw2Pw.setForeground(Color.GRAY);	//텍스트필드 폰트 색상 설정
		tfNewPw2Pw.setVisible(false);	//미노출 상태로 생성
		tfNewPw2Pw.addFocusListener(this);
		tfNewPw2Pw.addKeyListener(this);
		
		
		//비밀번호 변경 버튼
		JPanel pnChangeBtn = new JPanel();
		pnChangeBtn.setLayout(new FlowLayout());
		pnChangeBtn.setBackground(new Color(255, 252, 244));	//패널 색상 배경색과 동일하게 설정
		
		//비밀번호 변경 버튼 생성
		btnPwChange = new JButton(icChangeET);
		Boilerplate.setImageButton(btnPwChange, icChangeRo, icChangePr, 183, 50);	//이미지 버튼 세팅
		btnPwChange.setVisible(false);	//미노출 상태로 생성
		btnPwChange.addActionListener(this);
		
		
		pnPhonNumInput.add(tfPhonNumPw, BorderLayout.CENTER);
		pnPhonNumInput.add(btnSendPw, BorderLayout.EAST);
		
		pnCertif.add(tfCertifiNumPw, BorderLayout.CENTER);
		pnCertif.add(lblCountPw, BorderLayout.EAST);
		
		
		pnFindBtn.add(btnPwFind);
		
		pnChangeBtn.add(btnPwChange);
		
		pnPwFindInput.add(lblInfo);
		pnPwFindInput.add(tfIdPw);
		pnPwFindInput.add(pnPhonNumInput);
		pnPwFindInput.add(pnCertif);
		pnPwFindInput.add(pnFindBtn);
		pnPwFindInput.add(tfNewPwPw);
		pnPwFindInput.add(tfNewPw2Pw);
		pnPwFindInput.add(pnChangeBtn);
		
		pnPwFindBg.add(pnPwFindInput);
		
		tabPane.addTab(" 비밀번호찾기", pnPwFindBg);
		
	}
	
	//JTextField PlaceHolder 초기화 함수
	private void clearPH(JTextField tf) {
		
		tf.setText("");
		tf.setForeground(Color.BLACK);
		
	}
	
	//JTextField PlaceHolder 세팅 함수
	private void setPH(JTextField tf) {

		String str = "";
		
		if(tf == tfNameId) str = "  닉네임";
		else if(tf == tfSchNumId) str = "  학번";
		else if(tf == tfCarNumId) str = "  차량번호";
		else if(tf == tfPhonNumId || tf == tfPhonNumPw) str = "  휴대폰번호('-'제외)";
		else if(tf == tfCertifiNumId || tf == tfCertifiNumPw) str = "  인증번호";
		else if(tf == tfIdPw) str = "  아이디";
		
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
		
		if(tf == tfNewPwPw) str = "  새 비밀번호";
		else if(tf == tfNewPw2Pw) str = "  새 비밀번호 확인";
		
		tf.setEchoChar((char)0);	//작성되는 문자 그대로 보여지게 설정
		tf.setText(str);
		tf.setForeground(Color.GRAY);
		
	}
	
	//아이디 찾기 버튼 활성화 조건 검사
	private void idFindBtnChk() {
		
		if(!tfNameId.getText().equals("  닉네임") && !tfPhonNumId.getText().equals("  휴대폰번호('-'제외)")&& !tfCertifiNumId.getText().equals("  인증번호")) {
			if(Boilerplate.nameValidation(tfNameId.getText()) && Boilerplate.SchNumValidation(tfSchNumId.getText())  && Boilerplate.CarNumValidation(tfCarNumId.getText()) && Boilerplate.phonNumValidation(tfPhonNumId.getText()) && certifiFlagId) {
				btnIdFind.setEnabled(true);	//아이디 찾기 버튼 활성화
			} else {
				btnIdFind.setEnabled(false);	//아이디 찾기 버튼 비활성화
			}
		}
		
	}
	
	//비밀번호 찾기 버튼 활성화 조건 검사
	private void pwFindBtnChk() {
		
		if(!tfIdPw.getText().equals("  아이디") && !tfPhonNumPw.getText().equals("  휴대폰번호('-'제외)")&& !tfCertifiNumPw.getText().equals("  인증번호")) {
			if(Boilerplate.idValidation(tfIdPw.getText()) && Boilerplate.phonNumValidation(tfPhonNumPw.getText()) && certifiFlagPw) {
				btnPwFind.setEnabled(true);	//비밀번호 찾기 버튼 활성화
			} else {
				btnPwFind.setEnabled(false);	//비밀번호 찾기 버튼 비활성화
			}
		}
		
	}
	
	//에러 핸들링
	private void errorHandling() {
		
		if(idPwFlag == 1) {	//아이디 찾기 에러 핸들링
			setPH(tfNameId);
			setPH(tfSchNumId);
			setPH(tfCarNumId);
			setPH(tfPhonNumId);
			setPH(tfCertifiNumId);
			

			chageSend();
			
			btnIdFind.setEnabled(false);	//아이디 찾기 버튼 비활성화
			
			certifiFlagId = false;	//인증 완료 상태 변경
			
			JOptionPane.showMessageDialog(mainFrame, "입력하신 정보와 일치하는 회원이 없습니다.", "아이디 찾기 실패", JOptionPane.ERROR_MESSAGE);
			tfNameId.requestFocus();
			
			System.out.println("(ID Find) 아이디 찾기 실패");
		} else if(idPwFlag == 2) {	//비밀번호 찾기 에러 핸들링
			setPH(tfIdPw);
			setPH(tfPhonNumPw);
			setPH(tfCertifiNumPw);
			
			chageSend();	//전송하기 버튼으로 변경
			
			btnPwFind.setEnabled(false);	//비밀번호 찾기 버튼 비활성화
			
			certifiFlagPw = false;	//인증 완료 상태 변경
			
			JOptionPane.showMessageDialog(mainFrame, "입력하신 정보와 일치하는 회원이 없습니다.", "비밀번호 찾기 실패", JOptionPane.ERROR_MESSAGE);
			tfIdPw.requestFocus();
			
			System.out.println("(PW Find) 비밀번호 찾기 실패");
		}
		
	}

	//전송하기 버튼으로 변경
	private void chageSend() {
		
		if(idPwFlag == 1) {	//아이디 찾기
			sendCertifiFlagId = 1;
			btnSendId.setIcon(icSendET);
			btnSendId.setRolloverIcon(icSendRo);
			btnSendId.setPressedIcon(icSendPr);
			btnSendId.setEnabled(false);	//전송하기 버튼 비활성화			
		} else if(idPwFlag == 2) {	//비밀번호 찾기
			sendCertifiFlagPw = 1;
			btnSendPw.setIcon(icSendET);
			btnSendPw.setRolloverIcon(icSendRo);
			btnSendPw.setPressedIcon(icSendPr);
			btnSendPw.setEnabled(false);	//전송하기 버튼 비활성화
		}
		
	}
	
	//인증하기 버튼으로 변경
	private void changeCertifi() {
		
		if(idPwFlag == 1) {	//아이디 찾기
			sendCertifiFlagId = 2;
			btnSendId.setIcon(icCertifiedET);
			btnSendId.setRolloverIcon(icCertifiedRo);
			btnSendId.setPressedIcon(icCertifiedPr);
			btnSendId.setEnabled(false);	//인증하기 버튼 비활성화
		} else if(idPwFlag == 2) {	//비밀번호 찾기
			sendCertifiFlagPw = 2;
			btnSendPw.setIcon(icCertifiedET);
			btnSendPw.setRolloverIcon(icCertifiedRo);
			btnSendPw.setPressedIcon(icCertifiedPr);
			btnSendPw.setEnabled(false);	//인증하기 버튼 비활성화
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
		sendCertifiFlagId = 1;
		certifiFlagId = false;
		sendCertifiFlagPw = 1;
		certifiFlagPw = false;
		idPwFlag = 0;
		
	    Boilerplate.redraw(new Login());	//로그인 화면으로 전환
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == btnBack) {
			
			changeLogin();	//로그인 화면으로 전환
			
		} else if(obj == btnSendId && sendCertifiFlagId == 1 && Boilerplate.phonNumValidation(tfPhonNumId.getText())) {
			tfPhonNumId.setEnabled(false);	//휴대폰번호 텍스트필드 비활성화
			
			System.out.println("(ID Find) 인증번호 발송");
			
			certifiNum = Boilerplate.certificationNum();	//6자리 숫자 난수 생성
			JOptionPane.showMessageDialog(mainFrame, "인증번호는 [" + certifiNum + "]입니다.", tfPhonNumId.getText() + " 문자", JOptionPane.PLAIN_MESSAGE);
			
			changeCertifi();	//인증하기 버튼으로 변경
			
			idPwFlag = 1;
			
			//인증시간 카운트
			countThread = new CountThread(2, lblCountId, btnSendId, tfPhonNumId, tfCertifiNumId);	//카운트 스레드 생성
			countThread.start();	//카운트 스레드 시작
			System.out.println("(ID Find) Count Thread 실행");
		} else if(obj == btnSendId && sendCertifiFlagId == 2 && Boilerplate.certifiNumValidation(tfCertifiNumId.getText())) {
			System.out.println("(ID Find) 인증번호 일치여부 검사");
			
			if(certifiNum == Integer.parseInt(tfCertifiNumId.getText())) {
				System.out.println("(ID Find) 인증완료");
				
				JOptionPane.showMessageDialog(mainFrame, "인증이 완료되었습니다.", "인증 완료", JOptionPane.PLAIN_MESSAGE);
				
				certifiFlagId= true;	//인증 완료 저장
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				tfCertifiNumId.setEnabled(false);	//인증번호 텍스트필드 비활성화
				btnSendId.setEnabled(false);		//인증하기 버튼 비활성화
				
				idFindBtnChk();	//아이디 찾기 버튼 활성화 조건 검사
			} else {
				JOptionPane.showMessageDialog(mainFrame, "인증번호가 일치하지 않습니다.\n다시 시도해주세요.", "인증 실패", JOptionPane.ERROR_MESSAGE);
				
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				setPH(tfPhonNumId);		//아이디 찾기 휴대폰번호 텍스트필드 Placeholder 세팅
				setPH(tfCertifiNumId);	//아이디 찾기 인증번호 텍스트필드 Placeholder 세팅
				
				chageSend();
			}
		} else if(obj == btnIdFind) {
			usersInName = tfNameId.getText();	//입력받은 이름 저장
			
			usersInSchNum = tfSchNumId.getText();  //입력받은 학번 저장
			
			usersInCarNum = tfCarNumId.getText();  //입력받은 차량번호 저장
			
			usersInPhoneNum = tfPhonNumId.getText();	//입력받은 휴대폰번호 저장
			
			if(Boilerplate.nameValidation(usersInName) && Boilerplate.SchNumValidation(tfSchNumId.getText())  && Boilerplate.CarNumValidation(tfCarNumId.getText()) && Boilerplate.phonNumValidation(usersInPhoneNum)) {
				
				ResultSet rs = DB.getResult("select * from USERS WHERE USERSNAME = '" + usersInName + "' and USERSSCHNUM = '" + usersInSchNum + "' and USERSCARNUM = '" + usersInCarNum + "' and USERSPHONENUM = '" + usersInPhoneNum + "'");
				
				try {
					
					Vector<String> userId = new Vector<String>();
					
					while(rs.next()) {
						userId.add(rs.getString("USERSID"));
						System.out.println(rs.getString("USERSID"));
					}
					
					if(!userId.isEmpty()) {
						
						String message;
						message = usersInName + " 님의 아이디는 ";
						for(int i = 0; i < userId.size(); i++) {
							message = message + userId.get(i);
							if(i < userId.size()-1) message = message + ", ";
						}
						message = message + " 입니다.";
						
						JOptionPane.showMessageDialog(mainFrame, message, "아이디 찾기 완료", JOptionPane.PLAIN_MESSAGE);
						
						System.out.println("(ID Find) 회원 조회(ID Find) 성공");
						
						changeLogin();	//로그인 화면으로 전환
					} else {
						errorHandling();
					}
				} catch (SQLException e1) {
					System.out.println("(ID Find) 예외발생 : 회원 조회에 실패했습니다.");
					errorHandling();
					e1.printStackTrace();
				} finally {
					try {
						rs.close();
						System.out.println("(ID Find) ResultSet 객체 종료");
					} catch (SQLException e1) {
						System.out.println("(ID Find) 예외발생 : ResultSet 객체 종료에 실패했습니다.");
						e1.printStackTrace();
					}
				}
			} else {
				errorHandling();
			}
		} else if(obj == btnSendPw && sendCertifiFlagPw == 1 && Boilerplate.phonNumValidation(tfPhonNumPw.getText())) {
			tfPhonNumPw.setEnabled(false);	//휴대폰번호 텍스트필드 비활성화
			
			System.out.println("(PW Find) 인증번호 발송");
			
			certifiNum = Boilerplate.certificationNum();	//6자리 숫자 난수 생성
			JOptionPane.showMessageDialog(mainFrame, "인증번호는 [" + certifiNum + "]입니다.", tfPhonNumPw.getText() + " 문자", JOptionPane.PLAIN_MESSAGE);
			
			changeCertifi();	//인증하기 버튼으로 변경
			
			//인증시간 카운트
			countThread = new CountThread(3, lblCountPw, btnSendPw, tfPhonNumPw, tfCertifiNumPw);	//카운트 스레드 생성
			countThread.start();	//카운트 스레드 시작
			System.out.println("(PW Find) Count Thread 실행");
		} else if(obj == btnSendPw && sendCertifiFlagPw == 2 && Boilerplate.certifiNumValidation(tfCertifiNumPw.getText())) {
			System.out.println("(PW Find) 인증번호 일치여부 검사");
			
			if(certifiNum == Integer.parseInt(tfCertifiNumPw.getText())) {
				System.out.println("(PW Find) 인증완료");
				
				JOptionPane.showMessageDialog(mainFrame, "인증이 완료되었습니다.", "인증 완료", JOptionPane.PLAIN_MESSAGE);
				
				certifiFlagPw = true;	//인증 완료 저장
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				tfCertifiNumPw.setEnabled(false);	//인증번호 텍스트필드 비활성화
				btnSendPw.setEnabled(false);		//인증하기 버튼 비활성화
				
				certifiFlagPw  = true;
				
				pwFindBtnChk();	//비밀번호 찾기 버튼 활성화 조건 검사
			} else {
				JOptionPane.showMessageDialog(mainFrame, "인증번호가 일치하지 않습니다.\n다시 시도해주세요.", "인증 실패", JOptionPane.ERROR_MESSAGE);
				
				if(countThread.isAlive()) countThread.interrupt();		//카운트 쓰레드 종료
				
				setPH(tfPhonNumPw);		//아이디 찾기 휴대폰번호 텍스트필드 Placeholder 세팅
				setPH(tfCertifiNumPw);	//아이디 찾기 인증번호 텍스트필드 Placeholder 세팅
				
				chageSend();	//전송하기 버튼으로 변경
			}
		} else if(obj == btnPwFind) {
			usersInId = tfIdPw.getText();				//입력받은 아이디 저장
			usersInPhoneNum = tfPhonNumPw.getText();	//입력받은 휴대폰번호 저장
			
			if(Boilerplate.idValidation(usersInId) && Boilerplate.phonNumValidation(usersInPhoneNum)) {
				
				ResultSet rs = DB.getResult("select * from USERS WHERE USERSID = '" + usersInId + "' and USERSPHONENUM = '" + usersInPhoneNum + "'");
				
				try {
					if(rs.next()) {
						System.out.println("(PW Find) 회원 조회(PW Find) 성공");
						
						tfIdPw.setEnabled(false);		//아이디 텍스트필드 비활성화
						tfNewPwPw.setVisible(true);		//새 비밀번호 텍스트필드 노출
						tfNewPw2Pw.setVisible(true);	//새 비밀번호 확인 텍스트필드 노출
						btnPwChange.setVisible(true);	//비밀번호 변경 버튼 노출
						
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
		} else if(obj == btnPwChange) {
			
			String pw1 = getPassword(tfNewPwPw);
			
			String pw2 = getPassword(tfNewPw2Pw);
			
			if(pw1.equals(pw2)) {	//새 비밀번호와 새 비밀번호 확인 일치여부 검사
				usersInPw = pw1;
				
				if(Boilerplate.pwValidation(usersInPw)) {	//비밀번호 유효성 검사
					String pwSalt = Encryption.Salt();	//SHA512 암호화에 사용할 난수 생성
					usersInPw = Encryption.SHA512(usersInPw, pwSalt);	//입력받은 비밀번호를 Salt 값으로 SHA512 암호화
					
					String sqlUpdate = "UPDATE USERS SET USERSPW = '" + usersInPw + "', USERSPWSALT = '" + pwSalt + "' where USERSID = '" + usersInId + "'";	//회원정보 Insert문 생성
					DB.executeSQL(sqlUpdate);	//DB로 Update문 전송
					
					JOptionPane.showMessageDialog(mainFrame, "비밀번호 변경이 완료되었습니다.", "비밀번호 변경 완료", JOptionPane.PLAIN_MESSAGE);
					
					certifiFlagId = false;	//아이디 찾기 인증완료 초기화
					certifiFlagPw = false;	//비밀번호 찾기 인증완료 초기화
					sendCertifiFlagId = 1;	//아이디 찾기 전송하기로 변경
					sendCertifiFlagPw = 1;	//비밀번호 찾기 전송하기로 변경
					
					changeLogin();	//로그인 화면으로 전환
				} else {
					JOptionPane.showMessageDialog(mainFrame, "사용할 수 없는 비밀번호입니다.\n비밀번호는 영문, 숫자, 특수문자 조합으로\n최소 8자리 이상, 최대 15자리 이하로 사용 가능합니다.\n다시 시도해주세요.", "비밀번호 변경 실패", JOptionPane.ERROR_MESSAGE);
					
					setPH(tfNewPwPw);	//새 비밀번호 텍스트필드 Placeholder 세팅
					setPH(tfNewPw2Pw);	//새 비밀번호 확인 텍스트필드 Placeholder 세팅
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.\n다시 시도해주세요.", "비밀번호 변경 실패", JOptionPane.ERROR_MESSAGE);
				
				setPH(tfNewPwPw);	//새 비밀번호 텍스트필드 Placeholder 세팅
				setPH(tfNewPw2Pw);	//새 비밀번호 확인 텍스트필드 Placeholder 세팅
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfNameId) {
			//아이디 찾기 이름 텍스트필드 PlaceHolder
			if(tfNameId.getText().equals("  닉네임")) {
				clearPH(tfNameId);	//PlaceHolder 초기화
			}
			
		} else if(obj == tfSchNumId) {
			//아이디 찾기 학번 텍스트필드 PlaceHolder
			if(tfSchNumId.getText().equals("  학번")) {
				clearPH(tfSchNumId);	//PlaceHolder 초기화
			}
		} else if(obj == tfCarNumId) {
			//아이디 찾기 차량번호 텍스트필드 PlaceHolder
			if(tfCarNumId.getText().equals("  차량번호")) {
				clearPH(tfCarNumId);	//PlaceHolder 초기화
			}
		} else if(obj == tfPhonNumId) {
			//아이디 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumId.getText().equals("  휴대폰번호('-'제외)")) {
				clearPH(tfPhonNumId);	//PlaceHolder 초기화
				idPwFlag = 1;
			}
		} else if(obj == tfCertifiNumId) {
			//아이디 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumId.getText().equals("  인증번호")) {
				clearPH(tfCertifiNumId);	//PlaceHolder 초기화
				idPwFlag = 1;
			}
		} else if(obj == tfIdPw) {
			//비밀번호 찾기 아이디 텍스트필드 PlaceHolder
			if(tfIdPw.getText().equals("  아이디")) {
				clearPH(tfIdPw);	//PlaceHolder 초기화
			}
		} else if(obj == tfPhonNumPw) {
			//비밀번호 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumPw.getText().equals("  휴대폰번호('-'제외)")) {
				clearPH(tfPhonNumPw);	//PlaceHolder 초기화
				idPwFlag = 2;
			}
		} else if(obj == tfCertifiNumPw) {
			//비밀번호 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumPw.getText().equals("  인증번호")) {
				clearPH(tfCertifiNumPw);	//PlaceHolder 초기화
				idPwFlag = 2;
			}
		} else if(obj == tfNewPwPw) {
			
			String pw1 = getPassword(tfNewPwPw);
			
			//비밀번호 찾기 새 비밀번호 텍스트필드 PlaceHolder
			if(pw1.equals("  새 비밀번호")) {
				clearPwPH(tfNewPwPw);	//PlaceHolder 초기화
			}
		} else if(obj == tfNewPw2Pw) {
			
			String pw2 = getPassword(tfNewPw2Pw);
			
			//비밀번호 찾기 새 비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.equals("  새 비밀번호 확인")) {
				clearPwPH(tfNewPw2Pw);	//PlaceHolder 초기화
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == tfNameId) {
			//아이디 찾기 이름 텍스트필드 PlaceHolder
			if(tfNameId.getText().isEmpty()) {
				setPH(tfNameId);	//PlaceHolder 세팅
			}
		} else if(obj == tfSchNumId) {
			//아이디 찾기 학번 텍스트필드 PlaceHolder
			if(tfSchNumId.getText().isEmpty()) {
				setPH(tfSchNumId);	//PlaceHolder 세팅
			}
		} else if(obj == tfCarNumId) {
			//아이디 찾기 차량번호 텍스트필드 PlaceHolder
			if(tfCarNumId.getText().isEmpty()) {
				setPH(tfCarNumId);	//PlaceHolder 세팅
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
		} else if(obj == tfIdPw) {
			//비밀번호 찾기 아이디 텍스트필드 PlaceHolder
			if(tfIdPw.getText().isEmpty()) {
				setPH(tfIdPw);	//PlaceHolder 세팅
			}
		} else if(obj == tfPhonNumPw) {
			//비밀번호 찾기 휴대폰번호 텍스트필드 PlaceHolder
			if(tfPhonNumPw.getText().isEmpty()) {
				setPH(tfPhonNumPw);	//PlaceHolder 세팅
			}
		} else if(obj == tfCertifiNumPw) {
			//비밀번호 찾기 인증번호 텍스트필드 PlaceHolder
			if(tfCertifiNumPw.getText().isEmpty()) {
				setPH(tfCertifiNumPw);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewPwPw) {
			
			String pw1 = getPassword(tfNewPwPw);
			
			//비밀번호 찾기 새 비밀번호 텍스트필드 PlaceHolder
			if(pw1.isEmpty()) {
				setPwPH(tfNewPwPw);	//PlaceHolder 세팅
			}
		} else if(obj == tfNewPw2Pw) {
			
			String pw2 = getPassword(tfNewPw2Pw);
			
			//비밀번호 찾기 새 비밀번호 확인 텍스트필드 PlaceHolder
			if(pw2.isEmpty()) {
				setPwPH(tfNewPw2Pw);	//PlaceHolder 세팅
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
		
		if(!tfPhonNumId.getText().equals("  휴대폰번호('-'제외)") && sendCertifiFlagId == 1 && !certifiFlagId && idPwFlag == 1) {
			if(Boilerplate.phonNumValidation(tfPhonNumId.getText())) {	//아이디 찾기 전송하기 버튼 활성화 조건 검사
				btnSendId.setEnabled(true);	//아이디 찾기 전송하기 버튼 활성화
			} else {
				btnSendId.setEnabled(false);	//아이디 찾기 전송하기 버튼 비활성화
			}
		}
		
		if(!tfCertifiNumId.getText().equals("  인증번호") && sendCertifiFlagId == 2 && !certifiFlagId && idPwFlag == 1) {
			if(Boilerplate.certifiNumValidation(tfCertifiNumId.getText())) {	//아이디 찾기 인증하기 버튼 활성화 조건 검사
				btnSendId.setEnabled(true);	//아이디 찾기 인증하기 버튼 활성화
			} else {
				btnSendId.setEnabled(false);	//아이디 찾기 인증하기 버튼 비활성화
			}
		}
		
		if(!tfPhonNumPw.getText().equals("  휴대폰번호('-'제외)") && sendCertifiFlagPw == 1 && !certifiFlagPw && idPwFlag == 2) {
			if(Boilerplate.phonNumValidation(tfPhonNumPw.getText())) {	//비밀번호 찾기 전송하기 버튼 활성화 조건 검사
				btnSendPw.setEnabled(true);	//비밀번호 찾기 전송하기 버튼 활성화
			} else {
				btnSendPw.setEnabled(false);	//비밀번호 찾기 전송하기 버튼 비활성화
			}
		}
		
		if(!tfPhonNumPw.getText().equals("  인증번호") && sendCertifiFlagPw == 2 && !certifiFlagPw && idPwFlag == 2) {
			if(Boilerplate.certifiNumValidation(tfCertifiNumPw.getText())) {	//비밀번호 찾기 인증하기 버튼 활성화 조건 검사
				btnSendPw.setEnabled(true);	//비밀번호 찾기 인증하기 버튼 활성화
			} else {
				btnSendPw.setEnabled(false);	//비밀번호 찾기 인증하기 버튼 비활성화
			}
		}
		
		String pw1 = getPassword(tfNewPwPw);
		
		String pw2 = getPassword(tfNewPw2Pw);
		
		if(!pw1.equals("  새 비밀번호") && !pw2.equals("  새 비밀번호 확인") && idPwFlag == 2) {
			if(Boilerplate.pwValidation(pw1) && Boilerplate.pwValidation(pw2)) {
				btnPwChange.setEnabled(true);
			} else {
				btnPwChange.setEnabled(false);				
			}
		}
		
		idFindBtnChk();	//아이디 찾기 버튼 활성화 조건 검사
		
		pwFindBtnChk();	//비밀번호 찾기 버튼 활성화 조건 검사
		
	}

	

	public static void setSendCertifiFlagId(int sendCertifiFlagIdIn) {
		
		sendCertifiFlagId = sendCertifiFlagIdIn;
		
	}

	public static void setSendCertifiFlagPw(int sendCertifiFlagPwIn) {
		
		sendCertifiFlagPw = sendCertifiFlagPwIn;
		
	}

	public static void setCertifiNum(int certifiNumIn) {
		
		certifiNum = certifiNumIn;
		
	}


}
