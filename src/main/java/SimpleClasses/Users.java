package SimpleClasses;

public class Users {
   private String user_name;
   private String password;
   private Integer id;
    public Users(String user_name, String password)
    {
        this.user_name = user_name;
        this.password = password;
    }
    public String getUser_name() {
        return user_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
