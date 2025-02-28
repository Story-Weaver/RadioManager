package by.roman.worldradio2.data.model;

public class User {
    private int id;
    private String login;
    private String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    // Геттеры
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword(){return password;}

    // Сеттеры
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String login) { this.login = login; }
}
