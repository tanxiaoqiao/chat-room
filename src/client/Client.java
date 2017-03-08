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

//�ͻ���
public class Client {
	private static final int LOG = 1;// ֻ����������д�������ٷ�������д
	private static final int REG = 2;
	private static final int CHAT_ALL = 3;
	private static final int CHAT_ONE = 4;
	private static final int CHAT_RANDOM = 5;
	private static final int CHECT_ONLINE = 6;
	private static final int EXIT_SYSTEM = 7;
	private static final int HEART_BEAT = 8;

	// ============================�ͻ���
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		// ��Ҫ��final ������
		try {
			Socket socket = new Socket("127.0.0.1", 9301);
			new ReadThread(socket).start(); // �Ķ��߳���Ҫ��static
			OutputStream os = socket.getOutputStream();// ��װ����Ҫ����while����
			// ============������
			ObjectOutputStream oos = new ObjectOutputStream(os);
			// ========�����߳�����
			new HeartBeat(oos).start();
			while (true) {
				System.out.println("������ 1��¼ 2ע��3Ⱥ�� 4˽�� 5�������� 6�鿴�����û� 7�˳� ");
				String line = sc.nextLine();
				// ExportObject eo = new ExportObject();//// ����������ÿ�δ���һ���µģ�������
				ClientMenu cm = new ClientMenu(sc, oos);
				if (!line.matches("^[1-7]$")) {
					System.out.println("�������������1-7֮�������");
					continue;
				}
				int i = Integer.parseInt(line);
				// ===============�˵���
				switch (i) {
				case LOG:
					//��½
					cm.log(LOG);
					break;
				case REG:
					//ע��
					cm.reg(REG);
					break;
				case CHAT_ALL:
					//Ⱥ��
					cm.chatAll(CHAT_ALL);
					break;
				case CHAT_ONE:
					//˽��
					cm.chatOne(CHAT_ONE);
					break;
				case CHAT_RANDOM:
					//��������
					cm.chatRandom(CHAT_RANDOM);
					break;
				case CHECT_ONLINE:
					//�鿴�����û�
					cm.checkOnline(CHECT_ONLINE);
					break;
				case EXIT_SYSTEM:
					//�˳�ϵͳ
					cm.extiSystem(EXIT_SYSTEM);
					System.out.println("�ͻ����ѶϿ�����");
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

	// =============================�Ķ��߳�
	private static class ReadThread extends Thread {// Ϊʲô��static����Ϊ��������static��
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
					if (line.equals("�Զ��˳�ϵͳ")) {
						System.exit(0);						
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// ===================�����߳�
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
					eo.setContent("����");
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
