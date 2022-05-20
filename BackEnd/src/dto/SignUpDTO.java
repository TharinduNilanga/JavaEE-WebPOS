package dto;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class SignUpDTO {
    private String userName;
    private String email;
    private String password;

    public SignUpDTO() {
    }

    public SignUpDTO(String userName, String email, String password) {
        this.setUserName(userName);
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpDTO{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
