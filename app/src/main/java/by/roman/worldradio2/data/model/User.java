package by.roman.worldradio2.data.model;

public class User {
    private final int id;
    private String login;
    private final String password;
    private int inSystem;

    public User(int id, String login, String password,int inSystem) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.inSystem = inSystem;
    }

    // Getters
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public int getInSystem() { return inSystem; }
}
