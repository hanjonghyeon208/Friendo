//로그인 후 나타날 메인 화면 패널 클래스

package friendo.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import friendo.common.Boilerplate;
import friendo.histroy.ResvHistory;
import friendo.login.Login;
import friendo.modify.Modify;
import friendo.reservation.Reservation;
import friendo.start.StartFrame;

public class Open extends JPanel implements MouseListener {

	
	private StartFrame mainFrame = null;
	private ImageIcon icResvET, icResvRo, icResvPr, TaResv, icCaclET, icCaclRo, icCaclPr, TaCacl, icModfET, icModfRo, icModfPr, TaModf, icOutET,
						icOutRo, icOutPr, TaOut;
	private JButton btnResv, btnCacl, btnModf, btnOut;

	public Open() {
		  	setLayout(new BorderLayout());
	        setBackground(new Color(255, 252, 244));
	        
	        mainFrame  = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
	        
	        mainFrame.setTitle("Main");
	        
	        makeImageIcon();	//이미지 아이콘 생성
	        
	        makeMainTitle();  //메인 타이틀 생성 
	        
	        makeMovingBtns(); // 이동 버튼 목록 생성
	        
	}
	//이미지 아이콘 생성
	private void makeImageIcon() {
		
		
		icResvET = new ImageIcon("images/main/Btn_Resv_EnabledTrue.png");				//예약하기 버튼(EnabledTrue) 이미지 아이콘
		icResvRo = new ImageIcon("images/main/Btn_Resv_Rollover.png");					//예약하기 버튼(Rollover) 이미지 아이콘
		icResvPr = new ImageIcon("images/main/Btn_Resv_Pressed.png");					//예약하기 버튼(Pressed) 이미지 아이콘
		TaResv = new ImageIcon("images/main/Ta_Resv.png");								//예약하기 텍스트

		icCaclET = new ImageIcon("images/main/Btn_Cacl_EnabledTrue.png");				//취소하기 버튼(EnabledTrue) 이미지 아이콘
		icCaclRo = new ImageIcon("images/main/Btn_Cacl_Rollover.png");					//취소하기 버튼(Rollover) 이미지 아이콘
		icCaclPr = new ImageIcon("images/main/Btn_Cacl_Pressed.png");					//취소하기 버튼(Pressed) 이미지 아이콘
		TaCacl = new ImageIcon("images/main/Ta_Cacl.png");								//취소하기 텍스트

		icModfET = new ImageIcon("images/main/Btn_Modf_EnabledTrue.png");				//수정하기 버튼(EnabledTrue) 이미지 아이콘
		icModfRo = new ImageIcon("images/main/Btn_Modf_Rollover.png");					//수정하기 버튼(Rollover) 이미지 아이콘
		icModfPr = new ImageIcon("images/main/Btn_Modf_Pressed.png");					//수정하기 버튼(Pressed) 이미지 아이콘
		TaModf = new ImageIcon("images/main/Ta_Modf.png");								//수정하기 텍스트
		
		icModfET = new ImageIcon("images/main/Btn_Modf_EnabledTrue.png");				//수정하기 버튼(EnabledTrue) 이미지 아이콘
		icModfRo = new ImageIcon("images/main/Btn_Modf_Rollover.png");					//수정하기 버튼(Rollover) 이미지 아이콘
		icModfPr = new ImageIcon("images/main/Btn_Modf_Pressed.png");					//수정하기 버튼(Pressed) 이미지 아이콘
		TaModf = new ImageIcon("images/main/Ta_Modf.png");								//수정하기 텍스트

		icOutET = new ImageIcon("images/main/Btn_Out_EnabledTrue.png");					//로그아웃 버튼(EnabledTrue) 이미지 아이콘
		icOutRo = new ImageIcon("images/main/Btn_Out_Rollover.png");					//로그아웃 버튼(Rollover) 이미지 아이콘
		icOutPr = new ImageIcon("images/main/Btn_Out_Pressed.png");						//로그아웃 버튼(Pressed) 이미지 아이콘
		TaOut = new ImageIcon("images/main/Ta_Out.png");								//로그아웃 텍스트
	}
	
