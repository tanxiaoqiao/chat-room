package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import bean.ExportObject;

//客户端
public class Client {
	private static final int LOG = 1;// 只能在类里面写，不能再方法里面写
	private static final int REG = 2;
	private static final int CHAT_ALL = 3;
	private static final int CHAT_ONE = 4;
	private static final int CHAT_RANDOM = 5;
	private static final int CHECT_ONLINE = 6;
	private static final int EXIT_SYSTEM = 7;
	private static final int HEART_BEAT = 8;

	// ============================客户端
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		// 需要用final 才能用
		try {
			Socket socket = new Socket("127.0.0.1", 9301);
			new ReadThread(socket).start(); // 阅读线程需要加static
			OutputStream os = socket.getOutputStream();// 包装流不要放在while里面
			// ============对象流
			ObjectOutputStream oos = new ObjectOutputStream(os);
			// ========心跳线程启动
			new HeartBeat(oos).start();
			while (true) {
				System.out.println("请输入 1登录 2注册3群聊 4私聊 5匿名聊天 6查看在线用户 7退出 ");
				String line = sc.nextLine();
				// ExportObject eo = new ExportObject();//// 对象流必须每次创建一个新的？？？？
				ClientMenu cm = new ClientMenu(sc, oos);
				if (!line.matches("^[1-7]$")) {
					System.out.println("输入错误，请输入1-7之间的数字");
					continue;
				}
				int i = Integer.parseInt(line);
				// ===============菜单栏
				switch (i) {
				case LOG:
					//登陆
					cm.log(LOG);
					break;
				case REG:
					//注册
					cm.reg(REG);
					break;
				case CHAT_ALL:
					//群聊
					cm.chatAll(CHAT_ALL);
					break;
				case CHAT_ONE:
					//私聊
					cm.chatOne(CHAT_ONE);
					break;
				case CHAT_RANDOM:
					//匿名聊天
					cm.chatRandom(CHAT_RANDOM);
					break;
				case CHECT_ONLINE:
					//查看在线用户
					cm.checkOnline(CHECT_ONLINE);
					break;
				case EXIT_SYSTEM:
					//退出系统
					cm.extiSystem(EXIT_SYSTEM);
					System.out.println("客户端已断开连接");
					System.exit(0);
					break;
				default:
					break;

				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// =============================阅读线程
	private static class ReadThread extends Thread {// 为什么用static，因为主方法是static的
		private Socket socket;

		public ReadThread(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public synchronized void run() {
	
			
			try {
				InputStream is = socket.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line = null;
				while ((line = reader.readLine()) != null) {
					line.replace("\n", "");
					System.out.println(line);
					if (line.equals("自动退出系统")) {
						System.exit(0);						
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// ===================心跳线程
	private static class HeartBeat extends Thread {
		private ObjectOutputStream oos;
		private ExportObject eo;

		public HeartBeat(ObjectOutputStream oos) {
			super();
			this.oos = oos;
		}

		@Override
		public void run() {
			try {
				while (true) {

					eo = new ExportObject();
					eo.setType(HEART_BEAT);
					eo.setContent("心跳");
					oos.writeObject(eo);
					sleep(2000);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
