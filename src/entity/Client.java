package entity;

public class Client extends User{
	private String username;
	private String password;
	private String name;
	private String identity;
	private String uid;
	private String regTime;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public void setRegTime(String regTime) {
		// TODO Auto-generated method stub
		this.regTime = regTime;
	}
	
	public String getRegTime() {
		return regTime;
	}
	
	@Override
	public String getManagerId() {
		return uid;
	}
}
