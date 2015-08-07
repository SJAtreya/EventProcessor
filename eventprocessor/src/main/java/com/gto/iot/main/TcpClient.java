package com.gto.iot.main;

import java.io.IOException;

public class TcpClient {

	public static void sendMessage(String msg) throws IOException {
		TcpClientConnection.getInstance().sendMessage(msg);
	}
}
