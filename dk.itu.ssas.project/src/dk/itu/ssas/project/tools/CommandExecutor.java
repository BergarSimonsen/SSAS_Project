package dk.itu.ssas.project.tools;


public class CommandExecutor {

	private final String PASSWORD = "SSAS";
	private final String COMMAND = "sudo -S gnome-terminal -x bash";
	private final String BASH = "bash";
	private final String ARG = "-c";
	
	String[] cmd;
	
	public CommandExecutor() {
		cmd = buildCommand();
		start();
	}
	
	private String[] buildCommand() {
		cmd = new String[3];
		cmd[0] = BASH;
		cmd[1] = ARG;
		cmd[2] = String.format("echo %s | %s", PASSWORD, COMMAND);
		return cmd;
	}
	
	public void start() {
		runCommand(cmd);
	}

	private void runCommand(String[] cmd) {
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}