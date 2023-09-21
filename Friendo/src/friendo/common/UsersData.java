//입력받은 회원아이디를 저장하는 클래스

package friendo.common;


public class UsersData {

	private static String usersId;	//회원 아이디
	
	public static String getUsersId() {
		return usersId;
	}
	
	public static void setUsersId(String usersIdIn) {
		usersId = usersIdIn;
	}
	
	
}
