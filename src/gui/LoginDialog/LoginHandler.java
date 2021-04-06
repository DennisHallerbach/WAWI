package gui.LoginDialog;


@FunctionalInterface
public interface LoginHandler {
	void checkLogin(String benutznername , String passwort);
}
