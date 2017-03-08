package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//=============================������
public class Server {
	public static void main(String[] args) {

		try {
			final ServerSocket server = new ServerSocket(9301);// ����socketʱ����Ҫ��final

			new Thread() {

				public void run() {
					try {
						while (true) {
							Socket socket = server.accept();// ����socketʱ����������Ҫ��final
							Admin admin = new Admin(socket);
							new ServerThread(socket).start();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
			System.out.println("��������ʼ����~");
		} catch (IOException e) {
			// �˴�ʡ�ԣ���Ȼ���ױ���
		}

	}
}
