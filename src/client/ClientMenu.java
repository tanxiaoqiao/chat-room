package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import bean.ExportObject;

public class ClientMenu {

	private Scanner sc;
	private ObjectOutputStream oos;

	public ClientMenu(Scanner sc, ObjectOutputStream oos) {// 把sc传过来
		super();
		this.sc = sc;
		this.oos = oos;

	}
//===========================登录方法
	public void log(int LOG) throws IOException, InterruptedException {
		while (true) {
			System.out.println("请输入用户名");
			String name = sc.nextLine();
			System.out.println("请输入密码");
			String pwd = sc.nextLine();
			if (name.equals("") || pwd.equals("")) { // 此处不能用等于
				System.out.println("输入内容不能为空~");
				continue;
			}
			ExportObject eo = new ExportObject();
			eo.setNamePwd(name + "," + pwd);
			eo.setType(LOG);
			oos.writeObject(eo);// 穿给服务器
			oos.flush();
			Thread.sleep(300);
	return;
		}
	}
	//===========================注册方法
	public void reg(int REG) throws IOException, InterruptedException {

		while (true) {
			System.out.println("请输入用户名");
			String name = sc.nextLine();
			System.out.println("请输入密码");
			String pwd = sc.nextLine();
			System.out.println("请再次输入密码");
			String dpwd = sc.nextLine();
			if (name.equals("") || pwd.equals("") || dpwd.equals("")) { // 此处不能用等于
				System.out.println("输入内容不能为空~");
				continue;
			}
			if (!pwd.equals(dpwd)) {
				System.out.println("两次密码输入不一致，请重新输入~");
				continue;
			}
			ExportObject eo = new ExportObject();
			eo.setNamePwd(name + "," + pwd);
			eo.setType(REG);
			oos.writeObject(eo);// 传给服务器
			oos.flush();
			Thread.sleep(300);
		return;
		}
	}
	//===========================群聊
	public void chatAll(int CHAT_ALL) throws IOException, InterruptedException {

		System.out.println("请输入内容(退出群聊请输入exit)");
		while (true) {
			String line = sc.nextLine();
			line.replace("\n", "");

			if (line.equals("")) {
				System.out.println("请输入内容");
				Thread.sleep(300);
				continue;
			}
			if (line.equals("exit")) {
				Thread.sleep(300);
				break;
			}
			ExportObject eo = new ExportObject();// 这是一个对象！！！！！！！！！
			eo.setType(CHAT_ALL);
			eo.setContent(line);

			oos.writeObject(eo);
			oos.flush();

		}
	}
	//===========================私聊
	public void chatOne(int CHAT_ONE) throws IOException, InterruptedException {

		System.out.println("请选择私聊用户");
		String name = sc.nextLine();
		System.out.println("请输入内容(退出私聊请输入exit)");
		while (true) {
			String line = sc.nextLine();
			line.replace("\n", "");
			if (line.equals("")) {
				System.out.println("请输入内容");
				Thread.sleep(300);
				break;
			}
			if (line.equals("exit")) {
				Thread.sleep(300);
				break;
			}
			ExportObject eo = new ExportObject();
			eo.setType(CHAT_ONE);
			eo.setNamePwd(name);
			eo.setContent(line);
			oos.writeObject(eo);
			oos.flush();
			continue;
		}
	}
	//===========================匿名聊天
	public void chatRandom(int CHAT_RANDOM) throws InterruptedException,
			IOException {
		System.out.println("请输入内容(退出群聊请输入exit)");
		while (true) {

			String line = sc.nextLine();
			line.replace("\n", "");
			if (line.equals("")) {
				System.out.println("请输入内容");
				Thread.sleep(300);

			}
			if (line.equals("exit")) {
				Thread.sleep(300);
				break;
			}
			ExportObject eo = new ExportObject();
			eo.setType(CHAT_RANDOM);
			eo.setContent(line);
			oos.writeObject(eo);
			oos.flush();

		}
	}
	//===========================查看在线用户
	public void checkOnline(int CHECT_ONLINE) throws IOException,
			InterruptedException {
		ExportObject eo = new ExportObject();
		eo.setType(CHECT_ONLINE);
		oos.writeObject(eo);
		oos.flush();
		Thread.sleep(300);
	}
	//===========================退出系统
	public void extiSystem(int EXIT_SYSTEM) throws IOException {
		ExportObject eo = new ExportObject();
		eo.setType(EXIT_SYSTEM);
		oos.writeObject(eo);
		oos.flush();

	}

}