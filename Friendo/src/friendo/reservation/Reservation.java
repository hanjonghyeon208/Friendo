//예약하기 패널 클래스

package friendo.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import friendo.common.Boilerplate;
import friendo.common.DB;
import friendo.common.UsersData;
import friendo.histroy.ResvHistory;
import friendo.main.Open;
import friendo.start.StartFrame;


public class Reservation extends JPanel implements ActionListener {

	private StartFrame mainFrame = null;	//메인 프레임 객체
	
	private JButton btnBack;				//뒤로가기 버튼
	
	private String usersInId;				//사용자에게 입력 받은 아이디

	
	
	

	private ImageIcon icBackET;				//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
	private ImageIcon icBackRo;				//뒤로가기 버튼(Rollover) 이미지 아이콘
	private ImageIcon icBackPr;				//뒤로가기 버튼(Pressed) 이미지 아이콘


	private JComboBox<String> cbUniv;
	private String[] univ = {"자바대학교", "응용대학교", "인하공전대학"};

	private ImageIcon icSearchET;

	private ImageIcon icSearchRo;

	private ImageIcon icSearchPr;

	private JButton btnSearch;

	private ImageIcon icBd1stET;

	
	private ImageIcon icBd2ndET;

	
	private ImageIcon icBd3rdET;

	private ImageIcon icBd3rdRo;

	private ImageIcon icBd3rdPr;

	private ImageIcon icBd4thET;

	private ImageIcon icBd4thRo;

	private ImageIcon icBd4thPr;

	private ImageIcon icBd1stRo;

	private ImageIcon icBd1stPr;

	private JButton btnBd1st;

	private ImageIcon icBd2ndRo;

	private ImageIcon icBd2ndPr;

	private JButton btnBd2nd;

	private JButton btnBd3rd;

	private JButton btnBd4th;


	private ImageIcon icCompleteET;

	private ImageIcon icCompleteRo;

	private ImageIcon icCompletePr;

	private JButton btnComplete;

	private ImageIcon icPossibleET;

	private ImageIcon icPossiblePr;

	private ImageIcon icPossibleRo;

	private JButton btnPossible;

	private JButton[] btn;

	private JButton[] btn2;

	private JLabel lblParkingNum;
	
	private JLabel lblParkingStatus;


	private JPanel pnBackground;

	private String resvNum;

	private String userParkingNum;


	
	

	

	

	
	
	//아이디/비밀번호 찾기 화면
	public Reservation() {
		
		setLayout(new BorderLayout());
        setBackground(new Color(255, 252, 244));
        
        mainFrame = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
        
        mainFrame.setTitle("Reservation");
        
		
		
		makeImageIcon();	//이미지 아이콘 생성
		
        makeTitle();	//타이틀 영역 생성
        
        makeBuilding();

        DateTime();
		
	}
	
