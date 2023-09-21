//메인프레임 클래스

package friendo.start;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import friendo.common.DB;
import friendo.login.Login;


public class StartFrame extends JFrame implements WindowListener {

	private static JFrame mainFrame;
	private JPanel currentPanel;
	
	//로그인 화면
	public StartFrame(String title) {
		
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(300, 50);
//        setLocationRelativeTo(null);
        setSize(1050, 750);
        setResizable(false);
        setLayout(new BorderLayout());
        
        mainFrame = this;
        
        
        //타이틀바 아이콘 설정
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.getImage("images/login/logo_titleBar.png");
        setIconImage(img);
        
        
        currentPanel = new Login();
        add(currentPanel);
        
        setVisible(true);
        
        DB.init();	//DB 연결
        
        addWindowListener(this);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		DB.closeDB(DB.conn, DB.stmt);	//DB 연결 종료
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	public static JFrame getMainFrame() {
		
		return mainFrame;
		
	}

	public static void setMainFrame(JFrame mainFrame) {
		
		StartFrame.mainFrame = mainFrame;
		
	}
	
	public JPanel getCurrentPanel() {
		
		return currentPanel;
		
	}

	public void setCurrentPanel(JPanel currentPanel) {
		
		this.currentPanel = currentPanel;
		
	}

}
