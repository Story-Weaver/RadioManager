package by.roman.worldradio2.data.model;

public class User {
    private int id;
    private String login;
    private String password;
    private int inSystem;

    public User(int id, String login, String password,int inSystem) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.inSystem = inSystem;
    }

    // Геттеры
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public int getInSystem() { return inSystem; }

    // Сеттеры
    public void setLogin (String login) { this.login = login; }
    public void setPassword (String login) { this.login = login; }
    public void setInSystem (int inSystem) { this.inSystem = inSystem; }
}
