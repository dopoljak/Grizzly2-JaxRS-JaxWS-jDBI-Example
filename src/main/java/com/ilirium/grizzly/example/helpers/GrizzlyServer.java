package com.ilirium.grizzly.example.helpers;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.ProcessingException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public class GrizzlyServer
{
    public static HttpServer createHttpServer(final URI uri, final boolean secure, final SSLEngineConfigurator sslEngineConfigurator, final boolean start, final Integer corePoolSize, final Integer maxPoolSize) throws ProcessingException
    {
        final String host = (uri.getHost() == null) ? NetworkListener.DEFAULT_NETWORK_HOST : uri.getHost();
        final int port = (uri.getPort() == -1) ? 80 : uri.getPort();
        
        final HttpServer server = new HttpServer();
        
        final NetworkListener listener = new NetworkListener("grizzly", host, port);
        listener.setSecure(secure);
        
        if (sslEngineConfigurator != null) {
            listener.setSSLEngineConfig(sslEngineConfigurator);
        }

        final TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
        final TCPNIOTransport transport = builder
                .setIOStrategy(SameThreadIOStrategy.getInstance())
                .setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig()
                .setPoolName("Grizzly-worker")
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maxPoolSize))
                .build();
        listener.setTransport(transport);

        server.addListener(listener);

        // Map the path to the processor.
        final ServerConfiguration config = server.getServerConfiguration();
        config.setPassTraceRequest(true);

        if (start) {
            try {
                // Start the server.
                server.start();
            }
            catch (IOException ex) {
                throw new ProcessingException("IOException thrown when trying to start grizzly server", ex);
            }
        }

        return server;
    }
}
