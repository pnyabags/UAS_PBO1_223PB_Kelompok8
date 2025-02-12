package main.uas.pbo;
public class User {
    private int id_user;
    private String username;
    private String password;
    private String jabatan;    
    
    public User(int id_user, String username, String password, String jabatan) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.jabatan = jabatan;
    }
    public int getIdUser() { return id_user; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getJabatan() { return jabatan; }
}
