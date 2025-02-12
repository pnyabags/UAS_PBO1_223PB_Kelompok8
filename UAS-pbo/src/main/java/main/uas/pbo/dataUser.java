package main.uas.pbo;

public abstract class dataUser {
    protected int id_user;
    protected String username;
    protected String password;
    protected String jabatan;

    public dataUser(int id_user, String username, String password, String jabatan) {
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

    public abstract void displayRole();
}
