package by.roman.worldradio2.data.dto;

public class UserDTO {
    private int id;
    private String login;
    private String password;
    private boolean inSystem;
    public UserDTO(int id, String login, String password,boolean inSystem) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.inSystem = inSystem;
    }
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword(){ return password; }
    public boolean getInSystem(){ return inSystem; }
    public void setId(int id){ this.id = id;}
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String login) { this.login = login; }
    public void setInSystem(boolean inSystem){ this.inSystem = inSystem; }
}
