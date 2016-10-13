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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;

import org.glassfish.embeddable.BootstrapProperties;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.postgresql.ds.PGConnectionPoolDataSource;

/**
 * Payara Embedded App.
 */
public class WebAppContainer {

    private static final int   HTTPS_PORT   = 8082;
    private static final int   COMMAND_PORT = 10001;

    private final String       connectionPoolProperty;
    private final int          httpPort;

    private volatile GlassFish glassfish;

    /**
     * @param pConnnectionPoolProperty
     * @param pHttpPort
     */
    public WebAppContainer(final String pConnnectionPoolProperty, final int pHttpPort) {
        this.connectionPoolProperty = pConnnectionPoolProperty;
        this.httpPort = pHttpPort;
    }

    /**
     *
     */
    public void start() {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                startCore();
            }
        });
        th.start();
    }

    /**
     *
     */
    public void stop() {
        if (this.glassfish != null) {
            try {
                this.glassfish.stop();
            } catch (final GlassFishException e) {
                Logger.getLogger(WebAppContainer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void registerConnectionPool() throws GlassFishException {
        final String connectionPoolName = "conn-pool";

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
        glassfish.getCommandRunner().run("create-jdbc-connection-pool" //
                , "--datasourceclassname=" + PGConnectionPoolDataSource.class.getName() //
                , "--restype=" + ConnectionPoolDataSource.class.getName() //
                , "--steadypoolsize=2" //
                , "--maxpoolsize=10" //
                , "--poolresize=2" //
                , "--property", this.connectionPoolProperty //
                , connectionPoolName //
        );

        glassfish.getCommandRunner().run("create-jdbc-resource" //
                , "--connectionpoolid", connectionPoolName //
                , "jdbc/App" //
        );
    }

    private void startCore() {
        System.setProperty("java.awt.headless", Boolean.toString(true)); // ヘッドレスにしないとMacにおいてアイコンが起動して面倒 //$NON-NLS-1$

        try {
            sendStopCommand(COMMAND_PORT); // 既に起動しているプロセスがあれば停止する.

            final BootstrapProperties bootstrap = new BootstrapProperties();
            final GlassFishRuntime runtime = GlassFishRuntime.bootstrap(bootstrap);
            final GlassFishProperties glassfishProperties = new GlassFishProperties();
            glassfishProperties.setPort("http-listener", this.httpPort); //$NON-NLS-1$
            // glassfishProperties.setPort("https-listener", HTTPS_PORT); //$NON-NLS-1$
            this.glassfish = runtime.newGlassFish(glassfishProperties);
            glassfish.start();

            registerConnectionPool();

            glassfish.getDeployer().deploy( //
                    new File("src/main/webapp") // //$NON-NLS-1$
                    ,
                    new String[] { "--name=PayaraSample" // //$NON-NLS-1$
                            , "--contextroot" // //$NON-NLS-1$
                            , "/" }); //$NON-NLS-1$

            startMonitoringStopCommand(COMMAND_PORT, glassfish);

        } catch (final GlassFishException e) {
            Logger.getLogger(WebAppContainer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @param pArgs -
     */
    public static void main(final String[] pArgs) {
        new WebAppContainer("serverName=localhost:portNumber=5432:databaseName=app:user=app:password=xxx", 8081)
                .start();
    }

    private static void sendStopCommand(final int pPort) {
        try (final Socket accept = new Socket(InetAddress.getByName("127.0.0.1"), pPort)) {
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
        final Thread th = new Thread(new Runnable() {
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
        th.setDaemon(true);
        th.start();
    }
}
