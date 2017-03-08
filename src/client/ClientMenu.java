package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import bean.ExportObject;

public class ClientMenu {

	private Scanner sc;
	private ObjectOutputStream oos;

	public ClientMenu(Scanner sc, ObjectOutputStream oos) {// ��sc������
		super();
		this.sc = sc;
		this.oos = oos;

	}
//===========================��¼����
	public void log(int LOG) throws IOException, InterruptedException {
		while (true) {
			System.out.println("�������û���");
			String name = sc.nextLine();
			System.out.println("����������");
			String pwd = sc.nextLine();
			if (name.equals("") || pwd.equals("")) { // �˴������õ���
				System.out.println("�������ݲ���Ϊ��~");
				continue;
			}
			ExportObject eo = new ExportObject();
			eo.setNamePwd(name + "," + pwd);
			eo.setType(LOG);
			oos.writeObject(eo);// ����������
			oos.flush();
			Thread.sleep(300);
	return;
		}
	}
	//===========================ע�᷽��
	public void reg(int REG) throws IOException, InterruptedException {

		while (true) {
			System.out.println("�������û���");
			String name = sc.nextLine();
			System.out.println("����������");
			String pwd = sc.nextLine();
			System.out.println("���ٴ���������");
			String dpwd = sc.nextLine();
			if (name.equals("") || pwd.equals("") || dpwd.equals("")) { // �˴������õ���
				System.out.println("�������ݲ���Ϊ��~");
				continue;
			}
			if (!pwd.equals(dpwd)) {
				System.out.println("�����������벻һ�£�����������~");
				continue;
			}
			ExportObject eo = new ExportObject();
			eo.setNamePwd(name + "," + pwd);
			eo.setType(REG);
			oos.writeObject(eo);// ����������
			oos.flush();
			Thread.sleep(300);
		return;
		}
	}
	//===========================Ⱥ��
	public void chatAll(int CHAT_ALL) throws IOException, InterruptedException {

		System.out.println("����������(�˳�Ⱥ��������exit)");
		while (true) {
			String line = sc.nextLine();
			line.replace("\n", "");

			if (line.equals("")) {
				System.out.println("����������");
				Thread.sleep(300);
				continue;
			}
			if (line.equals("exit")) {
				Thread.sleep(300);
				break;
			}
			ExportObject eo = new ExportObject();// ����һ�����󣡣���������������
			eo.setType(CHAT_ALL);
			eo.setContent(line);

			oos.writeObject(eo);
			oos.flush();

		}
	}
	//===========================˽��
	public void chatOne(int CHAT_ONE) throws IOException, InterruptedException {

		System.out.println("��ѡ��˽���û�");
		String name = sc.nextLine();
		System.out.println("����������(�˳�˽��������exit)");
		while (true) {
			String line = sc.nextLine();
			line.replace("\n", "");
			if (line.equals("")) {
				System.out.println("����������");
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
	//===========================��������
	public void chatRandom(int CHAT_RANDOM) throws InterruptedException,
			IOException {
		System.out.println("����������(�˳�Ⱥ��������exit)");
		while (true) {

			String line = sc.nextLine();
			line.replace("\n", "");
			if (line.equals("")) {
				System.out.println("����������");
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
	//===========================�鿴�����û�
	public void checkOnline(int CHECT_ONLINE) throws IOException,
			InterruptedException {
		ExportObject eo = new ExportObject();
		eo.setType(CHECT_ONLINE);
		oos.writeObject(eo);
		oos.flush();
		Thread.sleep(300);
	}
	//===========================�˳�ϵͳ
	public void extiSystem(int EXIT_SYSTEM) throws IOException {
		ExportObject eo = new ExportObject();
		eo.setType(EXIT_SYSTEM);
		oos.writeObject(eo);
		oos.flush();

	}

}