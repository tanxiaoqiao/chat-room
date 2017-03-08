package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import bean.User;

import server.ServerMethod;

public class FileDemo {

	File sfile = new File("Users.txt");
	List<User> Users = new ArrayList<User>();// ???????????

	public FileDemo() {

	}

	public FileDemo(List<User> Users) {// 构造器 初始化集合内容
		super();

		Users = ServerMethod.Users;
	}

	// ===============加载内容给服务器 输入流
	public List load() {
		File sfile = new File("Users.txt");
		try {
			if (!sfile.exists() || sfile.length() == 0) {
				return Users;
			}
			BufferedReader read = new BufferedReader(new FileReader(sfile));
			String line = null;
			while ((line = read.readLine()) != null) {
				String[] arr = line.split(",");
				String name = arr[0];
				String pwd = arr[1];
				Users.add(new User(name, pwd));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Users;
	}

	public void save(List<User> users) {
		File sfile = new File("Users.txt");
		if (!sfile.exists()) {
			try {
				sfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter write = new BufferedWriter(new FileWriter(sfile));
			for (User u : Users) {
				StringBuilder line = new StringBuilder();
				line = line.append(u.getName()).append(",").append(u.getPwd())
						.append("\n");
				write.write(line.toString());// 需要用toString方法
				write.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}