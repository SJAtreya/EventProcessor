package com.gto.iot.main;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import com.gto.iot.dto.IgnitionStream;
import com.gto.iot.dto.Location;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.eventprocessor.AbstractClassifier;
import com.gto.iot.eventprocessor.LastKnownLocation;
import com.gto.iot.eventprocessor.TimeEventClassifier;
import com.gto.iot.eventprocessor.EventClassifier;

/**
 * Handles a server-side channel.
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<String> { // (1)

	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		System.out.println(msg);
		String[] array = msg.toString().split(",");
		if (array[0].equalsIgnoreCase("T")) {
			SpeedStream stream = new SpeedStream((String) msg);
			TimeEventClassifier.getInstance().identifyEvent(stream);
			LastKnownLocation.getInstance().put(stream.getVin(),
					new Location(stream.getLat(), stream.getLon()));
		} else if (array[0].equalsIgnoreCase("E")) {
			EventClassifier.getInstance().identifyEvent(
					new SpeedStream((String) msg));
		} else {
			System.out.println("Wrong input format");
		}

		try {
			TcpClient.sendMessage((String) msg);
			System.out.println("Forked message to tcp server!");
		} catch (IOException e) {
			// Ignore
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send greeting for a new connection.
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()
				+ "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(msg);

	}
}