	private void  DateTime() { //주문번호를 생성하기위해..현재 날짜와 시간 가져오기
		LocalDateTime dateTime = LocalDateTime.now(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyHHmmss");
		resvNum = (dateTime.format(formatter));
		
	}


	//이미지 아이콘 생성
	private void makeImageIcon() {
		
		icBackET = new ImageIcon("images/reservation/Btn_Back_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBackRo = new ImageIcon("images/reservation/Btn_Back_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBackPr = new ImageIcon("images/reservation/Btn_Back_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘

		icSearchET = new ImageIcon("images/reservation/Btn_Search_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icSearchRo = new ImageIcon("images/reservation/Btn_Search_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icSearchPr = new ImageIcon("images/reservation/Btn_Search_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘

		icBd1stET = new ImageIcon("images/reservation/Btn_Bd1st_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBd1stRo = new ImageIcon("images/reservation/Btn_Bd1st_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBd1stPr = new ImageIcon("images/reservation/Btn_Bd1st_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘

		icBd2ndET = new ImageIcon("images/reservation/Btn_Bd2nd_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBd2ndRo = new ImageIcon("images/reservation/Btn_Bd2nd_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBd2ndPr = new ImageIcon("images/reservation/Btn_Bd2nd_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘
		
		icBd3rdET = new ImageIcon("images/reservation/Btn_Bd3rd_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBd3rdRo = new ImageIcon("images/reservation/Btn_Bd3rd_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBd3rdPr = new ImageIcon("images/reservation/Btn_Bd3rd_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘

		icBd4thET = new ImageIcon("images/reservation/Btn_Bd4th_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icBd4thRo = new ImageIcon("images/reservation/Btn_Bd4th_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icBd4thPr = new ImageIcon("images/reservation/Btn_Bd4th_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘

		icPossibleET = new ImageIcon("images/reservation/Btn_Possible_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icPossibleRo = new ImageIcon("images/reservation/Btn_Possible_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icPossiblePr = new ImageIcon("images/reservation/Btn_Possible_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘
		
		icCompleteET = new ImageIcon("images/reservation/Btn_Complete_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
		icCompleteRo = new ImageIcon("images/reservation/Btn_Complete_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
		icCompletePr = new ImageIcon("images/reservation/Btn_Complete_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘
		
		
		
	}

	//타이틀 영역 생성
	private void makeTitle() {

		JPanel pnTitleBackground = new JPanel();
		pnTitleBackground.setLayout(new BorderLayout());
		pnTitleBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		
		//타이틀 생성
		JPanel pnTitle = new JPanel();
		pnTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnTitle.setBorder(new EmptyBorder(40, 0, 0, 120));	//패널 패딩 설정
		pnTitle.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JLabel lblTitle = new JLabel("Reservation");
		lblTitle.setFont(new Font("Santana-Black", Font.BOLD, 50));	//라벨 폰트 설정
		lblTitle.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 생상 설정
		
		pnTitle.add(lblTitle);
		pnTitleBackground.add(pnTitle, BorderLayout.CENTER);
		
		//뒤로가기 버튼 생성
		JPanel pnBack = new JPanel();
		pnBack.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnBack.setPreferredSize(new Dimension(120, 120));	//패널 크기 설정
		pnBack.setBorder(new EmptyBorder(50, 0, 0, 0));		//패널 패딩 설정
		pnBack.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
				
		btnBack = new JButton(icBackET);
		Boilerplate.setImageButton(btnBack, icBackRo, icBackPr, 100, 50);	//이미지 버튼 세팅
		btnBack.setEnabled(true);	
		btnBack.addActionListener(this);
		
		pnBack.add(btnBack);
		pnTitleBackground.add(pnBack, BorderLayout.WEST);
		
		JPanel pnCbUnivBtn = new JPanel();
		pnCbUnivBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnCbUnivBtn.setBorder(new EmptyBorder(40, 0, 40, 30));		//패널 패딩 설정
		pnCbUnivBtn.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		cbUniv = new JComboBox<String>(univ);
		cbUniv.setPreferredSize(new Dimension(350, 50));
		cbUniv.setBackground(Color.WHITE);
		
		btnSearch = new JButton(icSearchET);
		Boilerplate.setImageButton(btnSearch, icSearchRo, icSearchPr, 100, 50);	//이미지 버튼 세팅
		btnSearch.setEnabled(true);	
		btnSearch.addActionListener(this);
		
	
		pnCbUnivBtn.add(cbUniv);
		pnCbUnivBtn.add(btnSearch);
		pnTitleBackground.add(pnCbUnivBtn, BorderLayout.EAST); 
		
		add(pnTitleBackground, BorderLayout.NORTH);
		
				
	}

	private void makeBuilding() {
		pnBackground = new JPanel();
		pnBackground.setLayout(new BorderLayout());
		pnBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		
		JPanel pnButtons = new JPanel();
		pnButtons.setLayout(new BoxLayout(pnButtons, BoxLayout.Y_AXIS));
		pnButtons.setBackground(new Color(255, 252, 244));
		pnButtons.setPreferredSize(new Dimension(100, 300));
		
		btnBd1st = new JButton(icBd1stET);
		Boilerplate.setImageButton(btnBd1st, icBd1stRo, icBd1stPr, 100, 50);	//이미지 버튼 세팅
		btnBd1st.setEnabled(true);	
		btnBd1st.addActionListener(this);
		
		btnBd2nd = new JButton(icBd2ndET);
		Boilerplate.setImageButton(btnBd2nd, icBd2ndRo, icBd2ndPr, 100, 50);	//이미지 버튼 세팅
		btnBd2nd.setEnabled(false);	
		btnBd2nd.addActionListener(this);

		btnBd3rd = new JButton(icBd3rdET);
		Boilerplate.setImageButton(btnBd3rd, icBd3rdRo, icBd3rdPr, 100, 50);	//이미지 버튼 세팅
		btnBd3rd.setEnabled(false);	
		btnBd3rd.addActionListener(this);
		
		btnBd4th = new JButton(icBd4thET);
		Boilerplate.setImageButton(btnBd4th, icBd4thRo, icBd4thPr, 100, 50);	//이미지 버튼 세팅
		btnBd4th.setEnabled(false);	
		btnBd4th.addActionListener(this);
		
	
		pnButtons.add(btnBd1st);
		pnButtons.add(btnBd2nd);
		pnButtons.add(btnBd3rd);
		pnButtons.add(btnBd4th);
		
	

		
		JPanel pnBuilding = new JPanel();
		pnBuilding.setLayout(new BoxLayout(pnBuilding, BoxLayout.Y_AXIS));
		pnBuilding.setBackground(new Color(217, 217, 217));		//패널 색상 배경색과 동일하게 설정
		pnBuilding.setPreferredSize(new Dimension(350, 300));
		
	
		
		JPanel pnNorthBtn = new JPanel();
		pnNorthBtn.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnNorthBtn.setBackground(new Color(217, 217, 217));
		pnNorthBtn.setPreferredSize(new Dimension(115, 200));
		
		btn = new JButton[6];
		 for(int i=0; i<btn.length; i++){
			   btn[i] = new JButton("A" + i);
			   btn[i].setBackground(new Color(217, 217, 217));
			   btn[i].setBorder(new LineBorder(Color.WHITE, 5));
			   btn[i].setFont(new Font("D2 Coding", Font.BOLD, 35));	//텍스트필드 폰트 설정
			   btn[i].setForeground(Color.WHITE);	//텍스트필드 폰트 생상 설정
			   btn[i].setPreferredSize(new Dimension(115, 200));
			   btn[i].addActionListener(this);
			   pnNorthBtn.add(btn[i]);
		 }
		
		pnBuilding.add(pnNorthBtn);
		
		
		JPanel pnBuildingCenter = new JPanel();
		pnBuildingCenter.setLayout(null);
		pnBuildingCenter.setBackground(new Color(217, 217, 217));		//패널 색상 배경색과 동일하게 설정
		pnBuildingCenter.setPreferredSize(new Dimension(100, 100));
		pnBuilding.add(pnBuildingCenter);
		

	
		
		JPanel pnSouthBtn = new JPanel();
		pnSouthBtn.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSouthBtn.setBackground(new Color(217, 217, 217));
		pnSouthBtn.setPreferredSize(new Dimension(115, 200));
		
		 btn2 = new JButton[6];
		 for(int i=0; i<btn2.length; i++){
			   btn2[i] = new JButton("B" + i);
			   btn2[i].setBackground(new Color(217, 217, 217));
			   btn2[i].setBorder(new LineBorder(Color.WHITE, 5));
			   btn2[i].setFont(new Font("D2 Coding", Font.BOLD, 35));	//텍스트필드 폰트 설정
			   btn2[i].setForeground(Color.WHITE);	//텍스트필드 폰트 생상 설정);
			   btn2[i].setPreferredSize(new Dimension(115, 200));
			   btn2[i].addActionListener(this);
			   pnSouthBtn.add(btn2[i]);
		 }
		
		pnBuilding.add(pnSouthBtn);
		
		JPanel pnResv = new JPanel();
		pnResv.setLayout(new GridLayout(6, 1, 0, 0));
		pnResv.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
		pnResv.setPreferredSize(new Dimension(210, 300));
		
		
		JLabel lblWantNum = new JLabel(" 번호를 선택하세요");
		lblWantNum.setFont(new Font("D2Coding", Font.PLAIN, 22));	//텍스트필드 폰트 설정
		lblWantNum.setForeground(new Color(250, 89, 0));	//텍스트필드 폰트 생상 설정
		lblWantNum.setBackground(new Color(255, 252, 244));
		
		
		
		lblParkingNum = new JLabel();
		lblParkingNum.setHorizontalAlignment(SwingConstants.CENTER); //가로정렬 가운데로
		lblParkingNum.setPreferredSize(new Dimension(150, 30));
		lblParkingNum.setFont(new Font("D2Coding", Font.PLAIN, 30));
		lblParkingNum.setForeground(Color.RED);	//텍스트필드 폰트 생상 설정
		lblParkingNum.setBorder(BorderFactory.createLineBorder(new Color(250, 89, 0))); //라벨 테두리 설정
	
		
		btnPossible = new JButton(icPossibleET);
		Boilerplate.setImageButton(btnPossible, icPossibleRo, icPossiblePr, 60, 50);	//이미지 버튼 세팅
		btnPossible.setEnabled(true);	
		btnPossible.addActionListener(this);
		
		
		
		JLabel lblParkingLoc = new JLabel("선택하신 위치 상태");
		lblParkingLoc.setFont(new Font("D2Coding", Font.PLAIN, 22));	//텍스트필드 폰트 설정
		lblParkingLoc.setForeground(new Color(250, 89, 0));	//텍스트필드 폰트 생상 설정
		lblParkingLoc.setBackground(new Color(255, 252, 244));
		
		lblParkingStatus = new JLabel();
		lblParkingStatus.setHorizontalAlignment(SwingConstants.CENTER); //가로정렬 가운데로
		lblParkingStatus.setFont(new Font("D2Coding", Font.PLAIN, 30));	//텍스트필드 폰트 설정
		lblParkingStatus.setForeground(Color.RED);	//텍스트필드 폰트 생상 설정
		lblParkingStatus.setBackground(new Color(255, 252, 244));
		lblParkingStatus.setBorder(BorderFactory.createLineBorder(new Color(250, 89, 0))); //라벨 테두리 설정
		
		btnComplete = new JButton(icCompleteET);
		Boilerplate.setImageButton(btnComplete, icCompleteRo, icCompletePr, 210, 50);	//이미지 버튼 세팅
		btnComplete.setEnabled(true);	
		btnComplete.addActionListener(this);
		
		
		pnResv.add(lblWantNum);
		pnResv.add(lblParkingNum);
		pnResv.add(btnPossible);
		pnResv.add(lblParkingLoc);
		pnResv.add(lblParkingStatus);
		pnResv.add(btnComplete);
		
		pnBackground.add(pnButtons, BorderLayout.WEST);
		pnBackground.add(pnBuilding, BorderLayout.CENTER);
		pnBackground.add(pnResv, BorderLayout.EAST);
		
		add(pnBackground, BorderLayout.CENTER);
		
	}

	private void changeLogin() {
		
	    Boilerplate.redraw(new Open());	//로그인 화면으로 전환
		
	}
	
		
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnBack ) {
			
		changeLogin();	//로그인 화면으로 전환
		}
		else if(obj == btnSearch ) {
			System.out.println("대학명 조회");
			
			JOptionPane.showMessageDialog(mainFrame, "해당 버튼은 준비중입니다.", "준비중", JOptionPane.WARNING_MESSAGE);
		}
		else if(obj == btn[0] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A0");
		}
		else if(obj == btn[1] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A1");
		}
		else if(obj == btn[2] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A2");
		}
		else if(obj == btn[3] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A3");
		}
		else if(obj == btn[4] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A4");
		}
		else if(obj == btn[5] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("A5");
		}
		else if(obj == btn2[0] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B0");
		}
		else if(obj == btn2[1] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B1");
		}
		else if(obj == btn2[2] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B2");
		}
		else if(obj == btn2[3] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B3");
		}
		else if(obj == btn2[4] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B4");
		}
		else if(obj == btn2[5] ) {
			System.out.println("주차위치 선택");
			lblParkingNum.setText("B5");
		}else if(obj == btnPossible) {
			System.out.println("주차 가능 여부 확인");
			ResultSet rs;
			
			userParkingNum =lblParkingNum.getText();
			
			rs = DB.getResult("select * from PARKING WHERE PARKINGLOCNUM ='" + userParkingNum + "'");
		
				try {
					while(rs.next()) {
					String Possible = rs.getString("PARKINGSTATUS");
					lblParkingStatus.setText(Possible);
					System.out.println("DB 주차 가능 여부 조회 성공");
					if (Possible.equals("주차불가")) {
			            JOptionPane.showMessageDialog(mainFrame, "해당 위치는 예약이 완료되었습니다." + "\n 다른 위치를 선택해주세요.", "예약 불가"
			           , JOptionPane.WARNING_MESSAGE);
			            lblParkingNum.setText("");
			        	}
					}
				} catch (SQLException e1) {
					System.out.println("DB 주차 가능 여부 조회 실패");
					e1.printStackTrace();
				} 
				
		
				
		
				
		}else if(obj == btnComplete) {
		int result = JOptionPane.showConfirmDialog(pnBackground, "예를 누르시면 예약이 확정됩니다." +"\n 예약하시겠습니까?" 
		                                          , "예약하기", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					                                  
			if (result == JOptionPane.YES_OPTION) {
				
				
				userParkingNum =lblParkingNum.getText();
				
				String sqlInsert = "Insert into RESERVATION (resvNum, usersId, parkingLocNum, resvStatus) "
						+ "VALUES ('"+resvNum+"','"+(String) UsersData.getUsersId()+"','"+userParkingNum+"','예약완료')";
				DB.executeSQL(sqlInsert);	//DB로 Insert문 전송
				System.out.println("DB RESERVATION 데이터 삽입 완료");
				
				String sqlUpdate = "UPDATE PARKING Set parkingstatus='주차불가' WHERE PARKINGLOCNUM ='" + userParkingNum + "'";
				DB.executeSQL(sqlUpdate); 	//DB로 Update문 전송 
				System.out.println("DB PARKING 데이터 수정 완료");
		
				Boilerplate.redraw(new ResvHistory());
				
			}	else if(obj == btnPossible ) {
					 ResultSet rs2 =  DB.getResult("select resvstatus from reservation WHERE usersid ='" + usersInId + "'");
					 try {
						if (rs2.next() && rs2.getString("resvstatus").equals("예약완료")) {
							    btnComplete.setEnabled(false);
							}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				
				
			
			}
		}
	}
}		


	
		
		
	
	
	
	
