package com.vtc;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeOfDayServer {
	public static void main(String args[  ]) {
        try { 
            CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder( );

            int port = 55555;  
            if (args.length > 0) port = Integer.parseInt(args[0]);

            SocketAddress localport = new InetSocketAddress(port);

            ServerSocketChannel tcpserver = ServerSocketChannel.open( );
            tcpserver.socket( ).bind(localport);

            DatagramChannel udpserver = DatagramChannel.open( );
            udpserver.socket( ).bind(localport);

            tcpserver.configureBlocking(false);
            udpserver.configureBlocking(false);

            Selector selector = Selector.open( );

            tcpserver.register(selector, SelectionKey.OP_ACCEPT);
            udpserver.register(selector, SelectionKey.OP_READ);

            ByteBuffer receiveBuffer = ByteBuffer.allocate(0);

            for(;;) {
                try { 
                    selector.select( );

                    Calendar calParis = Calendar.getInstance();
                    calParis.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
                    String time = "Time in Paris: " +
                    calParis.get(Calendar.HOUR_OF_DAY) + ":" +
                    calParis.get(Calendar.MINUTE);
                    
                    ByteBuffer response=encoder.encode(CharBuffer.wrap(time));

                    Set keys = selector.selectedKeys( );
                    
                    for(Iterator i = keys.iterator( ); i.hasNext( ); ) {
                        SelectionKey key = (SelectionKey)i.next( );
                        i.remove( );

                        Channel c = (Channel) key.channel( );

                        if (key.isAcceptable( ) && c == tcpserver) { 
                            SocketChannel client = tcpserver.accept( );
                            if (client != null) {
                                client.write(response);  
                                client.close( );         
                            }
                        }
                        else if (key.isReadable( ) && c == udpserver) {
                            SocketAddress clientAddress =
                                udpserver.receive(receiveBuffer);
                            if (clientAddress != null)
                                udpserver.send(response, clientAddress);
                        }
                    }
                }
                catch(java.io.IOException e) {
                    Logger l = Logger.getLogger(TimeOfDayServer.class.getName( ));
                    l.log(Level.WARNING, "IOException in DaytimeServer", e);
                }
                catch(Throwable t) {
                    Logger l = Logger.getLogger(TimeOfDayServer.class.getName( ));
                    l.log(Level.SEVERE, "FATAL error in DaytimeServer", t);
                    System.exit(1);
                }
            }
        }
        catch(Exception e) { 
            System.err.println(e);
            System.exit(1);
        }
    }
}
