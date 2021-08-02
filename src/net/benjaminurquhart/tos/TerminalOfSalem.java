package net.benjaminurquhart.tos;

import java.io.EOFException;
import java.io.File;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapDumper;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;

import org.pcap4j.packet.*;

import net.benjaminurquhart.tos.game.ANSI;
import net.benjaminurquhart.tos.game.Game;
import net.benjaminurquhart.tos.game.GamePhase;
import net.benjaminurquhart.tos.handlers.*;

public class TerminalOfSalem {
	
	public static InetAddress TOS_SERVER, TOS_SERVER_IPV6;
	
	static {
		try {
			TOS_SERVER_IPV6 = Inet6Address.getByName("2001:4800:7818:104:be76:4eff:fe04:6c7c");
			TOS_SERVER = Inet4Address.getByName("104.239.145.241");
			
			Security.setProperty("crypto.policy", "unlimited");
			System.setProperty("crypto.policy", "unlimited");
		}
		catch(UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] a) throws Exception {
		System.out.print(ANSI.RESET);
		PrintStream out = System.out;
		int i = 2;
		File directory = new File("saves");
		if(!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(directory, "tos1.pcap"), ng = new File(directory, "tos1.pcapng");
		while(file.exists() || ng.exists()) {
			file = new File(directory, "tos"+i+".pcap");
			ng = new File(directory, "tos"+i+".pcapng");
			i++;
		}
		
		final String filename = file.getAbsolutePath();
		
		PcapDumper dumper;
		PcapHandle handle;
		Arguments args = new Arguments(a);
		System.out.println("Mode: " + args.getMode());
		if(args.getMode() == Mode.LIVE) {
			if(file.exists()) {
				file.delete();
			}
			PcapNetworkInterface captureInterface = Pcaps.getDevByName("any");
			List<PcapNetworkInterface> interfaces = Pcaps.findAllDevs();
			
			for(PcapNetworkInterface iface : interfaces) {
				System.out.printf("%s (%s): %s\n", iface.getName(), iface.getAddresses(), iface.getDescription());
			}
			if(captureInterface == null) {
				if(interfaces.isEmpty()) {
					System.err.println("No capture interfaces found!");
					System.exit(1);
				}
				else {
					System.out.println();
					InetAddress addr;
					selection:
					for(PcapNetworkInterface iface : interfaces) {
						for(PcapAddress address : iface.getAddresses()) {
							addr = address.getAddress();
							System.out.println(iface.getName() + " " + addr);
							if(addr != null && !addr.toString().endsWith(".1") && !addr.toString().startsWith("/fe80:") && !addr.toString().startsWith("/172.") && !addr.toString().equals("/0.0.0.0") && !addr.isLinkLocalAddress() && !addr.isMulticastAddress() && !addr.isLoopbackAddress()) {
								captureInterface = iface;
								break selection;
							}
						}
					}
					System.out.println();
				}
			}
			System.out.printf(
					"Capturing on interface %s (%s): %s...\n", 
					captureInterface.getName(), 
					captureInterface.getAddresses(), 
					captureInterface.getDescription()
			);
			handle = new PcapHandle.Builder(captureInterface.getName())
								   .promiscuousMode(PromiscuousMode.NONPROMISCUOUS)
								   .timestampPrecision(TimestampPrecision.NANO)
								   .timeoutMillis(10)
								   //.bufferSize(1<<16)
								   .build();
			
			dumper = handle.dumpOpen(filename);
			System.out.println("Writing to " + filename);
		}
		else {
			System.out.println("Reading file " + args.getFilename() + "...");
			handle = Pcaps.openOffline(args.getFilename());
			dumper = null;
		}
		InetAddress src, dst;
		IpPacket ipPacket;
		TcpPacket packet;
		Packet tmp;
		
		byte[] data;
		
		Set<Long> clientSeq = new HashSet<>(), serverSeq = new HashSet<>();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if(dumper != null) {
				System.out.printf("\n\n%sSaved session as %s\n", ANSI.RESET, filename);
				dumper.close();
			}
			handle.close();
		}));
		
		Game game = new Game();
		MessageHandler server, client = new ClientMessageHandler(game);
		
		if(args.getMode() == Mode.SUMMARIZE) {
			server = new Summarizer(game);
			System.setOut(new StubbedStream());
		}
		else {
			server = new ServerMessageHandler(game, args);
		}
		Timestamp previous = null, current = null;
		boolean gameStarted = false;
		while(true) {
			try {
				tmp = handle.getNextPacketEx();
				//System.out.println(tmp);
				if(args.getMode() == Mode.REPLAY) {
					current = handle.getTimestamp();
					if(game.getPhase() == GamePhase.PICK_NAME) {
						gameStarted = true;
					}
					if(previous != null && gameStarted) {
						Thread.sleep(current.getTime()-previous.getTime());
					}
					previous = current;
				}
			}
			catch(EOFException e) {
				System.out.println("Reached end of file");
				break;
			}
			catch(PcapNativeException e) {
				e.printStackTrace();
				continue;
			}
			catch(TimeoutException e) {
				//System.out.println(ANSI.GRAY+"Timed out while waiting for packets...");
				//e.printStackTrace(System.out);
				continue;
			}
			ipPacket = tmp.get(IpPacket.class);
			packet = tmp.get(TcpPacket.class);
			if(packet == null || ipPacket == null) {
				continue;
			}
			if(!packet.getHeader().getPsh()) {
				continue;
			}
			try {
				src = ipPacket.getHeader().getSrcAddr();
				dst = ipPacket.getHeader().getDstAddr();
				data = packet.getPayload().getRawData();
			}
			catch(Exception e) {
				System.out.printf("%sAn internal occured while receiving a packet:\n", ANSI.RED);
				e.printStackTrace(System.out);
				System.out.println(ANSI.GRAY);
				continue;
			}
			if(src.equals(TOS_SERVER) || src.equals(TOS_SERVER_IPV6)) {
				if(!serverSeq.add(packet.getHeader().getSequenceNumberAsLong())) {
					System.out.printf("%sIgnoring packet retransmission...\n", ANSI.GRAY);
					continue;
				}
				try {
					server.parseCommands(data);
				}
				catch(Throwable e) {
					System.out.printf("%sAn internal parsing error occured:\n", ANSI.RED);
					e.printStackTrace(System.out);
					System.out.printf("Message: %s%s\n\n", server.convertToString(data, false), ANSI.GRAY);
				}
			}
			else if(dst.equals(TOS_SERVER) || dst.equals(TOS_SERVER_IPV6)) {
				if(!clientSeq.add(packet.getHeader().getSequenceNumberAsLong())) {
					System.out.printf("%sIgnoring packet retransmission...\n", ANSI.GRAY);
					continue;
				}
				try {
					client.parseCommands(data);
				}
				catch(Throwable e) {
					System.out.printf("%sAn internal parsing error occured:\n", ANSI.RED);
					e.printStackTrace(System.out);
					System.out.printf("Message: %s%s\n\n", client.convertToString(data, false), ANSI.GRAY);
				}
			}
			else {
				continue;
			}
			if(args.getMode() == Mode.LIVE) {
				dumper.dump(tmp);
			}
		}
		if(args.getMode() == Mode.SUMMARIZE) {
			System.setOut(out);
			((Summarizer)server).summarize();
		}
	}

}