	 //메인 타이틀 생성
		private void makeMainTitle() 
		{
			JPanel pnMainLeft = new JPanel();
			pnMainLeft.setLayout(new GridLayout(0,1));
			pnMainLeft.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
			
			
			JPanel pnMainLogoTitle = new JPanel();
			pnMainLogoTitle.setLayout(new BorderLayout());
			pnMainLogoTitle.setBorder(new EmptyBorder(150, 55, 0, 0));	//패널 패딩 설정
			pnMainLogoTitle.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
			
			JLabel lblMainLogo = new JLabel(new ImageIcon("images/main/logo_main.png"));
		
			JPanel pnMainLblTitle = new JPanel();
			pnMainLblTitle.setLayout(new BorderLayout());
			pnMainLblTitle.setBorder(new EmptyBorder(0, 90, 200, 0));	//패널 패딩 설정
			pnMainLblTitle.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
			
			JLabel lblMainTitle = new JLabel("버튼을 눌러 이동하세요");
			lblMainTitle.setFont(new Font("D2Coding", Font.BOLD, 25));	//라벨 폰트 설정
			lblMainTitle.setForeground(new Color(140, 140, 140));	//텍스트필드 폰트 생상 설정
			
			pnMainLogoTitle.add(lblMainLogo);
			pnMainLblTitle.add(lblMainTitle);
			pnMainLeft.add(pnMainLogoTitle);
			pnMainLeft.add(pnMainLblTitle);
			
			add(pnMainLeft);
			
		}
		
		
		private void makeMovingBtns() {
			
			JPanel pnMainRight = new JPanel();
			pnMainRight.setLayout(new BorderLayout());
			pnMainRight.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
			
			
			JPanel pnButtons = new JPanel();
			pnButtons.setLayout(new GridLayout(0,2));
			pnButtons.setBorder(new EmptyBorder(50, 0, 50, 50));	//패널 패딩 설정
			pnButtons.setBackground(new Color(255, 252, 244));
			
			btnResv = new JButton(icResvET);
			Boilerplate.setImageButton(btnResv, icResvRo, icResvPr, 120, 120);	//이미지 버튼 세팅
			btnResv.setEnabled(true);	//활성화 상태로 생성
			btnResv.addMouseListener(this);
			JLabel lblResv = new JLabel(TaResv);

			btnCacl = new JButton(icCaclET);
			Boilerplate.setImageButton(btnCacl, icCaclRo, icCaclPr, 120, 120);	//이미지 버튼 세팅
			btnCacl.setEnabled(true);	//활성화 상태로 생성
			btnCacl.addMouseListener(this);
			JLabel lblCacl = new JLabel(TaCacl);
			
			btnModf = new JButton(icModfET);
			Boilerplate.setImageButton(btnModf, icModfRo, icModfPr, 120, 120);	//이미지 버튼 세팅
			btnModf.setEnabled(true);	//활성화 상태로 생성
			btnModf.addMouseListener(this);
			JLabel lblModf = new JLabel(TaModf);

			btnOut = new JButton(icOutET);
			Boilerplate.setImageButton(btnOut, icOutRo, icOutPr, 120, 120);	//이미지 버튼 세팅
			btnOut.setEnabled(true);	//활성화 상태로 생성
			btnOut.addMouseListener(this);
			JLabel lblOut = new JLabel(TaOut);
			
			
			pnButtons.add(btnResv);
			pnButtons.add(lblResv);
			pnButtons.add(btnCacl);
			pnButtons.add(lblCacl);
			pnButtons.add(btnModf);
			pnButtons.add(lblModf);
			pnButtons.add(btnOut);
			pnButtons.add(lblOut);
			pnMainRight.add(pnButtons);
			
			add(pnMainRight, BorderLayout.EAST);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		
		
		if(obj == btnResv) {				//예약하기 버튼 클릭시
			Boilerplate.redraw(new Reservation());	//예약하기 화면으로 전환
		} else if(obj == btnCacl) {			//취소 및 내역 버튼 클릭시
		Boilerplate.redraw(new ResvHistory());	// 취소 및 내역 화면으로 전환
		}else if(obj == btnModf) {			//수정하기 버튼 클릭시
		Boilerplate.redraw(new Modify());		//개인정보 수정 화면으로 전환
		}else if(obj == btnOut) {			//로그아웃 버튼 클릭시
		Boilerplate.redraw(new Login());	//로그인 화면으로 전환
	}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}
