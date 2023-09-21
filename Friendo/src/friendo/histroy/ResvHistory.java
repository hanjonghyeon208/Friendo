//예약 내역과 취소하기 패널 클래스

package friendo.histroy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import friendo.common.Boilerplate;
import friendo.common.DB;
import friendo.common.UsersData;
import friendo.main.Open;
import friendo.start.StartFrame;

public class ResvHistory extends JPanel implements ActionListener, MouseListener{
	
	private String usersid = UsersData.getUsersId();
	private StartFrame mainFrame = null;	//메인 프레임 객체
	private ImageIcon icBackET;
	private ImageIcon icBackRo;
	private ImageIcon icBackPr;
	private JButton btnBack;
	private JPanel pnBackground;
	private JPanel pnListBack;
	private JPanel pnList;
	private int iListnum;
	private String[] sResvNum;
	private String[] sResvLoc;
	private String[] sResvStatus;
	private JLabel[] lblResvNum;
	private JLabel[] lblResvLoc;
	private JLabel[] lblResvStatus;
	private String[] sResvUniv;
	private JLabel[] lblResvUniv;
	private JPanel pnListNothing;
	private JPanel[] pnListInner;



//아이디/비밀번호 찾기 화면
public ResvHistory() {
	
	setLayout(new BorderLayout());
    setBackground(new Color(255, 252, 244));
    
    mainFrame = (StartFrame) StartFrame.getMainFrame();	//메인 프레임 객체 주소 저장
    
    mainFrame.setTitle("Reservation History");
    
	
    getDataFromDB();
    
	makeImageIcon();	//이미지 아이콘 생성
	
    makeTitle();	//타이틀 영역 생성
    
    makeCenter();
    
 
  
	
}

private void getDataFromDB() { //데이터 베이스에서 값을 조회하여 가져와 배열 변수에 넣기
	ResultSet rsResv;
	ResultSet rsUniv;
	
	rsResv = DB.getResult("select count (distinct resvnum) from reservation where usersid='"+usersid+"'"); //유저아이디로 예약내역 카운트 조회
	try {
		rsResv.next();
		iListnum = rsResv.getInt(1); //데이터베이스에서 조회된 예약 내역의 수에 따라 iListnum 변수의 값이 결정
		rsResv.close();
		System.out.println("DB에서 예약 카운트 완료");
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("DB에서 예약 카운트 실패");
	}
	
	if(iListnum==0) return;
	
	sResvNum = new String[iListnum]; //각 배열들을 Listnum의 크기로 초기화
	sResvLoc  = new String[iListnum];
	sResvUniv = new String[iListnum];
	sResvStatus  = new String[iListnum];
	
	lblResvNum = new JLabel[iListnum];
	lblResvLoc = new JLabel[iListnum];
	lblResvUniv = new JLabel[iListnum];
	lblResvStatus = new JLabel[iListnum];
	
	rsResv = DB.getResult("select * from reservation"
			+ " where usersid='"+usersid+"' order by resvNum");
	try {
		for (int i = 0; i < iListnum; i++) {  //iListnum 개수만큼 1씩 증가하며 반복
 			if(rsResv.next())  
			{
				sResvNum[i] = rsResv.getString("resvnum"); //조회한 것 중 각 컬럼에 맞는 값을 배열 순서에 맞게 넣어준다
				sResvLoc[i] = rsResv.getString("parkingLocNum");
				rsUniv = DB.getResult("select parkingUniv from parking WHERE parkingLocNum='" + sResvLoc[i] + "'");
				if(rsUniv.next()) 
				{
					sResvUniv[i] = rsUniv.getString(1);
				}else 
				{
					sResvUniv[i] = "";
				}
				rsUniv.close();
				sResvStatus[i] = rsResv.getString("resvStatus"); 
				}
			}
		rsResv.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
}







//이미지 아이콘 생성
private void makeImageIcon() {
	
	icBackET = new ImageIcon("images/history/Btn_Back_EnabledTrue.png");		//뒤로가기 버튼(EnabledTrue) 이미지 아이콘
	icBackRo = new ImageIcon("images/history/Btn_Back_Rollover.png");			//뒤로가기 버튼(Rollover) 이미지 아이콘
	icBackPr = new ImageIcon("images/history/Btn_Back_Pressed.png");			//뒤로가기 버튼(Pressed) 이미지 아이콘
	
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
	
	JLabel lblTitle = new JLabel("Reservation History");
	lblTitle.setFont(new Font("D2Coding", Font.BOLD, 50));	//라벨 폰트 설정
	lblTitle.setForeground(new Color(255, 109, 28));	//텍스트필드 폰트 생상 설정
	
	pnTitle.add(lblTitle);
	pnTitleBackground.add(pnTitle, BorderLayout.CENTER);
	
	//뒤로가기 버튼 생성
	JPanel pnBack = new JPanel();
	pnBack.setLayout(new FlowLayout(FlowLayout.CENTER));
	pnBack.setPreferredSize(new Dimension(120, 120));	//패널 크기 설정
	pnBack.setBorder(new EmptyBorder(50, 0, 0, 0));		//패널 패딩 설정
	pnBack.setBackground(new Color(255, 252, 244));		//패널 색상 배경생과 동일하게 설정
			
	btnBack = new JButton(icBackET);
	Boilerplate.setImageButton(btnBack, icBackRo, icBackPr, 100, 50);	//이미지 버튼 세팅
	btnBack.setEnabled(true);	
	btnBack.addActionListener(this);
	
	pnBack.add(btnBack);
	pnTitleBackground.add(pnBack, BorderLayout.WEST);
	
	
	
	add(pnTitleBackground, BorderLayout.NORTH);
		
}






private void makeCenter() {
	pnBackground = new JPanel();
	pnBackground.setLayout(null);
	pnBackground.setBackground(new Color(255, 252, 244));		//패널 색상 배경색과 동일하게 설정
	
	pnListBack = new JPanel();
	pnListBack.setLayout(null);
	pnListBack.setLocation(100, 20);
	pnListBack.setSize(850, 550);
	pnListBack.setBorder(new EmptyBorder(0, 100, 0, 100));		//패널 패딩 설정
	pnListBack.setBackground(new Color(255, 252, 244));
	
	addResvTitlePanel();
	
	pnList = new JPanel();
	pnList.setLayout(null);
	pnList.setBackground(Color.WHITE);
	
	addListPanels();
	
	if(iListnum>=8) pnList.setPreferredSize(new Dimension(850, 44*iListnum));
	//예약 내역의 개수에 따라 세로로 스크롤할 수 있는 크기로 패널을 설정
	else if(iListnum>=0&&iListnum<8) pnList.setPreferredSize(new Dimension(850, 44*7));
	//예약 내역의 개수가 0 이상이면서 8 미만이면,
	//pnList 패널의 크기를 new Dimension(850, 44*7)으로 설정
	//예약 내역의 개수에 상관없이 세로로 7개의 아이템을 보여줄 수 있는 크기로 패널을 설정
	
	JScrollPane jsp = new JScrollPane(pnList,  //스크롤팬 생성
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	jsp.setSize(850,505);
	jsp.setLocation(0, 45);
	jsp.setBackground(new Color(255,236,198));
	jsp.getVerticalScrollBar().setUnitIncrement(8);
	pnListBack.add(jsp);
	
	pnBackground.add(pnListBack);
	add(pnBackground, BorderLayout.CENTER);
	
}






private void addResvTitlePanel() { 
	JPanel pnResvTitle = new JPanel();
	pnResvTitle.setLayout(null);
	pnResvTitle.setSize(850, 45);
	pnResvTitle.setLocation(0, 0);
	pnResvTitle.setBackground(new Color(255,236,198));
	pnResvTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	
	JLabel lblResvNum = new JLabel("주문 번호");
	lblResvNum.setSize(110, 18);
	lblResvNum.setLocation(100, 15);
	lblResvNum.setFont(new Font("D2Coding",Font.BOLD,15));
	pnResvTitle.add(lblResvNum);
	

	JLabel lblResvLoc = new JLabel("주차 위치");
	lblResvLoc.setSize(110, 18);
	lblResvLoc.setLocation(100+200, 15);
	lblResvLoc.setFont(new Font("D2Coding",Font.BOLD,15));
	pnResvTitle.add(lblResvLoc);
	
	JLabel lblResvUniv = new JLabel("대학명");
	lblResvUniv.setSize(110, 18);
	lblResvUniv.setLocation(100+410, 15);
	lblResvUniv.setFont(new Font("D2Coding",Font.BOLD,15));
	pnResvTitle.add(lblResvUniv);

	JLabel lblResvStatus = new JLabel("예약 상태");
	lblResvStatus.setSize(110, 18);
	lblResvStatus.setLocation(100+600, 15);
	lblResvStatus.setFont(new Font("D2Coding",Font.BOLD,15));
	pnResvTitle.add(lblResvStatus);
	
	
	pnListBack.add(pnResvTitle);
	
	
}






private void addListPanels() {
	if(iListnum==0)  //조회된 예약 개수가 없을 경우
	{
		pnListNothing = new JPanel();
		pnListNothing.setLayout(null);
		pnListNothing.setSize(837,44*7);
		pnListNothing.setLocation(0,0);
		pnListNothing.setBackground(Color.WHITE);
		
		JLabel lblNothing = new JLabel("주문 내역이 없습니다");
		lblNothing.setSize(250,20);
		lblNothing.setLocation(20, 20);
		lblNothing.setFont(new Font("D2Coding",Font.PLAIN,15));
		pnListNothing.add(lblNothing);
		
		pnList.add(pnListNothing);
		return;
	}
	
	//pnListInner 라는 큰 배열에 정보를 담은 다른 라벨 배열들 넣기
	    pnListInner = new JPanel[iListnum]; 
	for (int i = 0; i < iListnum; i++) {
		pnListInner[i] = new JPanel();  //리스트 패널 한 칸 생성
		pnListInner[i].setLayout(null);
		pnListInner[i].setSize(850,45);
		pnListInner[i].setLocation(0, 44*i);
		pnListInner[i].setBackground(Color.WHITE);
		pnListInner[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		pnListInner[i].addMouseListener(this);
		
		lblResvNum[i] = new JLabel(sResvNum[i]); //리스트 패널에 예약번호 추가
		lblResvNum[i].setSize(147,20);
		lblResvNum[i].setLocation(85, 12);
		lblResvNum[i].setFont(new Font("D2Coding",Font.PLAIN,15));
		pnListInner[i].add(lblResvNum[i]);
		
		lblResvLoc[i] = new JLabel(sResvLoc[i]); //리스트 패널에 예약 위치 추가 
		lblResvLoc[i].setSize(147,20);
		lblResvLoc[i].setLocation(320, 12);
		lblResvLoc[i].setFont(new Font("D2Coding",Font.PLAIN,15));
		pnListInner[i].add(lblResvLoc[i]);
		
		lblResvUniv[i] = new JLabel(sResvUniv[i]); //리스트 패널에 대학명 추가
		lblResvUniv[i].setSize(173,20);
		lblResvUniv[i].setLocation(490, 12);
		lblResvUniv[i].setFont(new Font("D2Coding",Font.PLAIN,15));
		pnListInner[i].add(lblResvUniv[i]);

		lblResvStatus[i] = new JLabel(sResvStatus[i]); //리스트 패널 에약 상태 추가
		lblResvStatus[i].setSize(173,20);
		lblResvStatus[i].setLocation(700, 12);
		lblResvStatus[i].setFont(new Font("D2Coding",Font.PLAIN,15));
		pnListInner[i].add(lblResvStatus[i]);
		
		pnList.add(pnListInner[i]);
		
		
	}
	
}
	


private void changeLogin() {
	
    Boilerplate.redraw(new Open());	//오픈 화면으로 전환
	
}




@Override
public void actionPerformed(ActionEvent e) {
	Object obj = e.getSource();
	
	if(obj == btnBack ) {
		
	changeLogin();	//로그인 화면으로 전환
	
	}
}

@Override
public void mouseClicked(MouseEvent e) {

	for (int i = 0; i < iListnum; i++) {
		if(e.getSource()==pnListInner[i]) //순서에 따라 리스트 패널을 클릭하면...
		{
			int result = JOptionPane.showConfirmDialog(pnBackground, "예를 누르시면 예약이 취소됩니다." +"\n 취소하시겠습니까?" 
                    , "예약 취소하기", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION) {
				String sqlUpdate1 = "UPDATE reservation SET resvstatus = '취소완료' WHERE PARKINGLOCNUM IN " 
						+ "(SELECT PARKINGLOCNUM FROM reservation WHERE resvnum = '" + lblResvNum[i].getText() + "')";
				String sqlUpdate2 = "UPDATE PARKING SET parkingstatus ='주차가능' WHERE PARKINGLOCNUM IN "
						+ "(SELECT PARKINGLOCNUM FROM reservation WHERE resvnum = '" + lblResvNum[i].getText() + "')";
				DB.executeSQL(sqlUpdate1); 	//DB로 Update문 전송 
				DB.executeSQL(sqlUpdate2); 	//DB로 Update문 전송 
				System.out.println("DB PARKING 데이터 수정 완료");
				JOptionPane.showMessageDialog(pnBackground, "예약 취소가 완료되었습니다.", "취소 완료", JOptionPane.INFORMATION_MESSAGE, null);
				if( result == JOptionPane.YES_OPTION) {
					Boilerplate.redraw(new ResvHistory());
				System.out.println("DB 예약 취소 완료");	
	        }
			
			}
		}
	}
}

@Override
public void mousePressed(MouseEvent e) {
	for (int i = 0; i < iListnum; i++) {
		if(e.getSource()==pnListInner[i]) 
		{
			pnListInner[i].setBackground(new Color(255,236,198));
	
			}
	}
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
	for (int i = 0; i < iListnum; i++) {
		if(e.getSource()==pnListInner[i]) 
		{
			pnListInner[i].setBackground(Color.WHITE);
	
			}
	}
}
}
