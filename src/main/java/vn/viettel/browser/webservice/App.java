package vn.viettel.browser.webservice;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vn.viettel.browser.webservice.global.Variables;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static vn.viettel.browser.webservice.global.Variables.ESNode;
import static vn.viettel.browser.webservice.global.Variables.ESPort;
import static vn.viettel.browser.webservice.global.Variables.clientEls;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ComponentScan
public class App 
{
    public static void main( String[] args ) throws IOException {
        int debug = 1;
        init(debug);
        SpringApplication.run(App.class, args);
    }

    private static void init(int debug) throws IOException {
        FileHandler fh;
        if (debug == 0) {
            fh = new FileHandler("/home/vbrowser/web_service.log", true);
            Settings settings = Settings.builder().put("cluster.name", "sfive")
                    .build();
            clientEls = new PreBuiltTransportClient(settings);
            for (String aESNode : ESNode)
                clientEls = clientEls.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(aESNode), ESPort));
        } else {
            fh = new FileHandler("/home/quytx/web_service.log", true);
            Settings settings = Settings.builder().put("cluster.name", "sfive").build();
            clientEls = new PreBuiltTransportClient(settings);
            for (String aESNode : ESNode)
                clientEls = clientEls.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(aESNode), ESPort));
        }

        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        Logger logger = Logger.getLogger(App.class.getName());
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        Variables.logger = logger;
    }
}
