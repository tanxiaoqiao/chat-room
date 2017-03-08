package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ExportObject;
import bean.User;

import file.FileDemo;

//判断方法
public class ServerMethod {
	private Socket socket;
	private String currentName;
	static FileDemo sfile = new FileDemo();// 需要加static
	public static List<User> Users = sfile.load();
	static Map<String, Socket> onlineUserMap = new HashMap<String, Socket>();// 在线集合，static之后就公用一个map
	static Map<String, Socket> groupUserMap = new HashMap<String, Socket>();// 小组集合
	boolean isLog = false;
	int num = 0;

	// ====================登录方法
	public void log(String name, String pwd, OutputStream os)
			throws IOException {
		while (num < 3) {
			for (User u : Users) {
				if (name.equals(u.getName())) {
					if (onlineUserMap.containsKey(name)) {
						os.write("该用户已经登入\n".getBytes());
						return;
					}

					if (onlineUserMap.containsValue(onlineUserMap
							.get(currentName))) {
						os.write("当前客户端已有用户使用，请用使用其他客户端登录\n".getBytes());
						isLog = true;
						return;
					}

					if (u.getName().equals(name) && u.getPwd().equals(pwd)) {
						os.write("登录成功\n".getBytes());
						currentName = name;
						onlineUserMap.put(name, socket);// 登录成功，放入集合
						String content = userInf(1);
						System.out.println(content);
						isLog = true;
						return;
					} else {
						os.write(("用户名或者密码错误\n").getBytes());
						num++;
						os.write(("密码输错" + num + "次，还有" + (3 - num) + "机会\n")
								.getBytes());
						
						if (num == 3) {
							os.write("自动退出系统\n".getBytes());
							socket.close();
							return;
						}
						continue;
					}
				}
			}
			return;
		}

	}

	// ======================注册方法
	public void reg(String name, String pwd, OutputStream os)
			throws IOException {

		for (User u : Users) {
			if (u.getName().equals(name)) {
				os.write("该用户已经被注册\n".getBytes());
				return;
			}

		}
		os.write("注册成功\n".getBytes());
		Users.add(new User(name, pwd));
		sfile.save(Users);// 保存到文本
	}

	// ==============查看用户信息
	public String userInf(int i) throws IOException {

		socket.getInetAddress();
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
		String now = sf.format(date);
		if (i == 1) {
			return ("IP:" + socket.getInetAddress() + "用户：" + currentName + " "
					+ now + "登录");
		}
		if (i == 2) {
			return ("IP:" + socket.getInetAddress() + "用户：" + currentName + " "
					+ now + "下线");
		}
		return null;
	}

	// =============群聊
	public void chatAll(String content, OutputStream os) throws IOException {// 之前没有群发，因为socket用的static属性，并且没有传值进来

		if (!isLog) {
			os.write("当前客户端没有登录，请输入exit退出重新登录\n".getBytes());
			return;
		}
		content.replace("\n", "");
		if (content.equals("exit")) {
			os.write("退出群聊\n".getBytes());
			return;
		}
		System.out.println(content + "服务器接受");// 有问题了。不会变化了
		for (String name : onlineUserMap.keySet()) {
			if (!name.equals(currentName)) {
				content += "\n";
				onlineUserMap.get(name).getOutputStream()
						.write((currentName + "发来群消息:" + content).getBytes());

			}

		}
	}

	// ==============私聊
	public boolean chatOne(String name, String content, OutputStream os)
			throws IOException {
		if (!isLog) {
			os.write("当前客户端没有登录，请输入exit退出重新登录\n".getBytes());
			return false;
		}

		if (Users.contains(name)) {
			os.write("没有该用户,请输入exit退出重新选择\n".getBytes());
			return false;
		}
		if (!onlineUserMap.containsKey(name)) {
			os.write("该用户不在线，请输入exit退出重新选择\n".getBytes());
			return false;
		}
		for (String num : onlineUserMap.keySet()) {
			if (num.equals(name)) {
				content = currentName + "发来消息：" + content + "\n";
				onlineUserMap.get(num).getOutputStream()
						.write(content.getBytes());
			}
		}
		return true;
	}

	// =============查看在线用户
	public void checkOnline(ExportObject eo, OutputStream os)
			throws IOException {
		if (!isLog) {
			os.write("当前客户端没有登录\n".getBytes());
			return;
		}
		if (onlineUserMap.isEmpty()) {
			os.write(("当前没有用户登录\n").getBytes());
			return;
		}
		os.write(("当前在线的用户有：" + onlineUserMap.keySet() + "\n").getBytes());
	}

	// ===================匿名聊天
	public void chatRandom(String content, OutputStream os) throws IOException {
		String NAME = randomName();
		if (!isLog) {
			os.write("当前客户端没有登录，请输入exit退出重新登录\n".getBytes());
			return;
		}

		content.replace("\n", "");
		if (content.equals("exit")) {
			os.write("退出群聊\n".getBytes());
			return;
		}
		for (String name : onlineUserMap.keySet()) {
			if (!name.equals(currentName)) {
				content += "\n";
				onlineUserMap.get(name).getOutputStream()
						.write((NAME + "发来群消息:" + content).getBytes());

			}

		}
	}

	// =======================匿名方法
	public String randomName() {
		String n1 = "“二狗子”";
		String n2 = "“张全蛋”";
		String n3 = "“轩辕铁柱”";
		String n4 = "“欧阳翠花”";
		String n5 = "“失散多年的亲爹”";
		String n6 = "“诈尸用户”";
		String n7 = "“好多人”";
		String n8 = "“智障青年”";
		String n9 = "“野生奥特曼”";
		String[] arr = { n1, n2, n3, n4, n5, n6, n7, n8, n9 };
		int i = (int) (Math.random() * 9);
		String randomName = arr[i];
		return randomName;

	}

	// ================退出系统
	public void exitSystem(OutputStream os) throws IOException {
		if (!isLog) {
			os.write("当前客户端没有登录\n".getBytes());
			return;
		}

		onlineUserMap.remove(currentName);
		String content = userInf(2);
		System.out.println(content);
	}

	// ===================心跳
	public void heartBeat(String content) {
		System.out.println(socket.getInetAddress() + content);
	}

	public ServerMethod(Socket socket) {
		super();
		this.socket = socket;
	}

}