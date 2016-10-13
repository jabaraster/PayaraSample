package info.jabara.sandbox.payara_sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.embeddable.BootstrapProperties;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

/**
 * Payara Embedded App.
 */
public class App {

    private static final int HTTP_PORT = 8081;
    private static final int HTTPS_PORT = 8082;
    private static final int COMMAND_PORT = 10001;

    /**
     * @param pArgs -
     */
    public static void main(final String[] pArgs) {

        System.setProperty("java.awt.headless", Boolean.toString(true)); // ヘッドレスにしないとMacにおいてアイコンが起動して面倒 //$NON-NLS-1$
        sendStopCommand(COMMAND_PORT); // 既に起動しているプロセスがあれば停止する.

        try {
            final BootstrapProperties bootstrap = new BootstrapProperties();
            final GlassFishRuntime runtime = GlassFishRuntime.bootstrap(bootstrap);
            final GlassFishProperties glassfishProperties = new GlassFishProperties();
            glassfishProperties.setPort("http-listener", HTTP_PORT); //$NON-NLS-1$
            glassfishProperties.setPort("https-listener", HTTPS_PORT); //$NON-NLS-1$
            final GlassFish glassfish = runtime.newGlassFish(glassfishProperties);
            glassfish.start();

            registerConnectionPool(glassfish);

            glassfish.getDeployer().deploy( //
                    new File("src/main/webapp") // //$NON-NLS-1$
                    ,
                    new String[] { "--name=PayaraSample" // //$NON-NLS-1$
                            , "--contextroot" // //$NON-NLS-1$
                            , "/" }); //$NON-NLS-1$

        } catch (final GlassFishException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void registerConnectionPool(final GlassFish glassfish) {
        // RDBとしてH2を使う場合.
        // glassfish.getCommandRunner().run("create-jdbc-connection-pool" //
        // , "--datasourceclassname=" + JdbcDataSource.class.getName() //
        // , "--restype=" + DataSource.class.getName() //
        // , "--property", "url=jdbc\\:h2\\:" + Environment.getH2DatabasePath() //
        // , connectionPoolName //
        // );

        // RDBとしてPostgreSQL9.4を使う場合.
        // この場合、pom.xmlの下記記述を有効にする必要がある.
        // <dependency>
        // <groupId>org.postgresql</groupId>
        // <artifactId>postgresql</artifactId>
        // <version>9.4-1203-jdbc41</version>
        // <scope>provided</scope>
        // </dependency>
        // glassfish.getCommandRunner().run("create-jdbc-connection-pool" //
        // , "--datasourceclassname=" + PGConnectionPoolDataSource.class.getName() //
        // , "--restype=" + ConnectionPoolDataSource.class.getName() //
        // , "--steadypoolsize=2" //
        // , "--maxpoolsize=10" //
        // , "--poolresize=2" //
        // , "--property", "serverName=192.168.50.13:portNumber=5432:databaseName=app:user=app:password=xxx" //
        // , connectionPoolName //
        // );

        // glassfish.getCommandRunner().run("create-jdbc-resource" //
        // , "--connectionpoolid", connectionPoolName //
        // , "jdbc/App" //
        // );

        startMonitoringStopCommand(COMMAND_PORT, glassfish);
    }

    private static void sendStopCommand(final int pPort) {
        try (final Socket accept = new Socket(InetAddress.getLocalHost(), pPort)) {
            new BufferedWriter(new OutputStreamWriter(accept.getOutputStream())).newLine();
        } catch (@SuppressWarnings("unused") final ConnectException e) {
            // nop
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                TimeUnit.SECONDS.sleep(1); // 別プロセスの停止を少し待つ
            } catch (@SuppressWarnings("unused") final InterruptedException e) {
                // nop
            }
        }
    }

    private static void startMonitoringStopCommand(final int pPort, final GlassFish pGlassfish) {
        final ExecutorService ex = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable pR) {
                final Thread ret = new Thread(pR);
                ret.setDaemon(true);
                return ret;
            }
        });
        ex.execute(new Runnable() {
            @Override
            public void run() {
                try (final ServerSocket server = new ServerSocket(pPort); //
                        final Socket accept = server.accept()) {
                    new BufferedReader(new InputStreamReader(accept.getInputStream())).readLine();
                    pGlassfish.stop();
                } catch (final IOException | GlassFishException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
