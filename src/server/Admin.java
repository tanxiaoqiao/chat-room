package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

//管理员方法
public class Admin extends ServerMethod {

	public Admin(Socket socket) {
		super(socket);

	}

	private void mian() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入指令：1.停止心跳提示 2断开指定客户端连接 ");
		String choose = sc.nextLine();
		if (choose.matches("^[1-2]$")) {
			System.out.println("请输入1-2之间的数字");
			return;
		}
		int i = Integer.parseInt(choose);
		while (true) {

			switch (i) {
			case 1:
				heartBeat(choose);
				break;
			case 2:
				for (String name : onlineUserMap.keySet()) {
					System.out.println("当前在线用户有：" + onlineUserMap.keySet()
							+ "\n" + "请输入要强制下线的用户并以，隔开");
					String line = sc.nextLine();
					String[] arr = line.split(",");
					onlineUserMap.get(Arrays.toString(arr)).close();
					break;
				}
			}
		}
	}

	@Override
	public void heartBeat(String content) {
		System.out.print("");
	}

}
