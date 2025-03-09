package by.roman.worldradio2.data.dto;

public class UserDTO {
    private String login;
    private String password;
    private int inSystem;
    public UserDTO(String login, String password,int inSystem) {
        this.login = login;
        this.password = password;
        this.inSystem = inSystem;
    }
    public String getLogin() { return login; }
    public String getPassword(){ return password; }
    public int getInSystem(){ return inSystem; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String login) { this.login = login; }
    public void setInSystem(int inSystem){ this.inSystem = inSystem; }
}
