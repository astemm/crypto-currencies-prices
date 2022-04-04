
/*
package com.koblan.cryptoCurrencyTool;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.config.RuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;

import java.io.IOException;

@Configuration
public class MongoDbTestConfiguration {

    private static final String IP = "localhost";
    private static final int PORT = 28017;
    @Bean
    public IMongodConfig embeddedMongoConfiguration() throws IOException {

        return new MongodConfigBuilder()
                .version(Version.V3_2_0)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();
    }

    /*
    @Bean
    public IRuntimeConfig runtimeConfig() {

             return new RuntimeConfigBuilder()
                     .processOutput(ProcessOutput.getDefaultInstanceSilent())
                .build();
    }

} */
